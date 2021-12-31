package controller;

import java.util.Scanner;

import model.players.Player;
import model.players.PlayerList;
import model.setup.Setup;
import view.View;
/**
 * Class for the Controller Object.
 * 
 * @author: Adam Durning & Dylan Boland
 * @Date: 27/12/2021
 * 
 */
public class Controller {
	// Setting up the Controller variables.
	private PlayerList playerList;
	private View view;
	private boolean gameOver;
	private static Controller controller;
	
	// The getInstance method for the Controller Singleton.
    public static Controller getInstance(View viewInstance, Setup setupInstance, Scanner inputScanner){
        if(controller == null){
            controller = new Controller(viewInstance, setupInstance, inputScanner);
        }
        return controller;
    }
    
    // The Constructor of the Controller.
    public Controller(View viewInstance, Setup setupInstance, Scanner inputScanner) {
    	setupInstance.setupGame(inputScanner);
    	this.view = viewInstance;
    	this.playerList = PlayerList.getInstance();
    	this.gameOver = false;
    	
    }
    
    // This method is the main method for the controller and starts the Game.
    public void playGame(Scanner inputScanner) {
    	PlayerTurn currentTurn;
    	while(!gameOver) {
    		// Looping through the players in the game.
    		for(Player player: playerList.getList()) {
    			// Creating a new turn for the player.
    			currentTurn = new PlayerTurn(player, inputScanner);
    			// Starting the players turn.
    			currentTurn.startTurn();
    			// Ending the game if the player has won.
    			if(currentTurn.didPlayerWin()) {
    				this.view.display("You have won the game!");
    				gameOver = true;
    				break;
    			}
    		}
    	}
    }	
}
