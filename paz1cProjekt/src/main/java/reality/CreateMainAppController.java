package reality;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import persistent.DaoFactory;
import persistent.MapLayerDao;

public class CreateMainAppController {
	
	private MapLayerDao mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
	private MapLayerFxModel MapLayerModel;

	@FXML
	private Button changeMapLayerButton;

	@FXML
	private Button previewMapTileButton;

	@FXML
	private ImageView TilePreviewImageView;

	@FXML
	private TextField inputUrlTextField;

	@FXML
	private Button saveMapLayerButton;

	@FXML
	private TextField mapNameTextField;

	public CreateMainAppController() {
		mapLayerDao = DaoFactory.INSTANCE.getMapLayerDao();
		MapLayerModel = new MapLayerFxModel();
	}
	
	@FXML
	void initialize() {
		mapNameTextField.textProperty().bindBidirectional(MapLayerModel.nameProperty());
		inputUrlTextField.textProperty().bindBidirectional(MapLayerModel.urlProperty());
		
		previewMapTileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				// BufferedImage img = ImageIO.read(new URL(inputUrlTextField.getText()));
				Image tileImage = new Image(inputUrlTextField.getText());
				TilePreviewImageView.setImage(tileImage);
			}
		});
		
		saveMapLayerButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent actionEvent) {
				mapLayerDao.save(MapLayerModel.getMapLayer());
				
			}
		});

	}
}
