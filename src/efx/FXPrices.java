/**
 * 
 */
package efx;

/**
 * Store the current FX prices.
 * Provides an interface for subscribers and clients/components to:
 * 1.- Publish/Update FX prices
 * 2.- Get the current FX price for an specific value
 * 
 * @author Sergio Hernández
 *
 */
public interface FXPrices {
	
	/**
	 * It publish the FX price as the last FX price if there is no newer price stored.
	 * @param fx Last FX price
	 * @return True if the FX price has been updated. False otherwise
	 */
	public boolean publishPrice(FXPrice fx);
	
	/**
	 * Returns The last current price stored for the given instrument name
	 * @param name The instrument name we want to get the current proice
	 * @return The current FX price stored for the given instrument name
	 */
	public FXPrice getCurrentPrice(String name);

}
