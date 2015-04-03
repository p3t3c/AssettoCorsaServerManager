package assettocorsa.servermanager.services.data;

public class DriverBuilder {
    private String driverName;
    private String guid;

    public DriverBuilder setDriverName(String driverName) {
        this.driverName = driverName;
        return this;
    }

    public DriverBuilder setGuid(String guid) {
        this.guid = guid;
        return this;
    }

    /**
     * Copies fields of the input into this builder.
     * @param driver
     * @return
     */
    public DriverBuilder setDriver(Driver driver) {
        this.driverName = driver.getDriverName();
        this.guid = driver.getGuid();
        return this;
    }

    public Driver createDriver() {
        return new Driver(driverName, guid);
    }
}