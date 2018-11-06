package sofranko.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginScreenController {

    @FXML
    private PasswordField passwordTextfield;

    @FXML
    private TextField usernameTextfield;

    @FXML
    private Button signinButton;

    @FXML
    private Button signupButton;

    @FXML
    private Button resetpasswordButton;

    @FXML
    private Label wronginputTextlabel;

    @FXML
    void initialize() {
    	signinButton.setOnAction(new EventHandler<ActionEvent>() {
			
    		public void handle(ActionEvent event) {
				System.out.println("try to login.");
			}

			
		});
    	
//        assert passwordTextfield != null : "fx:id=\"passwordTextfield\" was not injected: check your FXML file 'loginScreen.fxml'.";
//        assert usernameTextfield != null : "fx:id=\"usernameTextfield\" was not injected: check your FXML file 'loginScreen.fxml'.";
//        assert signinButton != null : "fx:id=\"signinButton\" was not injected: check your FXML file 'loginScreen.fxml'.";
//        assert signupButton != null : "fx:id=\"signupButton\" was not injected: check your FXML file 'loginScreen.fxml'.";
//        assert resetpasswordButton != null : "fx:id=\"resetpasswordButton\" was not injected: check your FXML file 'loginScreen.fxml'.";
//        assert wronginputTextlabel != null : "fx:id=\"wronginputTextlabel\" was not injected: check your FXML file 'loginScreen.fxml'.";
    	
    	
    }
}
