package assettocorsa.servermanager.services;

import assettocorsa.servermanager.services.data.ACTrackData;
import assettocorsa.servermanager.services.data.ACTrackDataBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the service that does the work of extracting track information from assetto corsa.
 * Created by pete on 7/04/2015.
 */
public class AssettoCorsaTrackServiceImpl implements AssettoCorsaTrackService {
    @Override
    public List<ACTrackData> getAllTrackData(String assettoCorsaParentDir) {
        List<ACTrackData> acTrackDataList = new ArrayList<>();

        Path acTrackDir = FileSystems.getDefault().getPath(assettoCorsaParentDir, "content", "tracks");
        if (Files.isDirectory(acTrackDir)) {
                /*
                Get dir stream of directories only. Being defensive here, we don't know if someone has messed up
                their AC.
                 */
            try {
                DirectoryStream<Path> trackDirStream = Files.newDirectoryStream(acTrackDir, path -> Files.isDirectory(path));
                processDirectories(acTrackDataList, trackDirStream);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return acTrackDataList;
    }

    private void processDirectories(List<ACTrackData> acTrackDataList, DirectoryStream<Path> trackDirStream) {
        for (Path trackPath : trackDirStream) {
            try {
                if (isTrackDir(trackPath)) {
                    processTrackDirectory(acTrackDataList, trackPath);
                }
            } catch (IOException e) {
                // TODO proper logger
                System.err.println("Problem processing track directory " + trackPath);
                e.printStackTrace();
            }
        }
    }

    private void processTrackDirectory(List<ACTrackData> acTrackDataList, Path trackPath) throws IOException {
        ACTrackDataBuilder builder = new ACTrackDataBuilder();
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
     * @return the number of pit boxes read from the ui_track.json
     */
    Integer getNumberOfPitBoxes(Path uiDir) throws IOException {
        Integer numPitBoxes = 0;
        Path uiTrackJson = uiDir.resolve("ui_track.json");
        try {
            // Need to set the ISO_8859_1 explictly because Kunos started putting degree chars in some of these files.
            // Standard char set causes GSON to fail reading this.
            Reader reader = Files.newBufferedReader(uiTrackJson, StandardCharsets.ISO_8859_1);
            GsonBuilder b = new GsonBuilder();
            Gson gson = b.create();

            Map uiTrackDataMap = gson.fromJson(reader, Map.class);
            reader.close();

            // String is just what the GSON sees this field as for its Map.class translation.
            String s = (String) uiTrackDataMap.get("pitboxes"); // I hate magic containers but no choice here.
            numPitBoxes = Integer.parseInt(s);
        } catch (JsonSyntaxException jsonSyntaxException) {
            // TODO proper logger
            System.err.println("Failed to read JSON for " + uiTrackJson);
            jsonSyntaxException.printStackTrace();
        }
        return numPitBoxes;
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
