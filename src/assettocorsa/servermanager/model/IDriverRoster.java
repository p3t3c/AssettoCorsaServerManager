package assettocorsa.servermanager.model;

import javafx.collections.ObservableList;

/**
 * Created by pete on 31/03/2015.
 * TODO separate this into the service parts pure model and UI mediation ObservaleList etc.
 */
public interface IDriverRoster {
    /**
     * Load Drivers from storage.
     */
    void load();

    /**
     * Persist the driver data to storage.
     */
    void store();

    ObservableList<DriverOnRoster> getListOfDrivers();

    /**
     * Create a new Driver object add it to the end of the list.
     * The new driver object will have empty values.
     * @return the newly created and added Driver
     */
    DriverOnRoster createNewDriver();

    /**
     * Remove the Driver from the model store.
     * @param driver to remove.
     */
    void removeDriver(DriverOnRoster driver);


}
