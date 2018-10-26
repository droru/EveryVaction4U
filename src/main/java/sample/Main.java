package sample;

import Model.Query;
import Model.User;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


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
    public static final String imageDBPath = "src\\main\\java\\DataBase\\ProfilePics\\";
    public static final String defaultProfilePicPath = Main.imageDBPath + "defaultProfilePic.jpg";

    //screen sizes
    public static final int mainWidth = 1200;
    public static final int mainHeight = 650;
    public static final int registerWidth = 880;
    public static final int registerHeight = 550;
    public static final int loginWidth = 400;
    public static final int loginHeight = 300;
    public static final int changePassWidth = 550;
    public static final int changePassHeight = 300;

    public static String getExtention(File file) {
            String fileName = file.getName();
            if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
                return fileName.substring(fileName.lastIndexOf(".")+1);
            else return "";
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Vacation4You");
        fxmlLoader = new FXMLLoader();
        root = fxmlLoader.load(getClass().getResource("../View/MainScreen.fxml").openStream());
        Scene scene = new Scene(root, mainWidth, mainHeight);//main screen
        scene.getStylesheets().add(getClass().getResource("../View/Style.css").toExternalForm());
        primaryStage.setScene(scene);
        stage = primaryStage;
        primaryStage.centerOnScreen();
        primaryStage.show();



    }


    public static void main(String[] args) {

        launch(args);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    Query.conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
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
                break;
            case "../View/LoginForm.fxml":
                break;
            case "../View/RegisterForm.fxml":
                stage.setResizable(false);
                break;
            case "../View/UserDetailsScreen.fxml":
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
        user.setProfilePicPath(u.getProfilePicPath());
    }

    public static File openFileExplorer(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        return fileChooser.showOpenDialog(new Stage());
    }

}



