package assettocorsa.servermanager.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by pete on 30/03/2015.
 * Guessing at the moment how this might work
 */
public class DriverInRace {
    private ObjectProperty<DriverOnRoster> driver;
    private StringProperty model;
    private StringProperty skin;


    DriverInRace(DriverOnRoster driver) {
        this.driver = new SimpleObjectProperty<DriverOnRoster>(driver);
        model = new SimpleStringProperty();
        skin = new SimpleStringProperty();
    }
}
