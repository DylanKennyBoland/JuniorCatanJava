package model.setup;

import java.util.Scanner;

public class Setup {
    private static Setup setup;
    private SetupBoard setupBoard;
    private SetupPlayers setupPlayers;
    
    public static Setup getInstance(){
    	if(setup == null){
    		setup = new Setup();
        }
        return setup;
    }
      
    private Setup() {	
    	this.setupPlayers = new SetupPlayers();
    	this.setupBoard = new SetupBoard();
    }
    
    public void setupGame(Scanner inputScanner) {
    	setupPlayers.CreateAllPlayers(inputScanner);
    	setupPlayers.organizePlayers();
    	setupBoard.startingBoard();
    }
}
