/**
 * 
 */
package efx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Subscriber class that gets a message from the feed with the last FX prices,
 * process the message, store the FX prices and 
 * @author Sergio Hernández
 *
 */
public class Subscriber implements Feed {
	
	private FXPrices FX_prices; //FX prices store
	
	// It might make more sense to delegate applying the commission to a different class
	// In this example, this task is made by the subscriber for simplicity.
	private static final double COMISSION_PCT = 0.1;
	
	public Subscriber (FXPrices FX_prices)
	{
		this.FX_prices = FX_prices;
	}
	
	@Override
	/***
	 * Gets a message with FX prices from the feed
	 * @param message A message with FX prices
	 */
	public void onMessage(String message) {
		
		// We assume the message can contain multiple lines
		// I guess messages with the same timestamp should be provided
		// within the same message
        BufferedReader br = new BufferedReader(new StringReader(message));
        
        String line;
        try 
        {
        	//Read message line by line
			while ((line = br.readLine()) != null)
			{
				processIndividualMessage(line);
			}
			br.close();
		} 
        catch (IOException e) {
        	System.err.println("ERROR reading the message");
			e.printStackTrace();
		}
    }
	
	/**
	 * It handles an individual FXPrice read by the feed:
	 * 1.- Read the message with a new FX price
	 * 2.- Apply the commission to the read bid and ask prices
	 * 3.- Store the FX price
	 * 4.- Publish to a REST endpoint
	 * @param csv CSV string line with the information of a FX price 
	 */
	private void processIndividualMessage(String csv) {
		
		// Check the line has contents
		if(csv != null && !csv.isBlank())
		{	
			// 1.- Read the FXPrice
			String fields[] = csv.split(",");
			// We assume the message is well-formed according to the indicated CSV format
			int id = Integer.parseInt(fields[0]);
			String name = fields[1];
			double bid = Double.parseDouble(fields[2]);
			double ask = Double.parseDouble(fields[3]);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");
			LocalDateTime timestamp = LocalDateTime.parse(fields[4], formatter);
	
			// 2.- Apply commission to bid and ask prices
			bid = bid - (COMISSION_PCT/100.0 * bid);
			ask = ask + (COMISSION_PCT/100.0 * bid);
			
			// 3.- Store the FX price
			FXPrice fx = new FXPrice(id,name,bid,ask,timestamp);		
			boolean success = FX_prices.publishPrice(fx);
			
			// 4.- Publish the price to a REST Endpoint
			if(success) 
			{
				// POST /fx_prices/{$name} 
			}
		}
	}

}
