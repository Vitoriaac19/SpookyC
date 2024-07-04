package rooms;

/**
 * Represents the Living Room in the castle.
 * Extends the Room class.
 */
public class Livingroom extends Room {

    /**
     * Constructs a new Living Room object with the specified key.
     *
     * @param key The key required to access the Living Room.
     */
    public Livingroom(Key key) {
        super(RoomEnum.LIVINGROOM.getKey());
    }
}
