package main;

import java.util.Scanner;
import controller.Controller;
import model.setup.*;
import view.View;

/**
 * The main class of the Game.
 * Run this class to play Catan Junior!
 * 
 * @author: Adam Durning & Dylan Boland
 * @Date: 27/12/2021
 * 
 */
public class Main {
	public static void main(String[] args) {
		// Creating a Scanner object for getting user input.
		Scanner inputScanner = new Scanner(System.in);
		// Creating the View.
		View view = View.getInstance();
		// Creating the Controller and starting the game.
		Controller.getInstance(view, Setup.getInstance(), inputScanner).playGame(inputScanner);
		// Closing the scanner object when the game is over.
        inputScanner.close();
	}
}
