package model.setup;

import java.util.ArrayList;
import java.util.Scanner;
import model.enums.*;
import model.pieces.*;

public class SetupPlayers {

	private ArrayList<PlayerEnums> availableColors;
	public ArrayList<Player> playerList;
	private boolean validColour = false;
	
	public SetupPlayers() {}
	
	public void CreatePlayer(Scanner player) {
		System.out.println("\nCreating new player. Please enter your name: ");
		String name = player.nextLine();
		System.out.println("\nWhat color would you like: ");
		printAvailableColors();
		String colour = player.nextLine();
		isValidColour(colour, name);
		playerList.add(new Player(name, colour));
	}
	
	private void CreateAllPlayers(Scanner player) {
		System.out.println("\nHow many people are playing the game? (3 or 4): ");
		int num_players = player.nextInt();
		for(int i = 0; i < num_players; i++) {
			CreatePlayer(player);
		}
	}
	
	private void printAvailableColors() {
		for(PlayerEnums colour : availableColors) {
            System.out.println("\n" + colour.getColour());
        }
	}
	
	private void isValidColour(String colour, String name) {
		while(!validColour) {
			if(availableColors.contains(colour)){
				System.out.println("\n" + name + " has been registerd.");
				availableColors.remove(availableColors.indexOf(colour));
				validColour = true;
			} else {
				System.out.println("\nThat color is not available, please choose again!"
						+ "\nThe available colors are:");
				printAvailableColors();
			}
		}
	}
}
