package sample;


import Model.User;
import javafx.scene.control.*;

import java.sql.SQLException;

import static Model.Query.search;
import static Model.Query.search_by_pass;
import static Model.Query.update;


public class SwitchPasswordController
{
    public TextField curpass;
    public TextField newpass;
    public TextField repeat;

    public Label lbl_pass;
    public Label lbl_newpass;
    public Label repat_pass;

    public void update_pass() throws SQLException {
        boolean flag = true;
       User u =  search(Main.loggedUser.getUserName());
       if(u.getPassword().equals(curpass.getText()) == false ) {
           lbl_pass.setVisible(true);
           flag=false;
       }
       else
           lbl_pass.setVisible(false);

         u =  search_by_pass(newpass.getText());
        if(u != null|| newpass.getText().equals("")) {
            lbl_newpass.setVisible(true);
            flag=false;

        }
        else
            {
            lbl_newpass.setVisible(false);
                System.out.println( newpass.getText());
        }

        if(repeat.getText().equals(newpass.getText()) == false) {
            repat_pass.setVisible(true);
            flag=false;
        }
        else
            {
            repat_pass.setVisible(false);
        }

        if(flag == true) {
            Main.loggedUser.setPassword(newpass.getText());
            update(Main.loggedUser);
        }
    }


}
