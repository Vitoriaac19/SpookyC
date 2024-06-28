package rooms;

public class Livingroom  extends Room{
    public Livingroom(boolean isOccupied) {
        super(isOccupied, RoomEnum.KITCHEN.getKey());
    }
}
