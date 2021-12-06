package model.setup;
import model.board.*;
import java.util.ArrayList;
import java.util.Scanner;
import model.enums.*;

public class SetupPlayers {

	private PlayerList playerList;
	private ArrayList<PlayerEnums> availableColors = new ArrayList<PlayerEnums>();
	private Integer numPlayers;
	private String numPlayersString;
	private String colour;
	
	public SetupPlayers() {
		this.playerList = PlayerList.getInstance();
		this.availableColors.add(PlayerEnums.BLUE);
		this.availableColors.add(PlayerEnums.RED);
		this.availableColors.add(PlayerEnums.WHITE);
		this.availableColors.add(PlayerEnums.ORANGE);
	}
	
	public void organizePlayers() {
		this.playerList.sortByAge();
	}
	
	private void CreatePlayer(Scanner player) {
		System.out.println("\nCreating new player. Please enter your name:");
		String name = player.nextLine();
		System.out.println("\n What is your age?");
		String age = player.nextLine();
		String colour = getPlayerColour(player);
		playerList.addPlayer(new Player(name, PlayerEnums.valueOf(colour), age));
	}
	
	private String getPlayerColour(Scanner player) {
		System.out.println("\nWhat colour would you like: ");
		boolean validColour = false;
		while(!validColour) {
			printAvailableColors();
			colour = player.nextLine().toUpperCase();
			validColour = isValidColour(colour);
		}
		return colour;
	}
	
	public void CreateAllPlayers(Scanner player) {
		boolean playersSelected = false;
		numPlayers = getNumPlayers(player);

		while (!playersSelected) {
			for(int i = 0; i < numPlayers; i++) {
				CreatePlayer(player);
			}
			playersSelected = true;
		}
		//System.out.println("Players : " + playerList.toString());
	}
	
	private Integer getNumPlayers(Scanner player) {
		System.out.println("How many people are playing the game? (3 or 4): ");
		boolean validNumber = false;
		while(!validNumber) {
			numPlayersString = player.nextLine();
			numPlayers = Integer.parseInt(numPlayersString);
			validNumber = isValidNumber(numPlayers);
		}
		return numPlayers;
	}
	
	private boolean isValidNumber(Integer num) {
		if(num < 3 || num > 4) {
			return false;
		} else {
			return true;
		}
	}
	
	private void printAvailableColors() {
		int i = 1;
		for(PlayerEnums colour : availableColors) {
            System.out.println("\n" + i + " : " +  colour.getColour());
            i++;
        }
	}
	
	private boolean isValidColour(String colour) {
		if(availableColors.toString().contains(colour)){
			availableColors.remove(availableColors.indexOf(PlayerEnums.valueOf(colour)));
			return true;
		} else {
			return false;
		}
	}
	
	public void setNumPlayers(int num) {
		this.numPlayers = num;
	}
}
