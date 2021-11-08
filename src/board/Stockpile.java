package board;
import java.util.*;

public class Stockpile {
	private String name;
	private Map<String, Integer> stockpile = new HashMap<String, Integer>();
	
	public Stockpile(String name) { // The constructor...
				this.name = name;
	}
	
	public Stockpile(String name, Map<String, Integer> stockpile) {
		this.name = name;
		this.stockpile = stockpile;
	}
	
	public void updateStockPile(String resourceName, int number) {
		if(this.getStockPile().containsKey(resourceName)) {
			this.stockpile.replace(resourceName, number);
		}
		else {
			this.stockpile.put(resourceName, number);
		}
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
