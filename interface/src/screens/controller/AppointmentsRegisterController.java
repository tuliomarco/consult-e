package screens.controller;

import classes.Appointment;
import classes.Doctor;
import classes.Pacient;
import classes.Person;
import common.tools.CurrencyField;
import common.tools.Mask;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AppointmentsRegisterController {

  @FXML
  private DatePicker date;

  @FXML
  private TextField description;

  @FXML
  private ComboBox<Doctor> doctor;

  @FXML
  private TextField local;

  @FXML
  private ComboBox<Pacient> pacient;

  @FXML
  private ComboBox<String> status;

  @FXML
  private TextField time;

  @FXML
  private CurrencyField value;

  @FXML
  private Button saveButton;

  private HomeController parentController;

  private Appointment selectedAppointment;

  // Método para receber o controlador pai
  public void setParentController(HomeController parentController) {
    this.parentController = parentController;
  }

  public void setEditAppointment(Appointment appointment) {
    this.selectedAppointment = appointment;
    if (selectedAppointment != null) {
      preencherFormulario(selectedAppointment);
  }
  }

    public void initialize() {
        saveButton.setDisable(true);
        getSelectsData();
        loadHandlers();

        Mask.configureTimeMask(time);
    }

    private void getSelectsData() {
        ObservableList<String> statusList = FXCollections.observableArrayList(
            "Em espera",
            "Em atendimento",
            "Finalizada",
            "Retorno",
            "Cancelada"
    );

    status.setItems(statusList); 
    pacient.setItems(PacientsController.getPacientesList());
    renderPersonView(pacient);
    doctor.setItems(DoctorsController.getDoctorsList());
    renderPersonView(doctor);

    value.setCurrencyFormat(new Locale("pt", "BR"));
    }

    private <T extends Person> void renderPersonView(ComboBox<T> person) {
        person.setCellFactory(param -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
    
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getCode() + " - " + item.getName() + " (" + item.getIdentifier() + ")");
                }
            }
        });

        person.setConverter(new StringConverter<T>() {
            @Override
            public String toString(Person person) {
                return (person != null) ? person.getCode() + " - " + person.getName() + " (" + person.getIdentifier() + ")" : null;
            }

            @Override
            public T fromString(String string) {
                return null;
            }
        });

    }
    

  private void loadHandlers() {
    date.valueProperty().addListener((observable, oldValue, newValue) -> validarCamposObrigatorios());
    description.textProperty().addListener((observable, oldValue, newValue) -> validarCamposObrigatorios());
    doctor.valueProperty().addListener((observable, oldValue, newValue) -> validarCamposObrigatorios());
    time.textProperty().addListener((observable, oldValue, newValue) -> validarCamposObrigatorios());
    pacient.valueProperty().addListener((observable, oldValue, newValue) -> validarCamposObrigatorios());
    status.valueProperty().addListener((observable, oldValue, newValue) -> validarCamposObrigatorios());
    local.textProperty().addListener((observable, oldValue, newValue) -> validarCamposObrigatorios());
    value.textProperty().addListener((observable, oldValue, newValue) -> validarCamposObrigatorios());
}

private void validarCamposObrigatorios() {
    boolean camposPreenchidos = pacient.getValue() != null
            && date.getValue() != null
            && (!time.getText().isEmpty() && time.getText().length() == 5)
            && doctor.getValue() != null
            && status.getValue() != null
            && !local.getText().isEmpty();

    saveButton.setDisable(!camposPreenchidos);
}

  private void preencherFormulario(Appointment selectedItem) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        date.getEditor().setText(selectedItem.getDate().format(formatter));
        date.setValue(selectedItem.getDate());
        time.setText(selectedItem.getTime());
        description.setText(selectedItem.getDescription());
        local.setText(selectedItem.getLocal());
        doctor.setValue(selectedItem.getDoctor());
        pacient.setValue(selectedItem.getPacient());
        status.setValue(selectedItem.getStatus());
        value.setText(selectedItem.getValue());
  }

  @FXML
  private void saveButtonClicked(ActionEvent event) {
    LocalDate dataConsulta = date.getValue();
    String descricao = description.getText();
    Doctor medicoSelecionado = doctor.getValue();
    String localConsulta = local.getText();
    Pacient pacienteSelecionado = pacient.getValue();
    String statusSelecionado = status.getValue();
    String horario = time.getText();
    String valor = value.getText();

    if(selectedAppointment != null) {
      selectedAppointment.setDate(dataConsulta);
      selectedAppointment.setDescription(descricao);
      selectedAppointment.setDoctor(medicoSelecionado);
      selectedAppointment.setLocal(localConsulta);
      selectedAppointment.setPacient(pacienteSelecionado);
      selectedAppointment.setStatus(statusSelecionado);
      selectedAppointment.setTime(horario);
      selectedAppointment.setValue(valor);

      if (parentController != null) {
        parentController.editAppointmentData();
      }
    } else {
        Appointment appointment = new Appointment(dataConsulta, horario, localConsulta, valor, descricao,
        statusSelecionado, pacienteSelecionado, medicoSelecionado);

        if (parentController != null) {
        parentController.addAppointmentData(appointment);
      }
    }
    Stage stage = (Stage) saveButton.getScene().getWindow();
    stage.close();
  }
}
