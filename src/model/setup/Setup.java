package model.setup;

import java.util.Scanner;

/**
 * Class for Setting up the game.
 * The Setup class is a singleton
 * 
 * @author: Adam Durning & Dylan Boland
 * @Date: 27/12/2021
 * 
 */
public class Setup {
	// The Setup variables.
    private static Setup setup;
    private SetupBoard setupBoard;
    private SetupPlayers setupPlayers;
    
    // Gets the instance of the Setup object
    public static Setup getInstance(){
    	if(setup == null){
    		setup = new Setup();
        }
        return setup;
    }
   
    // The Setup constructor.
    private Setup() {	
    	this.setupPlayers = SetupPlayers.getInstance();
    	this.setupBoard = SetupBoard.getInstance();
    }
    
    // This method calls the necessary methods to set up the game.
    public void setupGame(Scanner inputScanner) {
    	setupPlayers.CreateAllPlayers(inputScanner);
    	setupPlayers.organizePlayers();
    	setupBoard.createIslands();
    }
}
