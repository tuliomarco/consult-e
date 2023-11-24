import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class IconUtils {
    public static Text createIcon(String iconName, int size) {
        Text icon = new Text(iconName);
        icon.setFont(Font.font("FontAwesome", size));
        return icon;
    }
}