package reality;

import org.springframework.security.crypto.bcrypt.BCrypt;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import persistent.DaoFactory;
import persistent.UserDao;

public class CreateUserController {

	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
	private UserFxModel createdUserModel;
	// private UserFxModel createdUser = new UserFxModel();

	@FXML
	private Button createuserButton;

	@FXML
	private PasswordField passwordTextfield;

	@FXML
	private TextField nameTextfield;

	@FXML
	private TextField surnameTextfield;

	@FXML
	private TextField emailTextfield;

	@FXML
	private TextField usernameTextfield;

	public CreateUserController() {
		userDao = DaoFactory.INSTANCE.getUserDao();
		createdUserModel = new UserFxModel();
	}

	@FXML
	void initialize() {
		nameTextfield.textProperty().bindBidirectional(createdUserModel.nameProperty());
		surnameTextfield.textProperty().bindBidirectional(createdUserModel.surnameProperty());
		emailTextfield.textProperty().bindBidirectional(createdUserModel.emailProperty());
		usernameTextfield.textProperty().bindBidirectional(createdUserModel.usernameProperty());
		passwordTextfield.textProperty().bindBidirectional(createdUserModel.passwordProperty());

		createuserButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(createdUserModel.toString());
				// vytvorenie hash hesla
				String salt = BCrypt.gensalt();
				String pwHash = BCrypt.hashpw(createdUserModel.getPassword(), salt);
				createdUserModel.setPassword(pwHash);
				// ulozenie pouzivatela
				userDao.save(createdUserModel.getUser());
				createuserButton.getScene().getWindow().hide();
			}
		});

	}
}
