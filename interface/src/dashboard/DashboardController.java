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
import javafx.util.Duration;
import screens.FxmlLoader;

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
    private void handleDoctorsButtonAction(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("screens/doctors.fxml");
        mainPane.setCenter(view);
    }

    @FXML
    private void handleHomeButtonAction(ActionEvent event) {
        initialPane();
    }

    @FXML
    private void handlePacientsButtonAction(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("screens/pacients.fxml");
        mainPane.setCenter(view);
    }

    @FXML
    private void handleSpecialitiesButtonAction(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("screens/specialities.fxml");
        mainPane.setCenter(view);
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
        scaleIn.setToX(1.02);
        scaleIn.setToY(1.02);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), button);
        scaleOut.setFromX(1.02);
        scaleOut.setFromY(1.02);
        scaleOut.setToX(1);
        scaleOut.setToY(1);

        button.setOnMouseEntered(event -> scaleIn.play());
        button.setOnMouseExited(event -> scaleOut.play());
    }

    private void initialPane() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("screens/home.fxml");
        mainPane.setCenter(view);
    }

}
