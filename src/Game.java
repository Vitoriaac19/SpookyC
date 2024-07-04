import exceptions.server.ServerStartupException;
import server.Server;

public class Game {

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
