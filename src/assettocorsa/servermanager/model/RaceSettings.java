package assettocorsa.servermanager.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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

    /**
     * The TrackModel that has been selected for this race.
     */
    private ObjectProperty<TrackModel> selectedTrackModelProperty;

    public RaceSettings() {
        raceName = new SimpleStringProperty();
        serverName = new SimpleStringProperty();
        selectedTrackModelProperty = new SimpleObjectProperty<TrackModel>();

        // TODO remove listener debug only
        selectedTrackModelProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("New Track Model selected " + newValue.trackNameProperty().getValue());
            }
        });
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

    public ObjectProperty<TrackModel> selectedTrackModelProperty() {
        return selectedTrackModelProperty;
    }

    public void cloneFrom(RaceSettings otherRaceSettings) {
        raceName.setValue(otherRaceSettings.getRaceName());
        serverName.setValue(otherRaceSettings.getServerName());
        selectedTrackModelProperty.setValue(otherRaceSettings.selectedTrackModelProperty().getValue());
    }

    public void setRaceName(String raceName) {
        this.raceName.setValue(raceName);
    }

    /**
     * This operation is used to indicate a UI selection has occured and properties may need to handle selection.
     * Perform selection operations on any properties that need to be selected on the UI.
     */
    public void performSelection() {
        TrackModel selectedTrackModel = selectedTrackModelProperty.getValue();
        if (selectedTrackModel != null) {
            selectedTrackModel.setSelected(true);
        }
    }
}
