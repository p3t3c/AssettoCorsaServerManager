package assettocorsa.servermanager;

import assettocorsa.servermanager.model.DriverOnRoster;
import assettocorsa.servermanager.model.DriverRoster;
import assettocorsa.servermanager.model.IDriverRoster;
import assettocorsa.servermanager.ui.listview.DriverRosterListCellCallback;
import com.sun.istack.internal.Nullable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    public ListView<DriverOnRoster> driverRosterListView;
    public TextField driverInfoNameTextField;
    public TextField driverInfoGuidTextField;
    /**
     * Data storage handler for the driver roster.
     * Injecting this would be best.
     */
    private IDriverRoster driverRoster;

    @Nullable
    private DriverOnRoster selectedDriverOnDriverRoster;

    /**
     * Driver info panel text field enable status.
     * Both text fields enableProperty shall be bound to this property.
     */
    private SimpleBooleanProperty driverInfoInputsEnabled;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialisePropertiesAndBindings();
        driverRoster = new DriverRoster();
        driverRoster.load();
        // TODO Try catch IOException on failed load goes here

        ObservableList<DriverOnRoster> driverList = driverRoster.getListOfDrivers();

        intialiseDriverRosterListView(driverList);
    }


    private void initialisePropertiesAndBindings() {
        selectedDriverOnDriverRoster = null;

        driverInfoInputsEnabled = new SimpleBooleanProperty(true);
        driverInfoNameTextField.editableProperty().bindBidirectional(driverInfoInputsEnabled);
        driverInfoGuidTextField.editableProperty().bindBidirectional(driverInfoInputsEnabled);
    }

    /**
     * Initialised the driver roster as multi select. With a custom cell factory which shall render the driver name.
     *
     * @param driverList
     */
    private void intialiseDriverRosterListView(ObservableList<DriverOnRoster> driverList) {
        driverRosterListView.setCellFactory(new DriverRosterListCellCallback());
        driverRosterListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        driverRosterListView.setItems(driverList);

    }

    /**
     * Acivated from new button in driver roster
     * @param actionEvent
     */
    public void newDriverInRosterAction(ActionEvent actionEvent) {
        unbindDriverSelectedOnRoster();
        selectedDriverOnDriverRoster = driverRoster.createNewDriver();
        driverRosterListView.getSelectionModel().clearSelection();
        driverRosterListView.getSelectionModel().select(selectedDriverOnDriverRoster);
        bindFirstDriverSelectedOnRoster();
    }

    /**
     * Activated from delete button in driver roster.
     * @param actionEvent
     */
    public void deleteDriverInRoster(ActionEvent actionEvent) {
        driverRosterListView.getSelectionModel().getSelectedItems().forEach(driver -> driverRoster.removeDriver(driver));
    }

    public void saveDriverRoster(ActionEvent actionEvent) {
        driverRoster.store();
    }

    /**
     * Executed on selection of driverRosterListView
     * @param event
     */
    public void updateDriverInfoFromRoster(Event event) {
        ObservableList<DriverOnRoster> selectedDrivers = driverRosterListView.getSelectionModel().getSelectedItems();

        unbindDriverSelectedOnRoster();

        if (selectedDrivers.size() == 1) {
            bindFirstDriverSelectedOnRoster();
        } else {
            disableDriverInfo();
        }
    }

    private void disableDriverInfo() {
        driverInfoInputsEnabled.setValue(false);
        selectedDriverOnDriverRoster = null;
    }

    private void bindFirstDriverSelectedOnRoster() {
        ObservableList<DriverOnRoster> selectedDrivers = driverRosterListView.getSelectionModel().getSelectedItems();
        driverInfoInputsEnabled.setValue(true);
        selectedDriverOnDriverRoster = selectedDrivers.get(0);
        driverInfoNameTextField.textProperty().bindBidirectional(selectedDriverOnDriverRoster.driverNameProperty());
        driverInfoGuidTextField.textProperty().bindBidirectional(selectedDriverOnDriverRoster.guidProperty());
    }

    private void unbindDriverSelectedOnRoster() {
        if (selectedDriverOnDriverRoster != null) {
            driverInfoNameTextField.textProperty().unbindBidirectional(selectedDriverOnDriverRoster.driverNameProperty());
            driverInfoGuidTextField.textProperty().unbindBidirectional(selectedDriverOnDriverRoster.guidProperty());
        }
    }

}
