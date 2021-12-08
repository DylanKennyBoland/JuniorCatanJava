package main;

import java.util.Scanner;

import controller.Controller;
import controller.PlayerTurn;
import model.board.*;
import model.enums.PlayerEnums;
import model.setup.*;
import view.View;


public class Main {
	public static void main(String[] args) {
		Scanner inputScanner = new Scanner(System.in);
		View view = View.getInstance();
		Controller.getInstance(Setup.getInstance(), inputScanner).playGame(inputScanner);

        inputScanner.close();
	}
}
