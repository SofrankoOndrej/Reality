package util;

import static org.junit.Assert.assertNotNull;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.junit.jupiter.api.Test;
import javafx.scene.image.Image;

public class MapUtilsTest {

	@Test
	void TestImageDownload() {
		Image tile = new Image("https://zbgis.skgeodesy.sk/zbgis/rest/services/ZBGIS/MapServer/tile/13/2822/4580.png",false);

		assertNotNull(tile.getHeight());
	}
	
	@Test
	void TestImageDownload2() {
		 
		try {
			// Create a new trust manager that trust all certificates
			TrustManager[] trustAllCerts = new TrustManager[]{
			    new X509TrustManager() {
			        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			            return null;
			        }
			        public void checkClientTrusted(
			            java.security.cert.X509Certificate[] certs, String authType) {
			        }
			        public void checkServerTrusted(
			            java.security.cert.X509Certificate[] certs, String authType) {
			        }
			    }
			};

			// Activate the new trust manager
			try {
			    SSLContext sc = SSLContext.getInstance("SSL");
			    sc.init(null, trustAllCerts, new java.security.SecureRandom());
			    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			} catch (Exception e) {
			}

			// And as before now you can use URL and URLConnection
			URL url = new URL("https://zbgis.skgeodesy.sk/zbgis/rest/services/ZBGIS/MapServer/tile/13/2822/4580");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			// .. then download the file
			Image image = new Image(is);
			
			assertNotNull(image.getHeight());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
