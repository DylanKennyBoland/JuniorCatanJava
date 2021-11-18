package pieces;
import java.util.HashMap;
import java.util.Map;

public class Player {
	private String name;
	private String colour;
	private Map<String, Integer> resources = new HashMap<String, Integer>();
	
	public Player(String name, String colour, Map<String, Integer> resources) { // The constructor...
		this.name = name;
		this.colour = colour;
		this.resources = resources;
	}
	
	public String toString() {
		String playerResources = "";
		for(String resource : this.resources.keySet()) {
			playerResources = playerResources + "\n" + resource + ": " + this.resources.get(resource);
		}
		return playerResources;
	}
	
	// 'get' and 'set' methods...
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getColour() {
		return this.colour;
	}
	
	public void setColour(String colour) {
		this.colour = colour;
	}
	
	public Map<String, Integer> getResources() {
		return this.resources;		
	}
}
