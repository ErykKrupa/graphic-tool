/** Main.java
 * @aplication Multifunctional Graphical Tool 2018
 * @author Eryk Krupa
 * @version 1.0
 */

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Sets up layout and launches application.
 */
public class Main extends Application
{
    /**
     * Essential pane of application, contains work field, data field and button.
     */
    public static Pane root;
    /**
     * Essential stage of application, contains root.
     */
    public static Stage primaryStage= new Stage();
    /**
     * Alert containing color picker.
     */
    public static Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    /**
     * Color picker allowing choose color to fill figures.
     */
    public static ColorPicker colorPicker = new ColorPicker(Color.web("#ffffff"));

    /**
     * Sets up stage and color picker alert.
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        alert.setTitle("Choose color");
        alert.setHeaderText(null);
        alert.setContentText("Choose your color:");
        alert.getDialogPane().getChildren().add(colorPicker);
        colorPicker.setLayoutX(195);
        colorPicker.setLayoutY(25);

        primaryStage.setTitle("Multifunctional Graphical Tool 2018");
        primaryStage.setScene(new Scene(root, 960, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Launches application.
     */
    public static void main() { launch(); }
}
