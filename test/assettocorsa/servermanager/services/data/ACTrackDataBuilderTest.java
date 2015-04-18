package assettocorsa.servermanager.services.data;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by pete on 7/04/2015.
 */
public class ACTrackDataBuilderTest {

    @Test
    public void resourceLoading() {
        // Playing around with how the pathing should work
        URL url = getClass().getResource("/default_preview.png");
        assertThat("The URL", url, notNullValue());
        System.out.println(url);
        File f = new File(getClass().getResource("/default_preview.png").getFile());
        assertThat("Getting a file from resource", f, notNullValue());
    }

    @Test
    public void testCreateACTrackData() throws Exception {
        ACTrackDataBuilder builder = new ACTrackDataBuilder();
        ACTrackData data = builder.createACTrackData();

        // Check the defaults
        assertThat(data.getOutlineImageFile(), notNullValue());
    }
}