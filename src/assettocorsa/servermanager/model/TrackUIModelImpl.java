package assettocorsa.servermanager.model;

import assettocorsa.servermanager.services.AssettoCorsaTrackService;
import assettocorsa.servermanager.services.AssettoCorsaTrackServiceImpl;
import assettocorsa.servermanager.services.data.ACTrackData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Created by pete on 8/04/2015.
 */
public class TrackUIModelImpl implements TrackUIModel {
    private final ObservableList<TrackModel> trackModelList;
    private AssettoCorsaTrackService trackService;

    public TrackUIModelImpl(AppSettings appSettings) {
        trackModelList = FXCollections.observableArrayList();
        trackService = new AssettoCorsaTrackServiceImpl();
        appSettings.assettoCorsaDirectoryProperty().addListener((observable, oldValue, newValue) -> load(newValue));
        load(appSettings.assettoCorsaDirectoryProperty().getValue());
    }

    private void load(String pathToACDirectory) {
        trackModelList.removeAll();
        List<ACTrackData> trackDataList = trackService.getAllTrackData(pathToACDirectory);
        for (ACTrackData acTrackData : trackDataList) {
            trackModelList.add(new TrackModelImpl(acTrackData));
        }
    }


    @Override
    public ObservableList<TrackModel> trackModelListProperty() {
        return trackModelList;
    }

    @Override
    public TrackModel getSelectedTrack() {
        return null;
    }

    @Override
    public void setSelectedTrack(TrackModel selectedTrackModel) {

    }

    @Override
    public void showTrackOutline() {

    }

    @Override
    public void showPreview() {

    }
}
