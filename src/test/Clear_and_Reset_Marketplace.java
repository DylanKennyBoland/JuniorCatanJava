package test;
import board.Marketplace;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class Clear_and_Reset_Marketplace {
	@Test
	public void clearAndResetMarketPlace() {
		ArrayList<String> defaultMarket = new ArrayList<String>(5);
		defaultMarket.add("Gold");
		defaultMarket.add("Gold");
		defaultMarket.add("Gold");
		defaultMarket.add("Gold");
		defaultMarket.add("Gold");
		
		// The setup stage...
		Marketplace marketplace = new Marketplace("The marketplace", defaultMarket);
		marketplace.resetMarketPlace();
		assertEquals(false, marketplace.areTilesAllSame()); // Not all the tiles are the same after the reset
		System.out.println(marketplace.getMarketPlace());
		
		// Let's now check that the trade method works:
		System.out.println(marketplace.trade("Wood", "Gold"));
		System.out.println(marketplace.getMarketPlace());
		
	}
}
