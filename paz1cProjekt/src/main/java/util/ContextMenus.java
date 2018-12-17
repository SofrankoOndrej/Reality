package util;

import java.io.IOException;

import entities.Address;
import entities.MapLayer;
import entities.User;
import fxModels.UserFxModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import reality.CreateAddressController;

public class ContextMenus {
	private UserFxModel userFxModel = new UserFxModel();
	private User user;
	private int xClick;
	private int yClick;
	
	public ContextMenus(User user) {
		this.user = user;
		this.userFxModel.setUser(user);
//	this.addressFxModel = addressFxModel;
	}

	public ContextMenu getCanvasContextMenu() {
		// https://o7planning.org/en/11115/javafx-contextmenu-tutorial#a3821208
		// Create ContextMenu
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("save this address");
		item1.setOnAction(actionEvent -> {
			// zrataj polohu kliknutia
			// get old BBOX from user
			double[] oldBbox = MapUtils.bBoxString2DoubleArray(userFxModel.getLastBoundingBox());

			// pixel position of old bounds upper left corner
			int[] oldBoxUL = TileUtils.globe2pixel(oldBbox[0], oldBbox[1], userFxModel.getLastZoom());

			// get new bbox bounds = old center +- half of canvas size, + x/y shift
			double[] contextMenuPosition = TileUtils.pixel2globe(oldBoxUL[0] + this.getxClick(), oldBoxUL[1] + this.getyClick(),
					userFxModel.getLastZoom());
			
			// napln polohu addressFxModelu
			Address address = new Address();
			address.setLongitude(String.valueOf(contextMenuPosition[0]));
			address.setLatitude(String.valueOf(contextMenuPosition[1]));

			CreateAddressController addressController = new CreateAddressController(user, address);
			showModalWindow(addressController, "../reality/CreateAddress.fxml");
		});

		MenuItem item2 = new MenuItem("Menu Item 2");
		item2.setOnAction(actionEvent -> {
			// TODO do other stuff
		});

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(item1, item2);
		return contextMenu;
	}

	public int getxClick() {
		return xClick;
	}

	public void setxClick(int xClick) {
		this.xClick = xClick;
	}

	public int getyClick() {
		return yClick;
	}

	public void setyClick(int yClick) {
		this.yClick = yClick;
	}

	/*
	 * NOT USED
	 */
	public ContextMenu getMapLayerSelectionContextMenu(ListView<MapLayer> layersListView, TabPane mainTabPane,
			Tab mapLayerTab) {
		ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("edit map layer");
		item1.setOnAction(actionEvent -> {
			// switch to mapLayer edit tab
		});
		MenuItem item2 = new MenuItem("delete map layer");
		item2.setOnAction(actionEvent -> {
			// delete map layer
		});
		MenuItem item3 = new MenuItem("overlay map");
		item3.setOnAction(actionEvent -> {
			// do other stuff
		});

		// Add MenuItem to ContextMenu
		contextMenu.getItems().addAll(item1, item2, item3);
		return contextMenu;
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
