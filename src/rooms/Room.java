package rooms;

import message.MessageStrings;
import server.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a room in the castle.
 */
public abstract class Room {
    private boolean isOccupied;
    private List<Server.ClientHandler> clients;
    private Key key;
    private boolean occupied;

    /**
     * Constructor to initialize the Room with a specified key.
     *
     * @param key The key associated with the room.
     */
    public Room(Key key) {
        this.isOccupied = false;
        this.clients = new ArrayList<>();
        this.key = key;
    }

    /**
     * Retrieves the key associated with the room.
     *
     * @return The key object associated with the room.
     */
    public synchronized Key getKey() {
        return key;
    }

    /**
     * Adds a client (player) to the room.
     * Notifies all clients in the room when two players are present.
     *
     * @param clientHandler The ClientHandler representing the player to add.
     */
    public synchronized void enterRoom(Server.ClientHandler clientHandler) {
        clients.add(clientHandler);
        isOccupied = true;

        if (clients.size() == 2) {
            for (Server.ClientHandler clientHandler1 : clients) {
                clientHandler1.send(MessageStrings.BOTH_PLAYERS_IN_ROOM);
            }
        }
    }

    /**
     * Removes a client (player) from the room.
     * Updates the occupied status based on remaining clients.
     *
     * @param clientHandler The ClientHandler representing the player to remove.
     */
    public synchronized void leaveRoom(Server.ClientHandler clientHandler) {
        clients.remove(clientHandler);
        isOccupied = !clients.isEmpty();
    }
}
