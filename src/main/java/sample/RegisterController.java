package sample;

import Model.Query;
import Model.User;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RegisterController {
    public TextField email;
    public TextField firstname;
    public TextField lastname;
    public PasswordField password;
    public TextField username;
    public TextField birthdate;
    public ChoiceBox city;
    public CheckBox agreeSign;
    public Button sign;
    public Label erorterms;
    public Label erordate;
    public Label erorcity;
    public Label erormail;
    public Label erorpass;
    public Label erorusername;
    public Label erorname;
    public Label erorlastname;
    public Button chooseFile;
    public ImageView profilePic;
    //private String cwd = System.getProperty("user.dir");
    private static final String imageDBPath = Main.imageDBPath;

    protected   User user = new User();
    protected  File file;

    public void initialize() throws FileNotFoundException {
        file = new File(Main.defaultProfilePicPath);
        profilePic.setImage(new Image(new FileInputStream(file)));
        user.setProfilePicPath(file.getPath());
    }

    public void signclicked() {
        if (!username.getText().isEmpty()) {
            user.setUserName(username.getText());
            erorusername.setVisible(false);
        }
        else
            erorusername.setVisible(true);
        if (Validation.validateMail(email.getText())) {
            user.setEmail(email.getText());
            erormail.setVisible(false);
        }
        else
            erormail.setVisible(true);

        if (Validation.validateName(firstname.getText())) {
            user.setFirstName(firstname.getText());
            erorname.setVisible(false);
        }
        else {
            erorname.setVisible(true);
            if(firstname.getText().isEmpty())
                erorname.setText("*עליך למלא שדה זה");
            else
                erorname.setText("*ערך השדה אינו חוקי");
        }
        if (Validation.validateName(lastname.getText())) {
            user.setLastName(lastname.getText());
            erorlastname.setVisible(false);
        }
        else {
            erorlastname.setVisible(true);
            if(lastname.getText().isEmpty())
                erorlastname.setText("*עליך למלא שדה זה");
            else
                erorlastname.setText("*ערך השדה אינו חוקי");
        }
        if (Validation.validatePassword(password.getText())) {
            user.setPassword(password.getText());
            erorpass.setVisible(false);
        }
        else
            erorpass.setVisible(true);
        if (Validation.validateDate(birthdate.getText())) {
            if(Validation.validateAge(birthdate.getText())>17) {
                user.setBirthDate(birthdate.getText());
                erordate.setVisible(false);
            }
            else{
                erordate.setVisible(true);
                erordate.setText("*גיל המשתמש חייב להיות מעל 18");
            }
        }
        else {
            erordate.setVisible(true);
            erordate.setText("*הקלד תאריך תקין בפורמט dd/mm/yyyy");
        }
        if (Validation.validatecity(city.getValue().toString())) {
            user.setCity(city.getValue().toString());
            erorcity.setVisible(false);
        }
        else
            erorcity.setVisible(true);
        //user.print();
        if ((user.getUserName() != null && user.getBirthDate() != null && user.getCity() != null && user.getEmail() != null && user.getFirstName() != null && user.getLastName() != null && user.getPassword() != null))
        {
            if(agreeSign.isSelected()&&file!=null) {
                user.print();
                try {
                    uploadPic(file,file.toPath());
                    int result = Query.insert((user));
                    if (result == 0) {
                        regmsg();
                        Main.switchScene("../View/LoginForm.fxml", (Stage) sign.getScene().getWindow(), Main.loginWidth, Main.loginHeight);
                    }
                    else if (result == 1)
                        errorregmsg();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                if (file==null)
                {
                    erorterms.setText("*אנא העלה תמונה");
                    erorterms.setVisible(true);
                }
                else
                {
                    erorterms.setText("*אנא אשר את התנאים");
                    erorterms.setVisible(true);
                }
                }
            }


    }

    //alert about successful registration
   private void regmsg(){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("תודה שנרשמת \n הינך מועבר לדף ההתחברות ");
        alert.showAndWait();
        alert.close();
    }
    //alert about bad registration, username already exists
    private void errorregmsg(){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ארעה שגיאה בעת ההרשמה");
        alert.setContentText("שם המשתמש שנבחר קיים כבר במערכת");
        alert.showAndWait();
        alert.close();

    }


    public void OpenfileChoose() throws IOException {

        file = Main.openFileExplorer();
        if (file!=null) {
            //uploadPic(file,file.toPath());
            profilePic.setImage(new Image(new FileInputStream(file)));
        }
    }


    private void uploadPic(File file,Path sourceDirectory) throws IOException {
        if (username != null) {
            //the name of the picture that uploaded is the username
            Path targetDirectory = Paths.get(imageDBPath + username.getText() + "." + Main.getExtention(file));
            if (!new File(imageDBPath + file.getName()).exists()) {
                Files.copy(sourceDirectory, targetDirectory);
                user.setProfilePicPath(targetDirectory.toString());
            } else
                System.out.println("already exist");
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("נא מלא קודם את שם המשתמש");
            alert.showAndWait();
        }
    }

}