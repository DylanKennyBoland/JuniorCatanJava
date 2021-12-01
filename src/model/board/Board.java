package model.board;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import model.board.*;


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
		this.marketplace = new Marketplace('Marketplace');
		this.stockpile = new Stockpile('Stockpile');
	}
	
	public void setIslands(List<Islands> islands) {
		this.islands = islands;
	}
	
}
