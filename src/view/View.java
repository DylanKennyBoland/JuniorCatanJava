package view;

import java.util.Scanner;

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
}
