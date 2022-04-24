package efx.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import efx.CHMFXPrices;
import efx.FXPrices;
import efx.Subscriber;

public class Test {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) {

		if(args.length != 1)
		{
			System.out.println("USAGE: java Test CSV_input_feed_filename");
		}
		else
		{		
			// Initialize components
			FXPrices fx_prices = new CHMFXPrices(); 
			Subscriber subscriber = new Subscriber(fx_prices);
			
			Client client_EUR_USD = new Client(fx_prices, "EUR/USD", System.out);
			Client client_GBP_USD = new Client(fx_prices, "GBP/USD", System.out);
			Client client_EUR_JPY = new Client(fx_prices, "EUR/JPY", System.out);
			
	        List<String> messages= new ArrayList<String>(); // We store the messages in a list in order to detect multi-line messages
			
			// Read the CSV file with the messages
	        BufferedReader br;
	        try 
	        {
				String filename = args[0];
	        	br  = new BufferedReader(new FileReader(filename));
	        	
	        	String message = null;
	        	LocalDateTime last_timestamp = null;
	        	

	        	
		        //Read file line by line		     
		        String line;
				while ((line = br.readLine()) != null)
				{		
					String fields[] = line.split(",");
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");
					LocalDateTime timestamp = LocalDateTime.parse(fields[4], formatter); // We assume the message is well-formed according to the indicated CSV format
					
					// Check if the new line has the same timestamp as the previous one read
					// If they have the same timestamp we assume that they are part of the same message
					if(last_timestamp == null) //First line
					{
						message = line;						
					}
					else if (last_timestamp.isEqual(timestamp)) // SAME MESSAGE
					{
						message += '\n'+line;
						
					}
					else //(!last_timestamp.isEqual(timestamp) // DIFFERENT MESSAGE
					{
						messages.add(message);
						message = line;
					}

					// Update last timestamp
					last_timestamp = timestamp; 
				}
				
				// Add the last message to the list
				if(message != null)
					messages.add(message);
				
				br.close();
			} 
	        catch (Exception e) {
	        	System.err.println("ERROR reading the input file");
				e.printStackTrace();
			}
	        
	        // For each message:
	        // 1.- The subscriber reads the message from the feed
	        // 2.- Each client ask for the price
	        for(String message: messages)
	        {
	        	System.out.println("NEW MESSAGE RECEIVED");
	        	
		        // 1.- The subscriber reads the message from the feed
	        	subscriber.onMessage(message);
	        	
		        // 2.- Each client ask for the price
	        	client_EUR_USD.checkPrice();
				client_GBP_USD.checkPrice();
				client_EUR_JPY.checkPrice();
	        }        
		}
	}
}
