package model.gameplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.board.*;
import model.enums.*;


public class PlayerTurn {
	private Player          player;
	private Board           board;
	private Scanner         inputScanner;
	private CocoEnums		cocoTile;
	private ResourceEnums	resource;
	private PlayerEnums		playerColour;
	private IslandEnums		islandType;
	
	
	public PlayerTurn(Player player, Scanner inputScanner) {
		this.player = player;
		this.board = Board.getInstance();
		this.inputScanner = inputScanner;
	}
	
	public void startTurn() {
		boolean turnOver = false;
		while(!turnOver) {
			displayOptions();
			
			String input = inputScanner.nextLine();
			
			switch(input) {
				case "1": turnOver = true; break;
				case "2": build(); break;
			}
		}
	}
	
	private void build() {
		System.out.println(this.player.getName() + " is building");
	};
	
	
	private void displayOptions() {
		List<String> options = new ArrayList<String>();
		options.add("End Turn");
		options.add("Build");
		int i = 1;
		for(String option: options) {
			System.out.println("\n" + i + " : " + option);
			i++;
		}
	}
}
