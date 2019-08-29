package uk.ac.ebi.ddi.task.massivexmlgenerator.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uk.ac.ebi.ddi.api.readers.utils.Constants;
import uk.ac.ebi.ddi.task.massivexmlgenerator.model.MassiveDatasetDetail;
import uk.ac.ebi.ddi.task.massivexmlgenerator.model.MassiveDatasetList;
import uk.ac.ebi.ddi.task.massivexmlgenerator.model.MassiveDatasetSummaryMassive;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MassiveService {

    private static final String MASSIVE_ENDPOINT = "http://massive.ucsd.edu";

    private static final Logger LOGGER = LoggerFactory.getLogger(MassiveService.class);

    private static final int BUFFER_LIMIT = 1024 * 1024;

    private RestTemplate restTemplate = new RestTemplate();

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<MassiveDatasetSummaryMassive> getAllDatasets() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(MASSIVE_ENDPOINT)
                .path("/ProteoSAFe")
                .path("/datasets_json.jsp");
        URI uri = builder.build().encode().toUri();
        try {
            ResponseEntity<String> res = restTemplate.getForEntity(uri, String.class);
            MassiveDatasetList datasetList = objectMapper.readValue(res.getBody(), MassiveDatasetList.class);
            if (datasetList != null && datasetList.getDatasets() != null) {
                return Arrays.asList(datasetList.getDatasets());
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred when fetching all datasets, ", e);
        }
        return new ArrayList<>();
    }

    /**
     * This function provides a way to retrieve the information of a dataset from Massive
     * Specially the metadata.
     *
     * @param datasetId the id of the dataset
     * @return the MassiveDatasetDetail
     */
    public MassiveDatasetDetail getDataset(String datasetId) throws IOException {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(MASSIVE_ENDPOINT)
                .path("/ProteoSAFe")
                .path("/MassiveServlet")
                .queryParam("task", datasetId)
                .queryParam("function", "massiveinformation");

        URI uri = builder.build().encode().toUri();
        ResponseEntity<String> res = restTemplate.getForEntity(uri, String.class);
        return objectMapper.readValue(res.getBody(), MassiveDatasetDetail.class);
    }

    public String getRepository(MassiveDatasetSummaryMassive massive) {
        if (massive.getTitle() != null && massive.getTitle().length() > 0) {
            if (massive.getTitle().contains(Constants.GNPS)) {
                return Constants.GNPS;
            }
        }
        return Constants.MASSIVE;
    }
}
