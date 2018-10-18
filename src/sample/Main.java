package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/LoginForm.fxml"));
        primaryStage.setTitle("Vacation4You");
        Scene scene=new Scene(root,400,300);
        scene.getStylesheets().add(getClass().getResource("../View/Loginstyle.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {
/*
        if (!isDatabaseExist("Vacation4You-DB.db")) {
            createNewDatabase("Vacation4You-DB.db");
            System.out.println("[+] Database created");
        }
        else
            System.out.println("[+] Database already exist");
        connect();
        */
        launch(args);
    }

//

}
