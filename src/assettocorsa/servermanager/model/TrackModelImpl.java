package assettocorsa.servermanager.model;

import assettocorsa.servermanager.services.data.ACTrackData;
import assettocorsa.servermanager.services.data.ACTrackDataBuilder;
import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * Created by pete on 8/04/2015.
 */
public class TrackModelImpl implements TrackModel {
    private final StringProperty trackNameProperty;
    private final StringProperty trackPitboxesProperty;
    private final ObjectProperty<Image> trackImageProperty;
    private final BooleanProperty selectedProperty;
    private ACTrackData acTrackData;
    private boolean displayOutline;


    public TrackModelImpl(ACTrackData acTrackData) {
        this.acTrackData = acTrackData;
        trackNameProperty = new SimpleStringProperty(getDisplayableTrackName());
        trackPitboxesProperty = new SimpleStringProperty(String.valueOf(acTrackData.getNumberOfPitBoxes()));
        trackImageProperty = new SimpleObjectProperty<Image>(new Image(ACTrackDataBuilder.DEFAULT_PREVIEW_URL.toExternalForm()));
        selectedProperty = new SimpleBooleanProperty(false);
        displayOutline = false;
        updateTrackImageProperty();
    }

    public void updateTrackImageProperty() {
        try {
            if (displayOutline) {
                trackImageProperty().setValue(new Image(Files.newInputStream(acTrackData.getOutlineImageFile(), StandardOpenOption.READ)));
            } else {
                trackImageProperty().setValue(new Image(Files.newInputStream(acTrackData.getPreviewImageFile(), StandardOpenOption.READ)));
            }
        } catch (IOException e) {
            System.err.println("Error loading image for " + acTrackData.getTrackName());
            e.printStackTrace();
        }
    }

    @Override
    public StringProperty trackNameProperty() {
        return trackNameProperty;
    }

    @Override
    public ObjectProperty<Image> trackImageProperty() {
        return trackImageProperty;
    }

    @Override
    public StringProperty trackPitboxesProperty() {
        return trackPitboxesProperty;
    }

    @Override
    public BooleanProperty selectedProperty() {
        return selectedProperty;
    }

    @Override
    public void setSelected(boolean selected) {
        selectedProperty.setValue(selected);
    }

    ACTrackData getAcTrackData() {
        return acTrackData;
    }

    /**
     * Displayable trackname is the track name and configuration if it has one.
     *
     * @return
     */
    private String getDisplayableTrackName() {
        StringBuilder sb = new StringBuilder();
        sb.append(acTrackData.getTrackName());

        String trackConfig = acTrackData.getTrackConfiguration();
        if (trackConfig != null) {
            sb.append(" ");
            sb.append(trackConfig);
        }

        return sb.toString();
    }

    @Override
    /**
     * Equals implementation follows that of the backing data ACTrackData.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrackModelImpl that = (TrackModelImpl) o;

        return !(acTrackData != null ? !acTrackData.equals(that.acTrackData) : that.acTrackData != null);

    }

    @Override
    /**
     * Hashcode implementation follows that of the backing data ACTrackData.
     * Warning: This means the hashCode() will be equivialent to the ACTrackData.
     */
    public int hashCode() {
        return acTrackData != null ? acTrackData.hashCode() : 0;
    }
}
