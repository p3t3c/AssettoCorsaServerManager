package assettocorsa.servermanager;

import assettocorsa.servermanager.model.*;
import assettocorsa.servermanager.ui.listview.DriverRosterListCellCallback;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    public ListView<DriverOnRoster> driverRosterListView;
    public TextField driverInfoNameTextField;
    public TextField driverInfoGuidTextField;
    public Button acLocationButton;
    public TextField outputLocationTextField;
    public TextField acLocationTextField;
    /**
     * Added the rootPane in order to get access to it's parent, the Stage.
     */
    public BorderPane rootPane;
    /**
     * Data storage handler for the driver roster.
     * Injecting this would be best.
     */
    private DriverRoster driverRoster;

    private DriverOnRoster selectedDriverOnDriverRoster;

    /**
     * Driver info panel text field enable status.
     * javafx.scene.control.TextField Both text fields enableProperty shall be bound to this property.
     */
    private SimpleBooleanProperty driverInfoInputsEnabled;
    private AppSettings appSettings;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initialisePropertiesAndBindings();

        driverRoster = new DriverRosterImpl();
        driverRoster.load();
        // TODO Try catch IOException on failed load should go into the driverRoster
        ObservableList<DriverOnRoster> driverList = driverRoster.getListOfDrivers();


        // TODO inject this
        appSettings = new AppSettingsImpl();
        appSettings.loadAppSettings();

        intialiseDriverRosterListView(driverList);
        initaliseBindingsToAppSettings(appSettings);
    }

    /**
     * Bindings to the AppSettings Property to the UI
     *
     * @param appSettings
     */
    private void initaliseBindingsToAppSettings(AppSettings appSettings) {
        acLocationTextField.textProperty().bindBidirectional(appSettings.assettoCorsaDirectoryProperty());
        outputLocationTextField.textProperty().bindBidirectional(appSettings.exportDirectoryProperty());
    }


    /**
     * Bindings between UI Components
     */
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
     *
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
     *
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
     *
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

    /**
     * Called App settings to set the output dir
     *
     * @param actionEvent
     */
    public void selectOutputDir(ActionEvent actionEvent) {
        appSettingsDirChooser("Select Output Folder", appSettings.exportDirectoryProperty());
    }

    /**
     * Called from app settings to set the assetto corsa dir
     *
     * @param actionEvent
     */
    public void selectAcLocation(ActionEvent actionEvent) {
        appSettingsDirChooser("Select Assetto Corsa Folder", appSettings.assettoCorsaDirectoryProperty());
    }

    /**
     * Opern a dir chooser and update the supplied property if a selection is made.
     * @param chooserTitle
     * @param propertyToUpdate
     */
    private void appSettingsDirChooser(String chooserTitle, StringProperty propertyToUpdate) {
        Window window = rootPane.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(chooserTitle);
        File inputDir = new File(propertyToUpdate.getValue());
        if (inputDir.exists())
            directoryChooser.setInitialDirectory(inputDir);
        File selectedFile = directoryChooser.showDialog(window);
        if (selectedFile != null) {
            // selection made
            propertyToUpdate.setValue(selectedFile.getAbsolutePath());
        }
    }
}
