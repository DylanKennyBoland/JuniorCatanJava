package controller;

import java.util.Scanner;

import model.board.Board;
import model.board.Player;
import model.board.PlayerList;
import model.gameplay.PlayerTurn;

public class Controller {
	private Player player;
	private PlayerList playerList;
	private Scanner inputScanner;
	private boolean gameOver;
	private static Controller controller;
	
    public static Controller getInstance(){
        if(controller == null){
            controller = new Controller();
        }
        return controller;
    }
    
    public Controller() {
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
