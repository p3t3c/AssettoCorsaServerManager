package assettocorsa.servermanager.services;

import assettocorsa.servermanager.services.data.ACTrackData;
import assettocorsa.servermanager.services.data.ACTrackDataBuilder;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the service that does the work of extracting track information from assetto corsa.
 * Created by pete on 7/04/2015.
 */
public class AssettoCorsaTrackServiceImpl implements AssettoCorsaTrackService {
    @Override
    public List<ACTrackData> getAllTrackData(String assettoCorsaParentDir) {
        List<ACTrackData> acTrackDataList = new ArrayList<>();

        Path acTrackDir = FileSystems.getDefault().getPath(assettoCorsaParentDir, "content", "tracks");
        try {
            if (Files.isDirectory(acTrackDir)) {
                /*
                Get dir stream of directories only. Being defensive here, we don't know if someone has messed up
                their AC.
                 */
                DirectoryStream<Path> trackDirStream = Files.newDirectoryStream(acTrackDir, path -> Files.isDirectory(path));
                for (Path trackPath : trackDirStream) {
                    ACTrackDataBuilder builder = new ACTrackDataBuilder();

                    if (isTrackDir(trackPath)) {
                        builder.setTrackName(trackPath.getFileName().toString());
                        builder.setTrackDirLocation(trackPath);

                        Path trackUiDir = trackPath.resolve("ui");
                        if (Files.isDirectory(trackUiDir)) {
                            if (isMultiTrackConfig(trackUiDir)) {
                                // Configurations is what AC calls different variations of a track
                                DirectoryStream<Path> trackUiConfigDirStream = Files.newDirectoryStream(trackUiDir, path -> Files.isDirectory(path));
                                for (Path trackUIConfigDir : trackUiConfigDirStream) {
                                    builder.setOutlineImageFile(trackUIConfigDir.resolve("outline.png"));
                                    builder.setPreviewImageFile(trackUIConfigDir.resolve("preview.png"));
                                    builder.setNumberOfPitBoxes(getNumberOfPitBoxes(trackUIConfigDir));
                                    builder.setTrackConfiguration(trackUIConfigDir.getFileName().toString());
                                    acTrackDataList.add(builder.createACTrackData());
                                }
                            } else {
                                builder.setOutlineImageFile(trackUiDir.resolve("outline.png"));
                                builder.setPreviewImageFile(trackUiDir.resolve("preview.png"));
                                builder.setNumberOfPitBoxes(getNumberOfPitBoxes(trackUiDir));
                                acTrackDataList.add(builder.createACTrackData());
                            }
                        }

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // TODO proper logging here
        }
        return acTrackDataList;
    }

    /**
     * The track will be considered multi config if only contains folders. If it contains files then it is a single
     * configuration.
     *
     * @param trackUiDir the dir to scan for only dirs
     * @return
     */
    Boolean isMultiTrackConfig(Path trackUiDir) throws IOException {
        Boolean isMultiConfig = Files.list(trackUiDir).allMatch((path -> Files.isDirectory(path)));
        return isMultiConfig;
    }

    /**
     * Get the number of pit boxes from the data files.
     *
     * @param uiDir this must be the dir which holds the ui_track.json. NOT the Path to the file itself
     * @return the number of pit boxes read from the ui_track_.json
     */
    Integer getNumberOfPitBoxes(Path uiDir) {
        return 0;
    }

    /**
     * Consider it a track dir if it has data/surfaces.ini file in it.
     *
     * @param trackPath
     * @return true if the path given contains data/surfaces.ini
     */
    boolean isTrackDir(Path trackPath) throws IOException {
        // If the track dir contains kn5 files it is probably a track dir
        return Files.list(trackPath).anyMatch(filePath -> FileSystems.getDefault().getPathMatcher("glob:**/*.kn5").matches(filePath));
    }
}
