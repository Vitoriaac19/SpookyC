package castle;

import player.Client;
import rooms.*;
import server.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Locale.filter;
import static rooms.RoomEnum.BATHROOM;

public class Castle {
    private String name;
    private List<Room> rooms;
    private RoomEnum type;
    //private List<Client> clients;
    private Client client;
    private Server server;


    public Castle(Server server) {
        this.rooms = new ArrayList<>();
        this.name = name;
        initializeRooms();

    }

    private void initializeRooms() {
        RoomEnum[] possibleRooms = RoomEnum.values();
        for (RoomEnum roomType : possibleRooms) {
            switch (roomType) {
                case KITCHEN:
                    rooms.add(new Kitchen(false));
                    break;
                case LIVINGROOM:
                    rooms.add(new Livingroom(false));
                    break;
                case OFFICE:
                    rooms.add(new Office(false));
                    break;
                case BATHROOM:
                    rooms.add(new Bathroom(false));
                    break;
                case BEDROOM:
                    rooms.add(new Bedroom(false));
                    break;
                default:
            }
        }

    }

    public List<Room> getRooms() {
        return rooms;
    }

/////lalalalalal
}

