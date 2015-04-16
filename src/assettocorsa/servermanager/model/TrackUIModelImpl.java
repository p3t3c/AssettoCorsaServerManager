package assettocorsa.servermanager.model;

import assettocorsa.servermanager.services.AssettoCorsaTrackService;
import assettocorsa.servermanager.services.AssettoCorsaTrackServiceImpl;
import assettocorsa.servermanager.services.data.ACTrackData;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Created by pete on 8/04/2015.
 */
public class TrackUIModelImpl implements TrackUIModel {
    private final ObservableList<TrackModel> trackModelList;
    private AssettoCorsaTrackService trackService;
    private ObjectProperty<TrackModel> selectedTrackModelProperty;

    public TrackUIModelImpl(AppSettings appSettings) {
        selectedTrackModelProperty = new SimpleObjectProperty<TrackModel>();
        trackModelList = FXCollections.observableArrayList(trackModel -> new Observable[]{trackModel.selectedProperty()});
        trackModelList.addListener(createSelectionListener());
        trackService = new AssettoCorsaTrackServiceImpl();
        appSettings.assettoCorsaDirectoryProperty().addListener((observable, oldValue, newValue) -> load(newValue));
        load(appSettings.assettoCorsaDirectoryProperty().getValue());
    }

    @Override
    /**
     * This list is observable for add and remove of TrackModels.
     * Also for changes in the selected status of TrackModels
     */
    public ObservableList<TrackModel> trackModelListProperty() {
        return trackModelList;
    }


    @Override
    public TrackModel getSelectedTrack() {
        return null;
    }

    @Override
    public void showTrackOutline() {

    }

    @Override
    public void showPreview() {

    }

    /**
     * Creates a listener that only listens for changes in the selectedProperty of each TrackModel.
     * If the changed TrackModel.selectedProperty() is true all other TrackModel.selectedProperty() in the list will have their values set false (deselected).
     * <p>
     * Post condition of this method is that if the change was a TrackModel.selectedProperty() true then the selectedTrackModelProperty field of this class is assigned the new TrackModel.
     * </p>
     *
     * @return Change listener for managing the selected state.
     */
    private ListChangeListener<? super TrackModel> createSelectionListener() {
        return change -> {
            while (change.next()) {
                if (change.wasUpdated()) {
                    // Only handling a single change. Am making an assumption about how selected works.
                    int fromIndex = change.getFrom();
                    ObservableList<? extends TrackModel> theList = change.getList();
                    TrackModel changedTrackModel = theList.get(fromIndex);
                    if (changedTrackModel.selectedProperty().getValue() == true) {
                        // Update the selected value
                        selectedTrackModelProperty.setValue(changedTrackModel);

                        // A selection was made clear any other selected values.

                        for (int i = 0; i < theList.size(); i++) {
                            if (i != fromIndex) {
                                theList.get(i).selectedProperty().setValue(false);
                            }
                        }
                    }
                }
            }
        };
    }

    private void load(String pathToACDirectory) {
        trackModelList.clear();
        List<ACTrackData> trackDataList = trackService.getAllTrackData(pathToACDirectory);
        for (ACTrackData acTrackData : trackDataList) {
            TrackModelImpl trackModel = new TrackModelImpl(acTrackData);
            trackModelList.add(trackModel);
        }
    }
}
