package screens.controller;

import classes.Appointment;
import classes.Pacient;
import common.tools.Mask;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PacientsController {

  @FXML
  private TextField address;

  @FXML
  private DatePicker birthDate;

  @FXML
  private TextField cpf;

  @FXML
  private TextField email;

  @FXML
  private ComboBox<String> genre;

  @FXML
  private TextField name;

  @FXML
  private TextField phoneNumber;

  @FXML
  private TextField plan;

  @FXML
  private CheckBox checkPlan;

  // ----------------------------------------------------- TABLE

  @FXML
  private TableView<Pacient> tableView;

  @FXML
  private TableColumn<Pacient, IntegerProperty> codeCol;

  @FXML
  private TableColumn<Pacient, String> nameCol;

  @FXML
  private TableColumn<Pacient, String> cpfCol;

  @FXML
  private TableColumn<Pacient, String> phoneNumberCol;

  @FXML
  private TableColumn<Pacient, LocalDate> birthDateCol;

  @FXML
  private TableColumn<Pacient, String> genreCol;

  @FXML
  private TableColumn<Pacient, String> statusCol;

  @FXML
  private Button saveButton;

  @FXML
  private Button removeButton;

  private static ObservableList<Pacient> pacientesList = FXCollections.observableArrayList();

  public void initialize() {
    tableView.setItems(pacientesList);
    saveButton.setDisable(true);
    removeButton.setDisable(true);

    getSelectValue();
    setCellValueFactory();
    loadHandlers();

    Mask.configurePhoneMask(phoneNumber);
    Mask.configureCpfMask(cpf);

    tableView
      .getSelectionModel()
      .selectedItemProperty()
      .addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
          preencherFormulario(newSelection);
        }
      });
  }

  private void getSelectValue() {
    ObservableList<String> genresList = FXCollections.observableArrayList(
      "Masculino",
      "Feminino",
      "Outro"
    );

    genre.setItems(genresList);
  }

  private void setCellValueFactory() {
    codeCol.setCellValueFactory(
      new PropertyValueFactory<Pacient, IntegerProperty>("code")
    );
    nameCol.setCellValueFactory(
      new PropertyValueFactory<Pacient, String>("name")
    );
    cpfCol.setCellValueFactory(
      new PropertyValueFactory<Pacient, String>("identifier")
    );
    phoneNumberCol.setCellValueFactory(
      new PropertyValueFactory<Pacient, String>("phoneNumber")
    );
    birthDateCol.setCellValueFactory(
      new PropertyValueFactory<Pacient, LocalDate>("dateOfBirth")
    );
    genreCol.setCellValueFactory(
      new PropertyValueFactory<Pacient, String>("gender")
    );
    statusCol.setCellValueFactory(
      new PropertyValueFactory<Pacient, String>("status")
    );
  }

  private void loadHandlers() {
    name
      .textProperty()
      .addListener((observable, oldValue, newValue) ->
        validarCamposObrigatorios()
      );
    phoneNumber
      .textProperty()
      .addListener((observable, oldValue, newValue) ->
        validarCamposObrigatorios()
      );
    address
      .textProperty()
      .addListener((observable, oldValue, newValue) ->
        validarCamposObrigatorios()
      );
    birthDate
      .valueProperty()
      .addListener((observable, oldValue, newValue) ->
        validarCamposObrigatorios()
      );
    genre
      .valueProperty()
      .addListener((observable, oldValue, newValue) ->
        validarCamposObrigatorios()
      );
    cpf
      .textProperty()
      .addListener((observable, oldValue, newValue) ->
        validarCamposObrigatorios()
      );
  }

  private void validarCamposObrigatorios() {
    boolean camposPreenchidos =
      !name.getText().isEmpty() &&
      (
        !phoneNumber.getText().isEmpty() && phoneNumber.getText().length() == 16
      ) &&
      !address.getText().isEmpty() &&
      (!cpf.getText().isEmpty() && cpf.getText().length() == 14) &&
      birthDate.getValue() != null &&
      genre.getValue() != null;

    saveButton.setDisable(!camposPreenchidos);
  }

  private void preencherFormulario(Pacient selectedItem) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    birthDate
      .getEditor()
      .setText(selectedItem.getDateOfBirth().format(formatter));
    name.setText(selectedItem.getName());
    cpf.setText(selectedItem.getIdentifier());
    email.setText(selectedItem.getEmail());
    genre.setValue(selectedItem.getGender());
    address.setText(selectedItem.getAddress());
    phoneNumber.setText(selectedItem.getPhoneNumber());
    plan.setText(selectedItem.getPlan());
    removeButton.setDisable(false);
  }

  @FXML
  private void saveButtonClicked(ActionEvent event) {
    Pacient pacienteSelecionado = tableView
      .getSelectionModel()
      .getSelectedItem();

    String nome = name.getText();
    String telefone = phoneNumber.getText();
    String email = this.email.getText();
    String endereco = address.getText();
    LocalDate dataNascimento = birthDate.getValue();
    String genero = genre.getValue();
    String cpf = this.cpf.getText();
    String plano = plan.getText();

    if (pacienteSelecionado != null) {
      pacienteSelecionado.setName(nome);
      pacienteSelecionado.setDateOfBirth(dataNascimento);
      pacienteSelecionado.setIdentifier(cpf);
      pacienteSelecionado.setEmail(email);
      pacienteSelecionado.setGender(genero);
      pacienteSelecionado.setAddress(endereco);
      pacienteSelecionado.setPhoneNumber(telefone);
      pacienteSelecionado.setPlan(plano);

      tableView.getSelectionModel().clearSelection();
    } else {
      Pacient paciente = new Pacient(
        nome,
        cpf,
        dataNascimento,
        endereco,
        telefone,
        email,
        genero,
        plano,
        "Ativo"
      );
      pacientesList.add(paciente);
    }
    tableView.refresh();
    limparCamposFormulario();
    removeButton.setDisable(true);
  }

  @FXML
  private void removeButtonClicked(ActionEvent event) {
    Pacient pacienteSelecionado = tableView
      .getSelectionModel()
      .getSelectedItem();

    if (pacienteSelecionado != null) {
      boolean pacienteEstaNaConsulta = false;
      for (Appointment appointment : HomeController.getAppointmentsList()) {
        if (appointment.getPacient().equals(pacienteSelecionado)) {
          pacienteEstaNaConsulta = true;
          break;
        }
      }

      if (pacienteEstaNaConsulta) {
        // O paciente está em uma consulta, exibir um Alert informativo
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(
          "O paciente está em uma consulta. Não é possível remover."
        );
        alert.showAndWait();
      } else {
        // O paciente não está em nenhuma consulta, pode remover
        pacientesList.remove(pacienteSelecionado);
        limparCamposFormulario();
        removeButton.setDisable(true);
      }
    }

    tableView.refresh();
  }

  private void limparCamposFormulario() {
    name.clear();
    phoneNumber.clear();
    this.email.clear();
    address.clear();
    birthDate.getEditor().clear();
    genre.getSelectionModel().clearSelection();
    cpf.clear();
    plan.clear();
  }

  public static ObservableList<Pacient> getPacientesList() {
    return pacientesList;
  }
}
