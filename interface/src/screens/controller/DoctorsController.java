package screens.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import classes.Doctor;
import classes.Speciality;
import common.tools.Mask;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import javafx.fxml.FXML;

public class DoctorsController {

    @FXML
    private TextField attendanceRoom;

    @FXML
    private DatePicker birthDate;

    @FXML
    private TextField crm;

    @FXML
    private TextField email;

    @FXML
    private ComboBox<String> genre;

    @FXML
    private TextField name;

    @FXML
    private TextField phoneNumber;

    @FXML
    private ComboBox<Speciality> speciality;

    // ----------------------------------------------------- TABLE
    
    @FXML
    private TableView<Doctor> tableView;

    @FXML
    private TableColumn<Doctor, IntegerProperty> codeCol;

    @FXML
    private TableColumn<Doctor, String> nameCol;

    @FXML
    private TableColumn<Doctor, Speciality> specialityCol;

    @FXML
    private TableColumn<Doctor, String> roomCol;

    @FXML
    private TableColumn<Doctor, String> contactCol;

    @FXML
    private TableColumn<Doctor, String> crmCol;

    @FXML
    private TableColumn<Doctor, String> statusCol;

    @FXML
    private Button saveButton;

    private static ObservableList<Doctor> doctorsList = FXCollections.observableArrayList();

      public void initialize() {
        tableView.setItems(doctorsList);
        saveButton.setDisable(true);

        getSelectValue();
        renderSpecialityField();
        setCellValueFactory();
        loadHandlers();

        Mask.configurePhoneMask(phoneNumber);

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                preencherFormulario(newSelection);
            }
        });
    }

    private void renderSpecialityField() {
        speciality.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Speciality item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getName());
            }
        });

        speciality.setConverter(new StringConverter<>() {
            @Override
            public String toString(Speciality person) {
                return (person != null) ? person.getName() : null;
            }

            @Override
            public Speciality fromString(String string) {
                return null;
            }
        });

        specialityCol.setCellFactory(param -> new TableCell<Doctor, Speciality>() {
            @Override
            protected void updateItem(Speciality item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
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

    speciality.setItems(SpecialitiesController.getSpecialitiesList());
    }

    private void setCellValueFactory() {
        codeCol.setCellValueFactory(new PropertyValueFactory<Doctor, IntegerProperty>("code"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Doctor, String>("name"));
        crmCol.setCellValueFactory(new PropertyValueFactory<Doctor, String>("identifier"));
        specialityCol.setCellValueFactory(new PropertyValueFactory<Doctor, Speciality>("speciality"));
        roomCol.setCellValueFactory(new PropertyValueFactory<Doctor, String>("attendenceRoom"));
        contactCol.setCellValueFactory(new PropertyValueFactory<Doctor, String>("phoneNumber"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Doctor, String>("status"));
    }

    private void loadHandlers() {
        name.textProperty().addListener((observable, oldValue, newValue) -> validarCamposObrigatorios());
        phoneNumber.textProperty().addListener((observable, oldValue, newValue) -> validarCamposObrigatorios());
        email.textProperty().addListener((observable, oldValue, newValue) -> validarCamposObrigatorios());
        attendanceRoom.textProperty().addListener((observable, oldValue, newValue) -> validarCamposObrigatorios());
        crm.textProperty().addListener((observable, oldValue, newValue) -> validarCamposObrigatorios());
        speciality.valueProperty().addListener((observable, oldValue, newValue) -> validarCamposObrigatorios());
    }

    private void validarCamposObrigatorios() {
        boolean camposPreenchidos = !name.getText().isEmpty()
                && (!phoneNumber.getText().isEmpty() && phoneNumber.getText().length() == 16)
                && !email.getText().isEmpty()
                && !crm.getText().isEmpty()
                && !attendanceRoom.getText().isEmpty()
                && speciality.getValue() != null;

        saveButton.setDisable(!camposPreenchidos);
    }
    
    private void preencherFormulario(Doctor selectedItem) {
        if (birthDate != null && selectedItem.getDateOfBirth() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            birthDate.getEditor().setText(selectedItem.getDateOfBirth().format(formatter));
        }
        name.setText(selectedItem.getName());
        crm.setText(selectedItem.getIdentifier());
        email.setText(selectedItem.getEmail());
        genre.setValue(selectedItem.getGender());
        speciality.setValue(selectedItem.getSpeciality());
        phoneNumber.setText(selectedItem.getPhoneNumber());
        attendanceRoom.setText(selectedItem.getAttendenceRoom());
    }

    @FXML
    private void saveButtonClicked(ActionEvent event) {
        Doctor medicoSelecionado = tableView.getSelectionModel().getSelectedItem();

        String nome = name.getText();
        String indentificador = crm.getText();
        String telefone = phoneNumber.getText();
        String email = this.email.getText();
        LocalDate dataNascimento = birthDate.getValue();
        String genero = genre.getValue();
        Speciality especialidade = speciality.getValue();
        String salaDeAtendimento = attendanceRoom.getText();

        if(medicoSelecionado != null) {
            medicoSelecionado.setName(nome);
            medicoSelecionado.setDateOfBirth(dataNascimento);
            medicoSelecionado.setIdentifier(indentificador);
            medicoSelecionado.setEmail(email);
            medicoSelecionado.setGender(genero);
            medicoSelecionado.setSpeciality(especialidade);
            medicoSelecionado.setPhoneNumber(telefone);
            medicoSelecionado.setAttendenceRoom(salaDeAtendimento);

            tableView.getSelectionModel().clearSelection();
        } else {
            Doctor medico = new Doctor(nome, indentificador, telefone, email, genero, especialidade, salaDeAtendimento, dataNascimento ,"Disponível");
            doctorsList.add(medico);
            int qtdMedicos = speciality.getValue().getDoctorsAmount();
            speciality.getValue().setDoctorsAmount(++qtdMedicos);
        }

        tableView.refresh();

        limparCamposFormulario();
    }

    private void limparCamposFormulario() {
        name.clear();
        phoneNumber.clear();
        email.clear();
        crm.clear();
        birthDate.getEditor().clear();
        genre.getSelectionModel().clearSelection();
        speciality.getSelectionModel().clearSelection();
        attendanceRoom.clear();
    }

    public static ObservableList<Doctor> getDoctorsList() {
        return doctorsList;
    }
}
