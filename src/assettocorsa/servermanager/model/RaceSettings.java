package assettocorsa.servermanager.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by pete on 4/04/2015.
 */
public class RaceSettings {

    /**
     * Name to be displayed on a list of races.
     */
    private StringProperty raceName;

    /**
     * Server configuration, the name of the server
     */
    private StringProperty serverName;

    RaceSettings() {
        raceName = new SimpleStringProperty();
        serverName = new SimpleStringProperty();
    }

    public String getRaceName() {
        return raceName.get();
    }

    public StringProperty raceNameProperty() {
        return raceName;
    }

    public String getServerName() {
        return serverName.get();
    }

    public StringProperty serverNameProperty() {
        return serverName;
    }

    public void cloneFrom(RaceSettings otherRaceSettings) {
        raceName.setValue(otherRaceSettings.getRaceName());
        serverName.setValue(otherRaceSettings.getServerName());
    }
}
