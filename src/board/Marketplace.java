package board;
import java.util.*;

public class Marketplace implements Tradeable {
	private Integer marketplaceSize = 5;
	private String name;
	private ArrayList<String> marketplace = new ArrayList<String>(marketplaceSize);
	
	public Marketplace(String name) {
		this.name = name;
		this.set(); // Setting up the marketplace
	}
	
	public void set() {
		this.marketplace.clear();
		this.marketplace.add("Wood");
		this.marketplace.add("Cutlass");
		this.marketplace.add("Goats");
		this.marketplace.add("Gold");
		this.marketplace.add("Molasses");
		Collections.shuffle(this.getMarketPlace()); // Shuffling them so the order of the tiles is not the same each time...
	}
	
	public ArrayList<String> getMarketPlace() {
		return this.marketplace;
	}
	
	public boolean areTilesAllSame() {
		boolean allEqual = true; // A local variable the value of which we'll return...
		for(String tile : this.getMarketPlace()) {
			if(! tile.equals(this.getMarketPlace().get(0))) {
				allEqual = false;
			}
		}
		return allEqual;
	}
		
	public String trade(String tilein, String tileout) {
		int index = this.getMarketPlace().indexOf(tileout);
		if(index == -1) {
			return String.format("There are no '%1$s' tiles in the marketplace to trade with.", tileout);
		}
		else {
			this.marketplace.set(index, tilein);
			return String.format("You've traded a %1$s for a %2$s", tilein, tileout);
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
