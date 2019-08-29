package uk.ac.ebi.ddi.task.massivexmlgenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.ac.ebi.ddi.api.readers.utils.Constants;
import uk.ac.ebi.ddi.api.readers.utils.Transformers;
import uk.ac.ebi.ddi.ddifileservice.services.IFileSystem;
import uk.ac.ebi.ddi.ddifileservice.type.ConvertibleOutputStream;
import uk.ac.ebi.ddi.task.massivexmlgenerator.configuration.MassiveXmlTaskProperties;
import uk.ac.ebi.ddi.task.massivexmlgenerator.filters.DatasetFilter;
import uk.ac.ebi.ddi.task.massivexmlgenerator.filters.DatasetSummaryExceptUserFilter;
import uk.ac.ebi.ddi.task.massivexmlgenerator.filters.DatasetSummaryTitleFilter;
import uk.ac.ebi.ddi.task.massivexmlgenerator.filters.DatasetSummaryTrancheFilter;
import uk.ac.ebi.ddi.task.massivexmlgenerator.model.MassiveDatasetDetail;
import uk.ac.ebi.ddi.task.massivexmlgenerator.model.MassiveDatasetSummaryMassive;
import uk.ac.ebi.ddi.task.massivexmlgenerator.services.MassiveService;
import uk.ac.ebi.ddi.xml.validator.parser.marshaller.OmicsDataMarshaller;
import uk.ac.ebi.ddi.xml.validator.parser.model.Database;
import uk.ac.ebi.ddi.xml.validator.parser.model.Entry;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class MassiveXmlGeneratorApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(MassiveDatasetSummaryMassive.class);

    @Autowired
    private MassiveService massiveService;

    @Autowired
    private MassiveXmlTaskProperties taskProperties;

    @Autowired
    private IFileSystem fileSystem;

    private List<Entry> entries = new ArrayList<>();

    private DatasetFilter<MassiveDatasetSummaryMassive> datasetFilter = new DatasetFilter<>();

    public MassiveXmlGeneratorApplication() {
        datasetFilter.addFilter(new DatasetSummaryExceptUserFilter<>("tranche_mbraga"));
        datasetFilter.addFilter(new DatasetSummaryTrancheFilter<>());
        datasetFilter.addFilter(new DatasetSummaryTitleFilter<>());
    }

    public static void main(String[] args) {
        SpringApplication.run(MassiveXmlGeneratorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        fileSystem.cleanDirectory(taskProperties.getOutputDir());
        List<MassiveDatasetSummaryMassive> dataSetSummaries = massiveService.getAllDatasets();
        AtomicInteger fileCount = new AtomicInteger(0);
        for (MassiveDatasetSummaryMassive summary : dataSetSummaries) {
            if (summary.getTask() == null || summary.getFileCount() < 1 || !datasetFilter.valid(summary)) {
                continue;
            }
            if (!massiveService.getRepository(summary).equalsIgnoreCase(taskProperties.getDatabase())) {
                continue;
            }
            MassiveDatasetDetail datasetDetail = massiveService.getDataset(summary.getTask());
            if (datasetDetail != null && datasetDetail.getIdentifier() != null) {
                if (summary.getCreated() != null) {
                    datasetDetail.setCreated(summary.getCreated());
                }

                if (datasetDetail.getSpecies() != null) {
                    Entry entry = Transformers.transformAPIDatasetToEntry(datasetDetail);
                    entries.add(entry);
                    if (entries.size() % taskProperties.getEntriesPerFile() == 0) {
                        writeDatasetsToFile(entries, entries.size(), fileCount.getAndIncrement());
                    }
                }
            }
        }
        writeDatasetsToFile(entries, entries.size(), fileCount.getAndIncrement());
    }

    private void writeDatasetsToFile(List<Entry> entries, int total, int fileCount) throws IOException {
        if (entries.size() < 1) {
            return;
        }

        String releaseDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        ConvertibleOutputStream outputStream = new ConvertibleOutputStream();
        try (Writer w = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            OmicsDataMarshaller mm = new OmicsDataMarshaller();

            Database database = new Database();
            if (taskProperties.getDatabase().equalsIgnoreCase(Constants.GNPS)) {
                database.setDescription(Constants.GNPS_DESCRIPTION);
            } else if (taskProperties.getDatabase().equalsIgnoreCase(Constants.MASSIVE)) {
                database.setDescription(Constants.MASSIVE_DESCRIPTION);
            } else {
                throw new RuntimeException("Unable to find description for " + taskProperties.getDatabase());
            }
            database.setName(taskProperties.getDatabase());
            database.setRelease(releaseDate);
            database.setEntries(entries);
            database.setEntryCount(total);
            mm.marshall(database, w);
        }

        String filePath = taskProperties.getOutputDir() + "/" + taskProperties.getPrefix() + fileCount + ".xml";
        LOGGER.info("Attempting to write data to {}", filePath);
        fileSystem.saveFile(outputStream, filePath);
        entries.clear();
    }
}
