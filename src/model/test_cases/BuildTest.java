package model.test_cases;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
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

public class BuildTest extends TestParent{
	Scanner input;
	Player Adam;
	Board board;
	Stockpile stockpile;
	@Before
	public void setupTest() {
		this.board = Board.getInstance();
		this.stockpile = board.getStockpile();
		stockpile.update("Wood", -2);
		stockpile.update("Molasses", -1);
		stockpile.update("Goats", -2);
		stockpile.update("Cutlass", -1);
		this.view = View.getInstance();
		PlayerList playerList = PlayerList.getInstance();
		Adam = new Player("Adam", PlayerEnums.BLUE, "22");
		Adam.giveResource("Wood", 1);
		Adam.giveResource("Goats", 2);
		Adam.giveResource("Cutlass", 1);
		playerList.addPlayer(Adam);
		// Setting up the Board.
		SetupBoard boardSetup = SetupBoard.getInstance();
		boardSetup.createIslands();
	}
	
	@Test
	public void testBuilding() {
		printTestName("Building Test");
		
		// Displaying the players resources and assets and the stockpile before building.
		this.view.viewResources(Adam);
		this.view.display(Adam.assetsToString());
		this.view.viewStockpile(this.board.getStockpile());
		

		PlayerTurn playerTurn = new PlayerTurn(Adam, this.input);
		
		/** Creating the simulated input from the user.
		 * 2 - The player selects to build a ship
		 * 1 - The player chooses to build option 1
		 * 5 - The player finishes building.
		 * */
		printTestName("Building Ship");
		this.input = setSimulatedInput("2\n1\n5\n");
		playerTurn = new PlayerTurn(Adam, this.input);
		playerTurn.build();
		List<String> expectedShips = new ArrayList<String>();
		expectedShips.add(" 11 - 12 ");
		expectedShips.add(" 22 - 23 ");
		expectedShips.add(" 12 - 13 ");
		assertEquals(expectedShips ,Adam.getShipAssets());
		
		/** Creating the simulated input from the user.
		 * 1 - The player selects to build a lair
		 * 1 - The player selects to build option 1
		 * 5 - The player finishes building.
		 * */	
		printTestName("Building Lair");
		this.input = setSimulatedInput("1\n1\n5\n");
		playerTurn = new PlayerTurn(Adam, this.input);
		playerTurn.build();
		List<String> expectedLairs = new ArrayList<String>();
		expectedLairs.add(" 12 ");
		expectedLairs.add(" 22 ");
		expectedLairs.add(" 11 ");
		assertEquals(expectedLairs, Adam.getLairAssets());
		
		/** Creating the simulated input from the user.
		 * 1 - The player selects to build another lair
		 * 5 - The player finishes building.
		 * */	
		printTestName("Failed Building Lair");
		this.input = setSimulatedInput("1\n5\n");
		playerTurn = new PlayerTurn(Adam, this.input);
		playerTurn.build();
		
		/** Creating the simulated input from the user.
		 * 2 - The player selects to build another ship
		 * 5 - The player finishes building.
		 * */	
		printTestName("Failed Building Ship");
		this.input = setSimulatedInput("2\n5\n");
		playerTurn = new PlayerTurn(Adam, this.input);
		playerTurn.build();
		
		// Displaying the players resources and assets and the stockpile after building.
		this.view.viewResources(Adam);
		this.view.display(Adam.assetsToString());
		this.view.viewStockpile(this.board.getStockpile());
	}
}
