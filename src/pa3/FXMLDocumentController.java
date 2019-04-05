/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pa3;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 *
 * @author Ryan J Z
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button buttonStepOne;
    @FXML
    private TextField inputStep1;
    @FXML
    private TextField inputStep2;
    @FXML
    private TextArea outputStep1;
    @FXML
    private TextArea outputStep2;
    
    
    @FXML
    private void stepOneButtonClicked(ActionEvent event) {
        System.out.println("Button 1 Clicked");
        int p = 0;
        int q = 0;
        outputStep1.setText("p is " + p + "\nq is " + q);
    }
    
    @FXML
    private void stepTwoButtonClicked(ActionEvent event) {
        System.out.println("Button 2 Clicked");
        System.out.println(inputStep2.getText());
        outputStep2.setText("t werkt!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
