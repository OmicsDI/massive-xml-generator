package uk.ac.ebi.ddi.task.massivexmlgenerator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.ddi.api.readers.model.IAPIDataset;
import uk.ac.ebi.ddi.api.readers.utils.Constants;
import uk.ac.ebi.ddi.ddidomaindb.dataset.DSField;
import uk.ac.ebi.ddi.task.massivexmlgenerator.utils.Utilities;
import uk.ac.ebi.ddi.xml.validator.utils.OmicsType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Yasset Perez-Riverol (ypriverol@gmail.com)
 * @date 06/11/15
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MassiveDatasetDetail extends MassiveAbstractDataset implements IAPIDataset {

    private static final Logger LOGGER = LoggerFactory.getLogger(MassiveDatasetDetail.class);

    @JsonProperty("description")
    private String description;

    @JsonProperty("dataset_id")
    private String id;

    @JsonProperty("publications")
    private Publication[] publications;

    @JsonProperty("pxaccession")
    private String pxAccession;

    @JsonProperty("convertedandcomputable")
    private String converted;

    @JsonProperty("subscriptsions")
    private String subscriptions;

    @JsonProperty("filesize")
    private String fileSize;

    @JsonProperty("has_access")
    private String hasAccess;

    @JsonProperty("filecount")
    private String fileCount;

    @JsonProperty("keywords")
    private String keywords;


    @JsonProperty("ftp")
    private String ftp;

    @JsonProperty("modifications")
    private String modification;

    private List<String> datasetPaths;


    @Override
    public String getDescription() {
        if (description != null && !description.equalsIgnoreCase(Constants.MASSIVE_STRING_NULL)) {
            return description;
        }
        return Constants.EMPTY_STRING;
    }

    @Override
    public String getIdentifier() {
        return id;
    }

    @Override
    public String getName() {
        return getTitle();
    }

    @Override
    public String getDataProtocol() {
        return Constants.EMPTY_STRING;
    }

    @Override
    public String getPublicationDate() {
        if (getCreated() != null && !getCreated().isEmpty()) {
            DateFormat df1 = new SimpleDateFormat("MMM. d, yyyy, h:mm a");
            try {
                return df1.parse(getCreated()).toString();
            } catch (ParseException e) {
                LOGGER.debug(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Map<String, String> getOtherDates() {
        return Collections.emptyMap();
    }

    @Override
    public String getSampleProcotol() {
        return Constants.EMPTY_STRING;
    }

    @Override
    public Set<String> getOmicsType() {
        Set<String> omics = new HashSet<>();
        if (getRepository().equalsIgnoreCase(Constants.GNPS)) {
            omics.add(OmicsType.METABOLOMICS.getName());
        } else if (getRepository().equalsIgnoreCase(Constants.MASSIVE)) {
            omics.add(OmicsType.PROTEOMICS.getName());
        }
        return omics;
    }

    @Override
    public String getRepository() {
        if (getName() != null && getName().length() > 0) {
            if (getName().contains(Constants.GNPS)) {
                return Constants.GNPS;
            }
        }
        return Constants.MASSIVE;
    }

    @Override
    public String getFullLink() {
        return Constants.MASSIVE_URL + getTask();
    }

    @Override
    public Set<String> getInstruments() {
        return Utilities.trimProperty(getInstrument());
    }

    @Override
    public Set<String> getSpecies() {
        return Utilities.trimPropertyFromBytes(getStringSpecies());
    }

    @Override
    public Set<String> getCellTypes() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getDiseases() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getTissues() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getSoftwares() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getSubmitter() {
        return getLabHead();
    }

    @Override
    public Set<String> getSubmitterEmails() {
        return getLabHeadMail();
    }

    @Override
    public Set<String> getSubmitterAffiliations() {
        return getLabHeadAffiliation();
    }

    @Override
    public Set<String> getSubmitterKeywords() {
        return Utilities.trimProperty(keywords);
    }

    @Override
    public Set<String> getLabHead() {
        Set<String> labNames = new HashSet<>();
        if (getPrincipalInvestigator() != null && getPrincipalInvestigator().length > 0) {
            Arrays.asList(getPrincipalInvestigator()).forEach(s -> {
                if (s != null && s.getName() != null && s.getName().length() > 0) {
                    labNames.add(s.getName().trim());
                }
            });
        }
        return labNames;
    }

    @Override
    public Set<String> getLabHeadMail() {
        Set<String> labEmails = new HashSet<>();
        if (getPrincipalInvestigator() != null && getPrincipalInvestigator().length > 0) {
            Arrays.asList(getPrincipalInvestigator()).forEach(s -> {
                if (s != null && s.getEmail() != null && s.getEmail().length() > 0) {
                    labEmails.add(s.getEmail().trim());
                }
            });
        }
        return labEmails;
    }

    @Override
    public Set<String> getLabHeadAffiliation() {
        Set<String> labAffiliations = new HashSet<>();
        if (getPrincipalInvestigator() != null && getPrincipalInvestigator().length > 0) {
            Arrays.asList(getPrincipalInvestigator()).forEach(s -> {
                if (s != null && s.getInstitution() != null && s.getInstitution().length() > 0) {
                    labAffiliations.add(s.getInstitution().trim());
                }
            });
        }
        return labAffiliations;
    }

    @Override
    public Set<String> getDatasetFiles() {
        Set<String> datasetFiles = new HashSet<>();
        if (ftp != null && ftp.length() > 0) {
            datasetFiles.add(ftp.trim());
        }
        if (datasetPaths != null && datasetPaths.size() > 0) {
            datasetPaths.forEach(s -> datasetFiles.add(s.trim()));
            return datasetFiles;
        }
        return datasetFiles;
    }

    @Override
    public Map<String, Set<String>> getCrossReferences() {
        Map<String, Set<String>> crossReferences = new HashMap<>();
        if (publications != null && publications.length > 0) {
            Arrays.stream(publications).filter(reference -> reference.getPubmedId() != null).forEach(reference -> {
                Set<String> values = new HashSet<>();
                if (crossReferences.containsKey(DSField.CrossRef.PUBMED.getName())) {
                    values = crossReferences.get(DSField.CrossRef.PUBMED.getName());
                }
                values.add(reference.getPubmedId().toString());
                crossReferences.put(DSField.CrossRef.PUBMED.getName(), values);
            });
        }
        return crossReferences;
    }

    @Override
    public Map<String, Set<String>> getOtherAdditionals() {
        Map<String, Set<String>> additionals = new HashMap<>();
        if (modification != null && modification.length() > 0) {
            Set<String> modifications = new HashSet<>();
            String[] modArray = modification.split(Constants.MASSIVE_INFO_SEPARATOR);
            Arrays.asList(modArray).forEach(s -> modifications.add(s.trim()));
            additionals.put(DSField.Additional.PTM_MODIFICATIONS.getName(), modifications);
        }

        if (pxAccession != null && pxAccession.length() > 0
                && !pxAccession.equalsIgnoreCase(Constants.MASSIVE_STRING_NULL)) {
            Set<String> accessions = new HashSet<>();
            accessions.add(pxAccession);
            additionals.put(DSField.Additional.SECONDARY_ACCESSION.getName(), accessions);
        }

        if (fileSize != null && fileSize.length() > 0) {
            Set<String> fileSizes = new HashSet<>();
            fileSizes.add(fileSize);
            additionals.put(DSField.Additional.FILE_SIZE.getName(), fileSizes);
        }

        if (fileCount != null && fileCount.length() > 0) {
            Set<String> fileCounts = new HashSet<>();
            fileCounts.add(fileCount);
            additionals.put(DSField.Additional.FILE_SIZE.getName(), fileCounts);
        }
        //Add only the publications without pubmed ID.
        if (publications != null && publications.length > 0) {
            Set<String> citations = new HashSet<>();
            Arrays.asList(publications).forEach(s -> {
                if (s.getPubmedId() == null && s.getPmcid() == null) {
                    String citation = (s.getTitle() != null) ? s.getTitle() : "";
                    citation = (s.getAuthors() != null) ? citation + ". " + s.getAuthors() : citation;
                    citation = (s.getCitation() != null) ? citation + ". " + s.getCitation() : citation;
                    citations.add(citation);

                }
            });
            additionals.put(DSField.Date.PUBLICATION.getName(), citations);
        }
        return additionals;
    }


    public void setDataFilePaths(List<String> dataFilePaths) {
        this.datasetPaths = dataFilePaths;
    }

    @Override
    public String toString() {
        return "MassiveDatasetDetail{" +
                "description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", publications=" + Arrays.toString(publications) +
                ", pxAccession='" + pxAccession + '\'' +
                ", converted='" + converted + '\'' +
                ", subscriptions='" + subscriptions + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", hasAccess='" + hasAccess + '\'' +
                ", fileCount='" + fileCount + '\'' +
                ", keywords='" + keywords + '\'' +
                ", ftp='" + ftp + '\'' +
                '}';
    }


}
