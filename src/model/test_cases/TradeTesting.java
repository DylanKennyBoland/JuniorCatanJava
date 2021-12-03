package model.test_cases;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.board.*;

class TradeTesting {

	@Test
	void test() {
		Marketplace marketplace = new Marketplace("The marketplace"); // Setting up a marketplace...
		
		// Check 1: is there 1 Wood available?
		assertEquals(true, marketplace.isAvailable("Wood", 1));
		
		// Check 2: can I trade a Goat in for a Wood?
		System.out.println(marketplace.trade("Goat", 1, "Wood", 1));
		// There should be 2 Goats now - are there?
		System.out.println(marketplace.toString());
		// We can also make use of the isAvailable() method:
		assertEquals(true, marketplace.isAvailable("Goat", 2));
		
		// Check 3: can I trade in 2 molasses for the 2 Goats?
		System.out.println(marketplace.trade("Molasses", 2, "Goat", 2));
		// Now there should be a total of 3 Molasses in the marketplace - are there?
		System.out.println(marketplace.toString());
		assertEquals(true, marketplace.isAvailable("Molasses", 2));
		
		// Check 4 (Won't be possible in actual game): what happens if I try to trade with a non-existent resource?
		System.out.println(marketplace.trade("Cutlass", 1, "Gun", 1));
	}

}
