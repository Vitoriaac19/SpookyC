package rooms;

public enum RoomEnum {
    KITCHEN(new Key("SPOON"), RoomType.KITCHEN),
    BEDROOM(new Key("PILLOW"), RoomType.BEDROOM),
    BATHROOM(new Key("TOOTHBRUSH"), RoomType.BATHROOM),
    LIVINGROOM(new Key("REMOTE"), RoomType.LIVINGROOM),
    GYM(new Key("DUMBBELL"), RoomType.GYM),
    OFFICE(new Key("PEN"), RoomType.OFFICE);


    public Key key;
    public RoomType roomType;


    RoomEnum(Key name, RoomType roomType) {
        this.key = name;
        this.roomType = roomType;
    }

    public Key getKey() {
        return key;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public String getName() {
        return this.name();
    }
}
