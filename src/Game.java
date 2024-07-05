import exceptions.server.ServerStartupException;
import server.Server;

/**
 * The Game class serves as the entry point for starting the server and handling startup exceptions.
 * It initializes a Server instance and attempts to start it. If a ServerStartupException occurs
 * during the startup process, it prints the error message and stack trace.
 */
public class Game {

    /**
     * The main method of the Game class.
     * It creates a new Server instance, starts it, and handles ServerStartupException.
     *
     * @param args command-line arguments (not used in this implementation)
     */
    public static void main(String[] args) {
        Server server = new Server();

        try {
            server.start();
        } catch (ServerStartupException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
