package main;

import java.util.Scanner;

import controller.Controller;
import model.board.*;
import model.enums.PlayerEnums;
import model.gameplay.PlayerTurn;
import model.setup.*;


public class Main {
	public static void main(String[] args) {
		Scanner inputScanner = new Scanner(System.in);
		
		Setup.getInstance().setupGame(inputScanner);
		
		Controller.getInstance().playGame(inputScanner);
//        SetupPlayers playerSetup = new SetupPlayers();
//        playerSetup.CreateAllPlayers(inputScanner);
//		Player player1 = new Player("Adam", PlayerEnums.BLUE);
//		player1.giveResource("Wood", 4);
//		player1.giveResource("Goats", 4);
//		player1.giveResource("Cutlass", 4);
//		player1.giveResource("Molasses", 4);
//		player1.addAsset("5");
//		player1.addAsset(" 5 - 4 ");
//		player1.addAsset("15");
//		player1.addAsset(" 15 - 16 ");
//		
//        PlayerTurn playerTurn = new PlayerTurn(player1, inputScanner);
//		playerTurn.startTurn();
        inputScanner.close();
	}
}
