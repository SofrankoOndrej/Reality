package reality;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RunMainApp extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		MainAppController createMainAppController = new MainAppController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainApp.fxml"));
		fxmlLoader.setController(createMainAppController);

		Parent rootPane = fxmlLoader.load();
		Scene scene = new Scene(rootPane);
		stage.setTitle("Main map app");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}