package assettocorsa.servermanager.model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by pete on 5/04/2015.
 */
public class RaceUIModelImpl implements RaceUIModel {
    private final ObservableList<RaceSettings> raceSettingsObseravleList;
    private final TrackUIModel trackUIModel;
    /**
     * Selected Race Settings is the RaceSettings that is modified by the UI.
     * It is a clone from one of the items on the list. The data is only put back into the raceSettingsObservableList
     * when store operation occurs.
     */
    private RaceSettings selectedRaceSettings;
    private RaceSettings currentRaceSettings;

    public RaceUIModelImpl(TrackUIModel trackUIModel) {
        // Exctactor function is probably going to get fairly large.
        // RaceUIModel.class.getFields() might work see http://stackoverflow.com/questions/19977600/javafx-8-custom-listview-cells-its-evil right at the end of the answer
        raceSettingsObseravleList = FXCollections.observableArrayList(raceSettings -> new Observable[]{raceSettings.raceNameProperty(), raceSettings.serverNameProperty()});
        this.trackUIModel = trackUIModel;

        initaliseRaceSettings();
    }

    private void initaliseRaceSettings() {
        selectedRaceSettings = new RaceSettings();
        selectedRaceSettings.selectedTrackModelProperty().bindBidirectional(trackUIModel.selectedTrackModelProperty());

        currentRaceSettings = selectedRaceSettings; // initial value but not bound, wait for user(UI) to choose.
    }

    @Override
    public void load() {

    }

    @Override
    public void store() {
        currentRaceSettings.cloneFrom(selectedRaceSettings);
        // TODO send data to service layer to store.
    }

    @Override
    public RaceSettings createNewRaceSettings() {
        RaceSettings raceSettings = new RaceSettings();
        raceSettings.raceNameProperty().set("New Race");

        raceSettingsObseravleList.add(raceSettings);

        return raceSettings;
    }

    @Override
    public void selectRaceSettings(RaceSettings nextRaceSettings) {
        if (raceSettingsObseravleList.contains(nextRaceSettings)) {
            selectedRaceSettings.cloneFrom(nextRaceSettings);
            selectedRaceSettings.performSelection();

            currentRaceSettings = nextRaceSettings;
        }
    }

    @Override
    public void cloneRaceSettings(RaceSettings raceSettings) {
        int index = raceSettingsObseravleList.indexOf(raceSettings);
        RaceSettings clonedRaceSettings = new RaceSettings();
        clonedRaceSettings.cloneFrom(raceSettings);
        clonedRaceSettings.raceNameProperty().set("Copy of " + clonedRaceSettings.getRaceName());
        raceSettingsObseravleList.add(index, clonedRaceSettings);
    }

    @Override
    public void deleteRaceSettings(RaceSettings raceSettings) {
        if (raceSettingsObseravleList.contains(raceSettings)) {
            raceSettingsObseravleList.remove(raceSettings);
            // Unbind anything in race settings
            raceSettings.selectedTrackModelProperty().unbindBidirectional(trackUIModel.selectedTrackModelProperty());

            if (!raceSettingsObseravleList.isEmpty()) {
                selectRaceSettings(raceSettingsObseravleList.get(0));
                store();
            } else {
                initaliseRaceSettings();
            }
        }
    }

    @Override
    public ObservableList<RaceSettings> raceSettingsListProperty() {
        return raceSettingsObseravleList;
    }

    @Override
    public RaceSettings selectedRaceSettingsProperty() {
        return selectedRaceSettings;
    }
}
