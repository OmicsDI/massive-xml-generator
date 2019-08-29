package uk.ac.ebi.ddi.task.massivexmlgenerator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Yasset Perez-Riverol (ypriverol@gmail.com)
 * @date 18/05/2015
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MassiveDatasetSummaryMassive extends MassiveAbstractDataset {

    @JsonProperty("dataset")
    private String id;

    @JsonProperty("datasetNum")
    private String datasetNum;

    @JsonProperty("site")
    private String site;

    @JsonProperty("flowname")
    private String flowName;

    @JsonProperty("createdMills")
    private String createdMills;

    @JsonProperty("fileCount")
    private Integer fileCount;

    @JsonProperty("fileSizeKB")
    private Long fileSize;

    @JsonProperty("status")
    private String status;

    @JsonProperty("hash")
    private String hash;

    @JsonProperty("id")
    private String ids;

    public String getModification() {
        return modification;
    }

    public void setModification(String modification) {
        this.modification = modification;
    }

    @JsonProperty("modification")
    private String modification;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatasetNum() {
        return datasetNum;
    }

    public void setDatasetNum(String datasetNum) {
        this.datasetNum = datasetNum;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getCreatedMills() {
        return createdMills;
    }

    public void setCreatedMills(String createdMills) {
        this.createdMills = createdMills;
    }

    public Integer getFileCount() {
        return fileCount;
    }

    public void setFileCount(Integer fileCount) {
        this.fileCount = fileCount;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }



    @Override
    public String toString() {
        return "MassiveDatasetSummaryMassive{" +
                "id='" + id + '\'' +
                ", datasetNum='" + datasetNum + '\'' +
                ", site='" + site + '\'' +
                ", flowName='" + flowName + '\'' +
                ", createdMills='" + createdMills + '\'' +
                ", fileCount='" + fileCount + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", status='" + status + '\'' +
                ", hash='" + hash + '\'' +
                ", task='" + getTask() + '\'' +
                ", ids='" + ids + '\'' +
                '}';
    }
}
