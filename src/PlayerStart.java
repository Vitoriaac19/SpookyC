import exceptions.client.ClientConnectionException;
import player.Client;

public class PlayerStart {
    public static void main(String[] args) {
        try {
            new Client();
        } catch (ClientConnectionException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
