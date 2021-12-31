package model.gameplay;

import java.util.ArrayList;
import model.board.Board;
import model.board.Marketplace;
import model.board.Stockpile;
import model.players.Player;

/**
 * Class for the Trade Object which is used for the trading functionality in the game.
 * 
 * @author: Adam Durning & Dylan Boland
 * @Date: 27/12/2021
 * 
 */
public class Trade {
	// Setting up the variables
	private Player player;
	private Board board;
	private Marketplace marketplace;
	private Stockpile stockpile;
	private boolean tradedWithMarketplace;
	
	/**
	 * The Trade constructor.
	 *
	 * @param: player - The player object who is trading.
	 * */
	public Trade(Player player) {
		this.player = player;
		this.board = Board.getInstance();
		this.marketplace = board.getMarketplace();
		this.stockpile = board.getStockpile();
		this.tradedWithMarketplace = false;
	}
	
	/** 
	 * This method checks if the player has already traded with the marketplace
	 * and returns a boolean indicating the result. A player can only trade with
	 * the marketplace once per turn.
	 * */
	public boolean canTradeWithMarketplace() {
		if(this.tradedWithMarketplace) {
			return false;
		}
		return true;
	}
	
	/**
	 * This method is used for trading with the marketplace.
	 * 
	 * @param: requestedResource - The resource that the player wants from the Marketplace.
	 * @param: givenResource - The resource the player is giving to the Marketplace.
	 * 
	 * @return: Returns a string indicating the status of the trade.
	 * */
	public String tradeMarketplace(String requestedResource, String givenResource) {
		// Checking if the player has already traded with Marketplace.
		if(!this.tradedWithMarketplace) {
			// Checking if the marketplace has the requested resource.
			if(this.marketplace.isAvailable(requestedResource, 1)) {
				// Checking if the player has the resource being given.
				if(player.isAvailable(givenResource, 1)) {
					// Performing the swap of the resources.
					player.update(requestedResource, 1);
					player.update(givenResource, -1);
					// Updating the variable indicating that the player has traded with the marketplace.
					this.tradedWithMarketplace = true;
					return(this.marketplace.trade(givenResource, 1, requestedResource, 1));
				}	
			}else {
				return("The marketplace does not have any " + requestedResource);
			}
		} else {
			return("You have already traded with the Marketplace this turn.");
		}
		return("Error, something went wrong!");
	}
	
	/**
	 * This method is used for trading with the stockpile.
	 * 
	 * @param: requestedResource - The resource that the player wants from the Stockpile.
	 * @param: num - The number of the requested resource that the player wants.
	 * @param: givenResources - A list of the types of resources that the player is giving.
	 * 
	 * @return: Returns a string indicating the status of the trade.
	 * */
	public String tradeStockpile(String requestedResource, int num, ArrayList<String> givenResources){
		String output = "";
		// If the number of given resources is more than 1, set num to 1
		// so that the player only gets 1 resource for each resource they are
		// giving.
		if(givenResources.size()>1){
			num = 1;
		}
		// Looping through the givenResources and performing the trade/trades.
		for(String givenResource: givenResources) {
			output  += swapWithStockpile(givenResource, requestedResource, num);
		}
		return output;
	}
	
	/**
	 * This method performs the trade with the stockpile.
	 * 
	 * @param: givenResource - The resource being given to the stockpile.
	 * @param: requestedResource - The resource requested from the stockpile.
	 * @param: num - The number of the requested resource.
	 * 
	 * @return: Returns a string indicating the status of the trade.
	 * */
	private String swapWithStockpile(String givenResource, String requestedResource, Integer num) {
		if(player.isAvailable(givenResource, num*2)) {
			player.update(requestedResource, num);
			player.update(givenResource, -num*2);
			return(this.stockpile.trade(givenResource, num*2, requestedResource, num));
		} else {return("You do not have enough " + givenResource + " for this trade!");}
	}
}
