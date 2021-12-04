package model.gameplay;

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
	
	public void tradeMarketplace() {
		System.out.println("What would you like?");
		String requestedResource = this.inputScanner.nextLine();
		System.out.println(this.marketplace.isAvailable(requestedResource, 1));
		if(this.marketplace.isAvailable(requestedResource, 1)) {
			System.out.println("What will you give?");
			String givenResource = this.inputScanner.nextLine();
			if(player.checkResources(givenResource, 1)) {
				player.giveResource(requestedResource, 1);
				player.takeResource(givenResource, 1);
				this.marketplace.trade(givenResource, requestedResource, 1);
				this.tradedWithMarketplace = true;
			}	
		}	
	}
	
	public boolean checkResources() {
		
		
		return true;
	}
	
	public void tradeStockpile(){
		System.out.println("What would you like?");
		String requestedResource = this.inputScanner.nextLine();
		System.out.println("How much " + requestedResource + " would you like?");
		String num = this.inputScanner.nextLine();
		Integer numOfResource = Integer.parseInt(num);
		if(this.stockpile.isAvailable(requestedResource, numOfResource)) {
			 System.out.println("What resource will you give?");
			 String givenResource = this.inputScanner.nextLine();
			 if(player.checkResources(givenResource, numOfResource*2)) {
				 player.giveResource(requestedResource, numOfResource);
				 player.takeResource(givenResource, numOfResource*2);
				 this.stockpile.trade(givenResource, requestedResource, numOfResource);
			 }
		}
	}
	
	
	
}
