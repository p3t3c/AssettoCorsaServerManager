package assettocorsa.servermanager.services.data;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.nio.file.Path;

/**
 * Class which holds the track data extracted from AC.
 * Created by pete on 7/04/2015.
 */
public class ACTrackData {


    private String trackName;

    /**
     * Track Configuration if present. Null value indicates track only has single configuration.
     */
    @Nullable
    private String trackConfiguration;

    /**
     * The number of pit boxes extracted from the data.
     */
    private Integer numberOfPitBoxes;

    private Path trackDirLocation;

    /**
     * Preivew Image for the track.
     * If for some reason a file can't be found then a default image is given.
     */
    private Path previewImageFile;

    /**
     * Outline image for the track.
     * If for some reason a file can't be found then a default image is given.
     */
    private Path outlineImageFile;

    public ACTrackData(String trackName, String trackConfiguration, Integer numberOfPitBoxes, Path trackDirLocation, Path previewImageFile, Path outlineImageFile) {
        this.trackName = trackName;
        this.trackConfiguration = trackConfiguration;
        this.numberOfPitBoxes = numberOfPitBoxes;
        this.trackDirLocation = trackDirLocation;
        this.previewImageFile = previewImageFile;
        this.outlineImageFile = outlineImageFile;
    }

    public String getTrackName() {

        return trackName;
    }

    public String getTrackConfiguration() {
        return trackConfiguration;
    }

    public Integer getNumberOfPitBoxes() {
        return numberOfPitBoxes;
    }

    public Path getTrackDirLocation() {
        return trackDirLocation;
    }

    public Path getPreviewImageFile() {
        return previewImageFile;
    }

    public Path getOutlineImageFile() {
        return outlineImageFile;
    }

    @Override
    public String toString() {
        return "ACTrackData{" +
                "trackName='" + trackName + '\'' +
                ", trackConfiguration='" + trackConfiguration + '\'' +
                ", numberOfPitBoxes=" + numberOfPitBoxes +
                ", trackDirLocation=" + trackDirLocation +
                ", previewImageFile=" + previewImageFile +
                ", outlineImageFile=" + outlineImageFile +
                '}';
    }
}
