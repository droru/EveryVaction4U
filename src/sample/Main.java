package sample;

import Model.User;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class Main extends Application {

    public static Stage getStage() {
        return stage;
    }
    private static FXMLLoader fxmlLoader;
    private static Stage stage;
    private static Parent root;
    public static User user=new User();         //The user that we want to search!
    public static User loggedUser;
    public static boolean isProfile=false;

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
        root = fxmlLoader.load(getClass().getResource("../View/MainScreen.fxml").openStream());
        //Scene scene = new Scene(root, 400, 300);//login form
        Scene scene = new Scene(root, 1000, 500);//main screen
        scene.getStylesheets().add(getClass().getResource("../View/Style.css").toExternalForm());
        primaryStage.setScene(scene);
        //SetStageCloseEvent(primaryStage);
        stage = primaryStage;
        //stage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void switchScene(String fxmlFile, Stage stage, int width, int height) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(fxmlFile));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loader.getController();
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.getScene().getStylesheets().add(Main.class.getResource("../View/Style.css").toExternalForm());

        switch (fxmlFile) {
            case "../View/MainScreen.fxml":
                stage.setResizable(false);
                break;
            case "../View/LoginForm.fxml":
                break;
            case "../View/RegisterForm.fxml":
                stage.setResizable(false);
                break;
            case "../View/UserDetailsScreen.fxml":
                stage.setResizable(false);
                break;
            case "../View/SwitchPassword.fxml":
                stage.setResizable(false);
                break;


        }
    }

    public static void  setUser(User u){
        user.setCity(u.getCity());
        user.setBirthDate(u.getBirthDate());
        user.setPassword(u.getPassword());
        user.setLastName(u.getLastName());
        user.setFirstName(u.getFirstName());
        user.setUserName(u.getUserName());
        user.setEmail(u.getEmail());
    }

}



