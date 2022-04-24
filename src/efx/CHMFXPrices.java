package efx;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Class implementing the FXPrices interface by using a Concurrent Hash Map that
 * stores the last FX price stored.
 * A Concurrent Hash Map is used since we are assuming the system should work properly in a
 * concurrent environment when different subscribers and clients are publishing/getting the 
 * FX prices at the same time.
 * 
 * It has been assumed that storing an historical record of the FX prices is not need.
 * @author Sergio Hernández
 *
 */
public class CHMFXPrices extends ConcurrentHashMap<String,FXPrice> implements FXPrices {

	private static final long serialVersionUID = 1L;

	/**
	 * It publish the FX price as the last FX price if there is no newer price stored.
	 * @param fx Last FX price
	 * @return True if the FX price has been updated. False otherwise
	 */
	public boolean publishPrice(FXPrice fx) {

		String name = fx.getName();

		FXPrice last_price = getCurrentPrice(name);
		if(last_price == null) // First time the FX price is stored
		{
			this.put(name, fx);
			return true;
		}
		else // There is a previous value stored
		{
			// We check if the provided FX price is newer than the existing one.
			// We assume there is the possibility of trying to update the FX price
			// with an older one and that behavior should be avoided.
			if(fx.getTimestamp().isAfter(last_price.getTimestamp()))
			{
				this.put(name, fx);
				return true;
			}
			else
			{
				return false;
			}
		}
				
	}
	
	/**
	 * Returns The last current price stored for the given instrument name
	 * @param name The instrument name we want to get the current proice
	 * @return The current FX price stored for the given instrument name
	 */
	public FXPrice getCurrentPrice(String name) {
		return this.get(name);
	}

}
