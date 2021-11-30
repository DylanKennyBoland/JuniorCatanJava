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
	
	public void trade(String tilein, Integer number, String tileout) {
		if(! this.isAvailable(tileout, number/2)) {
			return;
		}
		int currentNum = this.getStockPile().get(tilein);
		this.stockpile.replace(tilein, currentNum + number);
		int numOut = number/2;
		currentNum = this.getStockPile().get(tileout);
		this.stockpile.replace(tileout, currentNum - numOut);		
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
