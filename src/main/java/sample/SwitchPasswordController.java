package sample;

import Model.User;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.SQLException;
import static Model.Query.search;
import static Model.Query.update;


public class SwitchPasswordController
{
    public PasswordField curpass;
    public PasswordField newpass;
    public PasswordField repeat;

    public Label lbl_pass;
    public Label lbl_newpass;
    public Label repat_pass;

    private boolean changePass=false;


    public void update_pass() throws SQLException {
        lbl_pass.setVisible(false);
        repat_pass.setVisible(false);
        lbl_newpass.setVisible(false);
        User u =  search(Main.loggedUser.getUserName());
            if(!u.getPassword().equals(curpass.getText())) {
                lbl_pass.setText("*סיסמא ישנה אינה נכונה");
                lbl_pass.setVisible(true);
            }
            else if(newpass.getText().length()<4 || newpass.getText().length()>8) {
                //lbl_pass.setVisible(false);
                lbl_newpass.setText("*הכנס סיסמא בין 4-8 תווים");
                lbl_newpass.setVisible(true);
                if(repeat.getText().length()<4 || repeat.getText().length()>8) {
                    repat_pass.setText("*הכנס סיסמא בין 4-8 תווים");
                    repat_pass.setVisible(true);
                }
            }
            else {
                lbl_pass.setVisible(false);
                lbl_newpass.setVisible(false);
                repat_pass.setVisible(false);
                changePass=true;
            }


            if (repeat.getText().equals(newpass.getText())&&u.getPassword().equals(curpass.getText())&&changePass) {
                u.setPassword(newpass.getText());
                update(u);
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("עדכון ססמא בוצע!");
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    ((Stage) repat_pass.getScene().getWindow()).close();
                }
            }

            else if (!repeat.getText().equals(newpass.getText())){
                repat_pass.setText("*סיסמאות לא תואמות");
                repat_pass.setVisible(true);
            }
        }
    }

