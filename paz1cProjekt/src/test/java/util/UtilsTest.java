package util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UtilsTest {
	
	@Test
	void TestparseUrl2UrlMapBaseFormat(){
		String urlString = "https://static.mapy.hiking.sk/topo/13/4530/2826.png";
		assertEquals("https://static.mapy.hiking.sk/topo/", Utils.parseUrl2UrlMapBaseFormat(urlString));
	}
}
