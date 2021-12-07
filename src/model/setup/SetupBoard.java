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
		List<String> lairs = new ArrayList<String>();
		
		//Creating the first Forest Island
		Collections.addAll(lairs, "1","2","3","24","25","26");
		
		Islands forestA = new Islands("A", 3, IslandEnums.FOREST,lairs);
		lairs.clear();
		
		//Creating the second Forest Island
		Collections.addAll(lairs, "4","5","6","7","28","27");
		
		Islands forestB = new Islands("E", 2, IslandEnums.FOREST,lairs);
		lairs.clear();
		
		//Creating the third Forest Island
		Collections.addAll(lairs, "20","21","22","23","32","31");
		
		Islands forestC = new Islands("F", 1, IslandEnums.FOREST,lairs);
		lairs.clear();

		//Creating the first Caves Island
		Collections.addAll(lairs,"2","3","4","5");
		
		Islands caveA = new Islands("B", 1, IslandEnums.CAVES,lairs);
		lairs.clear();
		
		//Creating the second Caves Island
		Collections.addAll(lairs, "22","23","24","25");
		
		Islands caveB = new Islands("C", 4, IslandEnums.CAVES,lairs);
		lairs.clear();
		
		//Creating the first River Island
		Collections.addAll(lairs, "3","4","27","32","23","24");
		
		Islands riverA = new Islands("D", 5, IslandEnums.RIVERS,lairs);
		lairs.clear();
		
		//Creating the second River Island
		Collections.addAll(lairs, "10","11","16","17","30","29");
		
		Islands riverB = new Islands("J", 3, IslandEnums.RIVERS,lairs);
		lairs.clear();
		
		//Creating the first Pasture Island
		Collections.addAll(lairs, "7","8","9","10","29","28");
		
		Islands pastureA = new Islands("H", 1, IslandEnums.PASTURES,lairs);
		lairs.clear();
		
		//Creating the second Pasture Island
		Collections.addAll(lairs, "17","18","19","20","31","30");
		
		Islands pastureB = new Islands("I", 2, IslandEnums.PASTURES,lairs);
		lairs.clear();
		
		//Creating the third Pasture Island
		Collections.addAll(lairs, "11","12","13","14","15","16");
		
		Islands pastureC = new Islands("M", 5, IslandEnums.PASTURES,lairs);
		lairs.clear();
		
		//Creating the first Sugar Island
		Collections.addAll(lairs, "9","10","11","12");
		
		Islands sugarA = new Islands("K", 2, IslandEnums.SUGAR,lairs);
		lairs.clear();
		
		//Creating the second Sugar Island
		Collections.addAll(lairs, "15","16","17","18");
		
		Islands sugarB = new Islands("L", 4, IslandEnums.SUGAR,lairs);
		lairs.clear();
		
		//Creating the Spooky Island
		Islands spooky = new Islands("G", 6, IslandEnums.SPOOKY,lairs);
		
		Collections.addAll(boardIslands, forestA, forestB, forestC, 
						   caveA, caveB, riverA, riverB, pastureA,
						   pastureB, pastureC, sugarA, sugarB, spooky);
		board.setIslands(boardIslands);
		board.setGhostIsland(spooky);
	}
	
	public Board getBoard() {
		return this.board;
	}
	
}
