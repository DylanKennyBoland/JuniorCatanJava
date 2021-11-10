package board;
import java.util.*;
import enums.IslandEnums;

public class Islands{
	private int diceNumber;
	private IslandEnums islandType;
	private List<Integer> attachedLairs = new ArrayList<Integer>();
	private boolean ghostCaptain = false;
	
	
	public Islands(int diceNumber, IslandEnums islandType, List<Integer> lairs) {
		this.diceNumber = diceNumber;
		this.islandType = islandType;
		this.attachedLairs = lairs;
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
	
	public String toString() {
		return "\nDice Number: " + this.diceNumber + "\nIsland Type: " + this.islandType +
				"\nGhost Captain: " + this.ghostCaptain;
	}
	
}	

