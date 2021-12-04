package model.test_cases;
import model.setup.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.Test;

import model.setup.*;


public class SetupPlayersTest {
	@Test
	public void testSetNumPlayers() {
		Scanner inputScanner = new Scanner(System.in);
		
		SetupPlayers testPlayerSetup1 = new SetupPlayers();
		testPlayerSetup1.CreateAllPlayers(inputScanner);
	}
}
