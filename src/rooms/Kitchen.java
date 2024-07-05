package rooms;

/**
 * Represents the Kitchen room in the castle.
 * Extends the Room class.
 */
public class Kitchen extends Room {

    /**
     * Constructs a new Kitchen object with the specified key.
     *
     * @param key The key required to access the Kitchen room.
     */
    public Kitchen(Key key) {
        super(RoomEnum.KITCHEN.getKey());
    }


}
