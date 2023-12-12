package screens.controller;

import classes.Appointment;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AppointmentCardController {

  @FXML
  private Label codeLabel;

  @FXML
  private Label dateLabel;

  @FXML
  private Label doctorLabel;

  @FXML
  private Label pacientLabel;

  @FXML
  private Label timeLabel;

  @FXML
  private Label statusLabel;

  public void initialize(Appointment appointment) {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String formattedDate = appointment.getDate().format(dateFormatter);
    dateLabel.setText(formattedDate);

    codeLabel.setText("Cód. " + appointment.getCode());
    doctorLabel.setText(appointment.getDoctor().getName());
    pacientLabel.setText(appointment.getPacient().getName());
    timeLabel.setText(appointment.getTime());
    statusLabel.setText(appointment.getStatus());
  }
}
