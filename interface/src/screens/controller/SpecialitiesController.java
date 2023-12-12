package screens.controller;

import classes.Speciality;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SpecialitiesController {

  @FXML
  private TextField name;

  @FXML
  private TableColumn<Speciality, IntegerProperty> codeCol;

  @FXML
  private TableColumn<Speciality, IntegerProperty> doctorsAmountCol;

  @FXML
  private TableColumn<Speciality, String> nameCol;

  @FXML
  private TableView<Speciality> tableView;

  @FXML
  private Button saveButton;

  @FXML
  private Button removeButton;

  static Speciality especialidade = new Speciality("Geral");

  private static ObservableList<Speciality> specialities = FXCollections.observableArrayList(
    especialidade
  );

  public static ObservableList<Speciality> getSpecialitiesList() {
    return specialities;
  }

  public void initialize() {
    tableView.setItems(specialities);
    saveButton.setDisable(true);
    removeButton.setDisable(true);

    setCellValueFactory();
    name
      .textProperty()
      .addListener((observable, oldValue, newValue) -> {
        saveButton.setDisable(name.getText().isEmpty());
      });

    tableView
      .getSelectionModel()
      .selectedItemProperty()
      .addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
          preencherFormulario(newSelection);
        }
      });
  }

  private void setCellValueFactory() {
    codeCol.setCellValueFactory(
      new PropertyValueFactory<Speciality, IntegerProperty>("code")
    );
    nameCol.setCellValueFactory(
      new PropertyValueFactory<Speciality, String>("name")
    );
    doctorsAmountCol.setCellValueFactory(
      new PropertyValueFactory<Speciality, IntegerProperty>("doctorsAmount")
    );
  }

  private void preencherFormulario(Speciality selectedItem) {
    name.setText(selectedItem.getName());
    removeButton.setDisable(false);
  }

  @FXML
  private void saveButtonClicked(ActionEvent event) {
    Speciality especialidadeSelecionada = tableView
      .getSelectionModel()
      .getSelectedItem();

    String nome = name.getText();

    if (especialidadeSelecionada != null) {
      especialidadeSelecionada.setName(nome);

      tableView.getSelectionModel().clearSelection();
    } else {
      Speciality especialidade = new Speciality(nome);
      specialities.add(especialidade);
    }
    tableView.refresh();
    name.clear();
    removeButton.setDisable(true);
  }

  @FXML
  private void removeButtonClicked(ActionEvent event) {
    Speciality especialidadeSelecionada = tableView
      .getSelectionModel()
      .getSelectedItem();

    if (especialidadeSelecionada != null) {
      boolean epecialidadeUtilizada =
        especialidadeSelecionada.getDoctorsAmount() > 1;

      if (epecialidadeUtilizada) {
        // O paciente está em uma consulta, exibir um Alert informativo
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(
          "Está especialidade consta no registro de um ou mais médicos. Não é possível remover."
        );
        alert.showAndWait();
      } else {
        // O paciente não está em nenhuma consulta, pode remover
        specialities.remove(especialidadeSelecionada);
        name.clear();
        removeButton.setDisable(true);
      }
    }

    tableView.refresh();
  }
}
