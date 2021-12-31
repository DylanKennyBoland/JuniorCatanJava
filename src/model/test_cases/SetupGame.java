package model.test_cases;
import model.setup.*;
import model.board.*;
import java.util.*;
import org.junit.Before;
import org.junit.Test;

public class SetupGame {
	
	private SetupBoard boardSetup;
	//private Board gameBoard;
	@Before
	public void setUp() throws Exception {
		boardSetup = new SetupBoard();
		boardSetup.createIslands();;
	}

	@Test
	public void testSetup() {
		Board gameBoard = Board.getInstance();
		List<Islands> islands = gameBoard.getIslands();
		for(Islands island: islands) {
			System.out.println(island.toString() + "\n");
		}
		//System.out.println(gameBoard.getIslandInfo());
		Marketplace marketplace = gameBoard.getMarketplace();
		Stockpile stockpile = gameBoard.getStockpile();
		
		//if(marketplace.isAvailable("Wood", 1) && marketplace.isAvailable("Cutlass", 1) 
		//   && marketplace.isAvailable("Goats", 1) && marketplace.isAvailable("Gold", 1)
		//   && marketplace.isAvailable("Molasses", 1)) {
		//	System.out.println("Marketplace has been set up correctly");
		//}
		
		System.out.println(marketplace.toString());
		System.out.println(stockpile.toString());
	}
	
}
