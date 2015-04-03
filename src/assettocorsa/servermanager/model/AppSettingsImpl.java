package assettocorsa.servermanager.model;

import assettocorsa.servermanager.services.AppSettingsServiceImpl;
import assettocorsa.servermanager.services.IAppSettingsService;
import assettocorsa.servermanager.services.data.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;

/**
 * Created by pete on 3/04/2015.
 */
public class AppSettingsImpl implements AppSettings {

    private final IAppSettingsService appSettingsService;
    private final StringProperty assettoCorsaDirectoryProperty;
    private final StringProperty exportDirectoryProperty;

    public AppSettingsImpl() {
        appSettingsService = new AppSettingsServiceImpl(); // TODO Injection required
        assettoCorsaDirectoryProperty = new SimpleStringProperty();
        exportDirectoryProperty = new SimpleStringProperty();

    }

    @Override
    public void loadAppSettings() {
        try {
            appSettingsService.loadAppSettings();
            assettoCorsaDirectoryProperty.setValue(appSettingsService.getAssettoCorsaDirectory());
            exportDirectoryProperty.setValue(appSettingsService.getExportDirectory());
        } catch (IOException e) {
            System.err.println("Exception loading app settings");
            e.printStackTrace();
            // TODO log and update app status
        }
    }

    @Override
    public void storeAppSettings() {
        try {
            appSettingsService.storeAppSettings();
        } catch (IOException e) {
            System.err.println("Exception loading app settings");
            e.printStackTrace();
            // TODO log and update app status
        }
    }

    @Override
    public StringProperty assettoCorsaDirectoryProperty() {
        return assettoCorsaDirectoryProperty;
    }

    @Override
    public StringProperty exportDirectoryProperty() {
        return exportDirectoryProperty;
    }
}
