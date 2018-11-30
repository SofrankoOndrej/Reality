package reality;

import java.io.IOException;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
import entities.User;

public class LoginScreenController {

	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
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
	private Label wrongPasswordOrUsernameLabel;

	public LoginScreenController() {
		userDao = DaoFactory.INSTANCE.getUserDao();
		createdUserModel = new UserFxModel();
	}

	@FXML
	void initialize() {
		// UserModel je len s loginom a heslom
		usernameTextfield.textProperty().bindBidirectional(createdUserModel.usernameProperty());
		passwordTextfield.textProperty().bindBidirectional(createdUserModel.passwordProperty());
		
		signinButton.setOnAction(ActionEvent -> {
			// select z databazy podla uzivatelskeho mena, 
			User user2login = userDao.getByUsername(createdUserModel.getUsername());
			if (user2login != null) {
				// BCrypt overenie hesla
				String pwHash = user2login.getPassword();
				boolean ok = BCrypt.checkpw(createdUserModel.getPassword(), pwHash);
				
				// nacitanie uvitacieho zobrazenia aplikacie alebo chybova hlaska
				if (ok) {
					wrongPasswordOrUsernameLabel.setVisible(false);

					// napln UserModel datami z databazy
					createdUserModel.setUser(userDao.getByUsernameFull(createdUserModel.getUsername()));
					try { // nacitaj hlavne okno aplikacie
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainApp.fxml"));
						MainAppController createMainApp = new MainAppController();
						fxmlLoader.setController(createMainApp);
						Parent rootPane = fxmlLoader.load();
						Scene scene = new Scene(rootPane);

						Stage mainAppStage = new Stage();
						mainAppStage.setScene(scene);
						mainAppStage.show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// zavretie login okna
					signinButton.getScene().getWindow().hide();
				} else { // wrong password - zlyhalo overenie hashu hesla
					wrongPasswordOrUsernameLabel.setVisible(true);
					wrongPasswordOrUsernameLabel.setText("Wrong pasword!");
					;
					passwordTextfield.setText("");
				}
			} else { // wrong username - null result from database
				wrongPasswordOrUsernameLabel.setVisible(true);
				wrongPasswordOrUsernameLabel.setText("Wrong username!");
				
				usernameTextfield.setText("");
				passwordTextfield.setText("");
			}

		});

		signupButton.setOnAction(actionEvent -> {
			CreateUserController createUserController = new CreateUserController();
			showModalWindow(createUserController, "CreateUser.fxml");
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
