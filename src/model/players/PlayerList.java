package model.players;

import java.util.ArrayList;

/**
 * Class for the Playerlist object that stores a list of the players in the game.
 * The PlayerList object is a singleton.
 * 
 * @author: Adam Durning & Dylan Boland
 * @Date: 27/12/2021
 * 
 */
public class PlayerList {
	//Setting up the PlayerList variables
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private static PlayerList thePlayerList;
    
	//This method gets the instance of the PlayerList/ Creates the instance.
	public static PlayerList getInstance(){
        if(thePlayerList == null){
            thePlayerList = new PlayerList();
        }
        return thePlayerList;
    }
	
	/**
	 * This method adds a player to the list of players.
	 * 
	 * @param: player - The player being added to the list.
	 * */
	public void addPlayer(Player player) {
		this.playerList.add(player);
	}
	
	// This method uses the bubble sort algorithm to sort the players by age from youngest to oldest.
	public void sortByAge() {
        int n = this.playerList.size();
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (this.playerList.get(j).getAge() > this.playerList.get(j+1).getAge())
                {
                    // swap arr[j+1] and arr[j]
                    Player tmpPlayer = this.playerList.get(j);
                    this.playerList.set(j, this.playerList.get(j+1));
                    this.playerList.set(j+1, tmpPlayer);
                }
	}
    
	// Some Getter methods of the playerList attributes.
	public Integer getNumOfPlayers() {
		return this.playerList.size();
	}
	
	public ArrayList<Player> getList(){
		return this.playerList;
	}
	
	// Returns a string representation of the playerList.
	public String toString() {
		return this.playerList.toString();
	}
}
