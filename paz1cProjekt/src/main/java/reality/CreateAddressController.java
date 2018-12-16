package reality;

import persistent.AddressDao;
import persistent.DaoFactory;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateAddressController {
	private AddressDao addressDao = DaoFactory.INSTANCE.getAddressDao();
	private AddressFxModel createdAddressModel;
	
	
	
	@FXML
	private Button addParticipantButton;

	@FXML
	private TextField streetTextfield;

	@FXML
	private TextField numberTextfield;

	@FXML
	private TextField cityTextfield;

	@FXML
	private Button addPropertyButton;

	@FXML
	private PasswordField latitudeTextField;

	@FXML
	private PasswordField zipCodeTextfield;

	@FXML
	private TextField countryTextfield;

	@FXML
	private PasswordField longitudeTextField;
	
	public CreateAddressController() {
		addressDao = DaoFactory.INSTANCE.getAddressDao();
		createdAddressModel = new AddressFxModel();
	}

	@FXML
	void initialize() {
		countryTextfield.textProperty().bindBidirectional(createdAddressModel.stateProperty());
		cityTextfield.textProperty().bindBidirectional(createdAddressModel.cityProperty());
		streetTextfield.textProperty().bindBidirectional(createdAddressModel.streetProperty());
		zipCodeTextfield.textProperty().bindBidirectional(createdAddressModel.zipCodeProperty());
		numberTextfield.textProperty().bindBidirectional(createdAddressModel.numberProperty());
		longitudeTextField.textProperty().bindBidirectional(createdAddressModel.longitudeProperty());
		latitudeTextField.textProperty().bindBidirectional(createdAddressModel.latitudeProperty());
		
		
		addParticipantButton.setOnAction(actionEvent -> {
			addressDao.save(createdAddressModel.getAddress());
			// TODO open stage with adding participant
			
			
		});
		
		addPropertyButton.setOnAction(actionEvent ->{
			addressDao.save(createdAddressModel.getAddress());
			// TODO open new modal stage for editing properties
//			CreateUserController createUserController = new CreateUserController();
//			showModalWindow(createUserController, "CreateUser.fxml");
			System.out.println("opening stage for adding property");
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
