package reality;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import persistent.DaoFactory;
import persistent.MapLayerDao;

public class MainAppController {

	private MapLayerDao mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
	private MapLayerFxModel MapLayerModel;

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

	@FXML
	void initialize() {
		mapNameTextField.textProperty().bindBidirectional(MapLayerModel.nameProperty());

		inputUrlTextField.textProperty().bindBidirectional(MapLayerModel.sampleTileUrlProperty());
		mapServerUrlTextField.textProperty().bindBidirectional(MapLayerModel.mapServerUrlProperty());

		previewMapTileButton.setOnAction(actionEvent -> {
			// validate url string

			// BufferedImage img = ImageIO.read(new URL(inputUrlTextField.getText()));
			Image tileImage = new Image(inputUrlTextField.getText());
			TilePreviewImageView.setImage(tileImage);
		});

		saveMapLayerButton.setOnAction(actionEvent -> {
			// validate url string

			// parse url to basemap format

			mapLayerDao.save(MapLayerModel.getMapLayer());

		});

	}
}
