package model.test_cases;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import model.board.Board;
import model.enums.PlayerEnums;
import model.gameplay.Build;
import model.players.Player;

class BuildTesting {

	@Test
	void test() {
		Player dylan = new Player("Dylan", PlayerEnums.RED, "22"); // Creating a player object
		// Check 1: let's see if the player has their resource hash map set up:
		System.out.println(dylan.toString());

		Scanner inputScanner = new Scanner(System.in); // We can hopefully read in user input now...

		Build buildAction = new Build(dylan); // Our build object...

		Board board = Board.getInstance();
		board.getPlayerList().addPlayer(dylan); // Adding Dylan to the player list...
		// Check 2: can we call the buildShip() method?
//		buildAction.buildShip();
//		// Check 3: we try and make another build, and see if the ship sites available
//		// to use are different.
//		buildAction.buildShip();
//		buildAction.buildShip();
//		// Check 4: let's see if we can build lairs!
//		buildAction.buildLair();
//		buildAction.buildLair();
//		buildAction.buildLair();
	}

}
