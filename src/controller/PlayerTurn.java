package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import model.board.Board;
import model.board.Marketplace;
import model.board.Stockpile;
import model.enums.TradeEnums;
import model.gameplay.Build;
import model.gameplay.Trade;
import model.players.Player;
import model.players.PlayerList;
import view.View;

/**
 * Class for the Player Turn Object.
 * This class controls what the player can do each turn.
 * 
 * @author: Adam Durning & Dylan Boland
 * @Date: 27/12/2021
 * 
 */
public class PlayerTurn {
	// Setting up the player turn variables.
	private Player player;
	private Board board;
	private Stockpile stockpile;
	private Marketplace marketplace;
	private PlayerList playerList;
	private Scanner inputScanner;
	private Build buildOptions;
	private Trade tradeOptions;
	private Random random = new Random();
	private View view;
	private boolean turnOver;

	/**
	 * The PlayerTurn constructor.
	 * 
	 * @param: player - The player object who's turn it is.
	 * @param: inputScanner - A scanner object for getting the user input.
	 * */
	public PlayerTurn(Player player, Scanner inputScanner) {
		this.player = player;
		this.board = Board.getInstance();
		this.stockpile = board.getStockpile();
		this.marketplace = board.getMarketplace();
		this.inputScanner = inputScanner;
		this.buildOptions = new Build(player);
		this.tradeOptions = new Trade(player);
		this.playerList = PlayerList.getInstance();
		this.view = View.getInstance();
		this.turnOver = false;
	}

	// This method starts the Player's turn.
	public void startTurn() {
		//Rolling the dice, always at the start of the turn
		rollDice();
		// This while loop gives the player there options until they end their turn.
		while (!this.turnOver) {
			ArrayList<String> options = new ArrayList<String>();
			options.add("Build");
			options.add("Trade");
			options.add("View Resources");
			options.add("View Marketplace");
			options.add("View Board");
			options.add("End Turn");
			this.view.displayOptions(options);
			switch (inputScanner.nextLine()) {
			case "1":
				build();
				break;
			case "2":
				trade();
				break;
			case "3":
				this.view.viewResources(this.player);
				break;
			case "4":
				this.view.viewMarketplace(this.marketplace);
				break;
			case "5":
				this.view.viewBoard(this.playerList, this.board.getGhostIsland());
				break;
			case "6":
				this.turnOver = true;
				break;
			}
		}
	}
	
	/**
	 * This method generates a random number (1 - 6) for the dice and calls the board to
	 * produce resources for the corresponding islands.
	 * If a 6 is rolled the player can move the ghost captain.
	 * */
	private void rollDice() {
		Integer roll = random.nextInt(6) + 1;
		this.view.display("\nIt is " + this.player.getName() + "'s turn!");
		this.view.display("\nThe dice rolled a " + roll);
		if (roll == 6) {
			this.moveGhostCaptain();
		} else {
			this.view.display(this.board.produceResources(roll));
		}
	}
	
	/**
	 * This method gets the player's choice for moving the ghost captain.
	 * */
	private void moveGhostCaptain() {
		boolean validChoice = false;
		this.view.display("\nGhost Captain is on Island " + board.getGhostIsland().getName());
		this.view.display("\nWhere would you like to move it to? (Input a letter from A - L) ");
		while(!validChoice) {
			char moveTo = this.inputScanner.next().charAt(0);
			inputScanner.nextLine();
			if ( moveTo < 65 || moveTo > 76) {
				this.view.display("That is not a valid location. (Input a letter from A - L) ");
			} else if ( moveTo  == this.board.getGhostIsland().getName()) {
				this.view.display("You have to move the Ghost Captain to a new Island. Try Again!");
			} 
			else {
				this.board.moveGhostCaptain(moveTo);
				validChoice = true;
			}
		}
	}

