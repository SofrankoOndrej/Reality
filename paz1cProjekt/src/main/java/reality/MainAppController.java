package reality;

import entities.User;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import persistent.DaoFactory;
import persistent.MapLayerDao;
import util.Utils;

public class MainAppController {

	private MapLayerDao mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
	private MapLayerFxModel MapLayerModel;
	private UserFxModel userModel;

	@FXML
	private AnchorPane mapAnchorPane;

	@FXML
	private Canvas mapCanvas;

	@FXML
	private Button changeMapLayerButton;

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

	public MainAppController() {
		mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
		MapLayerModel = new MapLayerFxModel();
	}

	public MainAppController(UserFxModel userModel) {
		mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
		MapLayerModel = new MapLayerFxModel();
		this.userModel = userModel;
	}

	@FXML
	void initialize() {
		// automatic resize of canvas
		mapCanvas.widthProperty().bind(mapAnchorPane.widthProperty());
		mapCanvas.heightProperty().bind(mapAnchorPane.heightProperty());
		this.drawInCanvas();
		
		// listener zmeny velkosti canvas
		mapCanvas.widthProperty().addListener(observable -> redrawMap());
		mapCanvas.heightProperty().addListener(observable -> redrawMap());
		
		// mapLayer binding to model
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

		// save mapLayer to data storage
		saveMapLayerButton.setOnAction(actionEvent -> {
			// validate url string

			// parse url to basemap format

			mapLayerDao.save(MapLayerModel.getMapLayer(), userModel.getUser());

		});

	}

	private void redrawMap() {
		// get bounding box
		// get map tiles
		// construct map
		// draw map
		GraphicsContext gc = mapCanvas.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
	}
	
	
	public void drawInCanvas() {
		GraphicsContext gc = mapCanvas.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
		
	}

}
