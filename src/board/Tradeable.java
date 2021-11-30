package board;

public interface Tradeable {
	public void set();
	public boolean isAvailable(String resourceName, Integer number);
	public String trade(String tilein, String tileout);
	public String toString();
}
