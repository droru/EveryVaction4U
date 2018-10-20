package sample;

import Model.Query;
import Model.User;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

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


    // regular expression for mail valid

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_USER_NAME_LAST_REGEX =
            Pattern.compile("(?<=\\s|^)[a-zA-Z][a-zA-Z]*(?=[.,;:]?\\s|$)",Pattern.UNICODE_CASE);

    public static final Pattern VALID_Date =
            Pattern.compile("^([0-2][0-9]|3[0-1])/(0[0-9]|1[0-2])/([0-9][0-9])?[0-9][0-9]$",Pattern.CASE_INSENSITIVE);
    public FlowPane Pane;


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


    public void signclicked() throws InterruptedException {
        User user = new User();
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
        if (validateDate(birthdate.getText())) {
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
            if(agreeSign.isSelected()) {
                Query.insert((user));
                regmsg();
                Main.switchScene("../View/LoginForm.fxml", (Stage) sign.getScene().getWindow(), 720, 500);
            }
            else
                erorterms.setVisible(true);
        }


    }

   private void regmsg() throws InterruptedException {
        Alert alert=new Alert(Alert.AlertType.NONE);
        alert.setContentText("תודה שנרשמת \n הינך מועבר לדף ההתחברות ");
        alert.showAndWait();

    }

}
