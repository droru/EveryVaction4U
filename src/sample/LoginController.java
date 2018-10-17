package sample;

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
       System.out.println("Login clicked");
       if (isFilednotempty(Usernamefield.getText(),Passwordfield.getText()))
           System.out.println(Usernamefield.getText()+" " +Passwordfield.getText() +" Login");
           else
           erorm2.setVisible(true);


   }

public void openregister(){
    System.out.println("new user");
}
   private boolean isFilednotempty(String user,String Pass){
      if(user.isEmpty()||Pass.isEmpty())
            return false;
      return true;
   }


}