	/**
	 * This method is called when the Player chooses to Build.
	 * It gives the player there build options and handles there input.
	 * */
	private void build() {
		boolean finishedBuilding = false;
		ArrayList<String> buildOptionsList = new ArrayList<String>();
		buildOptionsList.add("Lair");
		buildOptionsList.add("Ship");
		buildOptionsList.add("View Resources");
		buildOptionsList.add("View My Assets");
		buildOptionsList.add("Go Back");
		while (!finishedBuilding) {
			this.view.display("\nWhat would you like to build?");
			this.view.displayOptions(buildOptionsList);
			switch (inputScanner.nextLine()) {
			case "1":
				this.buildLair();
				if (this.didPlayerWin()) {
					finishedBuilding = true;
					this.turnOver = true;
				}
				break;
			case "2":
				this.buildShip();
				break;
			case "3":
				this.view.viewResources(this.player);
				break;
			case "4":
				this.view.display(player.assetsToString());
				break;
			case "5":
				finishedBuilding = true;
				break;
			}
		}
	}

	/**
	 * This method is called when the player chooses to build a lair.
	 * It gets the available lair sites the player can build on from the model
	 * and then gives these as options to the player.
	 * This method is also called if a person draws a Build coco tile and
	 * chooses to build a lair. The skipResourcesCheckStatus variable is true
	 * if the player uses a Coco tile for building because this is a free build
	 * and they do not need to use resources.
	 * */
	private void buildLair() {
		ArrayList<String> validLairSites = new ArrayList<String>(this.buildOptions.validLairSites());
		if (validLairSites.size() == 0) {
			this.view.display("You currently have no valid lair sites to build on!\n");
			return;
		}
		if (this.player.skipResourcesCheckStatus() == false) { // Meaning we should not skip checking their resources...
			if (!this.buildOptions.checkResources(TradeEnums.BUILD_LAIR)) { // They don't have enough resources...
				this.view.display("You do not have enough resources");
				return;
			}
		}
		ArrayList<String> options = new ArrayList<String>(validLairSites);
		if (this.player.skipResourcesCheckStatus() == false) {
			options.add("Cancel Build");
		}
		this.view.display("Where would you like to build a lair?" +
				  " Enter your choice from 1 - " + options.size());
		this.view.displayOptions(options);
		boolean validInput = false;
		while (!validInput) {
			this.view.display("\nEnter here: ");
			if (inputScanner.hasNextInt()) {
				int option = inputScanner.nextInt();
				inputScanner.nextLine();
				if ((option >= 1) && (option <= validLairSites.size())) {
					this.view.display("You have chosen option " + option);
					this.view.display("\nBuilding... ");
					this.exchange(TradeEnums.BUILD_LAIR);
					this.buildOptions.buildLair(validLairSites.get(option - 1));
					validInput = true;
				} else if (option == options.size()) {
					if (this.player.skipResourcesCheckStatus() == true) {
						this.view.display("You have input " + option
								+ " which is outside the range of options. Please re-enter.");
					} else {
						this.view.display("Canceling build ...");
						validInput = true;
					}
				} else {
					this.view.display(
							"You have input " + option + " which is outside the range of options. Please re-enter.");
				}
			} else {
				this.view.display("Please input an integer for the option number.");
				inputScanner.next();
			}
		}
	}

	/**
	 * This method is called when the player chooses to build a ship.
	 * It gets the available ship sites the player can build on from the model
	 * and then gives these as options to the player.
	 * This method is also called if a person draws a Build coco tile and
	 * chooses to build a ship. The skipResourcesCheckStatus variable is true
	 * if the player uses a Coco tile for building because this is a free build
	 * and they do not need to use resources.
	 * */
	private void buildShip() {
		ArrayList<String> validShipSites = new ArrayList<String>(this.buildOptions.validShipSites());
		if (validShipSites.size() == 0) {
			this.view.display("You currently have no valid ship sites to build on!\n");
			return;
		}
		if (this.player.skipResourcesCheckStatus() == false) { // Meaning we should not skip checking their resources...
			if (!this.buildOptions.checkResources(TradeEnums.BUILD_SHIP)) { // They don't have enough resources...
				this.view.display("You do not have enough resources");
				return;
			}
		}
		ArrayList<String> options = new ArrayList<String>(validShipSites);
		/* Next, we need to check if the player is currently using a build-type coco
		 * tile... if they are then we shouldn't give the option to cancel a build
		 * (as the rules state that they must build a lair or ship immediately)
		 */ 
		if (this.player.skipResourcesCheckStatus() == false) {
			options.add("Cancel Build");
		}
		this.view.display("Where would you like to build a ship?" +
				  " Enter your choice from 1 - " + options.size());
		this.view.displayOptions(options);
		boolean validInput = false;
		while (!validInput) {
			this.view.display("\nEnter here: ");
			if (inputScanner.hasNextInt()) {
				Integer option = inputScanner.nextInt();
				inputScanner.nextLine();
				if ((option >= 1) && (option <= validShipSites.size())) {
					this.view.display("You have chosen option " + option);
					this.view.display("Building... ");
					this.exchange(TradeEnums.BUILD_SHIP);
					this.buildOptions.buildShip(validShipSites.get(option - 1));
					validInput = true;
				} else if (option == options.size()) {
					if (this.player.skipResourcesCheckStatus() == true) {
						this.view.display("You have input " + option
								+ " which is outside the range of options. Please re-enter.");
						continue;
					}
					this.view.display("Canceling build ...");
					validInput = true;
				} else {
					this.view.display(
							"You have input " + option + " which is outside the range of options. Please re-enter.");
				}
			} else {
				this.view.display("Please input an integer for the option number.");
				inputScanner.next();
			}
		}
	}

