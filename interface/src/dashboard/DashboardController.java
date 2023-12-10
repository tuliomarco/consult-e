package dashboard;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import screens.controller.FxmlLoader;
import screens.controller.HomeController;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button doctorsButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button pacientsButton;

    @FXML
    private Button specialitiesButton;

    @FXML
    private void handleHomeButtonAction(ActionEvent event) {
        initialPane();
    }

    @FXML
    private void handleDoctorsButtonAction(ActionEvent event) {
        closeHomeUtilities();
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("screens/doctors.fxml");
        mainPane.setCenter(view);
        
        estilizarBotoes(doctorsButton, homeButton, pacientsButton, specialitiesButton);
    }

    @FXML
    private void handlePacientsButtonAction(ActionEvent event) {
        closeHomeUtilities();
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("screens/pacients.fxml");
        mainPane.setCenter(view);
        estilizarBotoes(pacientsButton, doctorsButton, homeButton, specialitiesButton);
    }

    @FXML
    private void handleSpecialitiesButtonAction(ActionEvent event) {
        closeHomeUtilities();
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("screens/specialities.fxml");
        mainPane.setCenter(view);
        estilizarBotoes(specialitiesButton, doctorsButton, homeButton, pacientsButton);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialPane();

        applyTransition(homeButton, 0);
        addHoverTransition(homeButton);

        applyTransition(doctorsButton, 100);
        addHoverTransition(doctorsButton); 

        applyTransition(pacientsButton, 200);
        addHoverTransition(pacientsButton);

        applyTransition(specialitiesButton, 300);
        addHoverTransition(specialitiesButton); 
    }

    private void closeHomeUtilities() {
        Stage stage = HomeController.getUtilityStage();
        if (stage != null && stage.isShowing()) {
            stage.close();
        }
    }

    private void estilizarBotoes(Button selecionado, Button... outrosBotoes) {
        selecionado.getStyleClass().add("buttonSelected");
        selecionado.setTextFill(Color.web("#fbfbfb"));

        for (Button button : outrosBotoes) {
            button.getStyleClass().remove("buttonSelected");
            button.setTextFill(Color.web("#9f9ea5"));
        }
    }

    private void applyTransition(Button button, int delay) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), button);
        translateTransition.setFromX(-200);  
        translateTransition.setToX(0);      
        translateTransition.setInterpolator(Interpolator.EASE_OUT);  

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), button);
        fadeTransition.setFromValue(0); 
        fadeTransition.setToValue(1); 

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);
        parallelTransition.setDelay(Duration.millis(delay));
        button.setOpacity(0);

        parallelTransition.play();
    }

        private void addHoverTransition(Button button) {
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), button);
        scaleIn.setFromX(1);
        scaleIn.setFromY(1);
        scaleIn.setToX(1.01);
        scaleIn.setToY(1.01);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), button);
        scaleOut.setFromX(1.01);
        scaleOut.setFromY(1.01);
        scaleOut.setToX(1);
        scaleOut.setToY(1);

        button.setOnMouseEntered(event -> scaleIn.play());
        button.setOnMouseExited(event -> scaleOut.play());
    }

    private void initialPane() {
        estilizarBotoes(homeButton, doctorsButton, pacientsButton, specialitiesButton);
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("screens/home.fxml");
        mainPane.setCenter(view);
    }

}
