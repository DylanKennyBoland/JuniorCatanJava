package model.board;
import java.util.*;

import model.enums.IslandEnums;
import model.enums.ResourceEnums;

public class Islands{
	private int diceNumber;
	private IslandEnums islandType;
	private List<String> attachedLairs;
	private List<String> attachedShipSites = new ArrayList<String>();
	private boolean ghostCaptain = false;
	
	
	public Islands(int diceNumber, IslandEnums islandType, List<String> attachedLairs) {
		this.diceNumber = diceNumber;
		this.islandType = islandType;
		this.attachedLairs = new ArrayList<String>(attachedLairs);
		setAttachedShipSites();
		initializeGhostCaptain();
	}

	public int getDiceNumber() {
		return diceNumber;
	}

	public IslandEnums getIslandType() {
		return islandType;
	}
	
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

	public boolean hasGhostCaptain() {
		return ghostCaptain;
	}
	
	public void setGhostCaptain(boolean setGhostCaptain) {
		this.ghostCaptain = setGhostCaptain;
	}
	
	public List<String> getAttachedLairs(){
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
		List<String> lairs = new ArrayList<String>(); 
		for (int i = 0; i < this.attachedLairs.size(); i++) {
			if (i == (this.attachedLairs.size()-1)){
				if(this.attachedLairs.size() == 6) {
					String shipSite = " " + this.attachedLairs.get(0) + " - " + this.attachedLairs.get(i) + " ";
					attachedShipSites.add(shipSite);
				}
			}else {

			lairs = greaterThan(this.attachedLairs.get(i),this.attachedLairs.get(i+1));
			String shipSite = " " + lairs.get(0) + " - " + lairs.get(1) + " ";
			attachedShipSites.add(shipSite);
			}
		}
	}
	
	public List<String> greaterThan(String src, String dst) {
		if(Integer.parseInt(src) > Integer.parseInt(dst)) {
			String tmpDst = src;
			src = dst;
			dst = tmpDst;
		}
		List<String> lairs = new ArrayList<String>();
		lairs.add(src);
		lairs.add(dst);
		return lairs;
	}
	
	public List<String> getAttachedShipSites(){
		return this.attachedShipSites;
	}
	
	public String toString() {
		return "\nDice Number: " + this.diceNumber + "\nIsland Type: " + this.islandType +
			   "\nLair Sites: " + this.attachedLairs + "\nShip Sites: " + this.attachedShipSites + "\nGhost Captain: " + this.ghostCaptain;
	}
	
}	

