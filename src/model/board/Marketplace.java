package model.board;
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
		this.marketplace.add("Goat");
		this.marketplace.add("Gold");
		this.marketplace.add("Molasses");
		Collections.shuffle(this.getMarketPlace()); // Shuffling them so the order of the tiles is not the same each time...
	}
	
	public boolean isAvailable(String resourceName, Integer number) {
		if(this.locations(resourceName).size() >= number) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String trade(String tilein, Integer numIn, String tileout, Integer numOut) {
		if(!this.isAvailable(tileout, numOut)) {
			return String.format("INFO: there are not enough %1$s tiles in the Marketplace at the moment.", tileout);
		}
		ArrayList<Integer> indexes = this.locations(tileout);
		for(Integer index : indexes) {
			this.marketplace.set(index, tilein);
		}
		
		if((numIn > 1) && (this.areTilesAllSame())) {
			this.set(); // Resetting the marketplace
			return String.format("You've traded %1$d %2$s tiles for %3$d %4$s tiles."
					+ "\nINFO: resetting the marketplace as it's flooded with the same resource...", numIn, tilein, numOut, tileout);
		}
		else if((numIn == 1) && (this.areTilesAllSame())) {
			this.set();
			return String.format("You've traded %1$d %2$s tile for %3$d %4$s tile."
					+ "\nINFO: resetting the marketplace as it's flooded with the same resource...", numIn, tilein, numOut, tileout);
		}
		else if(numIn == 1) {
			return String.format("You've traded %1$d %2$s tile for %3$d %4$s tile.", numIn, tilein, numOut, tileout);
		}
		else {
			return String.format("You've traded %1$d %2$s tiles for %3$d %4$s tiles.", numIn, tilein, numOut, tileout);
		}
	}

	public String toString() {
		String result = "";
		for(String resource : this.marketplace) {
			result = result + resource + "\n";
		}
		return result;
	}
	
	public ArrayList<Integer> locations(String resourceName) {
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for(int i = 0; i < this.marketplaceSize; i++) {
			if(this.marketplace.get(i) == resourceName) {
				locations.add(i);
			}
		}
		return locations;
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
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
