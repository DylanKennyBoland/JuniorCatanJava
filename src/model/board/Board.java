package model.board;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
	
	private static Board gameBoard;
	private List<Integer> lairLocations ;
	private List<Islands> islands ;
	private Marketplace marketplace ;
	private Stockpile stockpile ;
	private PlayerList playerList;
	
    public static Board getInstance(){
        if(gameBoard == null){
            gameBoard = new Board();
        }
        return gameBoard;
    }
    
	private Board() {
		this.lairLocations = IntStream.rangeClosed(1, 32).boxed().collect(Collectors.toList());
		this.islands = new ArrayList<Islands>();
		this.marketplace = new Marketplace("The marketplace");
		this.stockpile = new Stockpile("The stockpile");
		this.playerList  = PlayerList.getInstance();
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
	
	public boolean isLairAvailable(String location) {
		for(Player player: playerList.getList()) {
			if(player.getAssets().contains(location)) {
				System.out.println("Lair location is not available");
				return false;
			}
		}
		return true;
	}
	
	public void updateLairLocations(String location) {
		this.lairLocations.remove(this.lairLocations.indexOf(location));
	} 

	public String getIslandInfo() {
		String islandInfo = "";
		for(Islands island: islands) {
			islandInfo += island.toString() + "\n";
		}
		return islandInfo;
	}
	
	public Marketplace getMarketplace() {
		return this.marketplace;
	}
	
	public Stockpile  getStockpile() {
		return this.stockpile;
	}
	
	public List<Islands> getIslands(){
		return this.islands;
	}
	
	public List<Integer> getLairList(){
		return this.lairLocations;
	}
}
