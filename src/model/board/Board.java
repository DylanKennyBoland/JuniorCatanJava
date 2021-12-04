package model.board;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
		this.marketplace = new Marketplace("The marketplace");
		this.stockpile = new Stockpile("The stockpile");
	}
	
	public void setIslands(List<Islands> islands) {
		this.islands = islands;
	}

	public List<Islands> getIslands() {
		// TODO Auto-generated method stub
		return this.islands;
	}

	public Marketplace getMarketplace() {
		// TODO Auto-generated method stub
		return this.marketplace;
	}

	public Stockpile getStockpile() {
		// TODO Auto-generated method stub
		return this.stockpile;
	}
	
}
