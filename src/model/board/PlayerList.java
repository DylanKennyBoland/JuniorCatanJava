package model.board;

import java.util.ArrayList;

public class PlayerList {
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private static PlayerList thePlayerList;
    
	public static PlayerList getInstance(){
        if(thePlayerList == null){
            thePlayerList = new PlayerList();
        }
        return thePlayerList;
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
	
}
