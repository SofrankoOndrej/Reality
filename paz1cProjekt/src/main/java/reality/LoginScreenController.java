package reality;

import java.io.IOException;

import org.springframework.security.crypto.bcrypt.BCrypt;

import entities.User;
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
import persistent.DaoFactory;
import persistent.UserDao;

public class LoginScreenController {

	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
	private User user;
	private UserFxModel createdUserModel;

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
    private Label wrongPasswordOrUsernameLabel;

	public LoginScreenController() {
		userDao = DaoFactory.INSTANCE.getUserDao();
		createdUserModel = new UserFxModel();
	}

	@FXML
	void initialize() {
		usernameTextfield.textProperty().bindBidirectional(createdUserModel.usernameProperty());
		passwordTextfield.textProperty().bindBidirectional(createdUserModel.passwordProperty());

		signinButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// select z databazy podla uzivatelskeho mena
				userDao.getByUsername(createdUserModel.getUsername());

				//				User user2login = ;
				//				String pwHash = ;
				
				
				// BCrypt overenie hesla
				boolean ok = BCrypt.checkpw(createdUserModel.getPassword(), pwHash);

				// nacitanie uvitacieho zobrazenia aplikacie alebo chybova hlaska
				if (ok) {

					try {
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainApp.fxml"));
						CreateMainAppController createMainApp = new CreateMainAppController();
						fxmlLoader.setController(createMainApp);
						Parent rootPane = fxmlLoader.load();
						Scene scene = new Scene(rootPane);

						Stage dialog = new Stage();
						dialog.setScene(scene);
						dialog.show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// zavretie login okna
					signinButton.getScene().getWindow().hide();
				}else {
					wrongPasswordOrUsernameLabel.setDisable(false);
				}
			}

		});

		signupButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				CreateUserController createUserController = new CreateUserController();
				showModalWindow(createUserController, "CreateUser.fxml");
			}

		});

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
