package menus;

/**
 * The Menu class provides static methods and constants to generate various
 * menu prompts for the game.
 */
public class Menu {

    /**
     * Help menu listing the available commands.
     */
    public static final String HELP = """ 
            +--------------------------------------------------+
            +------------List of available commands:-----------+
            +--------------------------------------------------+
            | [/p + <message>] Speak only to your opponent     |
            | [/s + <message>] Shout at your opponent          |
            | [/q + <space>] Exits the server                  |
            +--------------------------------------------------+
            > """;

    /**
     * Message for an invalid command.
     */
    public static final String COMMAND_NOT_FOUND = "Invalid command!";

    /**
     * Generates the main menu prompt.
     *
     * @return the main menu as a formatted String
     */
    public static String getMainMenu() {
        return "+--------------------------------------------------+\n" +
                "+ ------------Entrance Hall------------------------+\n" +
                "| -----The air is thick with an eerie silence------|\n" +
                "| --------Choose your direction...If you dare------|\n" +
                "+--------------------------------------------------+\n" +
                "| [1] Turn Left                                    |\n" +
                "| [2] Go Forward                                   |\n" +
                "| [3] Turn Right                                   |\n" +
                "| [4] Check Inventory                              |\n" +
                "+--------------------------------------------------+\n" +
                "+--------------------------------------------------+\n" +
                "+ [8] Help - List of available commands...         |\n" +
                "+ [9] Exit Castle... Do you have all keys?         |\n" +
                "+--------------------------------------------------+\n" +
                "+--------------------------------------------------+\n" +
                "> ";
    }

    /**
     * Generates the exit menu prompt.
     *
     * @return the exit menu as a formatted String
     */
    public static String getExitMenu() {
        return "+--------------------------------------------------+\n" +
                "+------------ Exit the Castle ---------------------+\n" +
                "|--- The air grows colder as you reach the exit ---|\n" +
                "|--- A whisper echoes, 'Will you ever return?' ----|\n" +
                "+--------------------------------------------------+\n" +
                "| [1] Yes, I'm sure.                               |\n" +
                "| [2] No, I want to stay a little longer.          |\n" +
                "+--------------------------------------------------+\n" +
                "> ";
    }

    /**
     * Generates the second menu prompt.
     *
     * @return the second menu as a formatted String
     */
    public static String getMenu2() {
        return "+--------------------------------------------------+\n" +
                "+----------Middle of the Hall----------------------+\n" +
                "|-------- Shadows flicker along the walls----------|\n" +
                "|-------- Choose your direction...quickly----------|\n" +
                "+--------------------------------------------------+\n" +
                "| [1] Turn Left                                    |\n" +
                "| [2] Go Forward                                   |\n" +
                "| [3] Turn Right                                   |\n" +
                "| [4] Go Back                                      |\n" +
                "| [5] Check Inventory                              |\n" +
                "+--------------------------------------------------+\n" +
                "+--------------------------------------------------+\n" +
                "> ";
    }

    /**
     * Generates the third menu prompt.
     *
     * @return the third menu as a formatted String
     */
    public static String getMenu3() {
        return "+--------------------------------------------------+\n" +
                "+---------------End of the Hall--------------------+\n" +
                "|---------The darkness ahead is overwhelming.------|\n" +
                "|------ Choose your direction... while you can-----|\n" +
                "+--------------------------------------------------+\n" +
                "| [1] Turn Left                                    |\n" +
                "| [2] Turn Right                                   |\n" +
                "| [3] Go Back                                      |\n" +
                "| [4] Check Inventory                              |\n" +
                "+--------------------------------------------------+\n" +
                "+--------------------------------------------------+\n" +
                "> ";
    }

    /**
     * Generates the kitchen door menu prompt.
     *
     * @return the kitchen door menu as a formatted String
     */
    public static String getKitchenDoorMenu() {
        return "+--------------------------------------------------+\n" +
                "| A chilling breeze greets you at the kitchen door.|\n" +
                "| The smell of forgotten meals lingers.            |\n" +
                "+--------------------------------------------------+\n" +
                "| [1] Enter the Kitchen                            |\n" +
                "| [2] Retreat into the shadows                     |\n" +
                "+--------------------------------------------------+\n" +
                "> ";
    }

    /**
     * Generates the gym door menu prompt.
     *
     * @return the gym door menu as a formatted String
     */
    public static String getGymDoorMenu() {
        return "+--------------------------------------------------+\n" +
                "| The gym echoes with phantom footsteps.           |\n" +
                "| Equipment moves on its own.                      |\n" +
                "+--------------------------------------------------+\n" +
                "| [1] Enter the Gym                                |\n" +
                "| [2] Retreat into the shadows                     |\n" +
                "+--------------------------------------------------+\n" +
                "> ";
    }

    /**
     * Generates the office door menu prompt.
     *
     * @return the office door menu as a formatted String
     */
    public static String getOfficeDoorMenu() {
        return "+--------------------------------------------------+\n" +
                "| The office whispers of secrets long forgotten.   |\n" +
                "| Papers rustle though no wind blows.              |\n" +
                "+--------------------------------------------------+\n" +
                "| [1] Enter the Office                             |\n" +
                "| [2] Retreat into the shadows                     |\n" +
                "+--------------------------------------------------+\n" +
                "> ";
    }

    /**
     * Generates the bathroom door menu prompt.
     *
     * @return the bathroom door menu as a formatted String
     */
    public static String getBathroomDoorMenu() {
        return "+--------------------------------------------------+\n" +
                "| The bathroom's silence is deafening.             |\n" +
                "| Water drips echo like distant footsteps.         |\n" +
                "+--------------------------------------------------+\n" +
                "| [1] Enter the Bathroom                           |\n" +
                "| [2] Retreat into the shadows                     |\n" +
                "+--------------------------------------------------+\n" +
                "> ";
    }

    /**
     * Generates the bedroom door menu prompt.
     *
     * @return the bedroom door menu as a formatted String
     */
    public static String getBedroomDoorMenu() {
        return "+--------------------------------------------------+\n" +
                "| The bedroom's darkness beckons.                  |\n" +
                "| A cold breeze stirs the curtains.                |\n" +
                "+--------------------------------------------------+\n" +
                "| [1] Enter the Bedroom                            |\n" +
                "| [2] Retreat into the shadows                     |\n" +
                "+--------------------------------------------------+\n" +
                "> ";
    }

    /**
     * Generates the living room door menu prompt.
     *
     * @return the living room door menu as a formatted String
     */
    public static String getLivingRoomDoorMenu() {
        return "+--------------------------------------------------+\n" +
                "| The living room is filled with eerie silence.    |\n" +
                "| Shadows dance on the walls.                      |\n" +
                "+--------------------------------------------------+\n" +
                "| [1] Enter the Living Room                        |\n" +
                "| [2] Retreat into the shadows                     |\n" +
                "+--------------------------------------------------+\n" +
                "> ";
    }

    /**
     * Generates the welcome message prompt.
     *
     * @return the welcome message as a String
     */

    public static String getWelcomeMessage() {
        return "Welcome to the Castle ...";
    }

}