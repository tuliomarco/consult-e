package common.tools;

import javafx.application.Platform;
import javafx.scene.control.TextField;

public abstract class Mask {

    public static void configureCpfMask(TextField textField) {
        try {
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!textField.isFocused()) {
                    return;
                }
    
                String numericText = newValue.replaceAll("[^\\d]", "");
    
                StringBuilder maskedText = new StringBuilder();
    
                for (int i = 0; i < numericText.length(); i++) {
                    if (i == 3 || i == 6) {
                        maskedText.append('.');
                    } else if (i == 9) {
                        maskedText.append('-');
                    }
                    maskedText.append(numericText.charAt(i));
                }
    
                Platform.runLater(() -> {
                    textField.setText(maskedText.toString());
                    textField.positionCaret(maskedText.length());
                    if (textField.getText().length() > 14) {
                        textField.setText(textField.getText().substring(0, 14));
                        textField.positionCaret(14);
                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void configureTimeMask(TextField textField) {
        try {
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!textField.isFocused()) {
                    return;
                }
    
                String numericText = newValue.replaceAll("[^\\d]", "");
    
                StringBuilder maskedText = new StringBuilder();
    
                for (int i = 0; i < numericText.length(); i++) {
                    if (i == 2) {
                        maskedText.append(':');
                    }
                    maskedText.append(numericText.charAt(i));
                }
    
                Platform.runLater(() -> {
                    textField.setText(maskedText.toString());
                    textField.positionCaret(maskedText.length());
                    if (textField.getText().length() > 5) {
                        textField.setText(textField.getText().substring(0, 5));
                        textField.positionCaret(5);
                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

  public static void configurePhoneMask(TextField textField) {
    try {
      textField
        .textProperty()
        .addListener((observable, oldValue, newValue) -> {
          if (!textField.isFocused()) {
            return;
          }

          String numericText = newValue.replaceAll("[^\\d]", "");

          int caretPosition = textField.getCaretPosition();
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

          Platform.runLater(() -> {
            textField.setText(maskedText.toString());

            // Ajusta a posição do cursor
            int newPosition = Math.min(
              caretPosition + (maskedText.length() - numericText.length()),
              maskedText.length()
            );
            textField.positionCaret(newPosition);

            // Limita o tamanho máximo
            if (textField.getText().length() > 14) {
              textField.setText(textField.getText().substring(0, 14));
            }
          });
        });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
