package assettocorsa.servermanager.model;

import assettocorsa.servermanager.services.data.ACTrackData;
import assettocorsa.servermanager.services.data.ACTrackDataBuilder;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    private ACTrackData acTrackData;
    private boolean displayOutline;


    public TrackModelImpl(ACTrackData acTrackData) {
        this.acTrackData = acTrackData;
        trackNameProperty = new SimpleStringProperty(getDisplayableTrackName());
        trackPitboxesProperty = new SimpleStringProperty(String.valueOf(acTrackData.getNumberOfPitBoxes()));
        trackImageProperty = new SimpleObjectProperty<Image>(new Image(ACTrackDataBuilder.DEFAULT_PREVIEW_URL.toExternalForm()));
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
}
