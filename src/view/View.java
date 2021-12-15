package view;

import java.util.ArrayList;

import model.board.Islands;
import model.board.Marketplace;
import model.board.Stockpile;
import model.players.Player;
import model.players.PlayerList;

public class View {
	private static View view;
	
    public static View getInstance(){
        if(view == null){
            view = new View();
        }
        return view;
    }
    
    public View() {}
    
    public void display(String string) {
    	System.out.println(string);
    }
    
	public void displayOptions(ArrayList<String> options) {
		int i = 1;
		display("\nYour options are: ");
		for (String option : options) {
			display("\n\t" + i + " : " + option);
			i++;
		}
	}
	
	public void viewMarketplace(Marketplace marketplace) {
		display("The Marketplace contains the following resources: ");
		display(marketplace.toString());
	}
	
	public void viewStockpile(Stockpile stockpile) {
		display(stockpile.toString());
	}
	
	public void viewResources(Player player) {
		display(player.getResources().toString());
	}
	
	public void viewBoard(PlayerList playerList, Islands ghostIsland) {
		display("The board currently looks like this: ");
		for(Player player: playerList.getList()) {
			display(player.toString());
		}
		display("\nThe Ghost captain is on island: \t" + ghostIsland.getName());
	}
}
