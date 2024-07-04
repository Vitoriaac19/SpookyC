package rooms;

/**
 * The RoomEnum enum represents various rooms, each associated with a specific key and room type.
 */
public enum RoomEnum {
    KITCHEN(new Key("Spoon"), RoomType.KITCHEN),
    BEDROOM(new Key("Pillow"), RoomType.BEDROOM),
    BATHROOM(new Key("Toothbrush"), RoomType.BATHROOM),
    LIVINGROOM(new Key("Remote"), RoomType.LIVINGROOM),
    GYM(new Key("Dumbbell"), RoomType.GYM),
    OFFICE(new Key("Pen"), RoomType.OFFICE);

    public Key key;
    public RoomType roomType;

    /**
     * Constructs a RoomEnum instance with the specified key and room type.
     *
     * @param key      The key associated with the room.
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
