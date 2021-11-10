package enums;

public enum CocoEnums {
	GHOST("Ghost"),
	BUILD("Build"),
	RESOURCE1("Resource option 1"),
	RESOURCE2("Resource option 2");
	
	
	private final String type;
	
	private CocoEnums(String type){
		this.type = type;  // String for name of Room
	}
	
/**
 * returns the string name of the EoomEnum
 * @return name of room which is a string.
 */
	public String getType(){
		return this.type ;
	}
}
