package model.board;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerList {
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private static PlayerList thePlayerList;
    
	public static PlayerList getInstance(){
        if(thePlayerList == null){
            thePlayerList = new PlayerList();
        }
        return thePlayerList;
    }
    
	public Integer getNumOfPlayers() {
		return this.playerList.size();
	}
	
	public void addPlayer(Player player) {
		this.playerList.add(player);
	}
	
	public ArrayList<Player> getList(){
		return this.playerList;
	}
	
	public String toString() {
		return this.playerList.toString();
	}
	
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
	
}
