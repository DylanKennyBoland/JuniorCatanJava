package model.setup;

import java.util.ArrayList;
import java.util.Scanner;

import model.board.Board;
import model.board.Stockpile;
import model.enums.PlayerEnums;
import model.players.Player;
import model.players.PlayerList;
import view.View;

/**
 * Class for Setting up the Players.
 * The SetupPlayers class is a singleton
 * 
 * @author: Adam Durning & Dylan Boland
 * @Date: 27/12/2021
 * 
 */
public class SetupPlayers {
	// The SetupPlayers variables
	private static SetupPlayers setupPlayers;
	private PlayerList playerList;
	private ArrayList<PlayerEnums> availableColors = new ArrayList<PlayerEnums>();
	private Integer numPlayers;
	private String numPlayersString;
	private ArrayList<Integer> colourIndex = new ArrayList<Integer>();
	private Stockpile stockpile;
	private Board board;
	private View view;
	
    // Gets the instance of the SetupPlayers object
    public static SetupPlayers getInstance(){
    	if(setupPlayers == null){
    		setupPlayers = new SetupPlayers();
        }
        return setupPlayers;
    }
    
    //The SetupPlayers Constructor
	public SetupPlayers() {
		this.playerList = PlayerList.getInstance();
		this.board = Board.getInstance();
		this.view = View.getInstance();
		this.stockpile = board.getStockpile();
	}
	
	/**
	 * This method is used to create all Players in the game.
	 * 
	 * @param: input - A scanner object used to get the Player's input. 
	 * */
	public void CreateAllPlayers(Scanner input) {
		boolean playersSelected = false;
		// Gets the number of people playing the game.
		this.numPlayers = getNumPlayers(input);

		// A loop that creates each player.
		while (!playersSelected) {
			for(int i = 0; i < numPlayers; i++) {
				CreatePlayer(input);
			}
			playersSelected = true;
		}
		//Removing the resources from the stockpile that are given to the Players at the start of the game.
		this.stockpile.updateStockPile("Wood", -this.numPlayers);
		this.stockpile.updateStockPile("Molasses", -this.numPlayers);
	}

	/**
	 * This method is used to create an individual player.
	 * 
	 * @param: input - A scanner object used to get the Player's input. 
	 * */
	private void CreatePlayer(Scanner input) {
		this.view.display("Creating new player. Please enter your name:");
		String name = input.nextLine();
		// Getting the players age.
		String age = getPlayerAge(input);
		//Getting the players Colour.
		String colour = getPlayerColour(input);
		// Adding the player to the player list.
		playerList.addPlayer(new Player(name, PlayerEnums.valueOf(colour), age));
	}
	
	// This method sorts the player list by age.
	public void organizePlayers() {
		this.playerList.sortByAge();
	}
	
	/**
	 * This method is used to get a player's age.
	 * 
	 * @param: input - A scanner object used to get the Player's input.
	 * 
	 *  @return: Returns a string representing a players age.
	 * */
	private String getPlayerAge(Scanner input) {
		boolean validAge = false;
		String age = " ";
		this.view.display("\nWhat is your age?");
		while(!validAge) {
			age = input.nextLine();
			// The try-catch expression is used to ensure the user inputs a a number.
			try {
				int ageInt = Integer.parseInt(age);
				if (ageInt < 0) {
					validAge = false;
					this.view.display("Number must be non negative. Try again.");
				} else {
					validAge = true;
				}
			} catch (NumberFormatException e) {
				this.view.display("That is not a number. Try again.");
				validAge = false;
			}
		}
		return age;
	}
	
	/**
	 * This method is used to get a player's colour.
	 * 
	 * @param: input - A scanner object used to get the Player's input. 
	 * 
	 * @return: Returns a string representation of the chosen colour.
	 * */
	private String getPlayerColour(Scanner input) {
		this.view.display("\nWhat colour would you like: ");
		boolean chosenColour = false;
		while(!chosenColour) {
			this.view.printAvailableColours(this.availableColors, this.colourIndex);
			String choice = "5";
			int choiceInt = 5;
			boolean validChoice = false;
			while(!validChoice) {
				// The try-catch expression is used to ensure user enters a number.
				try {
					choice = input.nextLine();
					choiceInt = Integer.parseInt(choice);
				} catch (NumberFormatException e) {
					this.view.display("That is not a number");
					choiceInt = 5;
				}
				if(this.colourIndex.contains(choiceInt)) {
					validChoice = true;
				} else {
					this.view.display("\nThat is not a valid choice. Try again.");
				}
			}
			// Updating the available colours according to the chosen colour.
			switch (choice) {
			case "1" :
				chosenColour = updateColourList(availableColors.indexOf(PlayerEnums.BLUE)); return "BLUE";
			case "2":
				chosenColour = updateColourList(availableColors.indexOf(PlayerEnums.RED)); return "RED";
			case "3":
				chosenColour = updateColourList(availableColors.indexOf(PlayerEnums.ORANGE)); return "ORANGE";
			case "4":
				chosenColour = updateColourList(availableColors.indexOf(PlayerEnums.WHITE)); return "WHITE";
			}
		}
		return " ";
	}
	
	/**
	 * This method is used to get the number of players in the game.
	 * 
	 * @param: input - A scanner object used to get the Player's input. 
	 * 
	 * @return: Returns an integer of the number of players.
	 * */
	private Integer getNumPlayers(Scanner input) {
		this.view.display("How many people are playing the game? (3 or 4): ");
		boolean validNumber = false;
		while(!validNumber) {
			numPlayersString = input.nextLine();
			try {
				numPlayers = Integer.parseInt(numPlayersString);
			} catch (NumberFormatException e) {
				numPlayers = 0;
			}
			// Checking the number entered is a valid number (i.e. between 3 and 4).
			validNumber = isValidNumber(numPlayers);
		}
		return numPlayers;
	}
	
	/**
	 * This method checks that the chosen number of players is valid and
	 * sets up the colours available to pick according to the number of players.
	 * (The colour WHITE is not available in a 3 player game.)
	 * 
	 * @param: num - The number being checked. 
	 * 
	 * @return: Returns a boolean indicating if the input number is valid.
	 * */
	private boolean isValidNumber(Integer num) {
		if(num < 3 || num > 4) {
			this.view.display("Not a valid number. Must be 3 or 4. Try again.");
			return false;
		} else if (num == 3) {
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
	
	/**
	 * This method updates the availableColours and colourIndex list. 
	 * 
	 * @param: index - The number of the chosen colour. 
	 * 
	 * @return: Returns a boolean indicating the lists have been updated.
	 * */
	private boolean updateColourList(int index) {
		if(availableColors.size() == 1) {
			return true;
		}
		this.availableColors.remove(index);
		this.colourIndex.remove(index);
		return true;
	}
}
