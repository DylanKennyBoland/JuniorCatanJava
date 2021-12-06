package model.gameplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.board.Board;
import model.board.Islands;
import model.board.Marketplace;
import model.board.Player;
import model.board.PlayerList;
import model.enums.CocoEnums;
import model.enums.IslandEnums;
import model.enums.PlayerEnums;
import model.enums.ResourceEnums;

public class PlayerTurn {
	private Player player;
	private Board board;
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
		this.islandList = board.getIslands();
		this.inputScanner = inputScanner;
		this.buildOptions = new Build(player, inputScanner);
		this.tradeOptions = new Trade(player, inputScanner);
		this.playerList = PlayerList.getInstance();
		rollDice();
	}

	private void rollDice() {
		Integer roll = random.nextInt(6) + 1;
		for (Islands island : islandList) {
			if (island.getDiceNumber() == roll) {
				for (Player player : playerList.getList()) {
					List<String> playerLairAssets = player.getLairAssets();
					List<String> playerShipAssets = player.getShipAssets();
					List<String> islandLairs = island.getAttachedLairs();
					if (!Collections.disjoint(playerLairAssets, islandLairs)) {
						Integer numOfAttachedLairs = playerLairAssets.stream().distinct().filter(islandLairs::contains)
								.collect(Collectors.toList()).size();
						player.giveResource(island.getIslandType().toString(), numOfAttachedLairs);
					}
				}
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
		tradeOptionsList.add("Go Back");
		Marketplace marketplace = board.getMarketplace();
		while (!finishedTrading) {
			System.out.println("\nWhat would you like to trade with?");
			displayOptions(tradeOptionsList);
			// String buildInput = inputScanner.nextLine();
			switch (inputScanner.nextLine()) {
			case "1":
				if (tradeOptions.canTradeWithMarketplace()) {
					tradeOptions.tradeMarketplace();
				} else {
					System.out.println("You have already traded with the Marketplace");
				}
				;
				break;
			case "2":
				tradeOptions.tradeStockpile();
				break;
			case "3":
				viewResources();
				break;
			case "4":
				System.out.println(marketplace.toString());
				break;
			case "5":
				finishedTrading = true;
				break;
			}
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
