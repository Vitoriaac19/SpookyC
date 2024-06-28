package rooms;

import player.Client;
import server.Server;

public abstract class Room {
    private boolean isOccupied;
    private Server.ClientHandler[] client;
    private Key key;


    public Room(boolean isOccupied, Key key) {
        this.isOccupied = isOccupied;
        this.client = new Server.ClientHandler[1];
        this.key = new Key();
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Key getKey() {
        return key;
    }
}
