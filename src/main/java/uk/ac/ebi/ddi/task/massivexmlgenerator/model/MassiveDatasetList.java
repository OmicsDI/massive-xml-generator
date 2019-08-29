package uk.ac.ebi.ddi.task.massivexmlgenerator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Yasset Perez-Riverol (ypriverol@gmail.com)
 * @date 18/05/2015
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MassiveDatasetList {

    @JsonProperty("datasets")
    private MassiveDatasetSummaryMassive[] datasets;

    public MassiveDatasetSummaryMassive[] getDatasets() {
        return datasets;
    }

    public void setDatasets(MassiveDatasetSummaryMassive[] datasets) {
        this.datasets = datasets;
    }
}
