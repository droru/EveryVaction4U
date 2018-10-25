package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import static Model.Query.delete;
import static Model.Query.update;
import static sample.RegisterController.validateMail;
import static sample.RegisterController.validateName;

public class UserDetailsScreenController {
    @FXML
    public ImageView img_profile;
    public TextField txt_userName;
    public TextField txt_firstName;
    public TextField txt_lastName;
    public TextField txt_birthDate;
    public ChoiceBox Cb_city;
    public TextField txt_email;
    public Button updateButton;
    public Button returnMain;
    public Button changeProfilePic;
    public Hyperlink makeEdit;
    public Hyperlink changePass;
    public Hyperlink deleteuser;

    public Label title;
    public Label erorfirstname;
    public Label erorlastname;
    public Label erormail;

    private File file;
    /*
    public Label lbl_firstName1;
    public Label lbl_lastName1;
    public Label lbl_birthDate1;
    public Label lbl_email1;
    public Label lbl_city1;
*/
    public void initialize() throws FileNotFoundException {

        if(Main.isProfile)
        {
            title.setText("אזור אישי");
            setTextInDetail();
            Cb_city.setValue(Main.loggedUser.getCity());
            txt_email.setText(Main.loggedUser.getEmail());
            img_profile.setImage(new Image(new FileInputStream(Main.loggedUser.getProfilePicPath())));

            makeEdit.setVisible(true);
            changePass.setVisible(true);
            deleteuser.setVisible(true);
            returnMain.setVisible(true);
        }
        else{
            title.setText("פרופיל משתמש");
            txt_userName.setText(Main.user.getUserName());
            txt_firstName.setText(Main.user.getFirstName());
            txt_lastName.setText(Main.user.getLastName());
            txt_birthDate.setText(Main.user.getBirthDate());
            Cb_city.setValue(Main.user.getCity());
            txt_email.setText(Main.user.getEmail());
            img_profile.setImage(new Image(new FileInputStream(Main.user.getProfilePicPath())));

            makeEdit.setVisible(false);
            changePass.setVisible(false);
            deleteuser.setVisible(false);
            returnMain.setVisible(false);
        }
        updateButton.setVisible(false);

    }
    public void enableEdit()
    {
        txt_userName.setDisable(false);
        txt_firstName.setDisable(false);
        txt_lastName.setDisable(false);
        txt_birthDate.setDisable(false);
        Cb_city.setDisable(false);
        txt_email.setDisable(false);

        txt_firstName.setText(Main.loggedUser.getFirstName());
        setTextInDetail();
        Cb_city.setValue(Main.loggedUser.getCity());
        txt_email.setText(Main.loggedUser.getEmail());
        updateButton.setVisible(true);
        changeProfilePic.setVisible(true);

    }

    private void setTextInDetail() {
        txt_userName.setText(Main.loggedUser.getUserName());
        txt_firstName.setText(Main.loggedUser.getFirstName());
        txt_lastName.setText(Main.loggedUser.getLastName());
        txt_birthDate.setText(Main.loggedUser.getBirthDate());
    }

    public void returnClick(){
        Main.switchScene("../View/MainScreen.fxml", Main.getStage(), 1000, 500);
    }
    public void updateClicked() throws SQLException {


        if (validateMail(txt_email.getText())) {
            Main.loggedUser.setEmail(txt_email.getText());
            erormail.setVisible(false);
        }
        else
            erormail.setVisible(true);
        if (validateName(txt_firstName.getText())) {
            Main.loggedUser.setFirstName(txt_firstName.getText());
            erorfirstname.setVisible(false);
        }
        else
            erorfirstname.setVisible(true);
        if (validateName(txt_lastName.getText())) {
            Main.loggedUser.setLastName(txt_lastName.getText());
            erorlastname.setVisible(false);
        }
        else
            erorlastname.setVisible(true);

        if(!erormail.isVisible()&&!erorlastname.isVisible()&&!erorfirstname.isVisible())
        {
            Main.loggedUser.setEmail(txt_email.getText());
            Main.loggedUser.setFirstName(txt_firstName.getText());
            Main.loggedUser.setLastName(txt_lastName.getText());
            //Main.user.setCity(_city.getValue().toString());

            try {
                uploadPic(file,file.toPath());
                update(Main.loggedUser);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Main.switchScene("../View/MainScreen.fxml", Main.getStage(), 1000,500);
        }

    }
    private void uploadPic(File file,Path sourceDirectory) throws IOException {
        //the name of the picture that uploaded is the username
        Path targetDirectory = Paths.get(Main.imageDBPath + txt_userName.getText() + "." + Main.getExtention(file));
        if(!new File(Main.imageDBPath + file.getName()).exists()) {
            Files.copy(sourceDirectory, targetDirectory);
            Main.loggedUser.setProfilePicPath(targetDirectory.toString());
        }
        else
            System.out.println("already exist");
    }

    public void delUser() throws SQLException {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("האם אתה בטוח שאתה רוצה למחוק את המשתמש שלך?");
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            delete(Main.loggedUser.getUserName());
            img_profile.imageProperty().set(null);
            img_profile.setImage(null);
            System.gc();
            deleteProfilePic(Main.loggedUser.getProfilePicPath());
            Main.loggedUser = null;
            Main.switchScene("../View/MainScreen.fxml", (Stage) updateButton.getScene().getWindow(), 1000,500);
        }
        else{
            alert.close();
        }
    }

    private void deleteProfilePic(String profilePicPath) {
        if(Main.loggedUser.getProfilePicPath() != null &&
                !profilePicPath.equals(Main.defaultProfilePicPath)){
            File file = new File(profilePicPath);
            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Main.loggedUser.setProfilePicPath(null);
        }
    }

    public void changepass() throws IOException {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../View/SwitchPassword.fxml"));
        Scene scene=new Scene(root,550,300);
        scene.getStylesheets().add(getClass().getResource("../View/Style.css").toExternalForm());
        stage.setTitle("Change password");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner((Stage)txt_firstName.getScene().getWindow());
        stage.setResizable(false);
        stage.show();
    }

    public void changeProfilePic() throws FileNotFoundException {
        file = Main.openFileExplorer();
        if (file!=null) {
            if(Main.loggedUser.getProfilePicPath() != null) {
                File oldPic = new File(Main.loggedUser.getProfilePicPath());
                img_profile.imageProperty().set(null);
                img_profile.setImage(null);
                System.gc();
                deleteProfilePic(oldPic.getPath());
            }

            img_profile.setImage(new Image(new FileInputStream(file)));
        }
    }
}
