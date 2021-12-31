package model.test_cases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import model.board.Board;
import model.board.Marketplace;
import model.board.Stockpile;
import model.enums.PlayerEnums;
import model.gameplay.Trade;
import model.players.Player;
import model.players.PlayerList;
import view.View;

public class MarketplaceTradeTest extends TestParent{

	Board board;
	Marketplace marketplace;
	Stockpile stockpile;
	Player Adam;
	
	@Before
	public void testSetup() {
		this.board = Board.getInstance();
		this.stockpile = board.getStockpile();
		stockpile.update("Wood", -1);
		stockpile.update("Molasses", -1);
		this.marketplace = board.getMarketplace();
		this.view = View.getInstance();
		PlayerList playerList = PlayerList.getInstance();
		Adam = new Player("Adam", PlayerEnums.BLUE, "22");
		playerList.addPlayer(Adam);
	}
	

	@Test
	public void testMarketplaceTrade() {
		
		printTestName("Trading with Marketplace Test");
		
		// Testing the trade with marketplace works
		Trade trade1 = new Trade(Adam);
		String expectedString = "You've traded 1 Wood for 1 Gold";
		assertEquals(expectedString, trade1.tradeMarketplace("Gold", "Wood"));
	    this.view.display(expectedString);	
	    
	    // Testing the player can only trade once per turn with the marketplace.
	    printSeperator(50);
	    expectedString = "You have already traded with the Marketplace this turn.";
		assertEquals(expectedString, trade1.tradeMarketplace("Molasses", "Gold"));
	    this.view.display(expectedString);	
		
	    // Testing if the marketplace does not have a resource.
	    printSeperator(50);
		Trade trade2 = new Trade(Adam);
		expectedString = "The marketplace does not have any Gold";
		assertEquals(expectedString, trade2.tradeMarketplace("Gold", "Wood"));
	    this.view.display(expectedString);	
	    printSeperator(50);
		
		this.marketplace.trade("Wood", 1, "Molasses", 1);
		this.marketplace.trade("Wood", 1, "Goats", 1);
		// Testing if the marketplace gets reset when it contains all of the same resource.
		expectedString = ("You've traded 1 Wood for 1 Cutlass."
		+ "\nINFO: resetting the marketplace as it's flooded with the same resource...");
		assertEquals(expectedString, this.marketplace.trade("Wood", 1, "Cutlass", 1));
		assertFalse(this.marketplace.areTilesAllSame());
	    this.view.display(expectedString);	
	}
}
