package model.gameplay;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import model.board.Board;
import model.board.Marketplace;
import model.board.Player;
import model.board.Stockpile;

public class Trade {
	private Board board;
	private Player player;
	private Map<String,Integer> playerResources;
	private Scanner inputScanner;
	private Marketplace marketplace;
	private Stockpile stockpile;
	private boolean tradedWithMarketplace;
	
	public Trade(Player player, Scanner inputScanner) {
		this.player = player;
		this.board = Board.getInstance();
		this.marketplace = board.getMarketplace();
		this.stockpile = board.getStockpile();
		this.inputScanner = inputScanner;	
		this.tradedWithMarketplace = false;
		
	}
	
	public boolean canTradeWithMarketplace() {
		if(this.tradedWithMarketplace) {
			return false;
		}
		return true;
	}
	
	public void tradeMarketplace(String requestedResource, String givenResource) {
		if(this.marketplace.isAvailable(requestedResource, 1)) {
			if(player.isAvailable(givenResource, 1)) {
				player.giveResource(requestedResource, 1);
				player.takeResource(givenResource, 1);
				this.marketplace.trade(givenResource, 1, requestedResource, 1);
				this.tradedWithMarketplace = true;
			}	
		}	
	}
	
	public void tradeStockpile(String requestedResource, int num, ArrayList<String> tradeInfo){
		if(tradeInfo.size()>1){
			num = 1;
		}
		for(String givenResource: tradeInfo) {
			swapWithStockpile(givenResource, requestedResource, num);
		}
	}
	
	private void swapWithStockpile(String givenResource, String requestedResource, Integer num) {
		if(player.isAvailable(givenResource, num*2)) {
			player.giveResource(requestedResource, num);
			player.takeResource(givenResource, num*2);
			this.stockpile.trade(givenResource, num*2, requestedResource, num);
		} else {System.out.println("You do not have enough " + givenResource + " for this trade!");}
	}
	
	
	
}
