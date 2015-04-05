package assettocorsa.servermanager.model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by pete on 5/04/2015.
 */
public class RaceUIModelImpl implements RaceUIModel {
    private final ObservableList<RaceSettings> raceSettingsObseravleList;
    private final RaceSettings selectedRaceSettings;
    private RaceSettings currentRaceSettings;

    public RaceUIModelImpl() {
        // Exctactor function is probably going to get fairly large.
        raceSettingsObseravleList = FXCollections.observableArrayList(raceSettings -> new Observable[]{raceSettings.raceNameProperty(), raceSettings.serverNameProperty()});

        selectedRaceSettings = new RaceSettings();
        currentRaceSettings = selectedRaceSettings; // initial value but not bound, wait for user(UI) to choose.
    }

    @Override
    public void load() {

    }

    @Override
    public void store() {

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
            selectedRaceSettings.raceNameProperty().unbindBidirectional(currentRaceSettings);
            selectedRaceSettings.raceNameProperty().bindBidirectional(nextRaceSettings.raceNameProperty());

            selectedRaceSettings.serverNameProperty().unbindBidirectional(currentRaceSettings);
            selectedRaceSettings.serverNameProperty().bindBidirectional(nextRaceSettings.serverNameProperty());

            currentRaceSettings = nextRaceSettings;
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
