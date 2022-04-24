package efx;

import java.time.LocalDateTime;

/***
 * Class used to represent information about FX prices. 
 * It provides the class constructor and getter and setter methods.
 * @author Sergio Hernández
 *
 */
public class FXPrice {
	
	private int id;
	private String name; // Alternatively an enum type could be used with the 3 possible values.
	private double bid;
	private double ask;
	private LocalDateTime timestamp;
	
	public FXPrice(int id, String name, double bid, double ask, LocalDateTime timestamp)
	{
		this.id = id;
		this.name = name;
		this.bid = bid;
		this.ask = ask;
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBid() {
		return bid;
	}

	public void setBid(double bid) {
		this.bid = bid;
	}

	public double getAsk() {
		return ask;
	}

	public void seAsky(double ask) {
		this.ask = ask;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
