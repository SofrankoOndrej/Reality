package reality;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCrypt;

import entities.Address;
import entities.MapLayer;
import entities.Property;
import entities.Tile;
import entities.User;
import fxModels.MapLayerFxModel;
import fxModels.PropertyFxModel;
import fxModels.UserFxModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import persistent.DaoFactory;
import persistent.MapLayerDao;
import persistent.PropertyDao;
import persistent.UserDao;
import util.ContextMenus;
import util.LoadWebMap;
import util.MapUtils;
import util.TileUtils;
import util.Utils;

public class MainAppController {

	@FXML
	private Button previewMapTileButton;

	@FXML
	private TextField emailTextField;

	@FXML
	private TabPane mainTabPane;

	@FXML
	private AnchorPane accounSettingsAnchorPane;

	@FXML
	private TextField surnameTextField;

	@FXML
	private Button directoryPickerButton;

	@FXML
	private Slider zoomSlider;

	@FXML
	private Tab mainTab;

	@FXML
	private TextField cacheDirectoryTextField;

	@FXML
	private TextField mapNameTextField;

	@FXML
	private Canvas mapCanvas;

	@FXML
	private AnchorPane mapAnchorPane;

	@FXML
	private TextField inputUrlTextField;

	@FXML
	private Tab accountSettingsTab;

	@FXML
	private PasswordField newPasswordField;

	@FXML
	private TextField nameTextField;

	@FXML
	private Button changeMapLayerButton;

	@FXML
	private Button saveUserChangesButton;

	@FXML
	private Label messageLabel;

	@FXML
	private ListView<MapLayer> layersListView;

	@FXML
	private PasswordField oldPasswordField;

	@FXML
	private ImageView TilePreviewImageView;

	@FXML
	private ListView<Property> propertyListView;

	@FXML
	private Tab mapLayerTab;

	@FXML
	private TextField mapServerUrlTextField;

	@FXML
	private Button addMapLayerButton;

	@FXML
	private Button saveMapLayerButton;

	@FXML
	private TextField usernameTextField;

	private double orgSceneX, orgSceneY;
	private double orgTranslateX, orgTranslateY;
	private double newTranslateX, newTranslateY;
	private String initialBbox;

	private MapLayerDao mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
	private PropertyDao propertyDao = DaoFactory.INSTANCE.getPropertyDao();

	private MapLayerFxModel mapLayerModel = new MapLayerFxModel();
	private UserFxModel userModel = new UserFxModel();
	private PropertyFxModel propertyFxModel = new PropertyFxModel();

	// trieda poskytujuca kontextove okna schopne otvarat fxml
	private ContextMenus kontextoveMenu;

	public MainAppController() {
//		mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
		mapLayerModel = new MapLayerFxModel();
	}

