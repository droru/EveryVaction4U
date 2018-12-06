package View;

import Model.Flight;
import Model.User;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Aview;
import sample.Main;

import java.io.IOException;
import java.sql.Date;


public class MainScreenController extends Aview {
    @FXML
    public TextField txt_searchUser;
    public TextField txt_searchDestination;
    public GridPane advancedSearchBox;
    public CheckBox advanceSearchCheckbox;
    public Hyperlink LoginRegister;
    public VBox loggedUserBox;
    public TextArea textArea_firstFlight;
    public TextArea textArea_secondFlight;
    public TextArea textArea_thirdFlight;
    public TextArea textArea_fourthFlight;
    public TextArea textArea_fifthFlight;
    public Label lbl_welcome;
    public TableView<Flight> flightsTable;

    public  void initialize(){
        if(Main.loggedUser == null) {
            lbl_welcome.setText("שלום אורח");
            LoginRegister.setVisible(true);
            LoginRegister.managedProperty().bind(LoginRegister.visibleProperty());
            loggedUserBox.setVisible(false);
        }
        else {
            lbl_welcome.setText("שלום " + Main.loggedUser.getUserName());
            LoginRegister.setVisible(false);
            LoginRegister.managedProperty().bind(LoginRegister.visibleProperty());
            loggedUserBox.setVisible(true);
        }

        ObservableList<Flight> flights = getController().getAllFlights();
        setTableData(flights);
    }

