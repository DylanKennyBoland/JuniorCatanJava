package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.board.Board;
import model.board.Islands;
import model.board.Marketplace;
import model.board.Stockpile;
import model.enums.*;
import model.gameplay.*;
import model.players.Player;
import model.players.PlayerList;

public class PlayerTurn {
	private Player player;
	private Board board;
	private Stockpile stockpile;
	private Marketplace marketplace;
	private List<Islands> islandList;
	private PlayerList playerList;
	private Scanner inputScanner;
	private CocoEnums cocoTile;
	private ResourceEnums resource;
	private PlayerEnums playerColour;
	private IslandEnums islandType;
	private Build buildOptions;
	private Trade tradeOptions;
	private Random random = new Random();

	public PlayerTurn(Player player, Scanner inputScanner) {
		this.player = player;
		this.board = Board.getInstance();
		this.stockpile = board.getStockpile();
		this.marketplace = board.getMarketplace();
		this.islandList = board.getIslands();
		this.inputScanner = inputScanner;
		this.buildOptions = new Build(player, inputScanner);
		this.tradeOptions = new Trade(player, inputScanner);
		this.playerList = PlayerList.getInstance();
		rollDice();
	}

	private void rollDice() {
		Integer roll = random.nextInt(6) + 1;
		System.out.println("\nIt is " + this.player.getName() + "'s turn!");
		System.out.println("\nThe dice rolled a " + roll);
		if (roll == 6) {
			System.out.println("\nGhost Captain is on Island " + board.getGhostIsland().getName());
			System.out.println("\nWhere would you like to move it to?");
			String moveTo = this.inputScanner.nextLine();
			this.board.moveGhostCaptain(moveTo);
			return;
		}
		produceResources(roll);
	}

	private void produceResources(Integer roll) {
		for (Islands island : islandList) {
			if (island.getDiceNumber() == roll && !island.hasGhostCaptain()) {
				for (Player player : playerList.getList()) {
					List<String> playerLairAssets = player.getLairAssets();
					List<String> islandLairs = island.getAttachedLairs();
					if (!Collections.disjoint(playerLairAssets, islandLairs)) {
						Integer numOfAttachedLairs = playerLairAssets.stream().distinct().filter(islandLairs::contains)
								.collect(Collectors.toList()).size();
						String resource = island.getIslandResource();
						if(resource.contains(" ")) {
							break;
						}else {
							System.out.println("Giving " + resource + " to " + player.getName());
							player.giveResource(resource, numOfAttachedLairs);
							this.stockpile.updateStockPile(resource, -numOfAttachedLairs);
						}
					}
				}
			} else if(island.getDiceNumber() == roll && island.hasGhostCaptain()) {
				System.out.println("Island " + island.getName() + " cannot produce!");
			}
		}
	}
	
	public void startTurn() {
		boolean turnOver = false;
		while (!turnOver) {

			// String input = inputScanner.nextLine();

			ArrayList<String> options = new ArrayList<String>();
			options.add("End Turn");
			options.add("Build");
			options.add("Trade");
			options.add("View Resources");
			displayOptions(options);
			switch (inputScanner.nextLine()) {
			case "1":
				turnOver = true;
				break;
			case "2":
				build();
				break;
			case "3":
				trade();
				break;
			case "4":
				viewResources();
				break;
			}
		}
	}

	private void viewResources() {
		System.out.println(this.player.getResources().toString());
	}

	private void build() {
		boolean finishedBuilding = false;
		ArrayList<String> buildOptionsList = new ArrayList<String>();
		buildOptionsList.add("Lair");
		buildOptionsList.add("Ship");
		buildOptionsList.add("View Resources");
		buildOptionsList.add("View Assets");
		buildOptionsList.add("Go Back");
		while (!finishedBuilding) {
			System.out.println("\nWhat would you like to build?");
			displayOptions(buildOptionsList);
			// String buildInput = inputScanner.nextLine();
			switch (inputScanner.nextLine()) {
			case "1":
				buildOptions.buildLair();
				break;
			case "2":
				buildOptions.buildShip();
				break;
			case "3":
				viewResources();
				break;
			case "4":
				player.viewAssets();
				break;
			case "5":
				finishedBuilding = true;
				break;
			}
		}
	}

	private void trade() {
		boolean finishedTrading = false;
		ArrayList<String> tradeOptionsList = new ArrayList<String>();
		tradeOptionsList.add("Marketplace");
		tradeOptionsList.add("Stockpile");
		tradeOptionsList.add("View Resources");
		tradeOptionsList.add("View Marketplace");
		tradeOptionsList.add("View Stockpile");
		tradeOptionsList.add("Go Back");
		Marketplace marketplace = board.getMarketplace();
		while (!finishedTrading) {
			System.out.println("\nWhat would you like to trade with?");
			displayOptions(tradeOptionsList);
			// String buildInput = inputScanner.nextLine();
			switch (inputScanner.nextLine()) {
			case "1":
				if (tradeOptions.canTradeWithMarketplace()) {
					tradeWithMarketplace();
				} else {
					System.out.println("You have already traded with the Marketplace");
				}
				;
				break;
			case "2":
				tradeWithStockpile();
				break;
			case "3":
				viewResources();
				break;
			case "4":
				System.out.println(marketplace.toString());
				break;
			case "5":
				System.out.println(this.stockpile.toString());
				break;
			case "6":
				finishedTrading = true;
				break;
			}
		}
	}

