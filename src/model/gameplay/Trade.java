package model.gameplay;

import java.util.ArrayList;
import java.util.Scanner;

import model.board.Board;
import model.board.Marketplace;
import model.board.Stockpile;
import model.players.Player;

public class Trade {
	private Player player;
	private Board board;
	private Marketplace marketplace;
	private Stockpile stockpile;
	private boolean tradedWithMarketplace;
	
	public Trade(Player player, Scanner inputScanner) {
		this.player = player;
		this.board = Board.getInstance();
		this.marketplace = board.getMarketplace();
		this.stockpile = board.getStockpile();
		this.tradedWithMarketplace = false;
		
	}
	
	public boolean canTradeWithMarketplace() {
		if(this.tradedWithMarketplace) {
			return false;
		}
		return true;
	}
	
	public String tradeMarketplace(String requestedResource, String givenResource) {
		if(this.marketplace.isAvailable(requestedResource, 1)) {
			if(player.isAvailable(givenResource, 1)) {
				player.update(requestedResource, 1);
				player.update(givenResource, -1);
				this.tradedWithMarketplace = true;
				return(this.marketplace.trade(givenResource, 1, requestedResource, 1));
			}	
		}else {
			return("The marketplace does not have any " + requestedResource);
		}
		return("Error, something went wrong!");
	}
	
	public String tradeStockpile(String requestedResource, int num, ArrayList<String> tradeInfo){
		String output = "";
		if(tradeInfo.size()>1){
			num = 1;
		}
		for(String givenResource: tradeInfo) {
			output  += swapWithStockpile(givenResource, requestedResource, num);
		}
		return output;
	}
	
	private String swapWithStockpile(String givenResource, String requestedResource, int num) {
		if(player.isAvailable(givenResource, num*2)) {
			player.update(requestedResource, num);
			player.update(givenResource, -num*2);
			return(this.stockpile.trade(givenResource, num*2, requestedResource, num));
		} else {return("You do not have enough " + givenResource + " for this trade!");}
	}
	
	
	
}
