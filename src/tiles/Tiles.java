package tiles;

public abstract class Tiles {
	@SuppressWarnings("rawtypes")
	private Enum type;
	@SuppressWarnings("rawtypes")
	Tiles(Enum type) {
		this.type = type;
	}
	@SuppressWarnings("rawtypes")
	public Enum getType() {
		return this.type;
	}

}
