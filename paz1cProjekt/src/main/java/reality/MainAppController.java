package reality;

import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import persistent.DaoFactory;
import persistent.MapLayerDao;
import util.Utils;

public class MainAppController {

	private MapLayerDao mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
	private MapLayerFxModel MapLayerModel;
	private UserFxModel userModel;

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
		mapNameTextField.textProperty().bindBidirectional(MapLayerModel.nameProperty());

		inputUrlTextField.textProperty().bindBidirectional(MapLayerModel.sampleTileUrlProperty());
		mapServerUrlTextField.textProperty().bindBidirectional(MapLayerModel.mapServerUrlProperty());

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

		saveMapLayerButton.setOnAction(actionEvent -> {
			// validate url string

			// parse url to basemap format

			mapLayerDao.save(MapLayerModel.getMapLayer(), userModel.getUser());

		});

	}
}
