package assettocorsa.servermanager.services;

import assettocorsa.servermanager.services.data.Driver;
import assettocorsa.servermanager.services.data.DriverBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pete on 31/03/2015.
 */
public class DriverRosterServiceTestImpl implements IDriverRosterService {
    List<Driver> driverList;

    public DriverRosterServiceTestImpl() {
        driverList = new ArrayList<Driver>();
    }

    @Override
    public void loadDrivers() {
        driverList.add(new Driver("Alice", "0"));
        driverList.add(new Driver("Bob", "1"));
        driverList.add(new Driver("Carl", "2"));
        driverList.add(new Driver("Dave", "3"));
        driverList.add(new Driver("Eric", "4"));
        driverList.add(new Driver("Frank", "5"));
        driverList.add(new Driver("Grant", "6"));
        driverList.add(new Driver("Henry", "7"));
        driverList.add(new Driver("Irene", "8"));
        driverList.add(new Driver("Janice", "9"));
        driverList.add(new Driver("Kate", "10"));
        driverList.add(new Driver("Louise", "11"));
        driverList.add(new Driver("Mary", "12"));
        driverList.add(new Driver("Nancy", "13"));
        driverList.add(new Driver("Oliver", "14"));
        driverList.add(new Driver("Pete", "15"));
        driverList.add(new Driver("Quincy", "16"));
        driverList.add(new Driver("Ray", "17"));
        driverList.add(new Driver("Sam", "18"));
        driverList.add(new Driver("Tracy", "19"));
        driverList.add(new Driver("Urslua", "20"));
        driverList.add(new Driver("Veronica", "21"));
        driverList.add(new Driver("William", "22"));
        driverList.add(new Driver("Xavier", "23"));
        driverList.add(new Driver("Yannick", "24"));
        driverList.add(new Driver("Zaphod", "25"));

    }

    @Override
    public void storeDrivers() {
        System.out.println("Would store");
        driverList.stream().forEach(System.out::println);
    }

    @Override
    public void fetchListOfDrivers(List<Driver> outputDriverList) {
        DriverBuilder builder = new DriverBuilder();
        for (Driver driver : driverList) {
            outputDriverList.add(builder.setDriver(driver).createDriver());
        }
    }

    @Override
    public void setListOfDrivers(List<Driver> inputDriverList) {
        DriverBuilder builder = new DriverBuilder();
        driverList.clear();
        for (Driver driver : inputDriverList) {
            driverList.add(builder.setDriver(driver).createDriver());
        }
    }
}
