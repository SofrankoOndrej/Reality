package reality;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApp extends Application {
	public void start(Stage stage) throws Exception {
		LoginScreenController loginScreenController = new LoginScreenController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
		fxmlLoader.setController(loginScreenController);
		
		Parent rootPane = fxmlLoader.load()	;
		Scene scene = new Scene(rootPane);
		stage.setTitle("login screen");
		stage.setScene(scene);
		stage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
