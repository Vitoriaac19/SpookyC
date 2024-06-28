package rooms;

public class Office extends Room {
    public Office(boolean isOccupied) {
        super(isOccupied, RoomEnum.KITCHEN.getKey());
    }
}
