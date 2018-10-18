package sample;
import Model.Query;
import Model.User;
import javafx.scene.control.*;

import java.security.PublicKey;


public class LoginController {
   public Button LoginButton;
   public TextField Usernamefield;
   public PasswordField Passwordfield;
    public Hyperlink Registerlink;
    public Label erorm;
    public Label erorm2;

   public void Loginclicked(){
       User user =new User();
       System.out.println("Login clicked");
       if (isFilednotempty(Usernamefield.getText(),Passwordfield.getText()))
           System.out.println("search");
           // user=search(Usernamefield.getText());
           else
           erorm.setVisible(true);
        if (user.getUserName()==null) {
            erorm.setVisible(false);
            erorm2.setVisible(true);
        }
   }


public void openregister(){
    System.out.println("new user");
}
   private boolean isFilednotempty(String user,String Pass){
       return !user.isEmpty() && !Pass.isEmpty();
   }


}
