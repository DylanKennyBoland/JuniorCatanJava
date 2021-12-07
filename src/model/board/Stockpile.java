package model.board;

import java.util.HashMap;
import java.util.Map;

public class Stockpile implements Tradeable {
	private Integer resourceQuantity = 18;
	private String name;
	private Map<String, Integer> stockpile = new HashMap<String, Integer>();

	public Stockpile(String name) { // The constructor...
		this.name = name;
		this.set();
	}

	@Override
	public void set() {
		this.stockpile.put("Wood", resourceQuantity);
		this.stockpile.put("Cutlass", resourceQuantity);
		this.stockpile.put("Goats", resourceQuantity);
		this.stockpile.put("Gold", resourceQuantity);
		this.stockpile.put("Molasses", resourceQuantity);
		this.stockpile.put("Coco tiles", resourceQuantity);
	}

	@Override
	public boolean isAvailable(String resourceName, Integer number) {
		if ((this.getStockPile().containsKey(resourceName)) && (this.getStockPile().get(resourceName) >= number)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String trade(String tilein, Integer numIn, String tileout, Integer numOut) {
		if (!this.isAvailable(tileout, numOut)) {
			return String.format("There are no '%1$s' tiles in the stockpile to trade with.", tileout);
		}
		int currNumTileIn = this.stockpile.get(tilein);
		int currNumTileOut = this.stockpile.get(tileout);
		// Now we update the values associated with the keys...
		this.stockpile.replace(tilein, currNumTileIn + numIn);
		this.stockpile.replace(tileout, currNumTileOut - numOut);
		return String.format("You've traded a %1$d %2$s for %3$d %4$s.", numIn, tilein, numOut, tileout);
	}

	@Override
	public String toString() {
		String stockpileResources = "Name: " + this.name + "\n";
		for (String resource : this.stockpile.keySet()) {
			stockpileResources = stockpileResources + resource + ": " + this.stockpile.get(resource) + "\n";
		}
		return stockpileResources;
	}

	public void updateStockPile(String resourceName, int number) {
		if(this.getStockPile().containsKey(resourceName)) {
			int numOfResource = this.stockpile.get(resourceName);
			this.stockpile.replace(resourceName, numOfResource + number);
		}
		else {
			this.stockpile.put(resourceName, number);
		}
	}

	// The 'get' and 'set' methods are very useful for when objects of other
	// classes want to interact with this one, Stockpile. For methods defined
	// inside this class, however, direct field or attribute access if faster and
	// more sensible than using the 'get' and 'set' methods.
	public Map<String, Integer> getStockPile() {
		return this.stockpile;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getNumOfResource(String resource) {
		return this.stockpile.get(resource);
	}
}
