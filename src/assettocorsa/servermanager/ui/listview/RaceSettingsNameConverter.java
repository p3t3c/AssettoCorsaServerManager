package assettocorsa.servermanager.ui.listview;

import assettocorsa.servermanager.model.RaceSettings;
import javafx.util.StringConverter;

/**
 * Created by pete on 7/04/2015.
 */
public class RaceSettingsNameConverter extends StringConverter<RaceSettings> {
    @Override
    public String toString(RaceSettings raceSettings) {
        return raceSettings.getRaceName();
    }

    @Override
    /**
     * Here we are in the weird sitation of creating a RaceSettings just to pass this new value back to the ListView.onEditCommit handler.
     * We will implement that to only pay attention to the race name.
     * <p>
     *     This seemed to be less code to write utilising the TextFieldListCell and this implmentation, rather than writting a custom ListCell implementation that used a text box.
     * </p>
     */
    public RaceSettings fromString(String raceName) {
        RaceSettings partialRaceSettings = new RaceSettings();
        partialRaceSettings.setRaceName(raceName);
        return partialRaceSettings;
    }
}
