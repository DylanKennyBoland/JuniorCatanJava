package model.test_cases;

import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import model.board.Board;
import model.board.Islands;
import model.enums.PlayerEnums;
import model.players.Player;
import model.players.PlayerList;
import model.setup.SetupBoard;
import view.View;

public class ProduceResourcesTest extends TestParent{
	Scanner input;
	View view;
	Board board;
	PlayerList playerList;
	@Before
	public void setupTest() {
		// Setting up the List of Players which is needed for setting up the players initial assets.
		this.board = Board.getInstance();
		this.view = View.getInstance();
		this.playerList = board.getPlayerList();
		Player Adam = new Player("Adam", PlayerEnums.BLUE, "22");
		playerList.addPlayer(Adam);
		playerList.addPlayer(new Player("Dylan", PlayerEnums.RED, "23"));
		playerList.addPlayer(new Player("Linda", PlayerEnums.ORANGE, "24"));
		playerList.addPlayer(new Player("Amy", PlayerEnums.WHITE, "25"));
		
		// Setting up the Board.
		SetupBoard boardSetup = SetupBoard.getInstance();
		boardSetup.createIslands();
	}
	
	@Test
	public void testProduceResources() {
		printSeperator(50);
		this.view.display("PRODUCE RESOURCES TEST");
		printSeperator(50);
		/** Simulating the dice rolled a 4 which should produce resources for all players.
		 * Cutlass for Adam and Linda
		 * Molasses for Dylan and Amy
		 * */
		view.display("Dice rolled a 4");
		view.display(board.produceResources(4));
		
		printSeperator(50);
		// Simulating the dice rolled a 3 which should produce wood for Linda and Amy.
		view.display("Dice rolled a 3");
		view.display(board.produceResources(3));
		
		printSeperator(50);
		//Moving the ghost captain to island A. Now no one should get resources because island A is blocked.
		List<Islands> islandList = this.board.getIslands();
		this.board.setGhostIsland(islandList.get(0));
		view.display("Dice rolled a 3");
		view.display(board.produceResources(3));
	}

}
