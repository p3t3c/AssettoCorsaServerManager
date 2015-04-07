package assettocorsa.servermanager.model;

import assettocorsa.servermanager.services.DriverRosterServiceTestImpl;
import assettocorsa.servermanager.services.IDriverRosterService;
import assettocorsa.servermanager.services.data.Driver;
import assettocorsa.servermanager.services.data.DriverBuilder;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by pete on 30/03/2015.
 */
public class DriverRosterImpl implements DriverRoster {
    private ObservableList<DriverOnRoster> listOfDrivers;
    private final IDriverRosterService driverRosterSerice;

    public DriverRosterImpl() {
        driverRosterSerice = new DriverRosterServiceTestImpl(); // TODO Injection required
        // Extractor function binds the name and guid properties to the ObserableList, it will change when they do
        listOfDrivers = FXCollections.observableArrayList(driver -> new Observable[]{driver.driverNameProperty(), driver.guidProperty()});
    }

    @Override
    public void load() {
        driverRosterSerice.loadDrivers();

        List<Driver> driverList = new ArrayList<Driver>();
        driverRosterSerice.fetchListOfDrivers(driverList);
        List<DriverOnRoster> driversOnRoster = driverList.stream().map(driver -> new DriverOnRoster(driver.getDriverName(), driver.getGuid())).collect(toList());
        listOfDrivers.clear();
        listOfDrivers.addAll(driversOnRoster);
    }

    @Override
    public void store() {
        DriverBuilder builder = new DriverBuilder();
        List<Driver> driverDataToStore = listOfDrivers.stream().map(driverOnRoster -> {
            builder.setDriverName(driverOnRoster.getDriverName());
            builder.setGuid(driverOnRoster.getGuid());
            return builder.createDriver();
        }).collect(toList());
        driverRosterSerice.setListOfDrivers(driverDataToStore);
        driverRosterSerice.storeDrivers();
    }

    @Override
    public ObservableList<DriverOnRoster> getListOfDrivers() {
        return listOfDrivers;
    }

    @Override
    public DriverOnRoster createNewDriver() {
        DriverOnRoster newDriver = new DriverOnRoster();
        listOfDrivers.add(newDriver);
        return newDriver;
    }

    @Override
    public void removeDriver(DriverOnRoster driver) {
        listOfDrivers.remove(driver);
    }
}
