package model.test_cases;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.board.Board;
import model.board.Stockpile;
import model.enums.PlayerEnums;
import model.gameplay.Trade;
import model.players.Player;
import model.players.PlayerList;
import view.View;

public class StockpileTradeTest extends TestParent{
	Board board;
	Stockpile stockpile;
	Player Adam;
	Trade trade;
	
	@Before
	public void testSetup() {
		this.board = Board.getInstance();
		this.stockpile = board.getStockpile();
		stockpile.update("Wood", -4);
		stockpile.update("Molasses", -6);
		this.view = View.getInstance();
		PlayerList playerList = PlayerList.getInstance();
		Adam = new Player("Adam", PlayerEnums.BLUE, "22");
		Adam.giveResource("Wood", 3);
		Adam.giveResource("Molasses", 5);
		playerList.addPlayer(Adam);
		this.trade = new Trade(Adam);
	}
	
	@Test
	public void testStockpileTrade() {
		
		printTestName("Trading with Stockpile Test");
		// Setting up the list for the given resources
		ArrayList<String> givenResources = new ArrayList<String>();
		
		// Testing a single trade
		givenResources.add("Wood");
		String expectedString = "You've traded 2 Wood for 1 Gold.";
		assertEquals(expectedString, trade.tradeStockpile("Gold", 1, givenResources));
		assertEquals(17, this.stockpile.getNumOfResource("Gold"));
		assertEquals(16, this.stockpile.getNumOfResource("Wood"));	
		this.view.display(expectedString);
		
	    // Testing a double trade giving the same resource.	
		givenResources.clear();
		givenResources.add("Molasses");
		expectedString = "You've traded 4 Molasses for 2 Gold.";
		assertEquals(expectedString, trade.tradeStockpile("Gold", 2, givenResources));		
		assertEquals(15, this.stockpile.getNumOfResource("Gold"));
		assertEquals(16, this.stockpile.getNumOfResource("Molasses"));	
		this.view.display(expectedString);
		
		// Testing a double trade giving two resources. 
		givenResources.clear();
		givenResources.add("Molasses");
		givenResources.add("Wood");
		expectedString = "You've traded 2 Molasses for 1 Gold.";
		expectedString += "You've traded 2 Wood for 1 Gold.";
		assertEquals(expectedString, trade.tradeStockpile("Gold", 2, givenResources));		
		assertEquals(13, this.stockpile.getNumOfResource("Gold"));
		assertEquals(18, this.stockpile.getNumOfResource("Wood"));	
		assertEquals(18, this.stockpile.getNumOfResource("Molasses"));	
		this.view.display(expectedString);
		
		// Testing a trade when the player does not have enough of the given resource
		printSeperator(50);
		this.view.display("Unsuccesful Trade (Not enough Molasses)");
		givenResources.clear();
		givenResources.add("Molasses");
		expectedString = "You do not have enough Molasses for this trade!";
		assertEquals(expectedString, trade.tradeStockpile("Gold", 1, givenResources));
		assertEquals(13, this.stockpile.getNumOfResource("Gold"));
		assertEquals(18, this.stockpile.getNumOfResource("Wood"));	
		assertEquals(18, this.stockpile.getNumOfResource("Molasses"));	
		this.view.display(expectedString);
		
		// Testing a trade when the stockpile does not have any of the requested resource.
		printSeperator(50);
		this.view.display("Unsuccesful Trade (Not enough Wood in stockpile)");
		this.stockpile.update("Wood", -18);
		givenResources.clear();
		givenResources.add("Gold");
		expectedString = "There is no Wood in the stockpile.";
		assertEquals(expectedString, trade.tradeStockpile("Wood", 1, givenResources));
		assertEquals(13, this.stockpile.getNumOfResource("Gold"));
		assertEquals(18, this.stockpile.getNumOfResource("Molasses"));
		assertEquals(0, this.stockpile.getNumOfResource("Wood"));
		this.view.display(expectedString);
	}
}
