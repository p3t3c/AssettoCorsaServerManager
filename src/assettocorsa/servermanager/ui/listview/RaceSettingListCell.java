package assettocorsa.servermanager.ui.listview;

import assettocorsa.servermanager.model.RaceSettings;
import assettocorsa.servermanager.model.RaceUIModel;
import javafx.scene.control.ListCell;

/**
 * Created by pete on 5/04/2015.
 */
public class RaceSettingListCell extends ListCell<RaceSettings> {
    @Override
    protected void updateItem(RaceSettings raceSettings, boolean empty) {
        super.updateItem(raceSettings, empty);

        if (empty) {
            setText("");
        } else {
            setText(raceSettings.getRaceName());
        }
    }
}
