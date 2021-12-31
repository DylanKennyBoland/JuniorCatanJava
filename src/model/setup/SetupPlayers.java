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
	private ArrayList<PlayerEnums> availableColours = new ArrayList<PlayerEnums>();
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
		this.stockpile.update("Wood", -this.numPlayers);
		this.stockpile.update("Molasses", -this.numPlayers);
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
	public String getPlayerAge(Scanner input) {
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
	public String getPlayerColour(Scanner input) {
		this.view.display("\nWhat colour would you like: ");
		boolean chosenColour = false;
		while(!chosenColour) {
			this.view.printAvailableColours(this.availableColours, this.colourIndex);
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
				chosenColour = updateColourList(availableColours.indexOf(PlayerEnums.BLUE));
				this.view.display("You have chosen the colour BLUE");
				return "BLUE";
			case "2":
				chosenColour = updateColourList(availableColours.indexOf(PlayerEnums.RED)); 
				this.view.display("You have chosen the colour RED");
				return "RED";
			case "3":
				chosenColour = updateColourList(availableColours.indexOf(PlayerEnums.ORANGE));
				this.view.display("You have chosen the colour ORANGE");
				return "ORANGE";
			case "4":
				chosenColour = updateColourList(availableColours.indexOf(PlayerEnums.WHITE));
				this.view.display("You have chosen the colour WHITE");
				return "WHITE";
			}
		}
		return " Something went wrong! ";
	}
	
	/**
	 * This method is used to get the number of players in the game.
	 * 
	 * @param: input - A scanner object used to get the Player's input. 
	 * 
	 * @return: Returns an integer of the number of players.
	 * */
	public int getNumPlayers(Scanner input) {
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
			validNumber = isValidNumber(numPlayers, 3, 4);
		}
		setColourList(numPlayers);
		return numPlayers;
	}
	
	/**
	 * This method checks that the integer num is between a lower and upper limit
	 * 
	 * @param: num - The number being checked. 
	 * @param lowerLim - The lower limit of the number.
	 * @param upperLim - The upper limit of the number.
	 * 
	 * @return: Returns a boolean indicating if the input number is between the limits.
	 * */
	public boolean isValidNumber(Integer num, Integer lowerLim, Integer upperLim) {
		if(num < lowerLim || num > upperLim) {
			this.view.display("Not a valid number. Must be between " + lowerLim  + " and " + upperLim);
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * This method sets the colourIndex and availableColours according to the number
	 * of Players in the game.
	 * 
	 * @param: numPlayers - Number of Players in the game.
	 * */
	public void setColourList(Integer numPlayers) {
		switch(numPlayers) {
		case 3:
			this.view.display("You have chosen 3 players");
			this.colourIndex.add(1);
			this.colourIndex.add(2);
			this.colourIndex.add(3);
			this.availableColours.add(PlayerEnums.BLUE);
			this.availableColours.add(PlayerEnums.RED);
			this.availableColours.add(PlayerEnums.ORANGE);
			break;
		case 4:
			this.view.display("You have chosen 4 players");
			this.colourIndex.add(1);
			this.colourIndex.add(2);
			this.colourIndex.add(3);
			this.colourIndex.add(4);
			this.availableColours.add(PlayerEnums.BLUE);
			this.availableColours.add(PlayerEnums.RED);
			this.availableColours.add(PlayerEnums.ORANGE);
			this.availableColours.add(PlayerEnums.WHITE);
			break;
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
		if(availableColours.size() == 1) {
			return true;
		}
		this.availableColours.remove(index);
		this.colourIndex.remove(index);
		return true;
	}
}
