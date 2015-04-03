package assettocorsa.servermanager.services.data;

/**
 * Created by pete on 3/04/2015.
 * Holds various settings for the server manager application itself.
 */
public class AppSettingsData {
    private String assettoCorsaDirectory;
    private String exportDirectory;

    public String getAssettoCorsaDirectory() {
        return assettoCorsaDirectory;
    }

    public void setAssettoCorsaDirectory(String assettoCorsaDirectory) {
        this.assettoCorsaDirectory = assettoCorsaDirectory;
    }

    public String getExportDirectory() {
        return exportDirectory;
    }

    public void setExportDirectory(String exportDirectory) {
        this.exportDirectory = exportDirectory;
    }
}
