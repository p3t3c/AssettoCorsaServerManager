package assettocorsa.servermanager.services;

import assettocorsa.servermanager.services.data.ACTrackData;
import org.junit.Test;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * A test using my local install of AC.
 * Want to be fast and not have to create a data set at the moment.
 * There are missing cases of when the dir scanning doesn't work but for the sake of time will skip those.
 * Created by pete on 7/04/2015.
 */
public class AssettoCorsaTrackServiceImplTest {

    @Test
    public void testIsTrackDir() throws Exception {
        AssettoCorsaTrackServiceImpl impl = new AssettoCorsaTrackServiceImpl();
        Path trackPath = FileSystems.getDefault().getPath("E:\\Program Files (x86)\\Steam\\SteamApps\\common\\assettocorsa", "content", "tracks", "adelaide2011");

        assertTrue(impl.isTrackDir(trackPath));
    }

    @Test
    public void testGetAllTrackData() throws Exception {
        AssettoCorsaTrackServiceImpl impl = new AssettoCorsaTrackServiceImpl();

        List<ACTrackData> trackDataList = impl.getAllTrackData("E:\\Program Files (x86)\\Steam\\SteamApps\\common\\assettocorsa");

//        trackDataList.stream().forEach(track -> System.out.println(track));
//        System.out.println(trackDataList.get(0));

        assertThat(trackDataList.size(), is(36));

        /* Test a simple track */
        ACTrackData track = trackDataList.get(0);

        assertThat(track.getTrackName(), is(equalTo("adelaide2011")));
        assertThat(track.getTrackConfiguration(), nullValue());
        assertThat(track.getNumberOfPitBoxes(), is(28));
        Path trackOneLocation = FileSystems.getDefault().getPath("E:\\Program Files (x86)\\Steam\\SteamApps\\common\\assettocorsa\\content\\tracks\\adelaide2011");
        assertThat(track.getTrackDirLocation(), is(equalTo(trackOneLocation)));

        Path trackOnePreviewLocation = FileSystems.getDefault().getPath("E:\\Program Files (x86)\\Steam\\SteamApps\\common\\assettocorsa\\content\\tracks\\adelaide2011\\ui\\preview.png");
        Path trackOneOutlineLocation = FileSystems.getDefault().getPath("E:\\Program Files (x86)\\Steam\\SteamApps\\common\\assettocorsa\\content\\tracks\\adelaide2011\\ui\\outline.png");
        assertThat(track.getPreviewImageFile(), is(equalTo(trackOnePreviewLocation)));
        assertThat(track.getOutlineImageFile(), is(equalTo(trackOneOutlineLocation)));

        /* Test against a track with configs */
        track = trackDataList.get(33);
        assertThat(track.getTrackName(), is(equalTo("vallelunga")));
        assertThat(track.getTrackConfiguration(), is(equalTo("club_circuit")));
        assertThat(track.getNumberOfPitBoxes(), is(22));
        Path trackLocation = FileSystems.getDefault().getPath("E:\\Program Files (x86)\\Steam\\SteamApps\\common\\assettocorsa\\content\\tracks\\vallelunga");
        assertThat(track.getTrackDirLocation(), is(equalTo(trackLocation)));

        Path trackPreviewLocation = FileSystems.getDefault().getPath("E:\\Program Files (x86)\\Steam\\SteamApps\\common\\assettocorsa\\content\\tracks\\vallelunga\\ui\\club_circuit\\preview.png");
        Path trackOutlineLocation = FileSystems.getDefault().getPath("E:\\Program Files (x86)\\Steam\\SteamApps\\common\\assettocorsa\\content\\tracks\\vallelunga\\ui\\club_circuit\\outline.png");
        assertThat(track.getPreviewImageFile(), is(equalTo(trackPreviewLocation)));
        assertThat(track.getOutlineImageFile(), is(equalTo(trackOutlineLocation)));

        /* Test the other config */
        track = trackDataList.get(34);
        assertThat(track.getTrackName(), is(equalTo("vallelunga")));
        assertThat(track.getTrackConfiguration(), is(equalTo("extended_circuit")));
        assertThat(track.getNumberOfPitBoxes(), is(22));
        trackLocation = FileSystems.getDefault().getPath("E:\\Program Files (x86)\\Steam\\SteamApps\\common\\assettocorsa\\content\\tracks\\vallelunga");
        assertThat(track.getTrackDirLocation(), is(equalTo(trackLocation)));

        trackPreviewLocation = FileSystems.getDefault().getPath("E:\\Program Files (x86)\\Steam\\SteamApps\\common\\assettocorsa\\content\\tracks\\vallelunga\\ui\\extended_circuit\\preview.png");
        trackOutlineLocation = FileSystems.getDefault().getPath("E:\\Program Files (x86)\\Steam\\SteamApps\\common\\assettocorsa\\content\\tracks\\vallelunga\\ui\\extended_circuit\\outline.png");
        assertThat(track.getPreviewImageFile(), is(equalTo(trackPreviewLocation)));
        assertThat(track.getOutlineImageFile(), is(equalTo(trackOutlineLocation)));
    }

    @Test
    public void testGetAllTrackDataFromWrongDir() throws Exception {
        AssettoCorsaTrackServiceImpl impl = new AssettoCorsaTrackServiceImpl();

        List<ACTrackData> trackDataList = impl.getAllTrackData("E:\\");

        assertThat(trackDataList.size(), is(0));
    }

    @Test
    public void testGetAllTrackDataFromNonExistantDir() throws Exception {
        AssettoCorsaTrackServiceImpl impl = new AssettoCorsaTrackServiceImpl();

        List<ACTrackData> trackDataList = impl.getAllTrackData("E:\\NotThere");

        assertThat(trackDataList.size(), is(0));
    }
}