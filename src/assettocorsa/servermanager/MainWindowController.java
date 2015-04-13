package assettocorsa.servermanager;

import assettocorsa.servermanager.model.*;
import assettocorsa.servermanager.ui.listview.DriverOnRosterNameConverter;
import assettocorsa.servermanager.ui.listview.RaceSettingsNameConverter;
import assettocorsa.servermanager.ui.trackview.TrackViewControl;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class MainWindowController implements Initializable {

    public ListView<DriverOnRoster> driverRosterListView;
    public TextField driverInfoNameTextField;
    public TextField driverInfoGuidTextField;
    public TextField outputLocationTextField;
    public TextField acLocationTextField;
    /**
     * Added the rootPane in order to get access to it's parent, the Stage.
     */
    public BorderPane rootPane;
    /**
     * List of all the races that have been configured.
     */
    public ListView<RaceSettings> raceListView;

    /* Service config panel fields */
    public TextField serverNameTextField;
    public TextField serverPasswordTextField;
    public TextField adminPasswordTextField;
    public TilePane trackListTilePane;
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

    private RaceUIModel raceUIModel;
    private TrackUIModel trackUIModel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialisePropertiesAndBindings();

        driverRoster = new DriverRosterImpl();
        driverRoster.load();
        // TODO Try catch IOException on failed load should go into the driverRoster
        ObservableList<DriverOnRoster> driverList = driverRoster.getListOfDrivers();


        // TODO inject this
        appSettings = new AppSettingsImpl();
        // TODO inject this
        trackUIModel = new TrackUIModelImpl(appSettings);

        initaliseBindingsToAppSettings(appSettings);
        initaliseTrackDisplay(trackUIModel);

        // Do the load after all other users so they get the first update to the properties
        appSettings.loadAppSettings();

        // TODO inject this
        raceUIModel = new RaceUIModelImpl();

        initiliseBindingToRaceUIModel();

        intialiseDriverRosterListView(driverList);
    }


    private void initiliseBindingToRaceUIModel() {
        raceListView.setCellFactory(TextFieldListCell.forListView(new RaceSettingsNameConverter()));
        raceListView.setItems(raceUIModel.raceSettingsListProperty());
        raceListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        RaceSettings raceSettings = raceUIModel.selectedRaceSettingsProperty();
        serverNameTextField.textProperty().bindBidirectional(raceSettings.serverNameProperty());
        // And the rest...
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
        driverRosterListView.setCellFactory(TextFieldListCell.forListView(new DriverOnRosterNameConverter()));
        driverRosterListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        driverRosterListView.setItems(driverList);

    }


    /**
     * Create the TrackViewControls and add them.
     * Perform bindings. The bindings are from model to control (unidirectional) as the data is read only.
     *
     * @param trackUIModel
     */
    private void initaliseTrackDisplay(TrackUIModel trackUIModel) {
        ObservableList<TrackModel> trackModels = trackUIModel.trackModelListProperty();
        // Listener that adds and removes TrackViewControls from the tile view for tracks
        trackModels.addListener(new ListChangeListener<TrackModel>() {
            @Override
            public void onChanged(Change<? extends TrackModel> change) {
                change.next();
                if (change.wasRemoved()) {
                    List<? extends TrackModel> removedTracks = change.getRemoved();
                    Set<String> removedTrackNames = removedTracks.stream().map(TrackModel::trackNameProperty).map(StringProperty::getValue).collect(toSet());
                    List<Node> nodesToRemove = trackListTilePane.getChildren().stream().filter(node -> node instanceof TrackViewControl && removedTrackNames.contains(((TrackViewControl) node).trackNameProperty().getValue())).
                            collect(toList());
                    trackListTilePane.getChildren().removeAll(nodesToRemove);
                }

                if (change.wasAdded()) {
                    List<? extends TrackModel> addedTrackModels = change.getAddedSubList();
                    addedTrackModels.forEach(trackModel -> {
                        TrackViewControl trackViewControl = new TrackViewControl();

                        trackViewControl.trackNameProperty().bind(trackModel.trackNameProperty());
                        trackViewControl.trackImageProperty().bind(trackModel.trackImageProperty());
                        trackViewControl.trackPitboxesProperty().bind(trackModel.trackPitboxesProperty());

                        trackListTilePane.getChildren().add(trackViewControl);
                    });
                }
            }
        });
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
     *
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

    public void newRaceAction(ActionEvent actionEvent) {
        raceUIModel.store(); // Store any of the current settings
        RaceSettings rs = raceUIModel.createNewRaceSettings();
        raceUIModel.selectRaceSettings(rs);
    }

    public void cloneRaceAction(ActionEvent actionEvent) {
        raceUIModel.cloneRaceSettings(getSelectedRaceSettings());
        raceUIModel.store();
    }


    public void deleteRaceAction(ActionEvent actionEvent) {
        raceUIModel.deleteRaceSettings(getSelectedRaceSettings());
        raceUIModel.store();
    }

    public void selectRaceAction(@SuppressWarnings("UnusedParameters") Event event) {
        raceUIModel.store(); // save changes on switch.
        raceUIModel.selectRaceSettings(getSelectedRaceSettings());
    }

    private RaceSettings getSelectedRaceSettings() {
        return raceListView.getSelectionModel().getSelectedItems().get(0);
    }

    public void raceListEditCommit(ListView.EditEvent<RaceSettings> raceListEditEvent) {
        RaceSettings partialRaceSettings = raceListEditEvent.getNewValue();
        /*
        We expect this to be the RaceSettings object created by a StringConverter installed into the list view.
        Expect that it only contains the race name because that is all that is edited via the list. Take the name and
        assign it to the currently selected item and throw away the result.
        */
        raceUIModel.selectedRaceSettingsProperty().setRaceName(partialRaceSettings.getRaceName());
        raceUIModel.store(); // Store and trigger change in observable list, which updates ui.
    }
}
