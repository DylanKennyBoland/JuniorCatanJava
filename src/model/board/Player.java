package model.board;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.enums.PlayerEnums;

public class Player {
	private String name;
	private PlayerEnums colour;
	private Map<String, Integer> resources = new HashMap<String, Integer>();
	private Integer initialNum = 0;
	private List<String> assets = new ArrayList<String>();
	
	public Player(String name, PlayerEnums colour) { // The constructor...
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
	
	public void addAsset(String asset) {
		this.assets.add(asset);
	}
	
	public void viewAsset() {
		System.out.println(this.assets.toString());
	}
	public List<String> getAssets(){
		return this.assets;
	}
	public String toString() {
		String playerResources = "";
		for(String resource : this.resources.keySet()) {
			playerResources = playerResources + "\n" + resource + ": " + this.resources.get(resource);
		}
		return "Name : " + this.name + "\n" + playerResources;
	}
	
	// 'get' and 'set' methods...
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public PlayerEnums getColour() {
		return this.colour;
	}
	
	public void setColour(PlayerEnums colour) {
		this.colour = colour;
	}
	
	public Map<String, Integer> getResources() {
		return this.resources;		
	}
	
	public void giveResource(String resource, Integer num) {
		this.resources.put(resource, this.resources.get(resource) + num);
	}
	
	public void takeResource(String resource, Integer num) {
		this.resources.put(resource, resources.get(resource) - num);
	}
	
//	public boolean checkResources(String buildChoice) {
//		if(this.resources.get("Wood") >= 1 && this.resources.get("Goats") >= 1) {
//			if(buildChoice.contains("Ship")) {
//				return true;
//			}
//			else if(this.resources.get("Cutlass") >= 1 && this.resources.get("Molasses") >= 1){
//				return true;
//			}
//		}
//		System.out.println("you do not have enough resources");
//		return false;
//	}
	
	
	public boolean checkResources(String resource, Integer num) {
		
		if(this.resources.get(resource) >= num) {
			return true;
		}
		return false;
	}
}
