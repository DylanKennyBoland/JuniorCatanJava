package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.board.Board;
import model.board.Islands;
import model.board.Marketplace;
import model.board.Stockpile;
import model.enums.PlayerEnums;
import model.players.Player;
import model.players.PlayerList;

/**
 * Class for the View object for displaying information to the players.
 * 
 * @author: Adam Durning & Dylan Boland
 * @Date: 27/12/2021
 * 
 */
public class View {
	private static View view;
	
	// The getInstance method for the Singleton View object.
    public static View getInstance(){
        if(view == null){
            view = new View();
        }
        return view;
    }
    
    // The constructor
    public View() {}
    
    // This method prints the input string to the terminal
    public void display(String string) {
    	System.out.println(string);
    }
    
    // This method is used to display options to the user from the input options ArrayList.
	public void displayOptions(ArrayList<String> options) {
		int i = 1;
		for (String option : options) {
			display("\t\t " + i + " : " + option + "\n");
			i++;
		}
	}
	
	// This method is used to display the contents of the marketplace.
	public void viewMarketplace(Marketplace marketplace) {
		display("The Marketpalce has the following resources: ");
		displayOptions(marketplace.getMarketPlace());
	}
	
	// This method is used to display the contents of the Stockpile
	public void viewStockpile(Stockpile stockpile) {
		ArrayList<String> stockpileOptions = new ArrayList<String>();
		stockpileOptions.add("Gold (There are " + stockpile.getNumOfResource("Gold") + " in the stockpile)");
		stockpileOptions.add("Mollasses (There are " + stockpile.getNumOfResource("Molasses") + " in the stockpile)");
		stockpileOptions.add("Wood (There are " + stockpile.getNumOfResource("Wood") + " in the stockpile)");
		stockpileOptions.add("Goats (There are " + stockpile.getNumOfResource("Goats") + " in the stockpile)");
		stockpileOptions.add("Cutlass (There are " + stockpile.getNumOfResource("Cutlass") + " in the stockpile)");
		display("The Stockpile contains : ");
		displayOptions(stockpileOptions);
	}
	
	public void viewResources(Player player) {
		display("You have the folowing resources:");
		for(Map.Entry<String, Integer> resource : player.getResources().entrySet()) {
			display("\t\t\t\t" + resource.getValue() + " " + resource.getKey());
		}
		display("\n");
	}
	
	public void viewBoard(PlayerList playerList, Islands ghostIsland, Board board) {
		display("The board currently looks like this: ");
		for(Player player: playerList.getList()) {
			display(player.toString());
		}
		display("\nThe Ghost captain is on island: \t" + ghostIsland.getName());
		display("\n\n\n");
		display(board.getBoardConfig());
	}
	
	public void printAvailableColours(List<PlayerEnums> availableColours, ArrayList<Integer> colourIndex) {
		int i = 0;
		for(PlayerEnums colour : availableColours) {
            this.display("\t\t\t   " + colourIndex.get(i) + " : " +  colour.getColour() + "\n");
            i++;
		}
	}
	
	public void displayWelcomeMessage() {
		display("Welcome to Catan Junior!\n");
	}
}
