package test;
import board.Stockpile;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class Should_Update_Stockpile {
	@Test
	public void updateStockPile() {
		// Setting up a stock pile object...
		Stockpile testStockPile = new Stockpile("Catan Stockpile");
		
		// Altering the stock pile object...
		testStockPile.updateStockPile("Cutlass", 6);
		testStockPile.updateStockPile("Wood", 4);
		
		// Checking if the update method has worked as it should...
		assertEquals((Object)6, (Object)testStockPile.getStockPile().get("Cutlass"));
		assertEquals((Object)4, (Object)testStockPile.getStockPile().get("Wood"));
		
		Map<String, Integer> treasureCave = new HashMap<String, Integer>();
		treasureCave.put("Wood", 6);
		treasureCave.put("Gold Ingots", 4);
		treasureCave.put("Goats", 3);
		// Setting up another stock pile object, but this time via the second constructor...
		Stockpile testStockPile2 = new Stockpile("Treasure Cave", treasureCave);
		testStockPile2.updateStockPile("Cutlass", 11);
		testStockPile2.updateStockPile("Wood", 9);
		// Again, we check if the update method has worked as it should...
		assertEquals((Object)11, (Object)testStockPile2.getStockPile().get("Cutlass"));
		assertEquals((Object)9, (Object)testStockPile2.getStockPile().get("Wood"));	
	}
}
