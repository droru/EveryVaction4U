package View;
import Model.User;

import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Aview;
import sample.Main;

import java.sql.SQLException;


public class LoginController extends Aview {
    public Button LoginButton;
    public TextField Usernamefield;
    public PasswordField Passwordfield;
    public Hyperlink Registerlink;
    public Label erorm2;



    public void Loginclicked() throws SQLException {
        User user;
        if (isFilednotempty(Usernamefield.getText(), Passwordfield.getText())) {
            user= getController().search(Usernamefield.getText());
            if (user != null) {
                if (user.getPassword().equals(Passwordfield.getText())) {
                    System.out.println("user found");
                    erorm2.setText("");
                    Main.loggedUser = user;
                    Main.switchScene("../View/MainScreen.fxml", Main.getStage(), Main.mainWidth, Main.mainHeight);
                    ((Stage) LoginButton.getScene().getWindow()).close();
                } else {
                    erorm2.setText("*שם משתמש או סיסמא לא נכונים");
                }
            }
            else
                erorm2.setText("*שם משתמש לא קיים");

        } else
            erorm2.setText("*נא להכניס שדות חוקיים");
    }


    public void openregister() {
        Main.switchScene("../View/RegisterForm.fxml", (Stage) LoginButton.getScene().getWindow(), Main.registerWidth, Main.registerHeight);
    }

    private boolean isFilednotempty(String user, String Pass) {
        return !user.isEmpty() && !Pass.isEmpty();
    }





}