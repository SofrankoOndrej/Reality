package reality;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;


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
				// select z databazy podla uzivatelskeho mena
				
				// BCrypt overenie hesla
				
				// nacitanie uvitacieho zobrazenia aplikacie alebo chybova hlaska
						
			}
		});

		signupButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				CreateUserController createUserController = new CreateUserController();
				showModalWindow(createUserController, "CreateUser.fxml");
			}

		});

		// assert passwordTextfield != null : "fx:id=\"passwordTextfield\" was not
		// injected: check your FXML file 'loginScreen.fxml'.";
		// assert usernameTextfield != null : "fx:id=\"usernameTextfield\" was not
		// injected: check your FXML file 'loginScreen.fxml'.";
		// assert signinButton != null : "fx:id=\"signinButton\" was not injected: check
		// your FXML file 'loginScreen.fxml'.";
		// assert signupButton != null : "fx:id=\"signupButton\" was not injected: check
		// your FXML file 'loginScreen.fxml'.";
		// assert resetpasswordButton != null : "fx:id=\"resetpasswordButton\" was not
		// injected: check your FXML file 'loginScreen.fxml'.";
		// assert wronginputTextlabel != null : "fx:id=\"wronginputTextlabel\" was not
		// injected: check your FXML file 'loginScreen.fxml'.";

	}

	private void showModalWindow(Object controller, String fxml) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
			fxmlLoader.setController(controller);
			Parent rootPane = fxmlLoader.load();
			Scene scene = new Scene(rootPane);

			Stage dialog = new Stage();
			dialog.setScene(scene);
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
