package rooms;

public class Bathroom extends Room{
    public Bathroom(boolean isOccupied) {
        super(isOccupied, RoomEnum.BATHROOM.getKey());
    }
}
