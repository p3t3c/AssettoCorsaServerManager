package assettocorsa.servermanager.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

/**
 * Created by pete on 8/04/2015.
 */
public interface TrackModel {
    StringProperty trackNameProperty();

    ObjectProperty<Image> trackImageProperty();

    StringProperty trackPitboxesProperty();

    BooleanProperty selectedProperty();
}
