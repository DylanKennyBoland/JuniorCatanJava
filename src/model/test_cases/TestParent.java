package model.test_cases;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import view.View;

/**
 * This is a class that contains some general functions used throughout the tests.
 * */
public class TestParent {
	Scanner input;
	View view = View.getInstance();
	
	protected void printTestName(String test) {
		printSeperator(50);
		this.view.display(test);
		printSeperator(50);
	}
	
	// This method is used to simulate user input.
	protected Scanner setSimulatedInput(String simulatedInput) {
		System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
		return new Scanner(System.in);
	}
	
	protected void printSeperator(int size) {
		StringBuilder seperator = new StringBuilder(size);
		seperator.append("\n");
		for (int i = 0; i < size; i++) {
			seperator.append("#");
		}
		seperator.append("\n");
		this.view.display(seperator.toString());
	}

}
