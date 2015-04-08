package assettocorsa.servermanager.services.data;

import java.io.File;
import java.nio.file.Path;

public class ACTrackDataBuilder {
    private String trackName;
    private String trackConfiguration;
    private Integer numberOfPitBoxes;
    private Path trackDirLocation;
    private Path previewImageFile = new File(getClass().getResource("/default_preview.png").getFile()).toPath();
    private Path outlineImageFile = new File(getClass().getResource("/default_outline.png").getFile()).toPath();

    public ACTrackDataBuilder setTrackName(String trackName) {
        this.trackName = trackName;
        return this;
    }

    public ACTrackDataBuilder setTrackConfiguration(String trackConfiguration) {
        this.trackConfiguration = trackConfiguration;
        return this;
    }

    public ACTrackDataBuilder setNumberOfPitBoxes(Integer numberOfPitBoxes) {
        this.numberOfPitBoxes = numberOfPitBoxes;
        return this;
    }

    public ACTrackDataBuilder setTrackDirLocation(Path trackDirLocation) {
        this.trackDirLocation = trackDirLocation;
        return this;
    }

    public ACTrackDataBuilder setPreviewImageFile(Path previewImageFile) {
        this.previewImageFile = previewImageFile;
        return this;
    }

    public ACTrackDataBuilder setOutlineImageFile(Path outlineImageFile) {
        this.outlineImageFile = outlineImageFile;
        return this;
    }

    public ACTrackData createACTrackData() {
        return new ACTrackData(trackName, trackConfiguration, numberOfPitBoxes, trackDirLocation, previewImageFile, outlineImageFile);
    }
}