package test;
import board.Stockpile;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class Trade_With_Stockpile {
	@Test
	public void tradeWithStockpile() {
		Map<String, Integer> treasureCave = new HashMap<String, Integer>();
		treasureCave.put("Wood", 6);
		treasureCave.put("Gold Ingots", 4);
		treasureCave.put("Goats", 3);
		treasureCave.put("Cutlass", 5);
		treasureCave.put("Molasses", 3);
		// Let's set up a stock pile object
		Stockpile testStockPile = new Stockpile("Treasure Cave");
		// Do some brief tests on its isAvailable() method....
		assertEquals(true, testStockPile.isAvailable("Wood", 4));
		assertEquals(false, testStockPile.isAvailable("Goats", 20)); // There are no 20 Goats available - does the method return 'false'?
		// Now let's do a trade. First we'll print out the stock pile contents
		System.out.println(testStockPile.getStockPile());
		int currNumMolasses = testStockPile.getStockPile().get("Molasses");
		int currNumWood = testStockPile.getStockPile().get("Wood");
		testStockPile.trade("Wood", "Molasses");
		System.out.println(testStockPile.getStockPile());
		assertEquals((int)testStockPile.getStockPile().get("Molasses"), (int)currNumMolasses - 1);
		assertEquals((int)testStockPile.getStockPile().get("Wood"), (int)currNumWood + 2);
	}
}
