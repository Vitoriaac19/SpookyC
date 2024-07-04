package rooms;

/**
 * Represents a gym room in the castle.
 * This class extends the {@link Room} class and initializes the gym room with a specific key.
 */
public class Gym extends Room {

    /**
     * Constructs a new Gym instance with the specified key.
     *
     * @param key The key associated with the gym room, used for identification.
     */
    public Gym(Key key) {
        super(RoomEnum.GYM.getKey());
    }
}
