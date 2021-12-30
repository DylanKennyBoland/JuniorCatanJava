package model.gameplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.enums.TradeEnums;
import model.board.Board;
import model.board.Stockpile;
import model.players.Player;

/**
 * Class for the Build Object which is used for the building functionality in the game.
 * 
 * @author: Adam Durning & Dylan Boland
 * @Date: 27/12/2021
 * 
 */

public class Build {
	//Setting up the Build variables.
	private Player player;
	private Board board;
	private Stockpile stockpile;
	private HashMap<String, Integer> lairCost = new HashMap<String, Integer>();
	private HashMap<String, Integer> shipCost = new HashMap<String, Integer>();

	
	/**
	 * The Build constructor.
	 * 
	 * @param: player - The player object that wants to build.
	 * 
	 * */
	public Build(Player player) {
		this.player = player;
		this.board = Board.getInstance();
		this.stockpile = board.getStockpile();
		this.lairCost.put("Wood", 1);
		this.lairCost.put("Cutlass", 1);
		this.lairCost.put("Molasses", 1);
		this.lairCost.put("Goats", 1);
		this.shipCost.put("Goats", 1);
		this.shipCost.put("Wood", 1);
	}

	/**
	 * This method checks if the player has enough resources to build the type of asset
	 * and returns a boolean.
	 * 
	 * @param: event - The build event to check the player has enough resources for. (Ship, Lair, Coco Tile)
	 * 
	 * @return: A boolean indicating if the player has enough resources for the asset type.
	 * */
	public boolean checkResources(TradeEnums event) {
		boolean result;
		switch (event) {
		case BUILD_SHIP:// Checking the players resources for building a ship.
			if (player.isAvailable("Wood", 1) && player.isAvailable("Goats", 1)) {
				result = true;

			} else {
				result = false;
			}
			break;
		case BUILD_LAIR:// Checking resources for a lair.
			if (player.isAvailable("Cutlass", 1) /*&& player.isAvailable("Molasses", 1) && player.isAvailable("Goats", 1)
					&& player.isAvailable("Wood", 1)*/) {
				result = true;
			} else {
				result = false;
			}
			break;
		case BUY_COCO_TILE:// Checking resources for a Coco Tile.		
			if (player.isAvailable("Cutlass", 1) && player.isAvailable("Molasses", 1)
					&& player.isAvailable("Gold", 1)) {
				result = true;
			} else {
				result = false;
			}
			break;
		default:
			result = false;
		}
		return result;
	}

	/**
	 * This method adds a lair to the player's assets, takes the necessary resources from
	 * the player, and puts them back in the stockpile.
	 * 
	 * @param: lair - The label of the lair site being built on.
	 * 
	 * @return: Returns a string indicating the build has been completed.
	 * */
	public String buildLair(String lair) {
		this.player.addLairAsset(lair);
		this.player.takeResource("Cutlass", 1);
		this.player.takeResource("Molasses", 1);
		this.player.takeResource("Goats", 1);
		this.player.takeResource("Wood", 1);
		this.stockpile.update("Cutlass", 1);
		this.stockpile.update("Molasses", 1);
		this.stockpile.update("Goats", 1);
		this.stockpile.update("Wood", 1);
		return ("Done!\n");
	}
	
	/**
	 * This method adds a ship to the player's assets, takes the necessary resources from
	 * the player, and puts them back in the stockpile.
	 * 
	 * @param: ship - The label of the ship site being built on.
	 * 
	 * @return: Returns a string indicating the build has been completed.
	 * */
	public String buildShip(String ship) {
		this.player.addShipAsset(ship);
		this.player.takeResource("Goats", 1);
		this.player.takeResource("Wood", 1);
		this.stockpile.update("Goats", 1);
		this.stockpile.update("Wood", 1);
		return ("Done!\n");
	}

	/**
	 * This method returns a list of the lair sites a player can build on.
	 * */
	public List<String> validLairSites() {
		// Getting the list of lair sites on the board.
		List<String> allLairSites = new ArrayList<String>(this.board.getLairList());
		// Removing the lair sites that have been built on.
		allLairSites.removeAll(this.board.getUsedLairSites());
		List<String> validLairSites = new ArrayList<String>();
		// This for loop checks all of the player's ship assets and adds the 
		// lair sites that are connected to these ship assets to the list that is returned.
		for (String lair : allLairSites) {
			for (String shipSite : this.player.getShipAssets()) {
				if (shipSite.contains(lair)) {
					validLairSites.add(lair);
				}
			}
		}
		return validLairSites;
	}

	/**
	 * This method returns a list of the ship sites a player can build on.
	 * The method works in a similar way to the validLairSites method.
	 * */
	public List<String> validShipSites() {
		List<String> allShipSites = new ArrayList<String>(this.board.getShipSites());
		allShipSites.removeAll(this.board.getUsedShipSites());
		List<String> freeShipSites = new ArrayList<String>(allShipSites);
		List<String> validShipSites = new ArrayList<String>();
		for (String lair : this.player.getLairAssets()) {
			for (String shipSite : freeShipSites) {
				if (shipSite.contains(lair)) {
					validShipSites.add(shipSite);
				}
			}
		}
		return validShipSites;
	}
}
