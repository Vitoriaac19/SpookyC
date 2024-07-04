package rooms;

/**
 * The RoomEnum enum represents various rooms, each associated with a specific key and room type.
 */
public enum RoomEnum {
    /**
     * The kitchen room, associated with a Spoon key.
     */
    KITCHEN(new Key("Spoon"), RoomType.KITCHEN),
    /**
     * The bedroom room, associated with a Pillow key.
     */
    BEDROOM(new Key("Pillow"), RoomType.BEDROOM),
    /**
     * The bathroom room, associated with a Toothbrush key.
     */
    BATHROOM(new Key("Toothbrush"), RoomType.BATHROOM),
    /**
     * The living room, associated with a Remote key.
     */
    LIVINGROOM(new Key("Remote"), RoomType.LIVINGROOM),
    /**
     * The gym room, associated with a Dumbbell key.
     */
    GYM(new Key("Dumbbell"), RoomType.GYM),
    /**
     * The office room, associated with a Pen key.
     */
    OFFICE(new Key("Pen"), RoomType.OFFICE);

    public Key key;
    public RoomType roomType;

    /**
     * Constructs a RoomEnum instance with the specified key and room type.
     *
     * @param name     The key name associated with the room.
     * @param roomType The type of the room.
     */
    RoomEnum(Key name, RoomType roomType) {
        this.key = name;
        this.roomType = roomType;
    }

    /**
     * Gets the key associated with the room.
     *
     * @return The key associated with the room.
     */
    public Key getKey() {
        return key;
    }

    /**
     * Gets the type of the room.
     *
     * @return The type of the room.
     */
    public RoomType getRoomType() {
        return roomType;
    }

    /**
     * Gets the name of the room enum constant.
     *
     * @return The name of the room enum constant.
     */
    public String getName() {
        return this.name();
    }
}
