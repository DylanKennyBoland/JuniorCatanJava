package model.test_cases;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import model.board.Player;
import model.enums.PlayerEnums;
import model.gameplay.Build;

class BuildTesting {

	@Test
	void test() {
		Player dylan = new Player("Dylan", PlayerEnums.RED); // Creating a player object
		// Check 1: let's see if the player has their resource hash map set up:
		System.out.println(dylan.toString());

		Scanner inputScanner = new Scanner(System.in); // We can hopefully read in user input now...

		Build buildAction = new Build(dylan, inputScanner); // Our build object...

		buildAction.getBoard().getPlayerList().addPlayer(dylan); // Adding Dylan to the player list...
		// Check 2: can we call the buildShip() method?
		buildAction.buildShip();
		// Check 3: we try and make another build, and see if the ship sites available
		// to use are different.
		buildAction.buildShip();
		buildAction.buildShip();
	}

}
