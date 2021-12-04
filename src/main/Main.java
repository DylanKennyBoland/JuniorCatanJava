package main;

import java.util.Scanner;

import model.board.*;
import model.enums.PlayerEnums;
import model.gameplay.PlayerTurn;
import model.setup.*;


public class Main {
	public static void main(String[] args) {
		Scanner inputScanner = new Scanner(System.in);
		
//        SetupPlayers playerSetup = new SetupPlayers();
//        playerSetup.CreateAllPlayers(inputScanner);
		Player player1 = new Player("Adam", PlayerEnums.BLUE);
		player1.giveResource("Wood", 4);
		player1.giveResource("Goats", 1);
		player1.giveResource("Cutlass", 1);
		player1.giveResource("Molasses", 1);
		
        PlayerTurn playerTurn = new PlayerTurn(player1, inputScanner);
		playerTurn.startTurn();
        inputScanner.close();
	}
}
