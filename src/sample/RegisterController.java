package sample;

import Model.Query;
import Model.User;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



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
    public FlowPane Pane;
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

// regular expression for mail valid

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_USER_NAME_LAST_REGEX =
            Pattern.compile("(?<=\\s|^)[a-zA-Z][a-zA-Z]*(?=[.,;:]?\\s|$)",Pattern.UNICODE_CASE);

    public static final Pattern VALID_Date =
            Pattern.compile("^([0-2][0-9]|3[0-1])/(0[0-9]|1[0-2])/([0-9][0-9])?[0-9][0-9]$",Pattern.CASE_INSENSITIVE);


    public static boolean validateDate(String emailStr) {
        Matcher matcher = VALID_Date .matcher(emailStr);
        return matcher.find();
    }

    public static boolean validateMail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public static boolean validateName(String value) {
        Matcher matcher = VALID_USER_NAME_LAST_REGEX .matcher(value);
        return matcher.find();
    }
    public static boolean validatePassword(String text) {
        return text.length() > 3 && text.length() < 9 && !text.contains(" ");
    }
    public static boolean validatecity(String text) {
        return !text.equals("בחר");
    }

    public int validateAge( ) {
        Date user_date = new Date();           //the date that the user enter.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            user_date = formatter.parse(birthdate.getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LocalDate start = LocalDate.of(user_date.getYear() + 1900, user_date.getMonth() + 1, user_date.getDate());
        LocalDate end = LocalDate.now();
        long years = ChronoUnit.YEARS.between(start, end);

        return (int) years;
    }

    public void signclicked() throws InterruptedException {
        if (!username.getText().isEmpty()) {
            user.setUserName(username.getText());
            erorusername.setVisible(false);
        }
        else
            erorusername.setVisible(true);
        if (validateMail(email.getText())) {
            user.setEmail(email.getText());
            erormail.setVisible(false);
        }
        else
            erormail.setVisible(true);

        if (validateName(firstname.getText())) {
            user.setFirstName(firstname.getText());
            erorname.setVisible(false);
        }
        else
            erorname.setVisible(true);
        if (validateName(lastname.getText())) {
            user.setLastName(lastname.getText());
            erorlastname.setVisible(false);
        }
        else
            erorlastname.setVisible(true);
        if (validatePassword(password.getText())) {
            user.setPassword(password.getText());
            erorpass.setVisible(false);
        }
            else
            erorpass.setVisible(true);
        if (validateDate(birthdate.getText()) && validateAge( )>17) {
            user.setBirthDate(birthdate.getText());
            erordate.setVisible(false);
        }
        else
            erordate.setVisible(true);
        if (validatecity(city.getValue().toString())) {
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
                    Query.insert((user));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                regmsg();
                Main.switchScene("../View/LoginForm.fxml", (Stage) sign.getScene().getWindow(), 400, 300);
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

   private void regmsg(){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("תודה שנרשמת \n הינך מועבר לדף ההתחברות ");
        alert.showAndWait();
        alert.close();

    }


    public void OpenfileChoose() throws IOException {

        file = Main.openFileExplorer();
        if (file!=null) {
            //uploadPic(file,file.toPath());
            //profilePic.setImage(new Image(new FileInputStream(imageDBPath+file.getName())));//cwd+"/src/"+file.getName())));
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