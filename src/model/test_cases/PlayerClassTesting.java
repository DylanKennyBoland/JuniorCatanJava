package model.test_cases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import model.board.Player;
import model.enums.PlayerEnums;

class PlayerClassTesting {

	@Test
	void test() {
		Player dylan = new Player("Dylan", PlayerEnums.ORANGE); // Creating a player object

		// Check 1: let's see if the player has their resource hash map set up:
		System.out.println(dylan.toString());
		// Check 2: let's see if the player's initial two lair and ship locations
		// have been set up:
		String[] orangeLairLocations = { "9", "25" };
		String[] orangeShipLocations = { " 9 - 10 ", " 25 - 24 " };

		assertEquals(orangeLairLocations[0], dylan.getAssets().get(0));
		assertEquals(orangeLairLocations[1], dylan.getAssets().get(1));
		assertEquals(orangeShipLocations[0], dylan.getAssets().get(2));
		assertEquals(orangeShipLocations[1], dylan.getAssets().get(3));

		// Check 3: can we add a new asset, say a new lair at site 4?
		dylan.addAsset("4");
		assertEquals("4", dylan.getAssets().get(4));
	}
}
