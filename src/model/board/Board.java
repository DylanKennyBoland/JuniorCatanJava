package model.board;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import board.*;


public class Board {
	
	private static Board gameBoard;
	private List<Integer> lairLocations ;
	private List<Integer> shipLocations ;
	private List<Islands> islands ;
	private Marketplace marketplace ;
	private Stockpile stockpile ;
	
    public static Board getInstance(){
        if(gameBoard == null){
            gameBoard = new Board();
        }
        return gameBoard;
    }
    
	private Board() {
		this.lairLocations = IntStream.rangeClosed(1, 37).boxed().collect(Collectors.toList());
		this.shipLocations = IntStream.rangeClosed(1, 37).boxed().collect(Collectors.toList());
		this.islands = new ArrayList<Islands>();
		this.marketplace = new Marketplace();
		this.stockpile = new Stockpile();
	}
	
//	public void addIsland(Islands island) {
//		this.islands.add(island);
//	}
	
//	public void buildLair(Integer i) {
//		this.lairLocations.remove(i);
//	}
	
	public void setIslands(List<Islands> islands) {
		this.islands = islands;
	}
	
	
	
}
