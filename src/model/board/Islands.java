package model.board;

import java.util.ArrayList;
import java.util.List;

import model.enums.IslandEnums;
import model.enums.ResourceEnums;

/**
 * Class for the Island Objects for the Board
 * 
 * @author: Adam Durning & Dylan Boland
 * @Date: 27/12/2021
 * 
 */


public class Islands{
	// Variable Setup
	private char name;
	private int diceNumber;
	private IslandEnums islandType;
	private List<String> attachedLairs;
	private List<String> attachedShipSites = new ArrayList<String>();
	// The ghostCaptain variable indicates if the island has the ghostCaptain.
	private boolean ghostCaptain = false;
	
	/**
	 *Constructor for the Islands object
	 *
	 *@param: name - The name of the island.
	 *@param: diceNumber - The associated number for that island for producing resources.
	 *@param: islandType - 1 of 6 Island types {PASTURES, CAVES, RIVERS, SUGAR, FOREST, SPOOKY}.
	 *					   Determines what resource the island will produce.
	 *@param: attachedLairs - A list of the lairs that surround the Island.
	 *						  This lairs must be ordered in a clockwise direction around the board.
	 *						  The lair names are numbers as labeled in the board.jpg file and the string
	 *						  must be formatted as " NUMBER " i.e. with a space before and after the number.
	 *						  This is needed for checking the players assets in the Player Class.
	 */
	public Islands(char name, int diceNumber, IslandEnums islandType, List<String> attachedLairs) {
		this.name = name;
		this.diceNumber = diceNumber;
		this.islandType = islandType;
		this.attachedLairs = new ArrayList<String>(attachedLairs);
		setAttachedShipSites();
		initializeGhostCaptain();
	}

	// A method for returning if the Island has the ghost captain.
	public boolean hasGhostCaptain() {
		return ghostCaptain;
	}
	
	/**
	 * A method for setting the variable ghostCaptain
	 * 
	 * @param: setGhostCaptain - The value of this input boolean is assigned to the ghostCaptain variable
	 */
	public void setGhostCaptain(boolean setGhostCaptain) {
		this.ghostCaptain = setGhostCaptain;
	}


	/** A method for initializing the ghostCaptain variable at the start of the game.
	 * If the islandType is SPOOKY then this is the island that should have the ghost captain
	 * at the start of the game.*/
	public void initializeGhostCaptain() {
		if (this.islandType.equals(IslandEnums.SPOOKY)) {
			this.ghostCaptain = true;
		} else {
			this.ghostCaptain = false;
		}
	}
	
	/**A method that creates a list of the ship sites that surround the Island. 
	 * The method uses the attachedLairSites parameter of the island to create the names
	 * of the ship sites. The List of Lair sites is given in a clockwise order around the
	 * island and the ship sites are created as follows.
	 * e.g	attachedLairSites = [" 1 "," 2 "," 3 "," 4 "," 5 "," 6 "]
	 * 		attachedShipSites = [" 1 - 2 "," 2 - 3 "," 3 - 4 "," 4 - 5 "," 5 - 6 "," 6 - 1 "]
	 */
	private void setAttachedShipSites() {
		List<String> lairs = new ArrayList<String>();
		for (int i = 0; i < this.attachedLairs.size(); i++) {
			if (i == (this.attachedLairs.size() - 1)) {
				if (this.attachedLairs.size() == 6) {
					String shipSite = this.attachedLairs.get(0) + "-" + this.attachedLairs.get(i);
					attachedShipSites.add(shipSite);
				}
			} else {

				lairs = greaterThan(this.attachedLairs.get(i), this.attachedLairs.get(i + 1));
				String shipSite = lairs.get(0) + "-" + lairs.get(1);
				attachedShipSites.add(shipSite);
			}
		}
	}
	
	/**This method sorts two input strings according to the size of
	 * the number in the string. e.g. List = greaterThan(" 2 "," 1 ")
	 * 								  List == [" 1 "," 2 "]
	 * This is used in the setAttachedShipSites method to ensure the name of the ship sites always
	 * begins with the lair that has the smaller number. Makes checking the Player assets easier.
	 * 
	 * @param: src - String of first lair
	 * @param: dst - String of second lair
	 * 
	 * @return: A list of the input strings, sorted by the size of the numbers in the strings.
	 * */
	
	public List<String> greaterThan(String src, String dst) {
		if (Integer.parseInt(src.trim()) > Integer.parseInt(dst.trim())) {
			String tmpDst = src;
			src = dst;
			dst = tmpDst;
		}
		List<String> lairs = new ArrayList<String>();
		lairs.add(src);
		lairs.add(dst);
		return lairs;
	}
	
	//This getter method gets the type of resource an Island produces according to the islandType
	public String getIslandResource() {
		switch(this.islandType){
		case FOREST: return ResourceEnums.WOOD.getType();
		case CAVES: return ResourceEnums.CUTLASS.getType();
		case RIVERS: return ResourceEnums.GOLD.getType();
		case SUGAR: return ResourceEnums.MOLASSES.getType();
		case PASTURES: return ResourceEnums.GOATS.getType();
		case SPOOKY: return " ";
		default : return " ";
		}
	}
	
	// Some getter methods for returning object variables.
	public int getDiceNumber() {
		return diceNumber;
	}

	public IslandEnums getIslandType() {
		return islandType;
	}
	
	public List<String> getAttachedLairs(){
		return this.attachedLairs;
	}

	public char getName() {
		return this.name;
	}
	
	public List<String> getAttachedShipSites(){
		return this.attachedShipSites;
	}
	
	// A toString method for displaying the island variables.
	public String toString() {
		return "\nDice Number: " + this.diceNumber + "\nIsland Type: " + this.islandType + "\nLair Sites: "
				+ this.attachedLairs + "\nShip Sites: " + this.attachedShipSites + "\nGhost Captain: "
				+ this.ghostCaptain;
	}

}
