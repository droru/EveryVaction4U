package sample;

import Model.Notification;
import Model.Query;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class Main extends Application {

    public static Stage getStage() {
        return stage;
    }
    private static Stage stage;
    public static User user=new User();         //The user that we want to search!
    public static User loggedUser;
    public static boolean isProfile=false;
    public static final String imageDBPath = "src\\main\\java\\DataBase\\ProfilePics\\";
    public static final String defaultProfilePicPath = Main.imageDBPath + "defaultProfilePic.jpg";
    public static Notification not;

    //screen sizes
    public static final int mainWidth = 1450;
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
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("../View/MainScreen.fxml").openStream());
        Scene scene = new Scene(root, mainWidth, mainHeight);//main screen
        scene.getStylesheets().add(getClass().getResource("../View/Style.css").toExternalForm());
        primaryStage.setScene(scene);
        stage = primaryStage;
        primaryStage.centerOnScreen();
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
        stage.setOnCloseRequest(event -> {
            try {
                Query.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static void switchScene(String fxmlFile, Stage stage, int width, int height) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(fxmlFile));
        Parent root;
        try {
            root = loader.load();
            loader.getController();
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        File file =fileChooser.showOpenDialog(new Stage());
        if(file !=null) {
            String type = Pictype(file);
            if (type.equals("jpg") || type.equals("gif") || type.equals("bmp") || type.equals("png"))
                return file;
            else
                return null;
        }
        else
            return null;
    }

    private static String Pictype(File file ){
        String ext = null;
        String s = file.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }


    }




