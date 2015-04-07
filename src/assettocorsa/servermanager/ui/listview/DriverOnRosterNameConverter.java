package assettocorsa.servermanager.ui.listview;

import assettocorsa.servermanager.model.DriverOnRoster;
import javafx.util.StringConverter;

/**
 * Used to convert the DriverOnRoster to a string. This string is the driverName.
 *
 * Created by pete on 7/04/2015.
 */
public class DriverOnRosterNameConverter extends StringConverter<DriverOnRoster> {
    @Override
    public String toString(DriverOnRoster driver) {
        return driver.getDriverName();
    }

    @Override
    /**
     * Editing not supported. Null will always be returned.
     */
    public DriverOnRoster fromString(String string) {
        return null;
    }
}
