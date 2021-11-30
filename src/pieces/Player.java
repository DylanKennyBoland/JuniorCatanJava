package pieces;
import java.util.HashMap;
import java.util.Map;

public class Player {
	private String name;
	private String colour;
	private Map<String, Integer> resources = new HashMap<String, Integer>();
	private Integer initialNum = 0;
	
	public Player(String name, String colour) { // The constructor...
		this.name = name;
		this.colour = colour;
		this.initialise();
	}
	
	public void initialise() {
		this.resources.put("Wood", this.initialNum);
		this.resources.put("Cutlass", this.initialNum);
		this.resources.put("Goats", this.initialNum);
		this.resources.put("Gold", this.initialNum);
		this.resources.put("Molasses", this.initialNum);
		this.resources.put("Coco tiles", this.initialNum);
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