    private void setTableData(ObservableList<Flight> flights) {
        TableColumn actionCol = new TableColumn();
        TableColumn<Flight, Integer> flightIDCol = new TableColumn<Flight, Integer>("מספר טיסה");
        TableColumn<Flight, String> destinationCol = new TableColumn<Flight, String>("יעד");
        TableColumn<Flight, String> destinationCountryCol = new TableColumn<Flight, String>("מדינה");
        TableColumn<Flight, String> destinationCityCol = new TableColumn<Flight, String>("עיר");
        TableColumn<Flight, Date> fromDateCol = new TableColumn<Flight, Date>("מתאריך");
        TableColumn<Flight, Date> toDateCol = new TableColumn<Flight, Date>("עד תאריך");
        TableColumn<Flight, Integer> priseCol = new TableColumn<Flight, Integer>("מחיר");
        TableColumn<Flight, String> isConnectionCol = new TableColumn<Flight, String>("קונקשיין");
        TableColumn<Flight, String> isSeparateCol = new TableColumn<Flight, String>("קניית כרטיסים בנפרד");
        TableColumn<Flight, String> companyCol = new TableColumn<Flight, String>("חברה");
        TableColumn<Flight, Integer> baggageCol = new TableColumn<Flight, Integer>("משקל כבודה");
        TableColumn<Flight, String> sellerCol = new TableColumn<Flight, String>("מוכר");//first name+last name

        actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        Callback<TableColumn<Flight, String>, TableCell<Flight, String>> cellFactory
                = new Callback<TableColumn<Flight, String>, TableCell<Flight, String>>() {
            @Override
            public TableCell call(final TableColumn<Flight, String> param) {
                final TableCell<Flight, String> cell = new TableCell<Flight, String>() {
                    final Button btn = new Button("לצפייה");
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                try {
                                    watchFlight(getTableView().getItems().get(getIndex()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        actionCol.setCellFactory(cellFactory);
        flightIDCol.setCellValueFactory(new PropertyValueFactory<>("flightID"));
        destinationCountryCol.setCellValueFactory(new PropertyValueFactory<>("destinationCountry"));
        destinationCityCol.setCellValueFactory(new PropertyValueFactory<>("destinationCity"));
        fromDateCol.setCellValueFactory(new PropertyValueFactory<>("fromDate"));
        toDateCol.setCellValueFactory(new PropertyValueFactory<>("toDate"));
        priseCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        isConnectionCol.setCellValueFactory(new PropertyValueFactory<>("isConnection"));
        isSeparateCol.setCellValueFactory(new PropertyValueFactory<>("isSeparate"));
        companyCol.setCellValueFactory(new PropertyValueFactory<>("company"));
        baggageCol.setCellValueFactory(new PropertyValueFactory<>("baggage"));
        sellerCol.setCellValueFactory(new PropertyValueFactory<>("seller.FirstName"));

        flightIDCol.setSortType(TableColumn.SortType.ASCENDING);
        flightsTable.setItems(flights);
        destinationCol.getColumns().addAll(destinationCountryCol, destinationCityCol);
        //flightsTable.getColumns().addAll(actionCol, flightIDCol, destinationCol, fromDateCol, toDateCol, priseCol, sellerCol, companyCol, baggageCol, isConnectionCol, isSeparateCol);
        flightsTable.getColumns().addAll(actionCol, flightIDCol, destinationCol, fromDateCol, toDateCol, priseCol, companyCol);


        FilteredList<Flight> filteredData = new FilteredList<>(flights, p -> true);
        txt_searchDestination.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(flight -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (flight.getDestinationCountry().toLowerCase().contains(lowerCaseFilter) ||
                    flight.getDestinationCity().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });
        SortedList<Flight> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(flightsTable.comparatorProperty());
        flightsTable.setItems(sortedData);
    }

    public void advanceSearchChacked(){
        if(advanceSearchCheckbox.isSelected())
            advancedSearchBox.setVisible(true);
        else
            advancedSearchBox.setVisible(false);
        advancedSearchBox.managedProperty().bind(advancedSearchBox.visibleProperty());
    }

    public void loginClicked() throws IOException {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../View/LoginForm.fxml"));
        StageDetail(stage, root, Main.loginWidth, Main.loginHeight, "Login/register");
        LoginRegister.setDisable(true);
    }
    public void logOutClicked(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("האם אתה בטוח שאתה רוצה להתנתק?");
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            Main.loggedUser = null;
            initialize();
        }
        else{
            alert.close();
        }
    }
    public void seeProfileClicked(){
        Main.isProfile=true;
        Main.switchScene("../View/UserDetailsScreen.fxml", Main.getStage(), Main.mainWidth,Main.mainHeight);

    }
    public void userSearchPresses(KeyEvent keyEvent) throws IOException {
        User userSerach;
        if(keyEvent.getCode().equals(KeyCode.ENTER))
        {
            userSerach=getController().search(txt_searchUser.getText());
            if (userSerach!=null) {
                userSerach.print();
                Main.setUser(userSerach);
                Main.isProfile = false;
                Stage stage=new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("../View/UserDetailsScreen.fxml"));
                StageDetail(stage, root, Main.registerWidth, Main.registerHeight, "User profile");
            }
            else {
                System.out.println("not found");
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("לא נמצאו תוצאות");
                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                alert.showAndWait();
            }
        }
    }

    public void watchFlight(Flight flight) throws IOException {
        Stage stage=new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/FlightDetailScreen.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        FlightDetailScreenController controller = fxmlLoader.getController();
        controller.setFlight(flight);

        StageDetail(stage, root, 825, 364, "Flight details");
        LoginRegister.setDisable(true);

        //Main.switchScene("../View/FlightDetailScreen.fxml", Main.getStage(), 825,364);
    }

    private void StageDetail(Stage stage, Parent root, int width, int height, String title) {
        stage.setTitle(title);
        Scene scene=new Scene(root,width,height);
        scene.getStylesheets().add(getClass().getResource("../View/Style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(txt_searchUser.getScene().getWindow());
        stage.show();
        stage.setOnCloseRequest(event -> LoginRegister.setDisable(false));
    }


    public void addFlightClicked(ActionEvent actionEvent) {
        Main.switchScene("../View/AddFlightScreen.fxml", Main.getStage(), Main.registerWidth,Main.registerHeight);
    }
}

