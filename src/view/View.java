package view;

import model.board.Board;

public class View {
	
	private static View view;
	public View() {
		
	}
	
	public static View getInstance() {
		if (view == null) {
			view = new View();
		}
		return view;
	}
	
	public void displayString(String string) {
		System.out.print();
	}
}
