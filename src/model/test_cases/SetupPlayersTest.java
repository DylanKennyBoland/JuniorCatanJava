package model.test_cases;
import model.setup.*;
import view.View;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Scanner;

public class SetupPlayersTest extends TestParent {
	Scanner input;
	View view = View.getInstance();
	@Test
	public void testSetupPlayers() {
		SetupPlayers testPlayerSetup1 = new SetupPlayers();
		
		/** Testing Choosing the number of players for the game. Must be between 3 and 4.
		 *  The simulatedInput String represents an example of what a user might input. The
		 *  inputs are separated by \n which simulates the Enter button on the keyboard.*/
		printSeperator(50);
		this.view.display("Testing choosing the number of Players.");
		printSeperator(50);
		input = setSimulatedInput("2\n5\n-1\n3\n");
		assertEquals(3,testPlayerSetup1.getNumPlayers(input));
		
		input = setSimulatedInput("4\n");
		assertEquals(4,testPlayerSetup1.getNumPlayers(input));
		
		/** Testing choosing the Player's colour.
		 * Initially the input outside the range of colours to choose from. (0 and 5)
		 * Then the input is a valid choice. (1 corresponds to BLUE)
		 * */
		printSeperator(50);
		this.view.display("Testing choosing the Player's Colour.");
		printSeperator(50);
		SetupPlayers testPlayerSetup2 = new SetupPlayers();
		testPlayerSetup2.setColourList(3);
		input = setSimulatedInput("0\n5\n1\n");
		assertEquals("BLUE", testPlayerSetup2.getPlayerColour(input));
		/**Testing the next Player's choice of colour. 
		 * The range to choose from has now decreased because the colour
		 * BLUE has already been chosen. (1 is no longer a valid choice)
		 * */
		input = setSimulatedInput("1\n2\n");
		assertEquals("RED", testPlayerSetup2.getPlayerColour(input));
		
		/**Testing the player's input for their age.
		 * The number must be non negative.
		 * */
		printSeperator(50);
		this.view.display("Testing choosing the Player's Age.");
		printSeperator(50);	
		input = setSimulatedInput("-1\n20\n");
		assertEquals("20",testPlayerSetup2.getPlayerAge(input));
	}
}
