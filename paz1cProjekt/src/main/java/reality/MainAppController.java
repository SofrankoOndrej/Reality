package reality;

import java.io.File;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;

import entities.Tile;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import persistent.DaoFactory;
import persistent.MapLayerDao;
import persistent.UserDao;
import util.LoadWebMap;
import util.Utils;

public class MainAppController {

	private MapLayerDao mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
	private MapLayerFxModel mapLayerModel;
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
	@FXML
	private Slider zoomSlider;

	public MainAppController() {
		mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
		mapLayerModel = new MapLayerFxModel();
	}

	public MainAppController(UserFxModel userModel) {
		mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
		userDao = DaoFactory.INSTANCE.getUserDao();
		mapLayerModel = new MapLayerFxModel();
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

		// bind BBox to user.BBox +
		
		
		// zoom binding
		zoomSlider.valueProperty().bindBidirectional(userModel.lastZoomProperty());

		// mapLayer binding to map model
		mapNameTextField.textProperty().bindBidirectional(mapLayerModel.nameProperty());
		inputUrlTextField.textProperty().bindBidirectional(mapLayerModel.sampleTileUrlProperty());
		mapServerUrlTextField.textProperty().bindBidirectional(mapLayerModel.mapServerUrlProperty());

		// preview map TILE from URL
		previewMapTileButton.setOnAction(actionEvent -> {
			// validate url string
			if (Utils.isValidURL(mapLayerModel.getSampleTileUrl())) {
				Image tileImage = new Image(mapLayerModel.getSampleTileUrl());
				TilePreviewImageView.setImage(tileImage);
				mapLayerModel.setUrl(Utils.parseUrl2UrlMapBaseFormat(mapLayerModel.getSampleTileUrl()));
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

			mapLayerDao.save(mapLayerModel.getMapLayer(), userModel.getUser());
		});

		// account settings tab
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
				if (!oldPasswordField.getText().isEmpty()) {
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
			}
			editedUserModel.setPassword(null);
			oldPasswordField.setText(null);
		});

		// select cache folder
		directoryPickerButton.setOnAction(actionEvent -> {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setTitle("Please select cache directory.");
			directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			File dirPath = directoryChooser.showDialog(null);
			if (dirPath != null) {
				String dirPathString = dirPath.toString();
				editedUserModel.setCacheFolderPath(dirPathString);
			}
		});
	}

	private void redrawMap() {
		GraphicsContext gc = mapCanvas.getGraphicsContext2D();
		// get bounding box
		
		// if user does not have BBox -> use default
		if (userModel.getLastBoundingBox() == null) {
			String bbox = "1; 1; 2; 2" ;
			userModel.setLastBoundingBox(bbox);
		}

		// determine what tiles you need
		// get zoom
		LoadWebMap webMapLoader = new LoadWebMap(userModel.getUser(), mapLayerModel.getMapLayer(), userModel.getLastZoom());
		List<Tile> tileList = webMapLoader.getListOfTiles(userModel.getLastBoundingBox());

		Image tileImage;
		for (Tile tile : tileList) {
			// get map tiles
			tileImage = webMapLoader.getTile(tile);
			// compute position of imageTile in respect to BBox = canvas
			int xPixelPosition = 0;
			int yPixelPosition = 0;
			// draw each tile into graphical context of canvas
			gc.drawImage(tileImage, xPixelPosition, yPixelPosition);
		}
		// draw map

		mapCanvas.setViewOrder(1);

//		gc.setFill(Color.rgb(15, 25, 95));
//		gc.fillRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
	}

	public void drawInCanvas() {
		GraphicsContext gc = mapCanvas.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
		gc.fillRect(10, 10, mapCanvas.getWidth() - 20, mapCanvas.getHeight() - 20);

	}

}
