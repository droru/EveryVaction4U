package sample;

import javafx.event.ActionEvent;
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
    public Button close;
    public Button changeProfilePic;
    public Hyperlink makeEdit;
    public Hyperlink changePass;
    public Hyperlink deleteuser;

    public Label title;
    public Label erorfirstname;
    public Label erorlastname;
    public Label erormail;
    public Label erorcity;
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
            close.setVisible(false);
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
            close.setVisible(true);
        }
        updateButton.setVisible(false);

    }
    public void enableEdit()
    {
        txt_userName.setDisable(true);
        txt_firstName.setDisable(false);
        txt_lastName.setDisable(false);
        txt_birthDate.setDisable(true);
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
        if(Main.isProfile && isDataNotSaved()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("השינויים לא נשמרו");
            alert.setContentText("האם אתה בטוח שאתה רוצה לחזור למסך הבית ללא שמירה?");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK)
                Main.switchScene("../View/MainScreen.fxml", Main.getStage(), Main.mainWidth, Main.mainHeight);
            else
                alert.close();
        }
        else
            Main.switchScene("../View/MainScreen.fxml", Main.getStage(), Main.mainWidth, Main.mainHeight);
    }
    private boolean isDataNotSaved() {
        if(!txt_firstName.getText().equals(Main.loggedUser.getFirstName()))
            return true;
        if(!txt_lastName.getText().equals(Main.loggedUser.getLastName()))
            return true;
        if(!Cb_city.getValue().toString().equals(Main.loggedUser.getCity()))
            return true;
        if(!txt_email.getText().equals(Main.loggedUser.getEmail()))
            return true;
        if(file != null)
            return true;
        return false;
    }

    public void updateClicked() throws SQLException {
        if (Validation.validateMail(txt_email.getText())) {
            Main.loggedUser.setEmail(txt_email.getText());
            erormail.setVisible(false);
        }
        else
            erormail.setVisible(true);
        if (Validation.validateName(txt_firstName.getText())) {
            Main.loggedUser.setFirstName(txt_firstName.getText());
            erorfirstname.setVisible(false);
        }
        else{
            erorfirstname.setVisible(true);
            if(txt_firstName.getText().isEmpty())
                erorfirstname.setText("*עליך למלא שדה זה");
            else
                erorfirstname.setText("*ערך השדה אינו חוקי");
        }
        if (Validation.validateName(txt_lastName.getText())) {
            Main.loggedUser.setLastName(txt_lastName.getText());
            erorlastname.setVisible(false);
        }
        else{
            erorlastname.setVisible(true);
            if(txt_lastName.getText().isEmpty())
                erorlastname.setText("*עליך למלא שדה זה");
            else
                erorlastname.setText("*ערך השדה אינו חוקי");
        }
        if (Validation.validatecity(Cb_city.getValue().toString())) {
            Main.loggedUser.setCity(Cb_city.getValue().toString());
            erorcity.setVisible(false);
        }
        else
            erorcity.setVisible(true);

        if(!erormail.isVisible()&&!erorlastname.isVisible()&&!erorfirstname.isVisible() && !erorcity.isVisible())
        {
            Main.loggedUser.setEmail(txt_email.getText());
            Main.loggedUser.setFirstName(txt_firstName.getText());
            Main.loggedUser.setLastName(txt_lastName.getText());
            Main.user.setCity(Cb_city.getValue().toString());

            try {
                if(file != null)//if file is null the pic didn't updated
                {
                    img_profile.imageProperty().set(null);
                    img_profile.setImage(null);
                    System.gc();
                    deleteProfilePic(new File(Main.loggedUser.getProfilePicPath()).getPath());
                    uploadPic(file, file.toPath());
                }
                int result = update(Main.loggedUser);
                if (result == 0){
                    sucsses();
                    Main.switchScene("../View/MainScreen.fxml", Main.getStage(), Main.mainWidth,Main.mainHeight);
                }
                else
                    errormsg();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    //alert about successful registration
    private void sucsses(){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("העדכון בוצע בהצלחה \n הינך מועבר למסך הבית ");
        alert.showAndWait();
        alert.close();
    }
    //alert about bad registration, username already exists
    private void errormsg(){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ארעה שגיאה בעת העדכון");
        alert.showAndWait();
        alert.close();

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
            Main.switchScene("../View/MainScreen.fxml", (Stage) updateButton.getScene().getWindow(), Main.mainWidth,Main.mainHeight);
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
        Scene scene=new Scene(root,Main.changePassWidth,Main.changePassHeight);
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
            img_profile.setImage(new Image(new FileInputStream(file)));
        }
    }

    public void close(ActionEvent actionEvent) {
        ((Stage) close.getScene().getWindow()).close();
    }
}
