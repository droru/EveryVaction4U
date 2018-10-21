package sample;
import Model.User;

import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.SQLException;
import static Model.Query.search;


public class LoginController {
    public Button LoginButton;
    public TextField Usernamefield;
    public PasswordField Passwordfield;
    public Hyperlink Registerlink;
    public Label erorm;
    public Label erorm2;



    public void Loginclicked() throws SQLException {
        User user;
        //System.out.println("Login clicked");
        if (isFilednotempty(Usernamefield.getText(), Passwordfield.getText())) {
            user = search(Usernamefield.getText());
            if (user != null) {
                if (user.getPassword().equals(Passwordfield.getText())) {
                    System.out.println("user found");
                    Main.loggedUser = user;
                    Main.switchScene("../View/MainScreen.fxml", Main.getStage(), 1000, 500);
                    ((Stage) LoginButton.getScene().getWindow()).close();
                } else {
                    erorm.setVisible(false);
                    erorm2.setVisible(true);
                }
            }

        } else
            erorm.setVisible(true);

    }


    public void openregister() {
        Main.switchScene("../View/RegisterForm.fxml", (Stage) LoginButton.getScene().getWindow(), 720, 500);
    }

    private boolean isFilednotempty(String user, String Pass) {
        return !user.isEmpty() && !Pass.isEmpty();
    }





}