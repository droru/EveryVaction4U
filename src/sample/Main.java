package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    public static Stage getStage() {
        return stage;
    }
    private static FXMLLoader fxmlLoader;
    private static Stage stage;
    private static Parent root;



    @Override
    public void start(Stage primaryStage) throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("../View/LoginForm.fxml"));
        primaryStage.setTitle("Vacation4You");
        Scene scene=new Scene(root,500,450);
        scene.getStylesheets().add(getClass().getResource("../View/Loginstyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
           */

            primaryStage.setTitle("Vacation4You");
            fxmlLoader = new FXMLLoader();
            root = fxmlLoader.load(getClass().getResource("../View/LoginForm.fxml").openStream());
            Scene scene = new Scene(root, 750, 600);
            scene.getStylesheets().add(getClass().getResource("../View/style.css").toExternalForm());
            primaryStage.setScene(scene);
            //SetStageCloseEvent(primaryStage);
            stage = primaryStage;
            stage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }
    public static void switchScene(String fxmlFile, Stage stage, int width, int height)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(fxmlFile));
            Parent root = (Parent) loader.load();
            loader.getController();
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.centerOnScreen();
            //stage.getScene().getStylesheets().add(Main.class.getResource("MyViewStyle.css").toExternalForm());
            switch (fxmlFile){
                case "../View/MainScreen.fxml":
                    stage.setResizable(true);
                    break;
                case "../View/LoginForm.fxml":
                    stage.setResizable(false);
                    break;
                case "../View/RegisterForm.fxml":
                    stage.setResizable(true);
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



