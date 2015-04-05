package assettocorsa.servermanager.model;

import javafx.collections.ObservableList;

/**
 * Created by pete on 4/04/2015.
 */
public interface RaceUIModel {

    public void load();

    public void store();

    public ObservableList<RaceSettings> raceSettingsListProperty();

    /**
     * Binding to this data will allow the observers to be notified of all changes to race and server settings.
     * It will also update when a new selection of race, re-binding is not necessary.
     *
     * @return an object that can be bound to get updates of race and server settings.
     */
    public RaceSettings selectedRaceSettingsProperty();

    /**
     * Create new raceSettings and add it to the raceSettingsList.
     * Creation of the new race settings does not automaticly the currentRaceSettings.
     */
    public void createNewRaceSettings();

    /**
     * RaceSettings parameter is set to become the current RaceSettings.
     * The RaceSettings parameter must be in the list of RaceSettings. If it is not in the list then no selection occurs.
     * @param raceSettings that will be set as the current RaceSettings if this paramter s in the raceSettingsList.
     */
    public void selectRaceSettings(RaceSettings raceSettings);
}
