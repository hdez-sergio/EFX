package efx;

/**
 * Interface for the feed where the messages are read from
 * @author Sergio Hernández
 *
 */
public interface Feed {

	/**
	 * Gets a message with FX prices from the feed
	 * @param message A message with FX prices
	 */
	public void onMessage(String message);
	
}
