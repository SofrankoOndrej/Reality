package reality;

import persistent.AddressDao;
import persistent.DaoFactory;

import java.io.IOException;

import entities.Address;
import entities.User;
import fxModels.AddressFxModel;
import fxModels.UserFxModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateAddressController {
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
	private TextField latitudeTextField;

	@FXML
	private TextField zipCodeTextField;

	@FXML
	private TextField countryTextfield;

	@FXML
	private TextField longitudeTextField;

	private UserFxModel userFxModel = new UserFxModel();
	private User user;

	private AddressDao addressDao = DaoFactory.INSTANCE.getAddressDao();
	private AddressFxModel createdAddressFxModel = new AddressFxModel();
	private Address address;
	
	public CreateAddressController(User user,Address address) {
		this.createdAddressFxModel.setAddress(address);
		this.address = address;
		this.userFxModel.setUser(user);
		this.user = user;
	}

	@FXML
	void initialize() {
		countryTextfield.textProperty().bindBidirectional(createdAddressFxModel.stateProperty());
		cityTextfield.textProperty().bindBidirectional(createdAddressFxModel.cityProperty());
		streetTextfield.textProperty().bindBidirectional(createdAddressFxModel.streetProperty());
		zipCodeTextField.textProperty().bindBidirectional(createdAddressFxModel.zipCodeProperty());
		numberTextfield.textProperty().bindBidirectional(createdAddressFxModel.numberProperty());
		longitudeTextField.textProperty().bindBidirectional(createdAddressFxModel.longitudeProperty());
		latitudeTextField.textProperty().bindBidirectional(createdAddressFxModel.latitudeProperty());

		addParticipantButton.setOnAction(actionEvent -> {
			addressDao.save(createdAddressFxModel.getAddress());
			// TODO open stage with adding participant

		});

		addPropertyButton.setOnAction(actionEvent -> {
			// ulozi adresu a dostane spat adresu aj s ID
			this.address = addressDao.save(createdAddressFxModel.getAddress());
			// TODO open new modal stage for editing properties
			CreatePropertyController propertyController = new CreatePropertyController(user,address);
			showModalWindow(propertyController, "../reality/CreateProperty.fxml");
			//System.out.println("opening stage for adding property");
			
			// close this dialog
			Stage stage = (Stage) addPropertyButton.getScene().getWindow();
			stage.hide();
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
