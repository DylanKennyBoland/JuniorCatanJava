package model.setup;
import model.board.*;
import java.util.ArrayList;
import java.util.Scanner;
import model.enums.*;

public class SetupPlayers {

	private ArrayList<PlayerEnums> availableColors = new ArrayList<PlayerEnums>(4);
	public ArrayList<Player> playerList = new ArrayList<Player>();
	private boolean validColour = false;
	
	public SetupPlayers() {
		for (PlayerEnums playerEnum : PlayerEnums.values()) {
			this.availableColors.add(playerEnum);
		}
//		this.availableColors.add(PlayerEnums.WHITE);
//		this.availableColors.add(PlayerEnums.ORANGE);
//		this.availableColors.add(PlayerEnums.BLUE);
	}
	
	public void CreatePlayer(Scanner player) {
		System.out.println("\nCreating new player. Please enter your name: ");
		player.nextLine();
		String name = player.nextLine();
		System.out.println("\nYou have entered your name as " + name);
		System.out.println("\nWhat color would you like: ");
		printAvailableColors();
		String colour = player.nextLine();
		isValidColour(colour, name);
		playerList.add(new Player(name, PlayerEnums.valueOf(colour)));
	}
	
	public void CreateAllPlayers(Scanner player) {
		System.out.println("\nHow many people are playing the game? (3 or 4): ");
		int num_players = player.nextInt();
		System.out.println("You have declared that there will be " + num_players + " players");
		for(int i = 0; i < num_players; i++) {
			CreatePlayer(player);
		}
	}
	
	private void printAvailableColors() {
		for(PlayerEnums colour : availableColors) {
            System.out.println("\n" + colour.getColour());
        }
	}
	
	//private void isValidColour(String colour, String name) {
	private void isValidColour(String colour, String name) {
		while(!validColour) {
			if(availableColors.contains(PlayerEnums.valueOf(colour))){
				System.out.println("\n" + name + " has been registerd.");
				availableColors.remove(availableColors.indexOf(PlayerEnums.valueOf(colour)));
				validColour = true;
			} else {
				System.out.println("\nThat color is not available, please choose again!"
						+ "\nThe available colors are:");
				printAvailableColors();
			}
		}
	}
}
