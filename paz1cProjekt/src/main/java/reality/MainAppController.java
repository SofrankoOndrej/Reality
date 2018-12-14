package reality;

import java.io.File;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCrypt;
import entities.MapLayer;
import entities.Tile;
import javafx.event.EventHandler;
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
import util.MapUtils;
import util.TileUtils;
import util.Utils;

public class MainAppController {

	private MapLayerDao mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
	private MapLayerFxModel mapLayerModel;
	private UserFxModel userModel;
	// private StringProperty bbox;

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

	private double orgSceneX, orgSceneY;
	private double orgTranslateX, orgTranslateY;
	private double newTranslateX, newTranslateY;
	private String initialBbox;

	public MainAppController() {
		mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
		mapLayerModel = new MapLayerFxModel();
	}

	public MainAppController(UserFxModel userModel) {
		mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
		userDao = DaoFactory.INSTANCE.getUserDao();
		mapLayerModel = new MapLayerFxModel();
		this.userModel = userModel;
		// if user does not have BBox -> use default
		if (this.userModel.getLastBoundingBox() == null) {
			// kosice bbox
			String bbox = "21.148414221191,48.694094875476,21.327628698729,48.73532285334";
			this.userModel.setLastBoundingBox(bbox);
			this.userModel.setLastZoom(13);
			initialBbox = userModel.getLastBoundingBox();
		}
	}

