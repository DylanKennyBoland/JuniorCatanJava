package model.setup;

import java.util.*;
import model.board.Board;
import model.board.Islands;
import model.enums.IslandEnums;

/**
 * Class for Setting up the Board.
 * The SetupBoard class is a singleton
 * 
 * @author: Adam Durning & Dylan Boland
 * @Date: 27/12/2021
 * 
 */
public class SetupBoard {
	
	private static SetupBoard setupBoard;
	private Board board;
	private List<Islands> boardIslands = new ArrayList<Islands>();
	
    // Gets the instance of the SetupBoard object.
    public static SetupBoard getInstance(){
    	if(setupBoard == null){
    		setupBoard = new SetupBoard();
        }
        return setupBoard;
    }
	
    // The SetupBoard Constructor.
	public SetupBoard() {
		this.board = Board.getInstance();
	}
	
	// This method creates all of the islands on the board.
	public void createIslands() {
		List<String> lairs = new ArrayList<String>();
		
		//Creating the first Forest Island
		Collections.addAll(lairs, " 1 "," 2 "," 3 "," 24 "," 25 "," 26 ");
		
		Islands forestA = new Islands('A', 3, IslandEnums.FOREST,lairs);
		lairs.clear();
		
		//Creating the second Forest Island
		Collections.addAll(lairs, " 4 "," 5 "," 6 "," 7 "," 28 "," 27 ");
		
		Islands forestE = new Islands('E', 2, IslandEnums.FOREST,lairs);
		lairs.clear();
		
		//Creating the third Forest Island
		Collections.addAll(lairs, " 20 "," 21 "," 22 "," 23 "," 32 "," 31 ");
		
		Islands forestF = new Islands('F', 1, IslandEnums.FOREST,lairs);
		lairs.clear();
	
		//Creating the first Caves Island
		Collections.addAll(lairs," 2 "," 3 "," 4 "," 5 ");
		
		Islands caveB = new Islands('B', 1, IslandEnums.CAVES,lairs);
		lairs.clear();
		
		//Creating the second Caves Island
		Collections.addAll(lairs, " 22 "," 23 "," 24 "," 25 ");
		
		Islands caveC = new Islands('C', 4, IslandEnums.CAVES,lairs);
		lairs.clear();
		
		//Creating the first River Island
		Collections.addAll(lairs, " 3 "," 4 "," 27 "," 32 "," 23 "," 24 ");
		
		Islands riverD = new Islands('D', 5, IslandEnums.RIVERS,lairs);
		lairs.clear();
		
		//Creating the second River Island
		Collections.addAll(lairs, " 10 "," 11 "," 16 "," 17 "," 30 "," 29 ");
		
		Islands riverI = new Islands('I', 3, IslandEnums.RIVERS,lairs);
		lairs.clear();
		
		//Creating the first Pasture Island
		Collections.addAll(lairs, " 7 "," 8 "," 9 "," 10 "," 29 "," 28 ");
		
		Islands pastureG = new Islands('G', 1, IslandEnums.PASTURES,lairs);
		lairs.clear();
		
		//Creating the second Pasture Island
		Collections.addAll(lairs, " 17 "," 18 "," 19 "," 20 "," 31 "," 30 ");
		
		Islands pastureH = new Islands('H', 2, IslandEnums.PASTURES,lairs);
		lairs.clear();
		
		//Creating the third Pasture Island
		Collections.addAll(lairs, " 11 "," 12 "," 13 "," 14 "," 15 "," 16 ");
		
		Islands pastureL = new Islands('L', 5, IslandEnums.PASTURES,lairs);
		lairs.clear();
		
		//Creating the first Sugar Island
		Collections.addAll(lairs, " 9 "," 10 "," 11 "," 12 ");
		
		Islands sugarJ = new Islands('J', 2, IslandEnums.SUGAR,lairs);
		lairs.clear();
		
		//Creating the second Sugar Island
		Collections.addAll(lairs, " 15 "," 16 "," 17 "," 18 ");
		
		Islands sugarK = new Islands('K', 4, IslandEnums.SUGAR,lairs);
		lairs.clear();
		
		//Creating the Spooky Island
		Islands spooky = new Islands('S', 6, IslandEnums.SPOOKY,lairs);
		
		Collections.addAll(boardIslands, forestA, forestE, forestF, 
						   caveB, caveC, riverD, riverI, pastureG,
						   pastureH, pastureL, sugarJ, sugarK, spooky);
		// Setting the islands on the board.
		board.setIslands(boardIslands);
		// Setting the board's ghost island to the spooky island
		board.setGhostIsland(spooky);
		// Setting up the ship sites according to the island's lair sites.
		board.setupShipSites();
		board.initializeBoardStatus();
	}
}
