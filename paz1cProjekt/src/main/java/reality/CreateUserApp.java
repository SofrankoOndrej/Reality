package reality;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreateUserApp extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		CreateUserController createUserController = new CreateUserController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateUser.fxml"));
		fxmlLoader.setController(createUserController);
		
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
