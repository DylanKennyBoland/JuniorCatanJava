package pieces;
import java.util.Random;

public class Dice {
	private String name;
	private int val;
	
	// Making an object of type "Random"... this will be useful for generating random numbers...
	Random random = new Random();
	
	//====================
	// Constructors
	//====================
	public Dice(String name, int val) {
		this.name = name;
		this.val = val;
	}
	
	public Dice(String name) { // With this constructor an initial value need not be supplied...
		this.name = name;
		// Next we'll assign a random integer to the value field. This hopefully
		// reflects the nature of how the dice's face-up value is more or less random
		// when the dice is taken out of the box and placed beside the board...
		this.val = random.nextInt(6) + 1;
	}
	
	public int roll() { // This method is meant to emulate the rolling of the dice...
		this.val = random.nextInt(6) + 1;
		return this.getVal();
	}
	
	// Let's add the 'get' and 'set' methods:
	public int getVal() {
		return this.val;
	}
	
	protected void setVal(int newVal) {
		this.val = newVal;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
}