package project.oop;

import javafx.application.Application;
import javafx.stage.Stage;
import project.oop.util.SceneNavigator;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        SceneNavigator.setPrimaryStage(stage);
        SceneNavigator.navigateTo("Login.fxml");
        stage.setTitle("Grand Hotel");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
