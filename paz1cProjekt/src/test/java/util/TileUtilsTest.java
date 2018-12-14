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
	
	@Test 
	void globe2Pixel() {
		int[] pixel = TileUtils.globe2pixel(10, 20, 13);
		double[] globe = TileUtils.pixel2globe(pixel[0], pixel[1], 13);
		
		assertEquals(20, globe[1]);
	}
	
	@Test 
	void pixel2globe() {
		
		double[] globe = TileUtils.pixel2globe(10000, 1000, 13);
		int[] pixel = TileUtils.globe2pixel(globe[0], globe[1], 13);
		assertEquals(10000, pixel[0]);
	}
}
