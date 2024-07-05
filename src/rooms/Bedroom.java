package rooms;

/**
 * Represents a bedroom room in the castle.
 * This class extends the {@link Room} class and initializes the bedroom room with a specific key.
 */
public class Bedroom extends Room {

    /**
     * Constructs a new Bedroom instance with the specified key.
     *
     * @param key The key associated with the bedroom room, used for identification.
     */
    public Bedroom(Key key) {
        super(RoomEnum.BEDROOM.getKey());
    }
}
