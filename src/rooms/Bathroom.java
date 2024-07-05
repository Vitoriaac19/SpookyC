package rooms;

/**
 * Represents a bathroom room in the castle.
 * This class extends the {@link Room} class and initializes the bathroom room with a specific key.
 */
public class Bathroom extends Room {

    /**
     * Constructs a new Bathroom instance with the specified key.
     *
     * @param key The key associated with the bathroom room, used for identification.
     */
    public Bathroom(Key key) {
        super(RoomEnum.BATHROOM.getKey());
    }
}
