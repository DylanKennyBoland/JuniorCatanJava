package model.setup;
import model.board.*;
import java.util.ArrayList;
import java.util.Scanner;
import model.enums.*;
import model.players.Player;
import model.players.PlayerList;

public class SetupPlayers {

	private PlayerList playerList;
	private ArrayList<PlayerEnums> availableColors = new ArrayList<PlayerEnums>();
	private Integer numPlayers;
	private String numPlayersString;
	private String colour;
	private ArrayList<Integer> colourIndex = new ArrayList<Integer>();
	private Stockpile stockpile;
	private Board board;
	
	public SetupPlayers() {
		this.playerList = PlayerList.getInstance();
	}
	
	public void organizePlayers() {
		this.playerList.sortByAge();
	}
	
	private void CreatePlayer(Scanner player) {
		System.out.print("Creating new player. Please enter your name:");
		String name = player.nextLine();
		String age = getPlayerAge(player);

		String colour = getPlayerColour(player);
		playerList.addPlayer(new Player(name, PlayerEnums.valueOf(colour), age));
	}
	private String getPlayerAge(Scanner player) {
		boolean validAge = false;
		String age = " ";
		System.out.println("\nWhat is your age?");
		while(!validAge) {
			age = player.nextLine();
			try {
				int ageInt = Integer.parseInt(age);
				if(ageInt < 0) {
					validAge = false;
					System.out.println("Number must be non negative. Try again.");
				}else {validAge = true;}
			}
			catch(NumberFormatException e) {
				System.out.println("That is not a number. Try again.");
				validAge = false;
			}
		}
		return age;
	}
	private String getPlayerColour(Scanner player) {
		System.out.println("\nWhat colour would you like: ");
		boolean validColour = false;
		while(!validColour) {
			printAvailableColors();
			String choice = "5";
			int choiceInt = 5;
			boolean validChoice = false;
			while(!validChoice) {
				try {
					choice = player.nextLine();
					choiceInt = Integer.parseInt(choice);
				}
				catch(NumberFormatException e) {
					System.out.println("That is not a number");
					choiceInt = 5;
				}
				if(this.colourIndex.contains(choiceInt)/**choiceInt <= this.availableColors.size() && choiceInt > 0*/) {
					validChoice = true;
				}else {
					System.out.println("\nThat is not a valid choice. Try again.");
				}
			}
			
			switch (choice) {
			case "1" :
				validColour = isValidColour(availableColors.indexOf(PlayerEnums.BLUE)); return "BLUE";
			case "2":
				validColour = isValidColour(availableColors.indexOf(PlayerEnums.RED)); return "RED";
			case "3":
				validColour = isValidColour(availableColors.indexOf(PlayerEnums.ORANGE)); return "ORANGE";
			case "4":
				validColour = isValidColour(availableColors.indexOf(PlayerEnums.WHITE)); return "WHITE";
			}
		}
		return " ";
	}
	
	public void CreateAllPlayers(Scanner player) {
		boolean playersSelected = false;
		this.numPlayers = getNumPlayers(player);

		while (!playersSelected) {
			for(int i = 0; i < numPlayers; i++) {
				CreatePlayer(player);
			}
			playersSelected = true;
		}
		this.board = Board.getInstance();
		this.stockpile = board.getStockpile();
		this.stockpile.updateStockPile("Wood", -playerList.getNumOfPlayers());
		this.stockpile.updateStockPile("Molasses", -playerList.getNumOfPlayers());
		//System.out.println("Players : " + playerList.toString());
	}
	
	private Integer getNumPlayers(Scanner player) {
		System.out.print("How many people are playing the game? (3 or 4): ");
		boolean validNumber = false;
		while(!validNumber) {
			numPlayersString = player.nextLine();
			try {
				numPlayers = Integer.parseInt(numPlayersString);
			}
			catch(NumberFormatException e){
				numPlayers = 0;
			}
			validNumber = isValidNumber(numPlayers);
		}
		return numPlayers;
	}
	
	private boolean isValidNumber(Integer num) {
		if(num < 3 || num > 4) {
			System.out.println("Not a valid number. Must be 3 or 4. Try again.");
			return false;
		} else if(num == 3){
			this.colourIndex.add(1);
			this.colourIndex.add(2);
			this.colourIndex.add(3);
			this.availableColors.add(PlayerEnums.BLUE);
			this.availableColors.add(PlayerEnums.RED);
			this.availableColors.add(PlayerEnums.ORANGE);
			return true;
		} else {
			this.colourIndex.add(1);
			this.colourIndex.add(2);
			this.colourIndex.add(3);
			this.colourIndex.add(4);
			this.availableColors.add(PlayerEnums.BLUE);
			this.availableColors.add(PlayerEnums.RED);
			this.availableColors.add(PlayerEnums.ORANGE);
			this.availableColors.add(PlayerEnums.WHITE);
			return true;
		}
	}
	
	private void printAvailableColors() {
		int i = 0;
		for(PlayerEnums colour : availableColors) {
            System.out.println("\n" + colourIndex.get(i) + " : " +  colour.getColour());
            i++;
		}
	}
	
	private boolean isValidColour(int index) {
		if(availableColors.size() == 1) {
			return true;
		}
		this.availableColors.remove(index);
		this.colourIndex.remove(index);
		return true;
	}
	
	public void setNumPlayers(int num) {
		this.numPlayers = num;
	}
}