	public MainAppController(UserFxModel userModel) {
//		mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
//		userDao = DaoFactory.INSTANCE.getUserDao();
		mapLayerModel = new MapLayerFxModel();
		this.userModel = userModel;
		// if user does not have BBox -> use default
		if (this.userModel.getLastBoundingBox() == null) {
			// kosice bbox
			String bbox = "21.148414221191,48.694094875476,21.327628698729,48.73532285334";
			this.userModel.setLastBoundingBox(bbox);
			this.userModel.setLastZoom(10);
			initialBbox = userModel.getLastBoundingBox();
		}

		// select default mapLayer
		List<MapLayer> mapLayers = mapLayerDao.getAll(userModel.getUser());
		// select default mapLayer
		if (!mapLayers.isEmpty()) {
			mapLayerModel.setMapLayer(mapLayers.get(0));
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

		// listener zmeny velkosti canvas
		mapCanvas.widthProperty().addListener(listener -> {
			updateBBox(0, 0, true);
		});
		mapCanvas.heightProperty().addListener(listener -> {
			updateBBox(0, 0, true);
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

		// zoom binding
		zoomSlider.valueProperty().bindBidirectional(userModel.lastZoomProperty());
		zoomSlider.valueProperty().addListener(listener -> {
			updateBBox(0, 0, true);
		});

		// kontextove menu mapy
		kontextoveMenu = new ContextMenus(userModel.getUser());
		ContextMenu canvasContextMenu = kontextoveMenu.getCanvasContextMenu();

		mapCanvas.setOnContextMenuRequested(actionEvent -> {
			canvasContextMenu.show(mapCanvas, actionEvent.getScreenX(), actionEvent.getScreenY());
			// poloha pridania nehnutelnosti
			kontextoveMenu.setxClick((int) actionEvent.getX());
			kontextoveMenu.setyClick((int) actionEvent.getY());
		});

		// open listView for changing layers
		changeMapLayerButton.setOnAction(actionEvent -> {
			if (layersListView.isVisible()) {
				layersListView.setVisible(false);
			} else {
				layersListView.setVisible(true);
			}
		});

		// hide layersListView after selection
		layersListView.setOnMouseClicked(mouseEvent -> {
			if (mouseEvent.getButton() == MouseButton.PRIMARY) {
				redrawMap();
				layersListView.setVisible(false);
			}
		});

		// kontextove menu volenia vrstiev
		// LIST VIEW mapLayers - ciastocne z prednasky
		layersListView.setItems(FXCollections.observableArrayList(mapLayerDao.getAll(userModel.getUser())));
		layersListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MapLayer>() {
			@Override
			public void changed(ObservableValue<? extends MapLayer> observable, MapLayer oldValue, MapLayer newValue) {
				mapLayerModel.setMapLayer(newValue);
			}
		});

		// context menu for mapLayers selection
		ContextMenu mapLayerSelectionContextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("edit map layer");
		item1.setOnAction(actionEvent -> {
			// switch to mapLayer edit tab
			layersListView.setVisible(false);
			mainTabPane.getSelectionModel().select(mapLayerTab);
			redrawMap();
		});

		MenuItem item2 = new MenuItem("delete map layer");
		item2.setOnAction(actionEvent -> {
			// delete map layer
			mapLayerDao.delete(mapLayerModel.getMapLayer());
			// TODO remove from listView
		});

		MenuItem item3 = new MenuItem("overlay map");
		item3.setOnAction(actionEvent -> {
			// do other stuff
			System.out.println("add layer to drawing function.");
		});

		// Add MenuItem to ContextMenu
		mapLayerSelectionContextMenu.getItems().addAll(item1, item2, item3);

		// show mapLayer context menu
		layersListView.setOnContextMenuRequested(actionEvent -> {
			mapLayerSelectionContextMenu.show(mapCanvas, actionEvent.getScreenX(), actionEvent.getScreenY());
		});

		// TODO PROPERTY LIST VIEW
		// LIST VIEW properties - ciastocne z prednasky
		propertyListView.setItems(FXCollections.observableArrayList(propertyDao.getAll(userModel.getUser())));
		propertyListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Property>() {
			@Override
			public void changed(ObservableValue<? extends Property> observable, Property oldValue, Property newValue) {
				propertyFxModel.setProperty(newValue);
			}
		});
		// TODO kontextove menu property
		ContextMenu propertySelectionContextMenu = new ContextMenu();
		// polozky v menu
		MenuItem editPropertyItem = new MenuItem("edit property");
		editPropertyItem.setOnAction(actionEvent -> {
			// switch to mapLayer edit tab
			System.out.println("editing property");
			Address address = new Address();
			address.setStreet("");
			address.setNumber("");
			CreatePropertyController propertyController = new CreatePropertyController(userModel.getUser(), address);
			showModalWindow(propertyController, "../reality/CreateProperty.fxml");
		});

		MenuItem deletePropertyItem = new MenuItem("delete property");
		deletePropertyItem.setOnAction(actionEvent -> {
			// delete map layer
			propertyDao.delete(propertyFxModel.getProperty());
			// TODO remove from listView
		});

		// Add MenuItem to ContextMenu
		propertySelectionContextMenu.getItems().addAll(editPropertyItem, deletePropertyItem);

		// show property context menu
		propertyListView.setOnContextMenuRequested(actionEvent -> {
			propertySelectionContextMenu.show(propertyListView, actionEvent.getScreenX(), actionEvent.getScreenY());
		});

		propertyListView.setOnMouseClicked(actionEvent -> {
			if (actionEvent.getClickCount() >= 2) {
				propertyListView.setItems(FXCollections.observableArrayList(propertyDao.getAll(userModel.getUser())));
			}
		});

		// MAP LAYER EDIT TAB
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

		// save mapLayer to data storage
		saveMapLayerButton.setOnAction(actionEvent -> {
			// validate url string
			boolean isValidUrl = Utils.isValidURL(mapLayerModel.getSampleTileUrl());
			if (isValidUrl) {
				mapLayerDao.save(mapLayerModel.getMapLayer(), userModel.getUser());
			}
		});
		addMapLayerButton.setOnAction(actionEvent -> {
			// validate url string
			boolean isValidUrl = Utils.isValidURL(mapLayerModel.getSampleTileUrl());
			if (isValidUrl) {
				mapLayerModel.setId(null);
				mapLayerModel.setMapLayer(mapLayerDao.save(mapLayerModel.getMapLayer(), userModel.getUser()));
				layersListView.getItems().add(mapLayerModel.getMapLayer());
			}
		});

		// ACCOUNT SETTINGS TAB
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

	private void updateBBox(int xShift, int yShift, boolean update) {
		// get old BBOX from user
		double[] oldBbox = MapUtils.bBoxString2DoubleArray(initialBbox);
		// zrataj vhodnu BBOX pre danu CANVAS
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

	// TODO implement viewing multiple layers at once (using alpha channel)
	public void redrawMap() {
		GraphicsContext gc = mapCanvas.getGraphicsContext2D();

		// determine what tiles you need
		LoadWebMap webMapLoader = new LoadWebMap(userModel.getUser(), mapLayerModel.getMapLayer(),
				userModel.getLastZoom());
		List<Tile> tileList = webMapLoader.getListOfTiles(userModel.getLastBoundingBox());

		// get pixel position of mapCanvas
		double[] globePositionBbox = MapUtils.bBoxString2DoubleArray(userModel.getLastBoundingBox());
		int[] pixelPositionBbox = TileUtils.globe2pixel(globePositionBbox[0], globePositionBbox[1],
				userModel.getLastZoom());

		gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());

		// NEW THREADS FOR LOADING IMAGES
		// loading via stream loads tiles concurrently
		tileList.stream().forEach(tile -> {
			Image tileImage;
			// get map tiles
			tileImage = webMapLoader.getTile(tile);

			// set pixel position for iterated tile
			int[] pixelPosition = TileUtils.tile2pixel(tile);

			int xPosition = pixelPosition[0] - pixelPositionBbox[0];
			int yPosition = pixelPosition[1] - pixelPositionBbox[1];
			// draw each tile into graphical context of canvas
			gc.drawImage(tileImage, xPosition, yPosition);
		});
		mapCanvas.setViewOrder(2);

		// TODO get properties inside BBOX

		// TODO draw symbols at positions of properties

	}

	public void drawInCanvas() {
		GraphicsContext gc = mapCanvas.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
		gc.fillRect(10, 10, mapCanvas.getWidth() - 20, mapCanvas.getHeight() - 20);
	}
}
