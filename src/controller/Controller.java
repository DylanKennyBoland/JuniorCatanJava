package controller;

import java.util.Scanner;

import model.board.Board;
import model.players.Player;
import model.players.PlayerList;
import model.setup.Setup;

public class Controller {
	private Player player;
	private PlayerList playerList;
	private Scanner inputScanner;
	private Setup setup;
	private boolean gameOver;
	private static Controller controller;
	
    public static Controller getInstance(Setup setupInstance, Scanner inputScanner){
        if(controller == null){
            controller = new Controller(setupInstance, inputScanner);
        }
        return controller;
    }
    
    public Controller(Setup setupInstance, Scanner inputScanner) {
    	setupInstance.setupGame(inputScanner);
    	this.playerList = PlayerList.getInstance();
    	this.gameOver = false;
    	
    }
    
    public void playGame(Scanner inputScanner) {
    	
    	PlayerTurn currentTurn;
    	while(!gameOver) {
    		for(Player player: playerList.getList()) {
    			currentTurn = new PlayerTurn(player, inputScanner);
    			currentTurn.startTurn();
    			if(currentTurn.didPlayerWin()) {
    				System.out.println("you have won the game");
    				gameOver = true;
    				break;
    			}
    		}
    	}
    }
	
	
	
}
