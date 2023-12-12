package screens.controller;

import classes.Appointment;
import classes.Doctor;
import classes.Pacient;
import classes.Person;
import common.tools.CurrencyField;
import common.tools.Mask;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

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

  @FXML
  private Button removeButton;

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
    removeButton.setDisable(true);
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
    person.setCellFactory(param ->
      new ListCell<T>() {
        @Override
        protected void updateItem(T item, boolean empty) {
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

    person.setConverter(
      new StringConverter<T>() {
        @Override
        public String toString(Person person) {
          return (person != null)
            ? person.getCode() +
            " - " +
            person.getName() +
            " (" +
            person.getIdentifier() +
            ")"
            : null;
        }

        @Override
        public T fromString(String string) {
          return null;
        }
      }
    );
  }

  private void loadHandlers() {
    date
      .valueProperty()
      .addListener((observable, oldValue, newValue) ->
        validarCamposObrigatorios()
      );
    description
      .textProperty()
      .addListener((observable, oldValue, newValue) ->
        validarCamposObrigatorios()
      );
    doctor
      .valueProperty()
      .addListener((observable, oldValue, newValue) ->  {
        validarCamposObrigatorios();
        setLocalSuggestion(newValue);
      }
      );
    time
      .textProperty()
      .addListener((observable, oldValue, newValue) ->
        validarCamposObrigatorios()
      );
    pacient
      .valueProperty()
      .addListener((observable, oldValue, newValue) ->
        validarCamposObrigatorios()
      );
    status
      .valueProperty()
      .addListener((observable, oldValue, newValue) ->
        validarCamposObrigatorios()
      );
    local
      .textProperty()
      .addListener((observable, oldValue, newValue) ->
        validarCamposObrigatorios()
      );
    value
      .textProperty()
      .addListener((observable, oldValue, newValue) ->
        validarCamposObrigatorios()
      );
  }

  private void validarCamposObrigatorios() {
    boolean camposPreenchidos =
      pacient.getValue() != null &&
      date.getValue() != null &&
      (!time.getText().isEmpty() && time.getText().length() == 5) &&
      doctor.getValue() != null &&
      status.getValue() != null &&
      !local.getText().isEmpty();

    saveButton.setDisable(!camposPreenchidos);
  }

  private void setLocalSuggestion(Doctor doctor) {
    local.setText(doctor.getAttendenceRoom());
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
    removeButton.setDisable(false);
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

     try {
        LocalTime horarioConsulta = LocalTime.parse(
          horario,
          DateTimeFormatter.ofPattern("HH:mm")
        );

        LocalDateTime dataHoraConsulta = LocalDateTime.of(
          date.getValue(),
          horarioConsulta
        );

        LocalDateTime agora = LocalDateTime.now();

        if (
          dataConsulta.isEqual(LocalDate.now()) &&
          dataHoraConsulta.isBefore(agora)
        ) {
          exibirAlerta(
            "Horário inválido",
            "O horário informado já passou para o dia de hoje. Verifique e tente novamente"
          );
          return;
        }
      } catch (DateTimeParseException e) {
        exibirAlerta(
          "Horário inválido",
          "O horário informado é inválido. Verifique e tente novamente"
        );
        return;
      }

    if (!validarDataConsulta(dataConsulta, horario)) {
      exibirAlerta(
        "Data inválida",
        "A data da consulta é anterior ao dia atual."
      );
      return;
    } else if (
      !validarConflitoConsulta(dataConsulta, horario, medicoSelecionado)
    ) {
      exibirAlerta(
        "Conflito de consulta",
        "Já existe outra consulta marcada para o mesmo médico, data e horário."
      );
      return;
    } else {
      if (selectedAppointment != null) {
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
        Appointment appointment = new Appointment(
          dataConsulta,
          horario,
          localConsulta,
          valor,
          descricao,
          statusSelecionado,
          pacienteSelecionado,
          medicoSelecionado
        );

        if (parentController != null) {
          parentController.addAppointmentData(appointment);
        }
      }
      Stage stage = (Stage) saveButton.getScene().getWindow();
      stage.close();
      removeButton.setDisable(true);
    }
  }

  private void exibirAlerta(String titulo, String mensagem) {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensagem);
    alert.showAndWait();
  }

  private boolean validarDataConsulta(LocalDate dataConsulta, String horario) {
    LocalDate hoje = LocalDate.now();
    if (dataConsulta.isBefore(hoje)) {
      return false;
    }
    return true;
  }

  private boolean validarConflitoConsulta(
    LocalDate dataConsulta,
    String horario,
    Doctor medico
  ) {
    ObservableList<Appointment> appointmentsList = HomeController.getAppointmentsList();

    for (Appointment appointment : appointmentsList) {
      if (appointment.equals(selectedAppointment)) {
        continue;
      }

      if (
        appointment.getDoctor().equals(medico) &&
        appointment.getDate().isEqual(dataConsulta) &&
        horariosConflitantes(appointment.getTime(), horario)
      ) {
        return false; // Conflito encontrado
      }
    }

    return true; // Sem conflito
  }

  private boolean horariosConflitantes(
    String horarioConsultaExistente,
    String horario
  ) {
    LocalTime horario1 = LocalTime.parse(
      horarioConsultaExistente,
      DateTimeFormatter.ofPattern("HH:mm")
    );
    LocalTime horario2 = LocalTime.parse(
      horario,
      DateTimeFormatter.ofPattern("HH:mm")
    );

    long diffInMinutes = ChronoUnit.MINUTES.between(horario1, horario2);

    // Verifica se a diferença é menor que 15 minutos (ou seja, conflito)
    return Math.abs(diffInMinutes) < 15;
  }

  @FXML
  private void removeButtonClicked(ActionEvent event) {
    if (selectedAppointment != null) {
      parentController.removeAppointmentData(selectedAppointment);
      removeButton.setDisable(true);
    }
    Stage stage = (Stage) saveButton.getScene().getWindow();
    stage.close();
  }
}
