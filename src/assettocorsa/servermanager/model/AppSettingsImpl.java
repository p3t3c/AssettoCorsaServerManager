package assettocorsa.servermanager.model;

import assettocorsa.servermanager.services.AppSettingsServiceImpl;
import assettocorsa.servermanager.services.IAppSettingsService;
import assettocorsa.servermanager.services.data.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


import javax.swing.event.ChangeEvent;
import java.io.IOException;

/**
 * Created by pete on 3/04/2015.
 */
public class AppSettingsImpl implements AppSettings {

    private final IAppSettingsService appSettingsService;
    private final StringProperty assettoCorsaDirectoryProperty;
    private final StringProperty exportDirectoryProperty;
    private final AutoSaveListener autoSaveListener;

    public AppSettingsImpl() {
        appSettingsService = new AppSettingsServiceImpl(); // TODO Injection required
        assettoCorsaDirectoryProperty = new SimpleStringProperty();
        exportDirectoryProperty = new SimpleStringProperty();
        autoSaveListener = new AutoSaveListener();
    }

    @Override
    public void loadAppSettings() {
        disableAutoSave();
        try {
            appSettingsService.loadAppSettings();
            assettoCorsaDirectoryProperty.setValue(appSettingsService.getAssettoCorsaDirectory());
            exportDirectoryProperty.setValue(appSettingsService.getExportDirectory());
        } catch (IOException e) {
            System.err.println("Exception loading app settings");
            e.printStackTrace();
            // TODO log and update app status
        }
        enableAutoSave();
    }


    /**
     * Double notification won't occur from the UI side
     */
    private void enableAutoSave() {
        assettoCorsaDirectoryProperty.addListener(autoSaveListener);
        exportDirectoryProperty.addListener(autoSaveListener);
    }

    /**
     * Double notification won't occur from the UI side
     */
    private void disableAutoSave() {
        assettoCorsaDirectoryProperty.removeListener(autoSaveListener);
        exportDirectoryProperty.removeListener(autoSaveListener);
    }



    @Override
    public void storeAppSettings() {
        try {
            appSettingsService.setAssettoCorsaDirectorty(assettoCorsaDirectoryProperty.getValue());
            appSettingsService.setExportDirectorty(exportDirectoryProperty.get());
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

    /**
     * Property add listener has a nasty little case of dynamic polymorphism with differnt listener types.
     */
    private final class AutoSaveListener implements ChangeListener {

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            // TODO address the fact that typingin a text box causes this to be called once for each key stroke.
            storeAppSettings();
        }
    }
}
