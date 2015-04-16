package assettocorsa.servermanager.ui.trackview;

import assettocorsa.servermanager.model.TrackModel;
import com.github.benjamingale.usercontrol.UserControl;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Created by pete on 8/04/2015.
 */
public class TrackViewControl extends UserControl {

    private final TrackModel trackModel;
    public ImageView trackImage;
    public Label trackPitboxes;
    public Label trackName;

    public TrackViewControl(TrackModel trackModel) {
        this.trackModel = trackModel;
        this.trackNameProperty().bind(trackModel.trackNameProperty());
        this.trackImageProperty().bind(trackModel.trackImageProperty());
        this.trackPitboxesProperty().bind(trackModel.trackPitboxesProperty());
        this.trackModel.selectedProperty().addListener(createSelectionListener());
    }

    public ObjectProperty<Image> trackImageProperty() {
        return trackImage.imageProperty();
    }

    public StringProperty trackPitboxesProperty() {
        return trackPitboxes.textProperty();
    }

    public StringProperty trackNameProperty() {
        return trackName.textProperty();
    }

    public TrackModel getTrackModel() {
        return trackModel;
    }

    /**
     * When the mouse is clicked on any of the views Nodes
     *
     * @param actionEvent
     */
    public void mouseClickOnTrack(Event actionEvent) {
        System.out.println("Clicked on " + trackNameProperty().getValue()); // TODO proper logger
        if (!trackModel.selectedProperty().get()) { // if not already selected
            trackModel.selectedProperty().setValue(true);
        }
    }

    private ChangeListener<? super Boolean> createSelectionListener() {
        return new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    final int depth = 70;
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.BLUE);
                    borderGlow.setWidth(depth);
                    borderGlow.setHeight(depth);

                    setEffect(borderGlow);
                } else {
                    setEffect(null);
                }
            }
        };
    }
}
