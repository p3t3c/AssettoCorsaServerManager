package assettocorsa.servermanager.ui.listview;

import assettocorsa.servermanager.model.DriverOnRoster;
import javafx.scene.control.ListCell;

/**
 * Created by pete on 30/03/2015.
 */
public class DriverNameListCell extends ListCell<DriverOnRoster> {

    @Override
    protected void updateItem(DriverOnRoster driver, boolean empty) {
        super.updateItem(driver, empty);

        if (empty || driver == null) {
            setText(null);
        } else {
            setText(driver.getDriverName());
        }
    }
}
