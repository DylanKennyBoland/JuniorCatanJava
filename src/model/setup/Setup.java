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
    	this.setupBoard = new SetupBoard();
    	this.setupPlayers = new SetupPlayers();
    }
    
    public void setupGame(Scanner inputScanner) {
    	setupBoard.startingBoard();
    	setupPlayers.CreateAllPlayers(inputScanner);
    	setupPlayers.organizePlayers();
    }
}
