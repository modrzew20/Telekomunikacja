package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    private final Stage primaryStage;

    @FXML
    private TextArea bitsTextArea;

    @FXML
    private TextArea resultTextArea;

    @FXML
    private TextArea inputTextArea;

    @FXML
    private TextArea infoTextArea;

    public Controller() {
        primaryStage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Loading
            primaryStage.setScene(new Scene(loader.load()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        primaryStage.showAndWait();
    }

    @FXML
    public void initialize() {

        inputTextArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                byte[][] tmp = functions.stringToBinary(newValue);
                tmp = functions.connect(functions.stringToBinary(newValue), functions.addParityBit(tmp));
                StringBuilder sb = new StringBuilder();
                for ( byte[] bytes : tmp ) {
                    for ( int j = 0; j < tmp[0].length; j++ ) {
                        sb.append(bytes[j]);
                    }
                }
                bitsTextArea.setText(sb.toString());
            }
        });

        bitsTextArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                byte[][] binary = new byte[newValue.length()/16][];
                byte[] nextLetter;
                int counter = 0;
                for ( int i = 0; i < newValue.length() - 15; i += 16 ) {
                    nextLetter = newValue.substring(i, i+16).getBytes();
                    for ( int j = 0; j < nextLetter.length; j++ ) {
                        nextLetter[j] -= 48;
                    }
                    binary[counter++] = nextLetter;
                }
                byte[][] checked = functions.addParityBit(binary);
                infoTextArea.setText(functions.findError(checked));
                resultTextArea.setText(functions.binaryToString(functions.disconnect(binary)));

            }
        });


    }
}
