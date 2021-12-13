package model.setup;
import model.board.*;
import java.util.ArrayList;
import java.util.Scanner;
import model.enums.*;
import model.players.Player;
import model.players.PlayerList;
import view.View;

public class SetupPlayers {

	private PlayerList playerList;
	private ArrayList<PlayerEnums> availableColors = new ArrayList<PlayerEnums>();
	private Integer numPlayers;
	private String numPlayersString;
	private ArrayList<Integer> colourIndex = new ArrayList<Integer>();
	private Stockpile stockpile;
	private Board board;
	private View view;
	
	public SetupPlayers() {
		this.playerList = PlayerList.getInstance();
		this.board = Board.getInstance();
		this.view = View.getInstance();
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
		//this.view.display("Players : " + playerList.toString());
	}

	private void CreatePlayer(Scanner player) {
		this.view.display("Creating new player. Please enter your name:");
		String name = player.nextLine();
		String age = getPlayerAge(player);

		String colour = getPlayerColour(player);
		playerList.addPlayer(new Player(name, PlayerEnums.valueOf(colour), age));
	}
	
	public void organizePlayers() {
		this.playerList.sortByAge();
	}
	
	private String getPlayerAge(Scanner player) {
		boolean validAge = false;
		String age = " ";
		this.view.display("\nWhat is your age?");
		while(!validAge) {
			age = player.nextLine();
			try {
				int ageInt = Integer.parseInt(age);
				if(ageInt < 0) {
					validAge = false;
					this.view.display("Number must be non negative. Try again.");
				}else {validAge = true;}
			}
			catch(NumberFormatException e) {
				this.view.display("That is not a number. Try again.");
				validAge = false;
			}
		}
		return age;
	}
	
	private String getPlayerColour(Scanner player) {
		this.view.display("\nWhat colour would you like: ");
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
					this.view.display("That is not a number");
					choiceInt = 5;
				}
				if(this.colourIndex.contains(choiceInt)/**choiceInt <= this.availableColors.size() && choiceInt > 0*/) {
					validChoice = true;
				}else {
					this.view.display("\nThat is not a valid choice. Try again.");
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
	
	private Integer getNumPlayers(Scanner player) {
		this.view.display("How many people are playing the game? (3 or 4): ");
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
		if(num < 0 || num > 4) {
			this.view.display("Not a valid number. Must be 3 or 4. Try again.");
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
	
	private boolean isValidColour(int index) {
		if(availableColors.size() == 1) {
			return true;
		}
		this.availableColors.remove(index);
		this.colourIndex.remove(index);
		return true;
	}
	
	private void printAvailableColors() {
		int i = 0;
		for(PlayerEnums colour : availableColors) {
            this.view.display("\n" + colourIndex.get(i) + " : " +  colour.getColour());
            i++;
		}
	}
	
	public void setNumPlayers(int num) {
		this.numPlayers = num;
	}
}
