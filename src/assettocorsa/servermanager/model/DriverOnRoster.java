package assettocorsa.servermanager.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by pete on 30/03/2015.
 */
public class DriverOnRoster {
    private StringProperty driverName;
    /**
     * Making GUID a string for the moment.
     * So far I have only seen these as very long numbers.
     */
    private StringProperty guid;

    /**
     * Default is for blank name and guid
     */
    public DriverOnRoster() {
        driverName = new SimpleStringProperty();
        guid = new SimpleStringProperty();
    }

    DriverOnRoster(String newDriverName, String newGuid) {
        driverName = new SimpleStringProperty(newDriverName);
        guid = new SimpleStringProperty(newGuid);

        // TODO remove this it is only debug
        driverName.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("driver "+ observable +  " name changed from " + oldValue + " to " + newValue);
            }
        });
    }

    public String getDriverName() {
        return driverName.get();
    }

    public StringProperty driverNameProperty() {
        return driverName;
    }

    public String getGuid() {
        return guid.get();
    }

    public StringProperty guidProperty() {
        return guid;
    }
}
