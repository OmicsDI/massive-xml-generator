package uk.ac.ebi.ddi.task.massivexmlgenerator.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("massive")
public class MassiveXmlTaskProperties {
    private int entriesPerFile = 30;
    private String prefix;
    private String outputDir;
    private String database;

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getEntriesPerFile() {
        return entriesPerFile;
    }

    public void setEntriesPerFile(int entriesPerFile) {
        this.entriesPerFile = entriesPerFile;
    }
}
