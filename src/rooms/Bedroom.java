package rooms;

public class Bedroom extends Room{
    public Bedroom(boolean isOccupied) {
        super(isOccupied, RoomEnum.BEDROOM.getKey());
    }
}
