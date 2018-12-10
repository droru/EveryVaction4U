package View;

import Model.Flight;
import Model.Notification;
import Model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Aview;
import sample.Main;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class MainScreenController extends Aview {
    @FXML
    public TextField txt_searchUser;
    public TextField txt_searchDestination;
    public GridPane advancedSearchBox;
    public CheckBox advanceSearchCheckbox;
    public CheckBox cb_isSeparate;
    public ComboBox combo_baggage;
    public CheckBox cb_isConnection;
    public DatePicker dp_fromDate;
    public DatePicker dp_toDate;
    public ComboBox combo_company;//
    public ComboBox combo_sort;

    public Hyperlink LoginRegister;
    public VBox loggedUserBox;
    public TextArea textArea_firstFlight;
    public TextArea textArea_secondFlight;
    public TextArea textArea_thirdFlight;
    public TextArea textArea_fourthFlight;
    public TextArea textArea_fifthFlight;
    public Label lbl_welcome;
    public TableView<Flight> flightsTable;
    public VBox notificationPane;

    public void initialize() {
        if (Main.loggedUser == null) {
            lbl_welcome.setText("שלום אורח");
            LoginRegister.setVisible(true);
            LoginRegister.managedProperty().bind(LoginRegister.visibleProperty());
            loggedUserBox.setVisible(false);
        } else {
            lbl_welcome.setText("שלום " + Main.loggedUser.getUserName());
            LoginRegister.setVisible(false);
            LoginRegister.managedProperty().bind(LoginRegister.visibleProperty());
            loggedUserBox.setVisible(true);
        }

        advancedSearchBox.managedProperty().bind(advancedSearchBox.visibleProperty());
        if (flightsTable.getColumns().size() == 0) {
            ObservableList<Flight> flights = getController().getAllFlights();
            setTableData(flights);
        }
        if (Main.loggedUser != null) {
            List<Notification> notifications = getController().getNotificationsByUser(Main.loggedUser.getUserName());
            setNotificationPane(notifications);
        }
        if (combo_company.getItems().size() == 0) {
            ObservableList<String> companies = getController().getAllCompanies();
            companies.add("הכל");
            combo_company.setItems(companies);
        }
    }

    public void removeFlight(int flightID) {
        List<Flight> flights = new ArrayList<>(flightsTable.getItems());
        flights.removeIf(o -> o.getFlightID() == flightID);
        ObservableList<Flight> observableFlights = FXCollections.observableArrayList(flights);
        flightsTable.setItems(observableFlights);
        setFilters(observableFlights);
    }

    private void setNotificationPane(List<Notification> notifications) {
        for (Notification notification : notifications) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/NotificationDetailsBox.fxml"));
            Parent root = null;
            try {
                root = (Parent) fxmlLoader.load();
                NotificationDetailsBoxController controller = fxmlLoader.getController();
                controller.setData(notification, notificationPane, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root, 250, 50);
            scene.getStylesheets().add(getClass().getResource("../View/Style.css").toExternalForm());
            notificationPane.getChildren().add(scene.getRoot());
        }
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
        fromDateCol.setCellFactory(column -> {
            return formatDate();
        });
        toDateCol.setCellValueFactory(new PropertyValueFactory<>("toDate"));
        toDateCol.setCellFactory(column -> {
            return formatDate();
        });
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

        setFilters(flights);
    }

    private void setFilters(ObservableList<Flight> flights) {
        combo_sort.valueProperty().addListener((observable -> {
            if (combo_sort.getSelectionModel().getSelectedItem() == null || combo_sort.getSelectionModel().getSelectedItem().equals("לפי מס' טיסה")) {
                FXCollections.sort(flights, Comparator.comparingInt(Flight::getFlightID));
            } else {
                Comparator<Flight> priceComparator = Comparator.comparingInt(Flight::getPrice);
                if (combo_sort.getSelectionModel().getSelectedItem().equals("מהיקר לזול"))
                    priceComparator = priceComparator.reversed();
                FXCollections.sort(flights, priceComparator);
            }
        }));

        FilteredList<Flight> filteredData = new FilteredList<>(flights, p -> true);

        txt_searchDestination.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(flight -> filter(flight, newValue)));
        cb_isSeparate.selectedProperty().addListener((observable) -> filteredData.setPredicate(flight -> filter(flight, "")));
        combo_baggage.valueProperty().addListener((obs) -> filteredData.setPredicate(flight -> filter(flight, "")));
        cb_isConnection.selectedProperty().addListener((observable -> filteredData.setPredicate(flight -> filter(flight, ""))));
        dp_fromDate.valueProperty().addListener((observable -> filteredData.setPredicate(flight -> filter(flight, ""))));
        dp_toDate.valueProperty().addListener((observable -> filteredData.setPredicate(flight -> filter(flight, ""))));
        combo_company.valueProperty().addListener((observable -> filteredData.setPredicate(flight -> filter(flight, ""))));

        SortedList<Flight> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(flightsTable.comparatorProperty());
        flightsTable.setItems(sortedData);
    }

    public boolean filter(Flight flight, String newValue) {
        //text destination
        /*if (newValue != null && !newValue.isEmpty()) {
            return false;
        }*/
        String lowerCaseFilter = newValue.toLowerCase();
        if (!flight.getDestinationCountry().toLowerCase().contains(lowerCaseFilter) &&
                !flight.getDestinationCity().toLowerCase().contains(lowerCaseFilter)) {
            return false;
        }

        //company
        if (combo_company.getSelectionModel().getSelectedItem() != null && !combo_company.getSelectionModel().getSelectedItem().equals("הכל") && !combo_company.getSelectionModel().getSelectedItem().equals(flight.getCompany()))
            return false;

        //date
        if (dp_fromDate.getValue() != null) {
            Instant instant = Instant.from(dp_fromDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            java.util.Date from = java.util.Date.from(instant);
            if (flight.getFromDate().before(from))
                return false;
        }
        if (dp_toDate.getValue() != null) {
            Instant instant = Instant.from(dp_toDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            java.util.Date to = java.util.Date.from(instant);
            if (flight.getToDate().after(to))
                return false;
        }

        //is connection
        if (cb_isConnection.isSelected() && !flight.isConnection())
            return false;

        //baggage
        if (combo_baggage.getSelectionModel().getSelectedItem() != null && !combo_baggage.getSelectionModel().getSelectedItem().equals("הכל") && !combo_baggage.getSelectionModel().getSelectedItem().equals(String.valueOf(flight.getBaggage())))
            return false;

        //is separate
        if (cb_isSeparate.isSelected() && !flight.isSeparate())
            return false;

        return true;
    }

    private TableCell<Flight, Date> formatDate() {
        TableCell<Flight, Date> cell = new TableCell<Flight, Date>() {
            private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(format.format(item));
                }
            }
        };
        return cell;
    }

    public void advanceSearchChacked() {
        if (advanceSearchCheckbox.isSelected())
            advancedSearchBox.setVisible(true);
        else {
            advancedSearchBox.setVisible(false);

            combo_sort.getSelectionModel().select(null);
            combo_company.getSelectionModel().select(null);
            dp_fromDate.setValue(null);
            dp_toDate.setValue(null);
            cb_isConnection.setSelected(false);
            combo_baggage.getSelectionModel().select(null);
            cb_isSeparate.setSelected(false);
        }
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
            notificationPane.getChildren().clear();
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

        //Main.switchScene("../View/FlightDetailScreen.fxml", Main.getStage(), 825,364);
    }

    private void  StageDetail(Stage stage, Parent root, int width, int height, String title) {
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

