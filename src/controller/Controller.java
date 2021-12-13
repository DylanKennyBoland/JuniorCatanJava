package controller;

import java.util.Scanner;

import model.board.Board;
import model.players.Player;
import model.players.PlayerList;
import model.setup.Setup;
import view.View;

public class Controller {
	private Player player;
	private PlayerList playerList;
	private Scanner inputScanner;
	private Setup setup;
	private View view;
	private boolean gameOver;
	private static Controller controller;
	
    public static Controller getInstance(View viewInstance, Setup setupInstance, Scanner inputScanner){
        if(controller == null){
            controller = new Controller(viewInstance, setupInstance, inputScanner);
        }
        return controller;
    }
    
    public Controller(View viewInstance, Setup setupInstance, Scanner inputScanner) {
    	setupInstance.setupGame(inputScanner);
    	this.view = viewInstance;
    	this.setup = setupInstance;
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
    				this.view.display("you have won the game");
    				gameOver = true;
    				break;
    			}
    		}
    	}
    }
	
	
	
}
