package reality;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;

public class MapApp extends Application {

	private MapView mapView;

	private void setupMap() {
		if (mapView != null) {
			Basemap.Type basemapType = Basemap.Type.STREETS_VECTOR;
			double latitude = 34.05293;
			double longitude = -118.24368;
			int levelOfDetail = 11;
			ArcGISMap map = new ArcGISMap(basemapType, latitude, longitude, levelOfDetail);
			mapView.setMap(map);
		}
	}

	@Override
	public void start(Stage stage) {
		StackPane stackPane = new StackPane();
		Scene scene = new Scene(stackPane);

		stage.setTitle("DevLabs");
		stage.setWidth(600);
		stage.setHeight(350);
		stage.setScene(scene);
		stage.show();

		/* ** ADD ** */
		mapView = new MapView();
		setupMap();
		
		/* ** ADD ** */
		stackPane.getChildren().add(mapView);
		
	}

	@Override
	public void stop() {
	  if (mapView != null) {
	    mapView.dispose();
	  }
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}
