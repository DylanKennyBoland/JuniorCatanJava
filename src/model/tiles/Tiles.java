package model.tiles;

public abstract class Tiles {
	@SuppressWarnings("rawtypes")
	private Enum type;
	private int num;
	@SuppressWarnings("rawtypes")
	Tiles(Enum type, int num) {
		this.type = type;
		this.num = num;
	}
	@SuppressWarnings("rawtypes")
	public Enum getType() {
		return this.type;
	}
	
	public int getNum() {
		return this.num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}

}
