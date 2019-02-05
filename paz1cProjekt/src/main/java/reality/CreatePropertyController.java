package reality;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entities.Address;
import entities.Participant;
import entities.Property;
import entities.User;
import fxModels.AddressFxModel;
import fxModels.ParticipantFxModel;
import fxModels.PropertyFxModel;
import fxModels.UserFxModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistent.AddressDao;
import persistent.DaoFactory;
import persistent.ParticipantDao;
import persistent.PropertyDao;
import persistent.UserDao;

public class CreatePropertyController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button savePropertyButton;

	@FXML
	private TextField ratingTextField;

	@FXML
	private TextField typeTextField;

	@FXML
	private TextField addressTextfield;

	@FXML
	private ListView<Participant> participantsListView;

	@FXML
	private TextField nameTextfield;

	@FXML
	private TextField urlTextField;

	@FXML
	private TextField priceTextField;

	@FXML
	private TextField descriptionTextField;

	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
	private UserFxModel userFxModel = new UserFxModel();
	private User user;
	private AddressDao addresDao = DaoFactory.INSTANCE.getAddressDao();
	private AddressFxModel addressFxModel = new AddressFxModel();
	private Address address;
	private ParticipantDao participantDao = DaoFactory.INSTANCE.getParticipantDao();
	private ParticipantFxModel participantFxModel = new ParticipantFxModel();
	private PropertyDao propertyDao = DaoFactory.INSTANCE.getPropertyDao();
	private PropertyFxModel propertyFxModel = new PropertyFxModel();
	List<Participant> participantList;

	public CreatePropertyController(User user, Address address) {
		this.user = user;
		this.address = address;
		participantList = new ArrayList<>();
		userFxModel.setUser(user);
	}

	public CreatePropertyController(User user, Property property) {
		this.user = user;
		this.address = addresDao.getById(property.getAddress_id());
		this.propertyFxModel.setProperty(property);
		// TODO load all participants - only if updating
		if (property.getParticipants() != null) {
			participantList = participantDao.getAll(property);
		}
		userFxModel.setUser(user);
	}

	@FXML
	void initialize() {
		ratingTextField.textProperty().bindBidirectional(propertyFxModel.ratingProperty());
		nameTextfield.textProperty().bindBidirectional(propertyFxModel.nameProperty());
		typeTextField.textProperty().bindBidirectional(propertyFxModel.typeProperty());
		descriptionTextField.textProperty().bindBidirectional(propertyFxModel.descriptionProperty());
		urlTextField.textProperty().bindBidirectional(propertyFxModel.urlProperty());
		priceTextField.textProperty().bindBidirectional(propertyFxModel.priceProperty());

		addressTextfield.setText(address.toString());

		// kontextove menu participantov
//		participantsListView.setItems(FXCollections.observableArrayList(participantList));
//		participantsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Participant>() {
//			@Override
//			public void changed(ObservableValue<? extends Participant> observable, Participant oldValue,
//					Participant newValue) {
//				participantFxModel.setParticipant(newValue);
//			}
//		});

		// context menu for participants on properties
		ContextMenu participantSelectionContextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("add participant");
		item1.setOnAction(actionEvent -> {
			// TODO open participant fxml
			System.out.println("pridavam ucastnika");

		});

		MenuItem item2 = new MenuItem("edit participant");
		item2.setOnAction(actionEvent -> {
			// TODO load participant to participant fxml
			System.out.println("editujem ucastnika");
		});

		MenuItem item3 = new MenuItem("delete participant");
		item3.setOnAction(actionEvent -> {
			// TODO delete participant
			System.out.println("mazem ucastnika");
		});

		// Add MenuItem to ContextMenu
		participantSelectionContextMenu.getItems().addAll(item1, item2, item3);

		// show participant context menu
		participantsListView.setOnContextMenuRequested(actionEvent -> {
			participantSelectionContextMenu.show(participantsListView, actionEvent.getScreenX(),
					actionEvent.getScreenY());
		});

		// TODO edit address
		addressTextfield.setOnMouseClicked(actionEvent -> {
			System.out.println("upravujem adresu");
		});

		// save property
		savePropertyButton.setOnAction(actionEvent -> {
			propertyDao.save(propertyFxModel.getProperty(), user, address);

			// draw point into map

			// update listview of properties

			// close this dialog
			Stage stage = (Stage) savePropertyButton.getScene().getWindow();
			stage.hide();
		});

	}
}
