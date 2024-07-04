package rooms;

public enum RoomEnum {
    KITCHEN(new Key("Spoon"), RoomType.KITCHEN),
    BEDROOM(new Key("Pillow"), RoomType.BEDROOM),
    BATHROOM(new Key("Toothbrush"), RoomType.BATHROOM),
    LIVINGROOM(new Key("Remote"), RoomType.LIVINGROOM),
    GYM(new Key("Dumbbell"), RoomType.GYM),
    OFFICE(new Key("Pen"), RoomType.OFFICE);


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
