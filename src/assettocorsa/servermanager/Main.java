package assettocorsa.servermanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Design guide
 * Interesting idea, but this app doesn't use afterburner.
 * http://www.oracle.com/technetwork/articles/java/javafx-productivity-2345000.html
 * <p>
 *     http://jedicoder.blogspot.com.au/2013/03/javafx-structuring-your-application.html
 * </p>
 *
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("Assetto Corsa Server Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