	public void tradeWithMarketplace() {
		ArrayList<String> giveResource = new ArrayList<String>();
		System.out.println("What would you like?");
		ArrayList<String> marketplaceOptions = new ArrayList<String>();
		marketplaceOptions = this.marketplace.getMarketPlace();
		displayOptions(marketplaceOptions);
		//String requestedResource = this.inputScanner.nextLine();
		switch (inputScanner.nextLine()) {
		case "1":
			giveResource = getGivenResources();
			tradeOptions.tradeMarketplace(marketplaceOptions.get(0), giveResource.get(0));
			break;
		case "2":
			giveResource = getGivenResources();
			tradeOptions.tradeMarketplace(marketplaceOptions.get(1), giveResource.get(0));
			break;
		case "3":
			giveResource = getGivenResources();
			tradeOptions.tradeMarketplace(marketplaceOptions.get(2), giveResource.get(0));
			break;
		case "4":
			giveResource = getGivenResources();
			tradeOptions.tradeMarketplace(marketplaceOptions.get(3), giveResource.get(0));
			break;
		case "5":
			giveResource = getGivenResources();
			tradeOptions.tradeMarketplace(marketplaceOptions.get(4), giveResource.get(0));
			break;
		}
	}
	private ArrayList<String> getGivenResources() {
		ArrayList<String> giveResource = new ArrayList<String>();
		System.out.println("What will you give?");
		ArrayList<String> giveOptions = new ArrayList<String>();
		for(Map.Entry resource: this.player.getResources().entrySet()) {
			giveOptions.add((String) resource.getKey() + " (You have " + (Integer)resource.getValue() + ")");
		}
		displayOptions(giveOptions);
		//String requestedResource = this.inputScanner.nextLine();
		switch (inputScanner.nextLine()) {
		case "1":
			giveResource.add("Gold");
			break;
		case "2":
			giveResource.add("Molasses");
			break;
		case "3":
			giveResource.add("Wood");
			break;
		case "4":
			giveResource.add("Goats");
			break;
		case "5":
			giveResource.add("Cutlass");
			break;
		}
		return giveResource;
	}
	
//	private ArrayList<String> getMarketplaceOptions(){
//		ArrayList<String> options = new ArrayList<String>();
//		
//		return options;
//	}
	public void tradeWithStockpile() {
		int num;
		ArrayList<String> tradeInfo ;
		System.out.println("What resource would you like?");
		ArrayList<String> stockpileOptions = new ArrayList<String>();
		stockpileOptions.add("Gold (There are " + this.stockpile.getNumOfResource("Gold") + " in the stockpile)");
		stockpileOptions.add("Mollasses (There are " + this.stockpile.getNumOfResource("Molasses") + " in the stockpile)");;
		stockpileOptions.add("Wood (There are " + this.stockpile.getNumOfResource("Wood") + " in the stockpile)");
		stockpileOptions.add("Goats (There are " + this.stockpile.getNumOfResource("Goats") + " in the stockpile)");
		stockpileOptions.add("Cutlass (There are " + this.stockpile.getNumOfResource("Cutlass") + " in the stockpile)");
		displayOptions(stockpileOptions);
		switch(inputScanner.nextLine()) {
		case "1":
			num = getNumberFromPlayer("Gold");
			tradeInfo = new ArrayList<String>(getTradeInfo("Gold", num));
			tradeOptions.tradeStockpile("Gold", num, tradeInfo);
			break;
		case "2":
			num = getNumberFromPlayer("Mollasses");
			tradeInfo = new ArrayList<String>(getTradeInfo("Mollasses", num));
			tradeOptions.tradeStockpile("Mollasses", num, tradeInfo);
			break;
		case "3":
			num = getNumberFromPlayer("Wood");
			tradeInfo = new ArrayList<String>(getTradeInfo("Wood", num));
			tradeOptions.tradeStockpile("Wood", num, tradeInfo);
			break;
		case "4":
			num = getNumberFromPlayer("Goats");
			tradeInfo = new ArrayList<String>(getTradeInfo("Goats", num));
			tradeOptions.tradeStockpile("Goats", num, tradeInfo);
			break;
		case "5":
			num = getNumberFromPlayer("Cutlass");
			tradeInfo = new ArrayList<String>(getTradeInfo("Cutlass", num));
			tradeOptions.tradeStockpile("Cutlass", num, tradeInfo);
			break;
		}
		
	}
	
	public int getNumberFromPlayer(String requestedResource) {
		boolean enough = false;
		int num = 0;
		while(!enough) {
			System.out.println("How many would you like?");
			num = Integer.parseInt(inputScanner.nextLine());
			if(this.stockpile.isAvailable(requestedResource, num)) {
				enough = true;
			} else {
				System.out.println("There is not enough " + requestedResource + " in the stockpile");
			}
		}
		return num;
	}
	
	public ArrayList<String> getTradeInfo(String requestedResource, int num){
		ArrayList<String> tradeInfo = new ArrayList<String>();
		int i = 1;
		if(this.stockpile.isAvailable(requestedResource, num)) {
			boolean givingMultiple = false;
			if(num > 1) {
				System.out.println("Are you giving multiple resources?");
				String answer = inputScanner.nextLine();
				if(answer.toUpperCase().contains("Y")) {
					givingMultiple = true;
				}
			}
			while(givingMultiple && i <= num) {
				tradeInfo.addAll(getGivenResources());
				i++;
			}
			if(!givingMultiple) {
				tradeInfo.addAll(getGivenResources());
			}
		}
		return tradeInfo;
	}
	
	public boolean didPlayerWin() {
		if (this.player.getLairAssets().size() == 7) {
			return true;
		} else {
			return false;
		}
	}

	private void displayOptions(ArrayList<String> options) {
		int i = 1;
		for (String option : options) {
			System.out.println("\n" + i + " : " + option);
			i++;
		}
	}
}
