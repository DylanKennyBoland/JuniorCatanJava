package model.setup;
import java.util.*;
import model.board.*;
import model.enums.*;
import model.board.Board;
import model.board.Islands;
import model.enums.IslandEnums;

public class SetupBoard {
	
	private Board board;
	private List<Islands> boardIslands = new ArrayList<Islands>();
	
	public SetupBoard() {
		this.board = Board.getInstance();
	}
	
	public void startingBoard() {
		createIslands();
	}
	
	private void createIslands() {
		List<Integer> lairs = new ArrayList<Integer>();
		
		//Creating the first Forest Island
		Collections.addAll(lairs, 1,2,3,24,25,26);
		
		Islands forestA = new Islands(3, IslandEnums.FOREST,lairs);
		lairs.clear();
		
		//Creating the second Forest Island
		Collections.addAll(lairs, 4,5,6,7,27,28);
		
		Islands forestB = new Islands(2, IslandEnums.FOREST,lairs);
		lairs.clear();
		
		//Creating the third Forest Island
		Collections.addAll(lairs, 20,21,22,23,31,32);
		
		Islands forestC = new Islands(1, IslandEnums.FOREST,lairs);
		lairs.clear();

		//Creating the first Caves Island
		Collections.addAll(lairs,2,3,4,5);
		
		Islands caveA = new Islands(1, IslandEnums.CAVES,lairs);
		lairs.clear();
		
		//Creating the second Forest Island
		Collections.addAll(lairs, 22,23,24,25);
		
		Islands caveB = new Islands(4, IslandEnums.CAVES,lairs);
		lairs.clear();
		
		//Creating the first River Island
		Collections.addAll(lairs, 3,4,23,24,27,32);
		
		Islands riverA = new Islands(5, IslandEnums.RIVERS,lairs);
		lairs.clear();
		
		//Creating the second River Island
		Collections.addAll(lairs, 10,11,16,17,29,30);
		
		Islands riverB = new Islands(3, IslandEnums.RIVERS,lairs);
		lairs.clear();
		
		//Creating the first Pasture Island
		Collections.addAll(lairs, 7,8,9,10,28,29);
		
		Islands pastureA = new Islands(1, IslandEnums.PASTURES,lairs);
		lairs.clear();
		
		//Creating the second Pasture Island
		Collections.addAll(lairs, 17,18,19,20,30,31);
		
		Islands pastureB = new Islands(2, IslandEnums.PASTURES,lairs);
		lairs.clear();
		
		//Creating the third Pasture Island
		Collections.addAll(lairs, 11,12,13,14,15,16);
		
		Islands pastureC = new Islands(5, IslandEnums.PASTURES,lairs);
		lairs.clear();
		
		//Creating the first Sugar Island
		Collections.addAll(lairs, 9,10,11,12);
		
		Islands sugarA = new Islands(2, IslandEnums.SUGAR,lairs);
		lairs.clear();
		
		//Creating the second Sugar Island
		Collections.addAll(lairs, 15,16,17,18);
		
		Islands sugarB = new Islands(4, IslandEnums.SUGAR,lairs);
		lairs.clear();
		
		//Creating the Spooky Island
		Islands spooky = new Islands(6, IslandEnums.SPOOKY,lairs);
		
		Collections.addAll(boardIslands, forestA, forestB, forestC, 
						   caveA, caveB, riverA, riverB, pastureA,
						   pastureB, pastureC, sugarA, sugarB, spooky);
		board.setIslands(boardIslands);
	}
	
}
