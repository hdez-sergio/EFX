package efx.test;
import java.io.PrintStream;
import java.util.Locale;

import efx.FXPrice;
import efx.FXPrices;


/**
 * Client class for testing.
 * Each client only asks for an specific instrument (EUR/USD, GBP/USD or EUR/JPY)
 * The client reads a value 
 * @author Sergio Hernández
 *
 */
public class Client {

	// It directly reads the FX price from the FXPrices class.
	// In a real-environment it would interact with the UI or the REST endpoint.
	private FXPrices prices;
	private PrintStream out;
	
	private String instrument;
	
	
	public Client(FXPrices prices, String instrument, PrintStream out) {
		this.prices = prices;
		this.instrument = instrument;
		this.out = out;
	}
	
	/**
	 * It get the current price for a FX price and prints the read price (if any)
	 */
	public void checkPrice()
	{
		FXPrice fx = prices.getCurrentPrice(instrument);
		
		// Print the read FX price
		if(fx == null)
		{
			out.println("There is no price for the " + instrument + " instrument yet.");
		}
		else
		{
			out.println(fx.getTimestamp().toString() + ":" + fx.getName() + " - Bid price: " + String.format(Locale.UK, "%.5f", fx.getBid()) + " - Ask price: " +  String.format(Locale.UK, "%.5f", fx.getAsk()));
		}
	}
}
