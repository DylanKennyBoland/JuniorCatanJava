package model.board;

// This interface will be implemented by the Stockpile, Marketplace, 
// Player classes.

public interface Tradeable {
	public void set(); // A method to set up a data structure of resources...
	
	// The isAvailable() method will query if a certain data structure  
	// has a certain number of a resource - it returns True or False...
	public boolean isAvailable(String resourceName, Integer number);
	// The signature for the trade() method below allows for it to be used 
	// in the Stockpile, Marketplace, and Player classes. The numIn and numOut
	// arguments are useful, as a trade done with the Marketplace is slightly different
	// from when a trade is done with the Stockpile.
	public String trade(String tilein, Integer numIn, String tileout, Integer numOut);
	// Every class should have a toString() method.
	public String toString();
}
