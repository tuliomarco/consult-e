package common.tools;

import javafx.scene.control.TextField;

public abstract class Mask {

     public static void configureCpfMask(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!textField.isFocused()) {
                return;
            }
    
            String numericText = newValue.replaceAll("[^\\d]", "");
    
            StringBuilder maskedText = new StringBuilder();
            int i = 0;
            for (char c : numericText.toCharArray()) {
                if (i == 3 || i == 6) {
                    maskedText.append('.');
                } else if (i == 9) {
                    maskedText.append('-');
                }
                maskedText.append(c);
                i++;
            }
    
            textField.setText(maskedText.toString());
    
            if (textField.getText().length() > 14) {
                textField.setText(textField.getText().substring(0, 14));
            }
        });
    }

    public static void configurePhoneMask(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!textField.isFocused()) {
                return;
            }
    
            String numericText = newValue.replaceAll("[^\\d]", "");
    
            StringBuilder maskedText = new StringBuilder();
            int i = 0;
            for (char c : numericText.toCharArray()) {
                if (i == 0) {
                    maskedText.append("(");
                } else if (i == 2) {
                    maskedText.append(") ");
                } else if (i == 3) {
                    maskedText.append(" ");
                } else if (i == 7) {
                    maskedText.append("-");
                }
                maskedText.append(c);
                i++;
            }
    
            textField.setText(maskedText.toString());
    
            if (textField.getText().length() > 16) {
                textField.setText(textField.getText().substring(0, 16));
            }

        });
    }

    public static void configureTimeMask(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!textField.isFocused()) {
                return;
            }

            String numericText = newValue.replaceAll("[^\\d]", "");

            StringBuilder maskedText = new StringBuilder();
            int i = 0;
            for (char c : numericText.toCharArray()) {
                if (i == 2) {
                    maskedText.append(":");
                }
                maskedText.append(c);
                i++;
            }

            textField.setText(maskedText.toString());

            if (textField.getText().length() > 5) {
                textField.setText(textField.getText().substring(0, 5));
            }
        });
    }
}
