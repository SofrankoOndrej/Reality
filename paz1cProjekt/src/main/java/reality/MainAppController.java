package reality;

import org.springframework.security.crypto.bcrypt.BCrypt;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import persistent.DaoFactory;
import persistent.MapLayerDao;
import persistent.UserDao;
import util.Utils;

public class MainAppController {

	private MapLayerDao mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
	private MapLayerFxModel MapLayerModel;
	private UserFxModel userModel;

	@FXML
	private AnchorPane mapAnchorPane;

	@FXML
	private Canvas mapCanvas;

	@FXML
	private Button changeMapLayerButton;

	@FXML
	private ScrollPane layersScrollPane;

	@FXML
	private AnchorPane layersAnchorPane;

	@FXML
	private ImageView TilePreviewImageView;

	@FXML
	private TextField inputUrlTextField;

	@FXML
	private Button previewMapTileButton;

	@FXML
	private TextField mapServerUrlTextField;

	@FXML
	private TextField mapNameTextField;

	@FXML
	private Button saveMapLayerButton;

	@FXML
	private Tab accountSettingsTab;

	@FXML
	private AnchorPane accounSettingsAnchorPane;

	@FXML
	private Button saveUserChangesButton;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField surnameTextField;

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField usernameTextField;

	@FXML
	private PasswordField oldPasswordField;

	@FXML
	private PasswordField newPasswordField;

	@FXML
	private TextField cacheDirectoryTextField;

	@FXML
	private Button directoryPickerButton;

	@FXML
	private Label messageLabel;

	public MainAppController() {
		mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
		MapLayerModel = new MapLayerFxModel();
	}

	public MainAppController(UserFxModel userModel) {
		mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
		userDao = DaoFactory.INSTANCE.getUserDao();
		MapLayerModel = new MapLayerFxModel();
		this.userModel = userModel;
	}

	@FXML
	void initialize() {
		// automatic resize of canvas
		mapCanvas.widthProperty().bind(mapAnchorPane.widthProperty());
		mapCanvas.heightProperty().bind(mapAnchorPane.heightProperty());

		// listener zmeny velkosti canvas
		mapCanvas.widthProperty().addListener(observable -> redrawMap());
		mapCanvas.heightProperty().addListener(observable -> redrawMap());

		// mapLayer binding to map model
		mapNameTextField.textProperty().bindBidirectional(MapLayerModel.nameProperty());
		inputUrlTextField.textProperty().bindBidirectional(MapLayerModel.sampleTileUrlProperty());
		mapServerUrlTextField.textProperty().bindBidirectional(MapLayerModel.mapServerUrlProperty());

		// preview map TILE from URL
		previewMapTileButton.setOnAction(actionEvent -> {
			// validate url string
			if (Utils.isValidURL(MapLayerModel.getSampleTileUrl())) {
				Image tileImage = new Image(MapLayerModel.getSampleTileUrl());
				TilePreviewImageView.setImage(tileImage);
				MapLayerModel.setUrl(Utils.parseUrl2UrlMapBaseFormat(MapLayerModel.getSampleTileUrl()));
			} else {
				inputUrlTextField.setText("Please enter valid URL string");
			}
		});

		// change layers
		changeMapLayerButton.setOnAction(actionEvent -> {
			if (layersScrollPane.isVisible()) {
				layersScrollPane.setVisible(false);
			} else {
				layersScrollPane.setVisible(true);
				// zobraz ikonky mapovych vrstiev

			}
		});

		// save mapLayer to data storage
		saveMapLayerButton.setOnAction(actionEvent -> {
			// validate url string

			// parse url to basemap format

			mapLayerDao.save(MapLayerModel.getMapLayer(), userModel.getUser());
		});

		// account settings tab
		//
		// copy of logged in user without password hash - for editing purposes
		UserFxModel editedUserModel = new UserFxModel();
		editedUserModel.setUser(userModel.getUser());
		editedUserModel.setPassword(null);
		// user binding to editable fields
		nameTextField.textProperty().bindBidirectional(editedUserModel.nameProperty());
		surnameTextField.textProperty().bindBidirectional(editedUserModel.surnameProperty());
		emailTextField.textProperty().bindBidirectional(editedUserModel.emailProperty());
		usernameTextField.textProperty().bindBidirectional(editedUserModel.usernameProperty());
		newPasswordField.textProperty().bindBidirectional(editedUserModel.passwordProperty());
		cacheDirectoryTextField.textProperty().bindBidirectional(editedUserModel.cacheFolderPathProperty());

		saveUserChangesButton.setOnAction(actionEvent -> {
			if (editedUserModel.getPassword() == null) {
				if (oldPasswordField.getText() != null) {
					messageLabel.setText("Please enter new password!");
					messageLabel.setVisible(true);
				} else {
					// update everything except password
					userDao.save(editedUserModel.getUser());

					messageLabel.setText("Changes saved successfully!");
					messageLabel.setVisible(true);
					// pass old password hash to updated userModel
					String password = userModel.getPassword();
					userModel.setUser(editedUserModel.getUser());
					userModel.setPassword(password);
				}
			} else {
				// validate password
				String pwHash = userModel.getPassword();

				// ak zadany novy password tak over
				boolean ok = BCrypt.checkpw(oldPasswordField.getText(), pwHash);
				if (ok) {
					// vytvorenie hashu hesla
					String salt = BCrypt.gensalt();
					String newPwHash = BCrypt.hashpw(editedUserModel.getPassword(), salt);
					editedUserModel.setPassword(newPwHash);
					// update all, including password
					userModel.setUser(editedUserModel.getUser());
					userDao.save(editedUserModel.getUser());

					// write successful message
					messageLabel.setText("Changes saved successfully!");
					messageLabel.setVisible(true);
				} else {
					messageLabel.setText("Wrong password!");
					messageLabel.setVisible(true);
				}
				editedUserModel.setPassword(null);
				oldPasswordField.setText(null);
			}
		});

	}

	private void redrawMap() {
		GraphicsContext gc = mapCanvas.getGraphicsContext2D();
		// get bounding box
//		userModel.

		// determine what tiles you need
		// get map tiles
		// construct map

		// draw map

		mapCanvas.setViewOrder(1);
		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
	}

	public void drawInCanvas() {
		GraphicsContext gc = mapCanvas.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
		gc.fillRect(10, 10, mapCanvas.getWidth() - 20, mapCanvas.getHeight() - 20);

	}

}
