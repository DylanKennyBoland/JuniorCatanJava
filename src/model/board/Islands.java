package model.board;
import java.util.*;

import model.enums.IslandEnums;

public class Islands{
	private int diceNumber;
	private IslandEnums islandType;
	private List<Integer> attachedLairs = new ArrayList<Integer>();
	private List<String> attachedShipSites = new ArrayList<String>();
	private boolean ghostCaptain = false;
	
	
	public Islands(int diceNumber, IslandEnums islandType, List<Integer> lairs) {
		this.diceNumber = diceNumber;
		this.islandType = islandType;
		this.attachedLairs = lairs;
		setAttachedShipSites();
		initializeGhostCaptain();
	}

	public int getDiceNumber() {
		return diceNumber;
	}

	public IslandEnums getIslandType() {
		return islandType;
	}

	public boolean hasGhostCaptain() {
		return ghostCaptain;
	}
	
	public void setGhostCaptain(boolean setGhostCaptain) {
		this.ghostCaptain = setGhostCaptain;
	}
	
	public List<Integer> getAttachedLairs(){
		return this.attachedLairs;
	}
	
	public void initializeGhostCaptain() {
		if (this.islandType.equals(IslandEnums.SPOOKY)) {
			this.ghostCaptain = true;
		}else {
			this.ghostCaptain = false;
		}
	}
	
	private void setAttachedShipSites() {
		for (int i = 0; i < this.attachedLairs.size(); i++) {
			if (i == (this.attachedLairs.size()-1)){
				@SuppressWarnings("unused")
				String shipSite = String.valueOf(this.attachedLairs.get(i)) + '-' + String.valueOf(this.attachedLairs.get(0));
			}else {
			String shipSite = String.valueOf(this.attachedLairs.get(i)) + '-' + String.valueOf(this.attachedLairs.get(i+1));
			attachedShipSites.add(shipSite);
			}
		}
	}
	
	public List<String> getAttachedShipSites(){
		return this.attachedShipSites;
	}
	
	public String toString() {
		return "\nDice Number: " + this.diceNumber + "\nIsland Type: " + this.islandType +
			   "\nLair Sites: " + this.attachedLairs + "\nShip Sites: " + this.attachedShipSites + "\nGhost Captain: " + this.ghostCaptain;
	}
	
}	

