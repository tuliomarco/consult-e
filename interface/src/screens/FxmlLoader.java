package screens;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class FxmlLoader {
    private Pane view;

    public Pane getPage(String fileUrl) {
        try {
            if (fileUrl == null || fileUrl.isEmpty()) {
                throw new java.io.FileNotFoundException("404 - PAGE NOT FOUND");
            }
            view = FXMLLoader.load(getClass().getClassLoader().getResource(fileUrl));
        } catch (Exception e) {
            System.out.println("No page " + fileUrl);
        }
        return view;
    }
}
