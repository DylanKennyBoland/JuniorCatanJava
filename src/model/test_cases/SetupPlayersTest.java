package model.test_cases;
import model.setup.*;
import java.util.Scanner;
import org.junit.Test;

public class SetupPlayersTest {
	@Test
	public void testSetNumPlayers() {
		Scanner inputScanner = new Scanner(System.in);
		
		SetupPlayers testPlayerSetup1 = new SetupPlayers();
		testPlayerSetup1.CreateAllPlayers(inputScanner);
	}
}
