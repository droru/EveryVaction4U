package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainScreen.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 1000, 500);
        scene.getStylesheets().add(getClass().getResource("../View/Style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
/*
        if (!isDatabaseExist("Vacation4You-DB")) {
            createNewDatabase("Vacation4You-DB");
            System.out.println("[+] Database created");
        }
        else
            System.out.println("[+] Database already exist");


        connect();
  */
        launch(args);
    }
}
