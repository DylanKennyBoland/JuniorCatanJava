package model.gameplay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import model.board.*;

public class Build {
	private Player player;
	private Board board;
	private String buildChoice;
	private HashMap<String,Integer> playerResources;
	private HashMap<String,Integer> lairCost = new HashMap<String,Integer>();
	private HashMap<String,Integer> shipCost = new HashMap<String,Integer>();
	private Scanner inputScanner;
	
	public Build(Player player, Scanner inputScanner) {
		this.player = player;
		this.board = Board.getInstance();
		this.inputScanner = inputScanner;
		this.lairCost.put("Wood", 1);
		this.lairCost.put("Cutlass", 1);
		this.lairCost.put("Molasses", 1);
		this.lairCost.put("Goats", 1);
		this.shipCost.put("Goats", 1);
		this.shipCost.put("Wood", 1);
	}
	
	public boolean checkResources() {
		if(player.isAvailable("Wood", 1) && player.isAvailable("Goats", 1)) {
			if(this.buildChoice.contains("Ship")) {
				return true;
			}
			else if(player.isAvailable("Cutlass", 1) && player.isAvailable("Molasses", 1)){
				return true;
			}
		}
		System.out.println("you do not have enough resources");
		return false;
	}
	
	public void buildLair() {
		System.out.println("Where would you like to build a lair? (1-32)");
		String lairLocation = inputScanner.nextLine();
		this.buildChoice = "Lair";
		//playerResources = player.getResources();
		if(board.isLairAvailable(lairLocation) && checkResources()) {
			swap(this.lairCost, lairLocation);
			System.out.println("You now own lair " + lairLocation);
		}
	}
	
	private void swap(HashMap<String, Integer> costs, String asset) {
		player.addAsset(asset);
		for(Map.Entry cost: costs.entrySet() ) {
			player.takeResource((String)cost.getKey(), (Integer)cost.getValue());
		}

	}
	
	public void buildShip() {
		this.buildChoice = "Ship";
		System.out.println("Building Ship");
	}
}
