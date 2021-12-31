package model.test_cases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import controller.PlayerTurn;
import model.board.Board;
import model.board.Stockpile;
import model.enums.PlayerEnums;
import model.players.Player;
import model.players.PlayerList;
import model.setup.SetupBoard;
import view.View;

public class CheckWinTest extends TestParent{
	Scanner input;
	Player Adam;
	Board board;
	Stockpile stockpile;
	
	@Before
	public void setupTest() {
		this.board = Board.getInstance();
		this.view = View.getInstance();
		PlayerList playerList = PlayerList.getInstance();
		Adam = new Player("Adam", PlayerEnums.BLUE, "22");
		Adam.giveResource("Goats", 1);
		Adam.giveResource("Cutlass", 1);
		// Giving the player some assets so they only have to build 1 more to win. 
		Adam.addLairAsset(" 1 ");
		Adam.addLairAsset(" 2 ");
		Adam.addLairAsset(" 3 ");
		Adam.addLairAsset(" 4 ");
		playerList.addPlayer(Adam);
		// Setting up the Board.
		SetupBoard boardSetup = SetupBoard.getInstance();
		boardSetup.createIslands();
	}
	
	@Test
	public void testIfPlayerWon() {
		printTestName("Testing the Win condition (Player has 7 lairs.)");
		// Player has not won yet
		assertFalse(Adam.getWinner());
		this.input = setSimulatedInput("1\n1\n");
		PlayerTurn playerTurn = new PlayerTurn(Adam, this.input);
		playerTurn.build();	
		// Player has won the game.
		assertTrue(Adam.getWinner());
	}
}
