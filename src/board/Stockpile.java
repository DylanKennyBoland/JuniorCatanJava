package board;
import java.util.*;

public class Stockpile implements Tradeable {
	private Integer resourceQuantity = 18;
	private String name;
	private Map<String, Integer> stockpile = new HashMap<String, Integer>();
	
	public Stockpile(String name) { // The constructor...
				this.name = name;
				this.set();
	}

	public void set() {
		this.stockpile.put("Wood", resourceQuantity);
		this.stockpile.put("Cutlass", resourceQuantity);
		this.stockpile.put("Goats", resourceQuantity);
		this.stockpile.put("Gold", resourceQuantity);
		this.stockpile.put("Molasses", resourceQuantity);
		this.stockpile.put("Coco tiles", resourceQuantity);
	}
	
	public void updateStockPile(String resourceName, int number) {
		if(this.getStockPile().containsKey(resourceName)) {
			this.stockpile.replace(resourceName, number);
		}
		else {
			this.stockpile.put(resourceName, number);
		}
	}
	
	public boolean isAvailable(String resourceName, Integer number) {
		if((this.getStockPile().containsKey(resourceName)) && (this.getStockPile().get(resourceName) >= number)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String trade(String tilein, String tileout) {
		if(this.getStockPile().get(tileout) == 0) {
			return String.format("There are no '%1$s' tiles in the stockpile to trade with.", tileout);
		}
		int currNumTileIn = this.stockpile.get(tilein);
		int currNumTileOut = this.stockpile.get(tileout);
		// Now we update the values associated with the keys...
		this.stockpile.replace(tilein, currNumTileIn + 2);
		this.stockpile.replace(tileout, currNumTileOut - 1);
		return String.format("You've traded a %1$s for a %2$s", tilein, tileout);
	}
	
	public Map<String, Integer> getStockPile() {
		return this.stockpile;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
