package assettocorsa.servermanager.services;

import assettocorsa.servermanager.services.data.Driver;

import java.util.List;

/**
 * Created by pete on 31/03/2015.
 */
public interface IDriverRosterService {

    /**
     * Load Driver from storage.
     */
    void loadDrivers();

    /**
     * Store drivers.
     */
    void storeDrivers();

    /**
     * Fill the list provided with the list of drivers.
     * If the load from disk has be performed it will be the contents read from disk.
     * @param driverList
     */
    void fetchListOfDrivers(List<Driver> driverList);

    /**
     * Assign the list of drivers from the List provided.
     * Contents of the List are copied
     * @param driverList
     */
    void setListOfDrivers(List<Driver> driverList);
}
