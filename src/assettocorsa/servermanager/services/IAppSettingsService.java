package assettocorsa.servermanager.services;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by pete on 3/04/2015.
 */
public interface IAppSettingsService {
    public void loadAppSettings() throws IOException, IOException;
    public void storeAppSettings() throws IOException;

    public String getAssettoCorsaDirectory();
    public void setAssettoCorsaDirectorty(String dir);

    public String getExportDirectory();
    public void setExportDirectorty(String dir);

}
