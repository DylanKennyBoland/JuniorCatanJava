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
	
	public void tradeMarketplace() {
		System.out.println("What would you like?");
		String requestedResource = this.inputScanner.nextLine();
		System.out.println(this.marketplace.isAvailable(requestedResource, 1));
		if(this.marketplace.isAvailable(requestedResource, 1)) {
			System.out.println("What will you give?");
			String givenResource = this.inputScanner.nextLine();
			if(player.isAvailable(givenResource, 1)) {
				player.giveResource(requestedResource, 1);
				player.takeResource(givenResource, 1);
				this.marketplace.trade(givenResource, 1, requestedResource, 1);
				this.tradedWithMarketplace = true;
			}	
		}	
	}
	
	public boolean checkResources() {
		
		
		return true;
	}
	
	public void tradeStockpile(String requestedResource, int num, ArrayList<String> tradeInfo){
		if(tradeInfo.size()>1){
			num = 1;
		}
		for(String givenResource: tradeInfo) {
			swapWithStockpile(givenResource, requestedResource, num);
		}
//		boolean givingMultiple = false;
//		int i = 1;
//		if(this.stockpile.isAvailable(resource, num)) {
//			if(num > 1) {
//				System.out.println("Are you giving multiple resources?");
//				String answer = inputScanner.nextLine();
//				if(answer.toUpperCase().contains("Y")) {
//					givingMultiple = true;
//				}
//			}
//			while(givingMultiple && i <= num) {
//				swapWithStockpile(resource, 1);
//				i++;
//			}
//			if(!givingMultiple) {
//				swapWithStockpile(resource, num);
//			}
//		}
	}
	
	private void swapWithStockpile(String givenResource, String requestedResource, Integer num) {
		if(player.isAvailable(givenResource, num*2)) {
			player.giveResource(requestedResource, num);
			player.takeResource(givenResource, num*2);
			this.stockpile.trade(givenResource, num*2, requestedResource, num);
		} else {System.out.println("You do not have enough " + givenResource + " for this trade!");}
	}
	
	
	
}
