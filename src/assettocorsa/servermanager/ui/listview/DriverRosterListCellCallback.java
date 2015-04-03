package assettocorsa.servermanager.ui.listview;

import assettocorsa.servermanager.model.DriverOnRoster;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Created by pete on 30/03/2015.
 */
public class DriverRosterListCellCallback implements Callback<ListView<DriverOnRoster>, ListCell<DriverOnRoster>> {

    @Override
    public ListCell<DriverOnRoster> call(ListView<DriverOnRoster> param) {
        return new DriverNameListCell();
    }
}
