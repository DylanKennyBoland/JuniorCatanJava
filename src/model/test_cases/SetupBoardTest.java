package model.test_cases;

import org.junit.Before;
import org.junit.Test;

import model.board.Board;
import model.enums.PlayerEnums;
import model.players.Player;
import model.players.PlayerList;
import model.setup.SetupBoard;
import view.View;

public class SetupBoardTest extends TestParent{
	Board board;
	PlayerList playerList;
	
	
	@Before
	public void setupTest() {
		// Setting up the List of Players which is needed for setting up the players initial assets.
		this.board = Board.getInstance();
		this.view = View.getInstance();
		this.playerList = board.getPlayerList();
		playerList.addPlayer(new Player("Adam", PlayerEnums.BLUE, "22"));
		playerList.addPlayer(new Player("Dylan", PlayerEnums.RED, "23"));
		playerList.addPlayer(new Player("Linda", PlayerEnums.ORANGE, "24"));
		playerList.addPlayer(new Player("Amy", PlayerEnums.WHITE, "25"));
		
	}
	
	/**
	 * This Test does not have any assert statements.
	 * Instead, the board is printed to the Terminal to verify that
	 * the board is setup correctly.
	 * */
	@Test
	public void testBoardSetup() {
		// Setting up the Board.
		SetupBoard boardSetup = SetupBoard.getInstance();
		boardSetup.createIslands();
		// Displaying the board to check it is correct.
		this.view.viewBoard(this.playerList, this.board.getGhostIsland(), this.board);
	}

}
