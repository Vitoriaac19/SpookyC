package castle;

import rooms.*;
import server.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Castle} class represents a collection of rooms in a castle and manages their initialization.
 * It provides methods to retrieve specific rooms and interacts with a server.
 */
public class Castle {
    private List<Room> rooms;
    private Server server;

    /**
     * Constructs a {@code Castle} object with a given server.
     * Initializes rooms based on the {@code RoomEnum} values.
     *
     * @param server The server associated with the castle.
     */
    public Castle(Server server) {
        this.server = server;
        rooms = new ArrayList<>(RoomEnum.values().length);

        // Initialize rooms based on RoomEnum values
        for (RoomEnum roomEnum : RoomEnum.values()) {
            rooms.add(initializeRooms(roomEnum));
        }
    }

    /**
     * Initializes a specific room based on the provided {@code RoomEnum}.
     *
     * @param roomEnum The {@code RoomEnum} specifying the type of room to initialize.
     * @return The initialized {@code Room} object corresponding to the {@code RoomEnum}.
     */
    private Room initializeRooms(RoomEnum roomEnum) {
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

    /**
     * Retrieves the {@code Room} object associated with the specified {@code RoomEnum}.
     *
     * @param roomEnum The {@code RoomEnum} representing the type of room to retrieve.
     * @return The {@code Room} object associated with the specified {@code RoomEnum}, or {@code null} if not found.
     */
    public Room getRoom(RoomEnum roomEnum) {
        return rooms.get(roomEnum.ordinal());
    }
}

