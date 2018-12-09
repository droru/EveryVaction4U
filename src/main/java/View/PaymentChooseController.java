package View;

import Model.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import sample.Aview;
import sample.Main;

public class PaymentChooseController extends Aview {

    public ImageView ImPaypal;
    public Button btn_pay;
    public Hyperlink link_backtoMain;
    public RadioButton paypalRB;
    public RadioButton creditcardRB;
    public Pane creditPane;
    public GridPane creditGridPane;
    public TextField txt_creditname;
    public TextField txt_creditnum;
    public TextField txt_exp;
    public TextField txt_cvs;
    public ComboBox cbox_credittype;
    public ObservableList<String> credittype;

    public Label eror_name;
    public Label eror_creditnumber;
    public Label eror_exp;
    public Label eror_type;
    public Label eror_cvs;
    public Hyperlink link_cvs;

    private final String[] list ={"Visa","MasterCard","DinersCard"};

    private boolean isPay=true;

    public void initialize(){

        Image paypalImage= new Image("/paypal.jpg");
        ImPaypal.setImage(paypalImage);
        credittype=FXCollections.observableArrayList(list);
        creditGridPane.setDisable(true);
        cbox_credittype.setItems(credittype);
    }

    public boolean isPay() {
        return isPay;
    }

    private void paypalClicked() throws IOException {
        //getController().delete(Main.not.getFlightID());
        ((Stage)this.eror_type.getScene().getWindow()).close();
        openNewStage("אישור תשלום",472,400,"../View/FinishTranscation.fxml");
    }

    private void openNewStage(String title ,int Height,int Width,String Fxml) throws IOException {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(Fxml));
        Scene scene=new Scene(root,Height ,Width);
        stage.setTitle(title);
        scene.getStylesheets().add(getClass().getResource("../View/Style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        //stage.initOwner(btn_buy.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    public void RbPaypalClicked(MouseEvent mouseEvent) {
            creditcardRB.setSelected(false);
            creditGridPane.setDisable(true);
            ClearGridPaneText();
    }

    private void ClearGridPaneText() {
        txt_creditname.setText("");
        txt_creditnum.setText("");
        txt_cvs.setText("");
        txt_exp.setText("");
        cbox_credittype.setValue(null);

    }

    public void RbcreditClicked(MouseEvent mouseEvent) {
        paypalRB.setSelected(false);
        creditGridPane.setDisable(false);

    }

    public void exitPay(MouseEvent mouseEvent) {
        ((Stage)link_backtoMain.getScene().getWindow()).close();
    }

    public void ConPay(MouseEvent mouseEvent) throws IOException {
        if(paypalRB.isSelected())
            paypalClicked();
        else if (creditcardRB.isSelected())
            CreditcardClick();
        else
            showAlertPay();
    }

    private void showAlertPay() {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("עליך לבחור אמצעי תשלום");
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.showAndWait();
    }

    private void CreditcardClick() throws IOException {
        isPay = true;
        if (txt_creditname.getText().equals("")) {
            eror_name.setText("עליך למלא שדה זה");
            isPay = false;
        } else if (!Validation.validateName(txt_creditname.getText())) {
            eror_name.setText("הכנס שם חוקי");
            isPay = false;
        } else
            eror_name.setText("");


        if (txt_creditnum.getText().equals("")) {
            eror_creditnumber.setText("עליך למלא שדה זה");
            isPay = false;
        } else if (!Validation.isNumber(txt_creditnum.getText()) || txt_creditnum.getText().length() != 16){
            eror_creditnumber.setText("נא להכניס מספר בעל 16 ספרות");
        isPay = false;
        }else
            eror_creditnumber.setText("");

        if (cbox_credittype.getValue()==null) {
            eror_type.setText("בחר סוג כרטיס");
            isPay=false;
        }
        else
            eror_type.setText("");

        if(txt_cvs.getText().equals("")) {
            eror_cvs.setText("עליך למלא שדה זה");
            isPay=false;
        }
        else if (txt_cvs.getText().length()!=3||!Validation.isNumber(txt_cvs.getText().toString())) {
            isPay=false;
            eror_cvs.setText("קוד cvv צריך להכיל 3 ספרות");
        }
        else
            eror_cvs.setText("");




        if(txt_exp.getText().equals("")) {
            eror_exp.setText("עליך למלא שדה זה");
            isPay = false;
        }else
        {
            if(!txt_exp.getText().contains("/")) {
                eror_exp.setText("הכנס תאריך בפורמט MM/YEAR");
                isPay = false;
            }
            else {
                String Month = txt_exp.getText().toString().split("/")[0];
                String Year = txt_exp.getText().toString().split("/")[1];
                if (Validation.isNumber(Month)&&Validation.isNumber(Year))
                    if(Integer.parseInt(Month)>13||Integer.parseInt(Year)< LocalDate.now().getYear()) {
                        eror_exp.setText("הכנס תאריך חוקי");
                        isPay=false;
                    }
                    else
                        eror_exp.setText("");
                else{
                    eror_exp.setText("נא הכנס תאריך בתור ספרות");
                    isPay=false;
                }
            }

        }

        if(isPay) {
            ((Stage)this.eror_type.getScene().getWindow()).close();
            openNewStage("אישור תשלום", 472, 400, "../View/FinishTranscation.fxml");
            }
        }

    public void showcvshelp(ActionEvent actionEvent) {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        Image image=new Image("/cvvhelp.jpg");
        alert.setGraphic(new ImageView(image));
        alert.setTitle("Cvv help");
        alert.setContentText("");
        alert.showAndWait();
    }
}


