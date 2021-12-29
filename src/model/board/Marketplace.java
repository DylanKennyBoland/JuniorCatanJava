package model.board;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @Author: Dylan Kenny Boland & Adam Durning
 * @Date: (of last update) 29/12/2021
 */

public class Marketplace implements Tradeable {
	private Integer marketplaceCapacity = 5;
	private String name;
	private ArrayList<String> marketplace = new ArrayList<String>(marketplaceCapacity);

	/**
	 * Constructor for the Marketplace object.
	 * @param name - the name of the marketplace.
	 * @param marketplaceCapacity - the maximum number of tiles the marketplace can hold.
	 * @param marketplace - the array which is representing the marketplace.
	 * 
	 */
	public Marketplace(String name) {
		this.name = name;
		this.set(); // Setting up the marketplace
	}

	@Override
	public void set() {
		this.marketplace.clear();
		this.update("Gold", 1);
		this.update("Molasses", 1);
		this.update("Wood", 1);
		this.update("Goats", 1);
		this.update("Cutlass", 1);
		Collections.shuffle(this.getMarketPlace()); 
		/* Shuffling them so the order of
		 * the tiles is not the same each time...
		 */
	}
	
	@Override
	public void update(String resource, int num) {
		if (this.marketplace.size() == this.marketplaceCapacity) {
			return;
		}
		this.marketplace.add(resource);		
	}

	@Override
	public boolean isAvailable(String resourceName, int number) {
		if (this.locations(resourceName).size() >= number) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String trade(String tilein, int numIn, String tileout, int numOut) {
		if (!this.isAvailable(tileout, numOut)) {
			return String.format("INFO: there are not enough %1$s tiles in the Marketplace at the moment.", tileout);
		}
		ArrayList<Integer> indexes = this.locations(tileout);
		for (Integer index : indexes) {
			this.marketplace.set(index, tilein);
		}

		if ((numIn > 1) && (this.areTilesAllSame())) {
			this.set(); // Resetting the marketplace
			return String.format(
					"You've traded %1$d %2$s tiles for %3$d %4$s tiles."
							+ "\nINFO: resetting the marketplace as it's flooded with the same resource...",
					numIn, tilein, numOut, tileout);
		} else if ((numIn == 1) && (this.areTilesAllSame())) {
			this.set();
			return String.format(
					"You've traded %1$d %2$s tile for %3$d %4$s tile."
							+ "\nINFO: resetting the marketplace as it's flooded with the same resource...",
					numIn, tilein, numOut, tileout);
		} else if (numIn == 1) {
			return String.format("You've traded %1$d %2$s tile for %3$d %4$s tile.", numIn, tilein, numOut, tileout);
		} else {
			return String.format("You've traded %1$d %2$s tiles for %3$d %4$s tiles.", numIn, tilein, numOut, tileout);
		}
	}

	@Override
	public String toString() {
		String result = "";
		for (String resource : this.marketplace) {
			result = result + "\t" + resource + "\n";
		}
		return result;
	}

	public ArrayList<Integer> locations(String resourceName) {
		ArrayList<Integer> locations = new ArrayList<Integer>();
		for (int i = 0; i < this.marketplaceCapacity; i++) {
			if (this.marketplace.get(i).contains(resourceName)) {
				locations.add(i);
			}
		}
		return locations;
	}

	public ArrayList<String> getMarketPlace() {
		return this.marketplace;
	}

	public boolean areTilesAllSame() {
		boolean answer = true; // A local variable the value of which we'll return...
		for (String tile : this.getMarketPlace()) {
			if (!tile.equals(this.getMarketPlace().get(0))) {
				answer = false;
			}
		}
		return answer;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
