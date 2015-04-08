package assettocorsa.servermanager.ui.trackview;

import com.github.benjamingale.usercontrol.UserControl;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by pete on 8/04/2015.
 */
public class TrackViewControl extends UserControl {
    public Label trackName;
    public ImageView trackImage;
    public Label trackPitboxes;

    public StringProperty trackNameProperty() {
        return trackName.textProperty();
    }

    public ObjectProperty<Image> trackImageProperty() {
        return trackImage.imageProperty();
    }

    public StringProperty trackPitboxesProperty() {
        return trackPitboxesProperty();
    }
}