	@FXML
	void initialize() {
		// set initial size of map canvas
		mapCanvas.setWidth(mapAnchorPane.getWidth());
		mapCanvas.setHeight(mapAnchorPane.getHeight());

		// automatic resize of canvas
		mapCanvas.widthProperty().bind(mapAnchorPane.widthProperty());
		mapCanvas.heightProperty().bind(mapAnchorPane.heightProperty());
		// mapCanvas.widthProperty().bind(mapCanvas.heightProperty());

		// listener zmeny velkosti canvas
		mapCanvas.widthProperty().addListener(listener -> {
			updateBBox(0, 0, true);
			// redrawMap();
		});
		mapCanvas.heightProperty().addListener(listener -> {
			updateBBox(0, 0, true);
//			redrawMap();
		});

		// listen to mouse gestures

		mapCanvas.setOnMousePressed(mapOnMousePressedEventHandler -> {
			orgSceneX = mapOnMousePressedEventHandler.getX();
			orgSceneY = mapOnMousePressedEventHandler.getY();
			orgTranslateX = mapCanvas.getTranslateX();
			orgTranslateY = mapCanvas.getTranslateY();

		});

		mapCanvas.setOnMouseDragged(mapOnMouseDraggedEventHandler -> {
			double offsetX = mapOnMouseDraggedEventHandler.getX() - orgSceneX;
			double offsetY = mapOnMouseDraggedEventHandler.getY() - orgSceneY;
			newTranslateX = orgTranslateX + offsetX;
			newTranslateY = orgTranslateY + offsetY;
			// System.out.println(newTranslateX + ", " + newTranslateY);
			// System.out.println(initialBbox);
			updateBBox((int) -newTranslateX, (int) -newTranslateY, false);
		});

		mapCanvas.setOnMouseReleased(mapOnMouseDragRealesedEventHandler -> {
			// System.out.println("UPDATED: " + newTranslateX + ", " + newTranslateY);
			// System.out.println(initialBbox);
			updateBBox((int) -newTranslateX, (int) -newTranslateY, true);
			newTranslateX = 0;
			newTranslateY = 0;
		});

		mapCanvas.setOnScroll(scrollEventHandler -> {
			double pixelsToScroll = scrollEventHandler.getDeltaY() / scrollEventHandler.getMultiplierY();
			userModel.setLastZoom(userModel.getLastZoom() + pixelsToScroll);
			if (userModel.getLastZoom() < 10) {
				userModel.setLastZoom(10);
			}
			if (userModel.getLastZoom() > 18) {
				userModel.setLastZoom(18);
			}

		});

		// listen to changes of last bbox
		userModel.lastBoundingBoxProperty().addListener(listener -> redrawMap());
		userModel.lastZoomProperty().addListener(listener -> redrawMap());
		// mapCanvas.widthProperty().addListener(listener -> updateBBox());
		// mapCanvas.heightProperty().addListener(listener -> updateBBox());

		// zoom binding
		zoomSlider.valueProperty().bindBidirectional(userModel.lastZoomProperty());
		zoomSlider.valueProperty().addListener(listener -> {
			updateBBox(0, 0, true);
			// redrawMap();
		});
		// zoomSlider.valueProperty().addListener(listener -> updateBBox());

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

	private void updateBBox(int xShift, int yShift, boolean update) {
		// get old BBOX from user
		double[] oldBbox = MapUtils.bBoxString2DoubleArray(initialBbox);
		// TODO zrataj vhodnu BBOX pre danu CANVAS
		double mapCanvasPixelHeight = mapCanvas.getHeight();
		double mapCanvasPixelWidth = mapCanvas.getWidth();

		// pixel position of old bounds center
		int[] oldBoxCenter = TileUtils.globe2pixel((oldBbox[0] + oldBbox[2]) / 2.0, (oldBbox[1] + oldBbox[3]) / 2.0,
				userModel.getLastZoom());

		// get new bbox bounds = old center +- half of canvas size, + x/y shift
		double[] newBboxUL = TileUtils.pixel2globe(oldBoxCenter[0] - (int) (mapCanvasPixelWidth / 2) + xShift,
				oldBoxCenter[1] - (int) (mapCanvasPixelHeight / 2) + yShift, userModel.getLastZoom());
		double[] newBboxBR = TileUtils.pixel2globe(oldBoxCenter[0] + (int) (mapCanvasPixelWidth / 2) + xShift,
				oldBoxCenter[1] + (int) (mapCanvasPixelHeight / 2) + yShift, userModel.getLastZoom());
		// write into newBboxGlobe
		double newBboxGlobe[] = new double[4];
		newBboxGlobe[0] = newBboxUL[0];
		newBboxGlobe[1] = newBboxUL[1];
		newBboxGlobe[2] = newBboxBR[0];
		newBboxGlobe[3] = newBboxBR[1];

		// update user with new BBOX
		userModel.setLastBoundingBox(MapUtils.bBoxDoubleArray2String(newBboxGlobe));
		if (update) {
			initialBbox = MapUtils.bBoxDoubleArray2String(newBboxGlobe);
			// System.out.println("UPDATED initial bbox: " + initialBbox);
		}
	}

	private void redrawMap() {
		GraphicsContext gc = mapCanvas.getGraphicsContext2D();

		// TODO implement mapLayer slection
		// select used mapLayer
		List<MapLayer> mapLayers = mapLayerDao.getAll(userModel.getUser());
		// select default mapLayer
		if (!mapLayers.isEmpty()) {
			mapLayerModel.setMapLayer(mapLayers.get(0));
		}

		// determine what tiles you need
		LoadWebMap webMapLoader = new LoadWebMap(userModel.getUser(), mapLayerModel.getMapLayer(),
				userModel.getLastZoom());
		List<Tile> tileList = webMapLoader.getListOfTiles(userModel.getLastBoundingBox());

		// get pixel position of mapCanvas
		double[] globePositionBbox = MapUtils.bBoxString2DoubleArray(userModel.getLastBoundingBox());
		int[] pixelPositionBbox = TileUtils.globe2pixel(globePositionBbox[0], globePositionBbox[1],
				userModel.getLastZoom());

		// TODO: IMPLEMENT NEW THREAD FOR LOADING IMAGES
		Image tileImage;
		for (Tile tile : tileList) {
			// get map tiles
			tileImage = webMapLoader.getTile(tile);
			// TODO compute pixel position of imageTile in respect to BBox = canvas ->
			// compute shift

			// set pixel position for iterated tile
			int[] pixelPosition = TileUtils.tile2pixel(tile);

			int xPosition = pixelPosition[0] - pixelPositionBbox[0];// + (int) mapCanvas.getWidth();
			int yPosition = pixelPosition[1] - pixelPositionBbox[1];// + (int) mapCanvas.getHeight();
			// draw each tile into graphical context of canvas
			// System.out.println(tile.toString() + "x position: " + xPosition + "; y
			// position: " + yPosition);

			gc.drawImage(tileImage, xPosition, yPosition);
		}
		mapCanvas.setViewOrder(1);
	}

	public void drawInCanvas() {
		GraphicsContext gc = mapCanvas.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
		gc.fillRect(10, 10, mapCanvas.getWidth() - 20, mapCanvas.getHeight() - 20);

	}

}
