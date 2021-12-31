package model.board;

/**
 * @Author: Dylan Kenny Boland
 * @Date: (of last update) 29/12/2021
 */

public interface Tradeable {
	public void initializeResources(); // A method to set up a data structure of resources...
	
	/* The isAvailable() method will query if a certain data structure  
	has a certain number of a resource - it returns True or False... */
	public boolean isAvailable(String resource, int number);
	
	/* The signature for the trade() method below allows for it to be used
	 * in the Stockpile, Marketplace, and Player classes. The numIn and numOut
	 * arguments are useful, as a trade done with the Marketplace is slightly different 
	 * from when a trade is done with the Stockpile.
	 */
	public String trade(String tileIn, int numIn, String tileOut, int numOut);
	
	/* The Player and Stockpile classes both have hash maps which can be
	 * updated in nearly identical ways. For this reason, it seems a good idea
	 * to include an update() method in this interface.
	 */
	public void update(String resource, int number);
	
	// Every class should have a toString() method.
	@Override
	public String toString();
}
