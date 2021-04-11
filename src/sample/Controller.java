package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;


import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.EventListener;
import java.util.Scanner;

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

    @FXML
    private TextArea originallyBitsTextArea;

    @FXML
    private Text originallySize;

    @FXML
    private Text afterSize;

    @FXML
    private Button save;

    @FXML
    private Button read;

    @FXML
    private Button wczytajwiadomosc;




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




        wczytajwiadomosc.setOnAction((EventHandler<ActionEvent>) actionEvent -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File f = fileChooser.showOpenDialog(primaryStage);

            Scanner in = null;
            try {
                in = new Scanner(f);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            String zdanie = in.nextLine();
            inputTextArea.setText(zdanie);
        });





        save.setOnAction((EventHandler<ActionEvent>) actionEvent -> {
           PrintWriter zapis = null;
                                       try {
                                           zapis = new PrintWriter("./dozmianybitow");
                                       } catch (FileNotFoundException fileNotFoundException) {
                                           fileNotFoundException.printStackTrace();
                                       }
                                       zapis.print(bitsTextArea.getText());
                                       zapis.close();

                                       try {
                                           zapis = new PrintWriter("./dwarazywiekszy.txt");
                                       } catch (FileNotFoundException e) {
                                           e.printStackTrace();
                                       }
                                       String result=bitsTextArea.getText().replaceAll("\n","");
                                        byte[][] binary = new byte[result.length()/8][];
                                        byte[] nextLetter;
                                        int counter = 0;
                                        for ( int i = 0; i < result.length() - 7; i += 8 ) {
                                            nextLetter = result.substring(i, i+8).getBytes();
                                            for ( int j = 0; j < nextLetter.length; j++ ) {
                                                nextLetter[j] -= 48;
                                            }
                                            binary[counter++] = nextLetter;
                                        }
                                        zapis.print(functions.binaryToString(binary));
                                        zapis.close();


        });




       read.setOnAction((EventHandler<ActionEvent>) actionEvent -> {

           File file = new File("./dozmianybitow");
           Scanner in = null;
           try {
                in = new Scanner(file);
           } catch (FileNotFoundException fileNotFoundException) {
               fileNotFoundException.printStackTrace();
           }
           String zdanie = "";

           while(in.hasNext()){
                zdanie+=in.nextLine();
                zdanie+="\n";
           }
           bitsTextArea.setText(zdanie);

       });


                inputTextArea.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                        byte[][] tmp = functions.stringToBinary(newValue);
                        byte[][] originally = functions.stringToBinary(newValue);
                        tmp = functions.connect(originally, functions.addParityBit(tmp));

                        int size = 0;
                        StringBuilder cb = new StringBuilder();
                        for (byte[] bytes : originally) {
                            for (int j = 0; j < originally[0].length; j++) {
                                cb.append(bytes[j]);
                                size++;
                            }
                            cb.append("\n");

                        }
                        originallyBitsTextArea.setText(cb.toString());
                        originallySize.setText("Rozmiar oryginalny: " + size/8);

                        size = 0;
                        StringBuilder sb = new StringBuilder();
                        for (byte[] bytes : tmp) {
                            for (int j = 0; j < tmp[0].length; j++) {
                                sb.append(bytes[j]);
                                size++;
                            }
                            sb.append("\n");
                        }
                        bitsTextArea.setText(sb.toString());
                        afterSize.setText("Rozmiar: " + size/8);

                    }
                });

        bitsTextArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {

                newValue=newValue.replaceAll("\n","");
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
                byte[][] originalMessage = binary;
                infoTextArea.setText(functions.findError(checked, binary));
                resultTextArea.setText(functions.binaryToString(functions.disconnect(binary)));

            }
        });
    }

}
