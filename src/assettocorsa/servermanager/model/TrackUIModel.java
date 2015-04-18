package assettocorsa.servermanager.model;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

/**
 * Created by pete on 8/04/2015.
 */
public interface TrackUIModel {
    ObservableList<TrackModel> trackModelListProperty();

    ObjectProperty<TrackModel> selectedTrackModelProperty();

    /**
     * Switch the TrackModels to show the track outline.
     */
    void showTrackOutline();

    /**
     * Switch the TrackModels to show the preview.
     */
    void showPreview();
}
