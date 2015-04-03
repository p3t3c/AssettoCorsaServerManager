package assettocorsa.servermanager.services;

import assettocorsa.servermanager.services.data.AppSettingsData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * Created by pete on 3/04/2015.
 */
public class AppSettingsServiceImpl implements IAppSettingsService {
    public static final String ASSETTO_CORSA_DEFAULT_DIR = "C:\\Program Files (x86)\\Steam\\SteamApps\\common\\assettocorsa";
    private static final String DEFAULT_EXPORT_DIR = System.getProperty("user.home" + File.separator+ "Documents");
    private final String applicationSettingsDirectory;
    private AppSettingsData settings;

    public AppSettingsServiceImpl() {
        /* This call gets the location where the program is run from. Will use this to store the settings */
        applicationSettingsDirectory = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        settings = new AppSettingsData();
    }

    @Override
    public void loadAppSettings() throws IOException {
        File settingsFile = new File(applicationSettingsDirectory);
        if (settingsFile.exists() && settingsFile.canRead()) {
            Reader reader = new FileReader(settingsFile);
            Gson gson = new GsonBuilder().create();
            settings = gson.fromJson(reader, AppSettingsData.class);
            reader.close();
        } else {
            loadDefaultAppSettings();
        }
    }

    private void loadDefaultAppSettings() {
        settings.setAssettoCorsaDirectory(ASSETTO_CORSA_DEFAULT_DIR);
        settings.setExportDirectory(DEFAULT_EXPORT_DIR);
    }

    @Override
    public void storeAppSettings() throws IOException {
        File settingsFile = new File(applicationSettingsDirectory);

            Writer writer = new FileWriter(settingsFile);
            Gson gson = new GsonBuilder().create();
            gson.toJson(settings, writer);
            writer.close();

    }

    @Override
    public String getAssettoCorsaDirectory() {
        return settings.getAssettoCorsaDirectory();
    }

    @Override
    public void setAssettoCorsaDirectorty(String dir) {
        settings.setAssettoCorsaDirectory(dir);
    }

    @Override
    public String getExportDirectory() {
        return settings.getExportDirectory();
    }

    @Override
    public void setExportDirectorty(String dir) {
        settings.setExportDirectory(dir);
    }
}
