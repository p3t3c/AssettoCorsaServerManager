package assettocorsa.servermanager.ui.listview;

import assettocorsa.servermanager.model.RaceSettings;
import assettocorsa.servermanager.model.RaceUIModel;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 * Created by pete on 5/04/2015.
 */
public class RaceSettingListCellCallback implements javafx.util.Callback<ListView<RaceSettings>, ListCell<RaceSettings>> {
    @Override
    public ListCell call(ListView<RaceSettings> param) {
        return new RaceSettingListCell();
    }
}
