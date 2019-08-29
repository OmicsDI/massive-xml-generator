package uk.ac.ebi.ddi.task.massivexmlgenerator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Yasset Perez-Riverol (ypriverol@gmail.com)
 * @date 06/11/15
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Publication {

    @JsonProperty("id")
    private String id;

    @JsonProperty("authors")
    private String authors;

    @JsonProperty("title")
    private String title;

    @JsonProperty("citation")
    private String citation;

    @JsonProperty("abstract")
    private String publicationAbstract;

    @JsonProperty("pmid")
    private Integer pubmedId;

    @JsonProperty("pmcid")
    private String pmcid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }

    public String getPublicationAbstract() {
        return publicationAbstract;
    }

    public void setPublicationAbstract(String publicationAbstract) {
        this.publicationAbstract = publicationAbstract;
    }

    public Integer getPubmedId() {
        return pubmedId;
    }

    public void setPubmedId(Integer pubmedId) {
        this.pubmedId = pubmedId;
    }

    public String getPmcid() {
        return pmcid;
    }

    public void setPmcid(String pmcid) {
        this.pmcid = pmcid;
    }
}
