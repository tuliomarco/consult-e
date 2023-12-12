package login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController implements Initializable {

  @FXML
  private Button loginButton;

  @FXML
  private TextField password;

  @FXML
  private TextField user;

  @FXML
  void onLoginButtonClick(ActionEvent event) {
    String usuario = user.getText();
    String senha = password.getText();

    Alert alert;

    if (usuario.equals("cic") && senha.equals("lp3")) {
      alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Informação");
      alert.setHeaderText(null);
      alert.setContentText("Logado com sucesso!");

      alert.showAndWait();
      trocarParaNovaTela();
    } else {
      alert = new Alert(AlertType.ERROR);
      alert.setTitle("Erro");
      alert.setHeaderText(null);
      alert.setContentText(
        "Email/usuário e/ou senha inválidos. Verifique e tente novamente!"
      );

      alert.showAndWait();
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    addHoverTransition(loginButton);
  }

  private void addHoverTransition(Button button) {
    ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), button);
    scaleIn.setFromX(1);
    scaleIn.setFromY(1);
    scaleIn.setToX(1.02);
    scaleIn.setToY(1.02);

    ScaleTransition scaleOut = new ScaleTransition(
      Duration.millis(200),
      button
    );
    scaleOut.setFromX(1.02);
    scaleOut.setFromY(1.02);
    scaleOut.setToX(1);
    scaleOut.setToY(1);

    button.setOnMouseEntered(event -> scaleIn.play());
    button.setOnMouseExited(event -> scaleOut.play());
  }

  private void trocarParaNovaTela() {
    try {
      FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/dashboard/dashboard.fxml")
      );

      Parent root = loader.load();

      Scene scene = new Scene(root);

      Stage stage = (Stage) loginButton.getScene().getWindow();

      stage.setScene(scene);
      stage.setResizable(true);

      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
