package screens.controller;

import classes.Appointment;
import classes.Doctor;
import classes.Pacient;
import dataStructure.Queue;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HomeController {

  private static Stage utilityStage;

  @FXML
  private Button addButton;

  @FXML
  private TableColumn<Appointment, IntegerProperty> codeCol;

  @FXML
  private TableColumn<Appointment, LocalDate> dateCol;

  @FXML
  private TableColumn<Appointment, Doctor> doctorCol;

  @FXML
  private TableColumn<Appointment, String> localCol;

  @FXML
  private TableColumn<Appointment, Pacient> pacientCol;

  @FXML
  private TableColumn<Appointment, String> statusCol;

  @FXML
  private TableColumn<Appointment, String> timeCol;

  @FXML
  private TableView<Appointment> tableView;

  @FXML
  private HBox cardContainer;

  private Queue<Appointment> appointmentQueue = new Queue<>();
  private static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

  public void initialize() {
    tableView.setItems(appointmentList);
    setCellValueFactory();

    tableView
      .getSelectionModel()
      .selectedItemProperty()
      .addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
          openRegisterStage(newSelection);
        }
      });
  }

  @FXML
  void handleAddButtonAction(ActionEvent event) {
    openRegisterStage(null);
  }

  private void openRegisterStage(Appointment selectedAppointment) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getResource("../appointmentsRegister.fxml")
      );
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();

      AppointmentsRegisterController registerController = fxmlLoader.getController();
      registerController.setParentController(this);
      registerController.setEditAppointment(selectedAppointment);

      fxmlLoader.setController(registerController);

      Stage primaryStage = (Stage) addButton.getScene().getWindow();
      stage.initOwner(primaryStage);

      stage.initStyle(StageStyle.UTILITY);

      utilityStage = stage;

      stage.setTitle("Nova consulta");
      stage.setScene(new Scene(root1));
      stage.show();

      stage.setOnHiding(event -> {
        editAppointmentData();
      });
    } catch (Exception e) {
      System.out.println("Can't load new window");
    }
  }

  private void setCellValueFactory() {
    codeCol.setCellValueFactory(
      new PropertyValueFactory<Appointment, IntegerProperty>("code")
    );
    pacientCol.setCellValueFactory(
      new PropertyValueFactory<Appointment, Pacient>("pacient")
    );
    dateCol.setCellValueFactory(
      new PropertyValueFactory<Appointment, LocalDate>("date")
    );
    timeCol.setCellValueFactory(
      new PropertyValueFactory<Appointment, String>("time")
    );
    localCol.setCellValueFactory(
      new PropertyValueFactory<Appointment, String>("local")
    );
    doctorCol.setCellValueFactory(
      new PropertyValueFactory<Appointment, Doctor>("doctor")
    );
    statusCol.setCellValueFactory(
      new PropertyValueFactory<Appointment, String>("status")
    );

    pacientCol.setCellFactory(param ->
      new TableCell<Appointment, Pacient>() {
        @Override
        protected void updateItem(Pacient item, boolean empty) {
          super.updateItem(item, empty);

          if (item == null || empty) {
            setText(null);
          } else {
            setText(
              item.getCode() +
              " - " +
              item.getName() +
              " (" +
              item.getIdentifier() +
              ")"
            );
          }
        }
      }
    );

    doctorCol.setCellFactory(param ->
      new TableCell<Appointment, Doctor>() {
        @Override
        protected void updateItem(Doctor item, boolean empty) {
          super.updateItem(item, empty);

          if (item == null || empty) {
            setText(null);
          } else {
            setText(
              item.getCode() +
              " - " +
              item.getName() +
              " (" +
              item.getIdentifier() +
              ")"
            );
          }
        }
      }
    );
  }

  public void addAppointmentData(Appointment appointment) {
    tableView.getItems().add(appointment);
    addToQueue(appointment);
    tableView.refresh();
  }

  public void editAppointmentData() {
    tableView.getSelectionModel().clearSelection();
    tableView.getSelectionModel().select(null);
    tableView.refresh();
    updateCardQueue();
  }

  public void removeAppointmentData(Appointment appointment) {
    if (appointment != null) {
      tableView.getSelectionModel().clearSelection();
      tableView.getSelectionModel().select(null);
      appointmentList.remove(appointment);
      removeFromQueue(appointment);
      tableView.refresh();
    }
  }

  public void addToQueue(Appointment appointment) {
    appointmentQueue.enqueue(appointment);

    appointmentQueue
      .getElements()
      .sort(
        Comparator
          .comparing(Appointment::getDate)
          .thenComparing(Appointment::getTime)
      );

    updateCardQueue();
  }

  public void removeFromQueue(Appointment appointment) {
    if (appointment != null) {
      appointmentQueue.getElements().remove(appointment);
      updateCardQueue();
    }
  }

  private void updateCardQueue() {
    cardContainer.getChildren().clear();
    for (Appointment appointment : appointmentQueue.getElements()) {
      if (appointmentList.contains(appointment)) {
        createAndAddAppointmentCard(appointment);
      }
    }
  }

  private void createAndAddAppointmentCard(Appointment appointment) {
    FXMLLoader loader = new FXMLLoader(
      getClass().getResource("../appointmentCard.fxml")
    );
    try {
      AnchorPane card = loader.load();
      AppointmentCardController controller = loader.getController();
      controller.initialize(appointment);
      cardContainer.getChildren().add(card);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Stage getUtilityStage() {
    return utilityStage;
  }

  public static ObservableList<Appointment> getAppointmentsList() {
    return appointmentList;
  }
}
