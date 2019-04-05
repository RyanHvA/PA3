/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pa3;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.math.BigInteger;
import java.util.ArrayList;

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
    private TextField inputStep3;
    @FXML
    private TextArea outputStep1;
    @FXML
    private TextArea outputStep2;
    @FXML
    private TextArea outputStep3;

    int p = 0;
    int q = 0;
    int relativePrime = 0;
    int n = 0;
    int m = 0;
    String text;

    static void SieveOfEratosthenes(int n, boolean isPrime[]) {
        // Makes an boolean array with weather n is a prime or not
        isPrime[0] = isPrime[1] = false;
        for (int i = 2; i <= n; i++) {
            isPrime[i] = true;
        }

        for (int p = 2; p * p <= n; p++) {
            // If isPrime[p] is not changed, then it is a prime 
            if (isPrime[p] == true) {
                // Update all multiples of p 
                for (int i = p * 2; i <= n; i += p) {
                    isPrime[i] = false;
                }
            }
        }
    }

    @FXML
    private void stepOneButtonClicked(ActionEvent event) {
        int flag = 0;
        long startTime = System.nanoTime();
        double elapsed = 0;
        int nanoToMilis = 1000000;
        System.out.println("Button 1 Clicked");

        n = Integer.parseInt(inputStep1.getText());
        // Generating primes using Sieve 
        boolean[] isPrime = new boolean[n + 1];
        SieveOfEratosthenes(n, isPrime);

        // Traversing all numbers to find first pair 
        for (int i = 2; i < n; i++) {
            int x = n / i;

            if (isPrime[i] && isPrime[x] && x != i && x * i == n) {
                p = i;
                q = x;
                elapsed = (double) (System.nanoTime() - startTime) / nanoToMilis;
                outputStep1.setText("p is " + i + "\nq is " + x + "\nelapsed: " + (elapsed) + " miliseconds");
                flag = 1;
                return;
            }
        }

        if (flag == 0) {
            outputStep1.setText("No such pair found");
        }

    }

    @FXML
    private void stepTwoButtonClicked(ActionEvent event) {
        System.out.println("Button 2 Clicked");

        relativePrime = getRelativePrime(p - 1, q - 1);
        outputStep2.setText("e is: <" + relativePrime + ">");
    }

    private int getRelativePrime(int p, int q) {

        int findRelativePrime = p * q;
        boolean[] isPrime = new boolean[findRelativePrime + 1];
        SieveOfEratosthenes(findRelativePrime, isPrime);

        for (int i = 0; i < isPrime.length; i++) {
            if (isPrime[i]) {
                relativePrime = i;
            }

        }
        return relativePrime;
    }

    @FXML
    private void stepThreeButtonClicked(ActionEvent event) {
        System.out.println("Button 3 Clicked");
        
        n = Integer.parseInt(inputStep1.getText());
        ArrayList<Integer> letters = new ArrayList<>();
        text = inputStep3.getText();    
        
        for (int i = 0; i < text.length(); i++) {
            BigInteger bigInteger = new BigInteger(String.valueOf((int) text.charAt(i)));
            bigInteger = bigInteger.pow(relativePrime);
            bigInteger = bigInteger.mod(new BigInteger(String.valueOf(n)));
            letters.add(Integer.valueOf(bigInteger.toString()));
        }
        
        outputStep3.setText("Message after encryption is: " + letters.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
