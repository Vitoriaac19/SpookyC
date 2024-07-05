package rooms;

/**
 * Represents the Office in the castle.
 * Extends the Room class.
 */
public class Office extends Room {

    /**
     * Constructs a new Office object with the specified key.
     *
     * @param key The key required to access the Office.
     */
    public Office(Key key) {
        super(RoomEnum.OFFICE.getKey());
    }
}
