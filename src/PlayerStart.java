import exceptions.client.ClientConnectionException;
import player.Client;

/**
 * The PlayerStart class serves as the entry point for starting the client application and handling connection exceptions.
 * It initializes a Client instance and attempts to create a connection. If a ClientConnectionException occurs
 * during the connection process, it prints the error message and stack trace.
 */
public class PlayerStart {

    /**
     * The main method of the PlayerStart class.
     * It creates a new Client instance, attempts to establish a connection, and handles ClientConnectionException.
     *
     * @param args command-line arguments (not used in this implementation)
     */
    public static void main(String[] args) {
        try {
            new Client();
        } catch (ClientConnectionException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
