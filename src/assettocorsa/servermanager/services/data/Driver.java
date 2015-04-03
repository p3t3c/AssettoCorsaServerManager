package assettocorsa.servermanager.services.data;

/**
 * Created by pete on 31/03/2015.
 */
public class Driver {

    private String driverName;

    private String guid;

    public Driver(String driverName, String guid) {
        this.driverName = driverName;
        this.guid = guid;
    }

    public String getGuid() {
        return guid;
    }

    public String getDriverName() {
        return driverName;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "guid='" + guid + '\'' +
                ", driverName='" + driverName + '\'' +
                '}';
    }
}
