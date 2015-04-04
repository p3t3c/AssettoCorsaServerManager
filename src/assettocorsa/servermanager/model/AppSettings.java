package assettocorsa.servermanager.model;

import javafx.beans.property.StringProperty;

/**
 * Created by pete on 3/04/2015.
 */
public interface AppSettings {
    public void loadAppSettings();
    public void storeAppSettings();

    StringProperty assettoCorsaDirectoryProperty();
    StringProperty exportDirectoryProperty();

}
