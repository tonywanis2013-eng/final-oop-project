package project.oop.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneNavigator {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) { primaryStage = stage; }

    public static void navigateTo(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(
                SceneNavigator.class.getResource("/org/example/" + fxmlFile)
            );
            Scene scene = primaryStage.getScene();
            if (scene == null) {
                primaryStage.setScene(new Scene(root));
            } else {
                scene.setRoot(root);
                primaryStage.sizeToScene();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