	public Integer pickCocoTile() {
		Map<Integer, String> cocoTypes = new HashMap<Integer, String>();
		cocoTypes.put(1, "Ghost Captain");
		cocoTypes.put(2, "Build");
		cocoTypes.put(3, "Resource Combination 1");
		cocoTypes.put(4, "Resource Combination 2");
		List<Integer> validTypes = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4));
		for (int i = 1; i < 5; i++) {
			String cocoType = cocoTypes.get(i);
			if (this.board.getCocoTiles().get(cocoType) == 0) {
				validTypes.remove(Integer.valueOf(i));
			}
		}
		Integer randi = random.nextInt(validTypes.size());
		return validTypes.get(randi);
	}
	
	public void hasMostCocoTiles() {
		if (this.player.getResources().get("Coco tiles") > this.board.getCurrentMaxCocoTiles()) {
			if (this.board.getPlayerWithMaxCocoTiles() != null) {
				this.board.getPlayerWithMaxCocoTiles().getLairAssets().remove(" 33 ");
			}
			this.board.setPlayerWithMaxCocoTiles(this.player);
			this.player.addLairAsset(" 33 ");
			this.board.incrementCurrentMaxCocoTiles();
			this.view.display("\nYou have the most Coco tiles, and now have a lair on Spooky Island!\n");
		} else if (this.player.getResources().get("Coco tiles") == this.board.getCurrentMaxCocoTiles()) {
			if (this.board.getPlayerWithMaxCocoTiles() != null) {
				this.board.getPlayerWithMaxCocoTiles().getLairAssets().remove(" 33 ");
				this.board.setPlayerWithMaxCocoTiles(null);
			}
		} else {
			return;
		}
	}
	
	public void buyCocoTile() {
		if (this.buildOptions.checkResources(TradeEnums.BUY_COCO_TILE) && this.board.getNumOfCocoTiles() >= 1) {
			this.exchange(TradeEnums.BUY_COCO_TILE);
			int cocoTileNum = this.pickCocoTile(); // A number corresponding to the type of Coco tile a player pulls
			switch (cocoTileNum) {
			case 1:
				this.exchange(TradeEnums.COCO_TILE_GHOST_CAPTAIN);
				boolean validChoice = false;
				this.view.display("You picked a ghost captain coco tile!");
				this.moveGhostCaptain();
				break;
			case 2:
				this.exchange(TradeEnums.COCO_TILE_BUILD);
				if ((this.buildOptions.validShipSites().size() != 0)
						&& (this.buildOptions.validShipSites().size() != 0)) {
					this.view.display("You can build a lair or a ship!");
					boolean validInput = false;
					while (!validInput) {
						this.view.display("Which would you like to build?");
						this.view.display("\n1: Ship\n2: Lair");
						if (inputScanner.hasNextInt()) {
							Integer option = inputScanner.nextInt();
							inputScanner.nextLine();
							if (option == 1) {
								this.player.setResourcesCheckStatus(true);
								this.buildShip();
								this.player.setResourcesCheckStatus(false);
								validInput = true;
							} else if (option == 2) {
								this.player.setResourcesCheckStatus(true);
								this.buildLair();
								this.player.setResourcesCheckStatus(false);
								validInput = true;
							} else {
								this.view.display("You have input " + option
										+ " which is outside the range of options. Please re-enter.");
							}
						} else {
							this.view.display("Please input an integer for the option number.");
							inputScanner.next();
						}
					}
				} else if (this.buildOptions.validLairSites().size() != 0) {
					this.view.display("You can buld a lair! (You currently have no valid ship sites.)");
					this.player.setResourcesCheckStatus(true);
					this.buildLair();
					this.player.setResourcesCheckStatus(false);
				} else {
					this.view.display("You can buld a ship! (You currently have no valid lair sites.)");
					this.player.setResourcesCheckStatus(true);
					this.buildShip();
					this.player.setResourcesCheckStatus(false);
				}
				break;
			case 3:
				this.exchange(TradeEnums.COCO_TILE_RESOURCE1);
				this.view.display("You get 2 Molasses and 2 Woods!\n");
				break;
			case 4:
				this.exchange(TradeEnums.COCO_TILE_RESOURCE2);
				this.view.display("You get 2 Goats and 2 Cutlass!\n");
				break;
			default:
				this.view.display("Invalid number received for coco tile type.");
				break;
			}
		} else {
			if (!this.buildOptions.checkResources(TradeEnums.BUY_COCO_TILE)) {
				this.view.display("You do not have enough resources for a coco tile unfortunately!");
			} else {
				this.view.display("Unfortunately there are no coco tiles left!");
			}
		}
	}
	/*
	 * The method below is meant to emulate some of the main builds or
	 * trades that can occur. A switch-case statement is used for easy
	 * readability. The reason for having this method is that for
	 * many transactions, such as building a lair or ship, or buying
	 * a coco tile, we know exactly how the resources of the
	 * player and stockpile (and board) are affected... as a result, we
	 * can group all the logic here, instead of having all of these
	 * update() method calls scattered throughout other parts of the
	 * code...
	 */
	public void exchange(TradeEnums event) {
		switch (event) {
		case BUILD_LAIR:
			this.player.update("Cutlass", -1);
			this.player.update("Molasses", -1);
			this.player.update("Goats", -1);
			this.player.update("Wood", -1);
			this.stockpile.update("Cutlass", 1);
			this.stockpile.update("Molasses", 1);
			this.stockpile.update("Goats", 1);
			this.stockpile.update("Wood", 1);
			break;
		case BUILD_SHIP:
			this.player.update("Goats", -1);
			this.player.update("Wood", -1);
			this.stockpile.update("Goats", 1);
			this.stockpile.update("Wood", 1);
			break;
		case BUY_COCO_TILE:
			this.player.update("Cutlass", -1);
			this.player.update("Molasses", -1);
			this.player.update("Goats", -1);
			this.player.update("Coco tiles", 1);
			this.stockpile.update("Cutlass", 1);
			this.stockpile.update("Molasses", 1);
			this.stockpile.update("Goats", 1);
			this.hasMostCocoTiles();
			break;
		case COCO_TILE_GHOST_CAPTAIN:
			// One can now imagine removing one of the Coco tiles from the side of
			// the board and giving it to the player...
			this.board.removeCocoTile("Ghost Captain");
			this.player.addCocoTile("Ghost Captain");
			break;
		case COCO_TILE_BUILD:
			this.board.removeCocoTile("Build");
			this.player.addCocoTile("Build");
			break;
		case COCO_TILE_RESOURCE1:
			this.board.removeCocoTile("Resource Combination 1");
			this.player.addCocoTile("Resource Combination 1");
			this.player.update("Molasses", 2);
			this.player.update("Wood", 2);
			this.stockpile.update("Molasses", -2);
			this.stockpile.update("Wood", -2);
			break;
		case COCO_TILE_RESOURCE2:
			this.board.removeCocoTile("Resource Combination 2");
			this.player.addCocoTile("Resource Combination 2");
			this.player.update("Goats", 2);
			this.player.update("Cutlass", 2);
			this.stockpile.update("Goats", -2);
			this.stockpile.update("Cutlass", -2);
			break;
		default:
			break;
		}
	}

	/**
	 * This method is called when the player chooses to Trade.
	 * The player is shown their options and they input what they would
	 * like to do.
	 * */
	private void trade() {
		boolean finishedTrading = false;
		ArrayList<String> tradeOptionsList = new ArrayList<String>();
		tradeOptionsList.add("Marketplace");
		tradeOptionsList.add("Stockpile");
		tradeOptionsList.add("Buy Coco tile");
		tradeOptionsList.add("View Resources");
		tradeOptionsList.add("View Marketplace");
		tradeOptionsList.add("View Stockpile");
		tradeOptionsList.add("Go Back");
		Marketplace marketplace = board.getMarketplace();
		while (!finishedTrading) {
			this.view.display("\nWhat would you like to trade with?");
			this.view.displayOptions(tradeOptionsList);
			switch (inputScanner.nextLine()) {
			case "1":
				if (tradeOptions.canTradeWithMarketplace()) {
					tradeWithMarketplace();
				} else {
					this.view.display("You have already traded with the Marketplace");
				}
				;
				break;
			case "2":
				tradeWithStockpile();
				break;
			case "3":
				this.view.viewResources(this.player);
				break;
			case "4":
				this.view.viewResources(this.player);
				break;
			case "5":
				this.view.display(marketplace.toString());
				break;
			case "6":
				this.view.display(this.stockpile.toString());
				break;
			case "7":
				finishedTrading = true;
				break;
			}
		}
	}

	/**
	 * This method is called if the player chooses to trade with the marketplace.
	 * The player is shown what resources they can get and when they make a
	 * choice they are then asked what resource they will give.
	 * */
	private void tradeWithMarketplace() {
		ArrayList<String> giveResource = new ArrayList<String>();
		this.view.display("What would you like? (Enter a number from 1 - 5. Enter any other key to escape)");
		ArrayList<String> marketplaceOptions = new ArrayList<String>();
		marketplaceOptions = this.marketplace.getMarketPlace();
		this.view.displayOptions(marketplaceOptions);
		switch (inputScanner.nextLine()) {
		case "1":
			giveResource = getGivenResources();
			this.view.display(tradeOptions.tradeMarketplace(marketplaceOptions.get(0), giveResource.get(0)));
			break;
		case "2":
			giveResource = getGivenResources();
			this.view.display(tradeOptions.tradeMarketplace(marketplaceOptions.get(1), giveResource.get(0)));
			break;
		case "3":
			giveResource = getGivenResources();
			this.view.display(tradeOptions.tradeMarketplace(marketplaceOptions.get(2), giveResource.get(0)));
			break;
		case "4":
			giveResource = getGivenResources();
			this.view.display(tradeOptions.tradeMarketplace(marketplaceOptions.get(3), giveResource.get(0)));
			break;
		case "5":
			giveResource = getGivenResources();
			this.view.display(tradeOptions.tradeMarketplace(marketplaceOptions.get(4), giveResource.get(0)));
			break;
		}
	}
	
	/**
	 * This method gets the resource(s) the player is going to give when
	 * trading with the Stockpile/Marketplace. 
	 * */
	private ArrayList<String> getGivenResources() {
		ArrayList<String> giveResource = new ArrayList<String>();
		this.view.display("What will you give?");
		ArrayList<String> giveOptions = new ArrayList<String>();
		for (Map.Entry<String, Integer> resource : this.player.getResources().entrySet()) {
			giveOptions.add((String) resource.getKey() + " (You have " + resource.getValue() + ")");
		}
		this.view.displayOptions(giveOptions);
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

	/**
	 * This method is called when the player chooses to trade with the stockpile.
	 * The player is shown how much of each resource is available and then they can
	 * pick what they would like and how much of that resource they would like.
	 * They are then asked what resource(s) they are going to give.
	 * */
	public void tradeWithStockpile() {
		int num;
		ArrayList<String> tradeInfo;
		this.view.display("What resource would you like?");
		ArrayList<String> stockpileOptions = new ArrayList<String>();
		stockpileOptions.add("Gold (There are " + this.stockpile.getNumOfResource("Gold") + " in the stockpile)");
		stockpileOptions.add("Mollasses (There are " + this.stockpile.getNumOfResource("Molasses") + " in the stockpile)");
		stockpileOptions.add("Wood (There are " + this.stockpile.getNumOfResource("Wood") + " in the stockpile)");
		stockpileOptions.add("Goats (There are " + this.stockpile.getNumOfResource("Goats") + " in the stockpile)");
		stockpileOptions.add("Cutlass (There are " + this.stockpile.getNumOfResource("Cutlass") + " in the stockpile)");
		this.view.displayOptions(stockpileOptions);
		switch(inputScanner.nextLine()) {
		case "1":
			num = getNumberFromPlayer("Gold");
			tradeInfo = new ArrayList<String>(getTradeInfo("Gold", num));
			this.view.display(tradeOptions.tradeStockpile("Gold", num, tradeInfo));
			break;
		case "2":
			num = getNumberFromPlayer("Mollasses");
			tradeInfo = new ArrayList<String>(getTradeInfo("Mollasses", num));
			this.view.display(tradeOptions.tradeStockpile("Mollasses", num, tradeInfo));
			break;
		case "3":
			num = getNumberFromPlayer("Wood");
			tradeInfo = new ArrayList<String>(getTradeInfo("Wood", num));
			this.view.display(tradeOptions.tradeStockpile("Wood", num, tradeInfo));
			break;
		case "4":
			num = getNumberFromPlayer("Goats");
			tradeInfo = new ArrayList<String>(getTradeInfo("Goats", num));
			this.view.display(tradeOptions.tradeStockpile("Goats", num, tradeInfo));
			break;
		case "5":
			num = getNumberFromPlayer("Cutlass");
			tradeInfo = new ArrayList<String>(getTradeInfo("Cutlass", num));
			this.view.display(tradeOptions.tradeStockpile("Cutlass", num, tradeInfo));
			break;
		}

	}

	/**
	 * This method is used to get the number of the requested resource the player would like form the stockpile.
	 * */
	public int getNumberFromPlayer(String requestedResource) {
		boolean enough = false;
		int num = 0;
		while (!enough) {
			this.view.display("How many would you like?");
			try {
				num = Integer.parseInt(inputScanner.nextLine());
			}
			catch(NumberFormatException e){
				this.view.display("That is not a number. Try again.");
			}
			if (num < 1) {
				this.view.display("Please enter a number greater than 0. Try again.");
			}else if (this.stockpile.isAvailable(requestedResource, num)) {
				enough = true;
			} else {
				this.view.display("There is not enough " + requestedResource + " in the stockpile");
			}
		}
		return num;
	}

	/**
	 * This method gets the the information of what the player is going to 
	 * give when trading with the stockpile. The player must give two of the same 
	 * resource for one resource from the stockpile.
	 * If the player wants multiple resources from the stockpile they can
	 * offer to give multiple resources, however the 2 for 1 rule still applies.
	 * 
	 * @param: requstedResoure - The resource the player wants from the stockpile
	 * @param: num - The amount of the requestedResource.
	 * 
	 * @return: Returns a list of the resource(s) that the player is going to give.
	 * */
	public ArrayList<String> getTradeInfo(String requestedResource, int num) {
		ArrayList<String> tradeInfo = new ArrayList<String>();
		int i = 1;
		if (this.stockpile.isAvailable(requestedResource, num)) {
			boolean givingMultiple = false;
			if (num > 1) {
				this.view.display("Are you giving multiple resources?");
				String answer = inputScanner.nextLine();
				if (answer.toUpperCase().contains("Y")) {
					givingMultiple = true;
				}
			}
			while (givingMultiple && i <= num) {
				tradeInfo.addAll(getGivenResources());
				i++;
			}
			if (!givingMultiple) {
				tradeInfo.addAll(getGivenResources());
			}
		}
		return tradeInfo;
	}
	
	// This method checks if the player has won the game.
	public boolean didPlayerWin() {
		if (this.player.getLairAssets().size() == 7) {
			return true;
		} else {
			return false;
		}
	}
}
