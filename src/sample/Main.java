package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static DataBase.SQLiteJDBCDriverConnection.connect;
import static DataBase.createDatabase.createNewDatabase;
import static DataBase.createDatabase.isDatabaseExist;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

        if (!isDatabaseExist("Vacation4You-DB.db")) {
            createNewDatabase("Vacation4You-DB.db");
            System.out.println("[+] Database created");
        }
        else
            System.out.println("[+] Database already exist");


        connect();
        //launch(args);
    }

//

}
