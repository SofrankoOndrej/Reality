package util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TileUtilsTest {

	@Test
	void testMercator2Tile() {
//		kosice GPS
//		x=21.29579&y=48.70644
//		https://static.mapy.hiking.sk/topo/13/4580/2823.png
		int zoomLevel = 13;
		
		double mercatorCoordinates[] = TileUtils.globe2mercator(21.29579, 48.70644);
		int tileNumbers[] = TileUtils.mercator2tile(mercatorCoordinates[0], mercatorCoordinates[1], zoomLevel);
		assertEquals(4580, tileNumbers[0]);
		assertEquals(2823, tileNumbers[1]);
	}
}
