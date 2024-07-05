package rooms;

/**
 * Represents a key in the castle, identified by its name.
 */
public class Key {
    private String name;

    /**
     * Constructs a new Key object with the specified name.
     *
     * @param name The name of the key.
     */
    public Key(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the key.
     *
     * @return The name of the key.
     */
    @Override
    public String toString() {
        return name;
    }
}
