package rooms;

import server.Server;

import java.util.ArrayList;
import java.util.List;

public abstract class Room {
    private boolean isOccupied;
    private List<Server.ClientHandler> clients;
    private Key key;


    public Room(Key key) {
        this.isOccupied = false;
        this.clients = new ArrayList<>();
        this.key = key;
    }


    public synchronized Key getKey() {
        return key;
    }

    public synchronized boolean isOccupied() {
        return isOccupied;
    }


    public synchronized void enterRoom(Server.ClientHandler clientHandler) {
        clients.add(clientHandler);
        isOccupied = true;

        if (clients.size() == 2) {
            for (Server.ClientHandler clientHandler1 : clients) {
                clientHandler1.send("Both players are in same room");
            }
        }
    }

    public synchronized void leaveRoom(Server.ClientHandler clientHandler) {
        clients.remove(clientHandler);
        isOccupied = !clients.isEmpty();
    }

    public synchronized boolean isPlayerInRoom(Server.ClientHandler clientHandler) {
        return clients.contains(clientHandler);
    }

    public synchronized boolean areTwoPlayersInRoom() {
        return clients.size() == 2;
    }

    public String getClients() {
        String client = "";
        for (Server.ClientHandler clientHandler : clients) {
            client += clientHandler.getName() + " ";
        }
        return client;
    }
}
