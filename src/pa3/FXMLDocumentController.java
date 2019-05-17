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
    //encryt
    @FXML
    private TextField inputStep1;
    @FXML
    private TextField inputStep3;
    @FXML
    private TextArea outputStep1;
    @FXML
    private TextArea outputStep2;
    @FXML
    private TextArea outputStep3;
    
    //decrypt
    @FXML
    private TextField inputStep4e;
    @FXML
    private TextField inputStep4n;
    @FXML
    private TextArea outputStep4;
    @FXML
    private TextField inputStep5;
    @FXML
    private TextArea outputStep5;
    
    int p = 0;
    int q = 0;
    int relativePrime = 0;
    int n = 0;
    int m = 0;
    int d = 0;
    String text;
    BigInteger bi3;

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
        int count = 0;
        for (int i = 0; i < isPrime.length; i++) {
            if (isPrime[i]) {
                relativePrime = i;
                count++;
                if (count == 4){
                    break;
                }
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
            bigInteger = bigInteger.mod(BigInteger.valueOf(n));
            letters.add(Integer.valueOf(bigInteger.toString()));
        }
        
        outputStep3.setText("Message after encryption is: " + letters.toString());
    }
    
    @FXML
    private void stepFourButtonClicked(ActionEvent event) {
    
        //step 1 encrypt
        int flag = 0;
       
        n = Integer.parseInt(inputStep4n.getText());
        BigInteger e = BigInteger.valueOf(Integer.parseInt(inputStep4e.getText()));
        // Generating primes using Sieve 
        boolean[] isPrime = new boolean[n + 1];
        SieveOfEratosthenes(n, isPrime);

        // Traversing all numbers to find first pair 
        for (int i = 2; i < n; i++) {
            int x = n / i;

            if (isPrime[i] && isPrime[x] && x != i && x * i == n) {
                p = i;
                q = x;
                
                flag = 1;
            }
        }
        
        
        if (flag == 0) {
            outputStep1.setText("No such pair found");
        }
        
        BigInteger lol = BigInteger.valueOf((p-1) * (q-1));
       
        bi3 = e.modInverse(lol);
        outputStep4.setText("D is <" + bi3.toString() + ">");
        
    }
    
    @FXML
    private void stepFiveButtonClicked(ActionEvent event) {
        String test = inputStep5.getText();
        test = test.replace("[", "").replace("]", "");
        String[] encryptedMessage = test.split(", ");
        String decryptedMessage = "";
        int[] k = new int[encryptedMessage.length];
        BigInteger[] m = new BigInteger[encryptedMessage.length];
        for (int i = 0; i < encryptedMessage.length; i++) {
            m[i] = BigInteger.valueOf(Integer.parseInt(encryptedMessage[i]));
            m[i] = m[i].pow(bi3.intValue());
            m[i] = m[i].mod(BigInteger.valueOf(n));
            decryptedMessage += (char) m[i].intValue();
        }
       outputStep5.setText(decryptedMessage);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
