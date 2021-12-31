package model.test_cases;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.board.Board;
import model.board.Islands;
import model.setup.SetupBoard;
import view.View;

public class MoveGhostCaptainTest extends TestParent{
	Board board;
	View view;
	Islands spooky;
	
	@Before
	public void testSetup() {
		// Setting up the List of Players which is needed for setting up the players initial assets.
		this.board = Board.getInstance();
		this.view = View.getInstance();
		SetupBoard boardSetup = SetupBoard.getInstance();
		boardSetup.createIslands();
		this.spooky = board.getGhostIsland();
	}
	@Test
	public void testMovingGC() {
		printTestName("Moving Ghost Captain Test");
		
		this.view.display("Ghost Captain starts on " + spooky.getIslandType() + " island.");
		
		this.view.display("\nMoving Ghost Captin\n");
		board.moveGhostCaptain('A');
		
		spooky = board.getGhostIsland();
		assertEquals('A', spooky.getName());
		
		this.view.display("Ghost Captain is now on the " + spooky.getIslandType() + " " + spooky.getName() + " island.");
	}
}
