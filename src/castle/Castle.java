package castle;

import rooms.*;
import server.Server;

import java.util.ArrayList;
import java.util.List;

public class Castle {
    private List<Room> rooms;
    //private List<Client> clients;

    private Server server;


    public Castle(Server server) {
        this.server = server;
        rooms = new ArrayList<>(RoomEnum.values().length);


        // Adiciona especifico
        for (RoomEnum roomEnum : RoomEnum.values()) {
            rooms.add(initializeRooms(roomEnum));
        }

    }

    private Room initializeRooms(RoomEnum roomEnum) {
        //  RoomEnum[] possibleRooms = RoomEnum.values();
        // for (RoomEnum roomType : possibleRooms) {
        switch (roomEnum) {
            case KITCHEN:
                return new Kitchen(roomEnum.getKey());

            case LIVINGROOM:
                return new Livingroom(roomEnum.getKey());

            case OFFICE:
                return new Office(roomEnum.getKey());

            case BATHROOM:
                return new Bathroom(roomEnum.getKey());
            case GYM:
                return new Gym(roomEnum.getKey());

            case BEDROOM:
                return new Bedroom(roomEnum.getKey());

            default:
                return null;

        }


    }


    public Room getRoom(RoomEnum roomEnum) {
        return rooms.get(roomEnum.ordinal());
    }


}

