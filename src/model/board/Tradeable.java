package model.board;

public interface Tradeable {
	public void set();
	public boolean isAvailable(String resourceName, Integer number);
	public String trade(String tilein, Integer numIn, String tileout, Integer numOut);
	public String toString();
}
