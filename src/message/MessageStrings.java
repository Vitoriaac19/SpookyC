package message;

/**
 * The MessageStrings class holds static string constants used throughout the application.
 */
public class MessageStrings {

    /**
     * Prompt message for user to write their answer.
     */
    public static final String PROMPT_USER_ANSWER = "Write your answer: ";

    /**
     * Message for invalid numeric answer.
     */
    public static final String INVALID_NUMERIC_ANSWER = "Please enter a valid numeric answer (1-4).";

    /**
     * Message for invalid answer range.
     */
    public static final String INVALID_ANSWER_RANGE = "Please enter a valid answer (1-4).";

    /**
     * Message when no questions are available.
     */
    public static final String NO_QUESTIONS_AVAILABLE = "No questions available for the ";

    /**
     * Message for correct answer.
     */
    public static final String CORRECT_ANSWER = "Correct answer!";

    /**
     * Message for incorrect answer.
     */
    public static final String INCORRECT_ANSWER = "Your answer is incorrect! You'll lose a key and be kicked out from the room.";

    /**
     * Message when both players are in the same room.
     */
    public static final String BOTH_PLAYERS_IN_ROOM = "Both players are in the same room";

    /**
     * Message for invalid choice.
     */
    public static final String INVALID_CHOICE = "A cold shiver runs down your spine... You've wandered astray. Return to the entrance hall before the darkness takes hold...";

    /**
     * Exit game message.
     */
    public static final String EXIT_GAME = "You are ending your game. See you again soon. Bye!";

    /**
     * Message when a player left the game.
     */
    public static final String PLAYER_LEFT_GAME = "left the game";

    /**
     * Message for empty message.
     */
    public static final String EMPTY_MESSAGE = "Empty MessageStrings!";

    /**
     * Error message for server startup.
     */
    public static final String SERVER_STARTUP_ERROR = "Failed to start the server on port 9000";

    /**
     * Error message for client connection.
     */
    public static final String CLIENT_CONNECTION_ERROR = "Error handling client connection";

    /**
     * Message when a player has connected.
     */
    public static final String PLAYER_CONNECTED = "Player connected. Total players: ";

    /**
     * Message when waiting for other players.
     */
    public static final String WAITING_FOR_PLAYERS = "Waiting for other players";

    /**
     * Message when maximum number of players is reached.
     */
    public static final String MAX_PLAYERS_REACHED = "Maximum number of players reached";

    /**
     * Message when starting the game.
     */
    public static final String STARTING_GAME = "Starting game";

    /**
     * Message when a player has joined the game.
     */
    public static final String PLAYER_JOINED_GAME = " has joined the game";

    /**
     * Message when entering a room.
     */
    public static final String ENTERED_ROOM = "You entered the ";

    /**
     * Message when leaving a room.
     */
    public static final String LEFT_ROOM = "You left the room";

    /**
     * Message when a player already owns a key.
     */
    public static final String KEY_ALREADY_OWNED = "You already have a ";

    /**
     * Message when a player receives a key.
     */
    public static final String RECEIVED_KEY = "You have received a ";

    /**
     * Message when a player has no keys to lose.
     */
    public static final String NO_KEYS_TO_LOSE = "You don't have any keys to lose.";

    /**
     * Message when a player loses a key.
     */
    public static final String LOST_KEY = "You have lost your ";

    /**
     * Message when a player has won the game.
     */
    public static final String PLAYER_WON_GAME = " won the game. This game is closing now. See you next time!";

    /**
     * Message when all keys are owned and the player can leave the castle.
     */
    public static final String ALL_KEYS_OWNED = "You have successfully left the castle. Congratulations, you won!";

    /**
     * Message when a player cannot leave the castle due to missing keys.
     */
    public static final String MISSING_KEYS = "You cannot leave the castle. You are missing some keys";

    /**
     * Error message for help command handling.
     */
    public static final String HELP_COMMAND_ERROR = "Error handling help command";

    /**
     * Message when help menu is sent.
     */
    public static final String HELP_SENT = "Help menu sent";

    /**
     * Message when shutting down the socket.
     */
    public static final String SHUTTING_DOWN_SOCKET = "Goodbye!";

    /**
     * Message when there is no more space available.
     */
    public static final String NO_MORE_SPACE = "We don't have space yet. Please try again later.";

    /**
     * Error message for failing to initialize client handler.
     */
    public static final String FAILED_TO_INITIALIZE_CLIENT_HANDLER = "Failed to initialize client handler";

    /**
     * Message prompt to enter name.
     */
    public static final String ENTER_YOUR_NAME = "Enter your name: ";

    /**
     * Message when name entered contains only letters.
     */
    public static final String ONLY_LETTERS = "Please, enter your name using only letters: ";

    /**
     * Error message for playing background music.
     */
    public static final String ERROR_PLAYING_BACKGROUND_MUSIC = "Error playing background music: ";

    /**
     * Error message for client handler thread interrupted.
     */
    public static final String CLIENT_HANDLER_THREAD_INTERRUPTED = "Client handler thread interrupted";

    /**
     * Error message for exit menu thread interrupted.
     */
    public static final String EXIT_MENU_THREAD_INTERRUPTED = "Exit menu thread interrupted";

    /**
     * Error message for exit menu navigation thread interrupted.
     */
    public static final String EXIT_MENU_NAVIGATION_THREAD_INTERRUPTED = "Exit menu navigation thread interrupted";

    /**
     * Error message for help handler thread interrupted.
     */
    public static final String HELP_HANDLER_THREAD_INTERRUPTED = "Help handler thread interrupted";

    /**
     * Error message for invalid menu choice handler thread interrupted.
     */
    public static final String INVALID_MENU_CHOICE_HANDLER_THREAD_INTERRUPTED = "Invalid menu choice handler thread interrupted";

    /**
     * Error message for reading answer.
     */
    public static final String ERROR_READING_ANSWER = "Error reading answer";

    /**
     * Message when player has no keys.
     */
    public static final String YOU_DONT_HAVE_ANY_KEYS = "You don't have any keys.";

    /**
     * Message prefix for displaying player's keys.
     */
    public static final String YOUR_KEYS = "Your keys : ";

    /**
     * Message when returning to the main menu.
     */
    public static final String BACK_TO_MAIN_MENU = "You going back to main menu";

    /**
     * Error message for start game thread interrupted.
     */
    public static final String START_GAME_THREAD_INTERRUPTED = "Start game thread interrupted";

    /**
     * Error message for connecting to server.
     */
    public static final String ERROR_CONNECTING_TO_SERVER = "Error connecting to server";

    /**
     * Error message for sound file not found.
     */
    public static final String SOUND_FILE_NOT_FOUND = "Sound file not found: ";

    /**
     * Error message for shutting down client.
     */
    public static final String ERROR_SHUTTING_DOWN_CLIENT = "Error shutting down client";

    /**
     * Error message for reading input.
     */
    public static final String ERROR_READING_INPUT = "Error reading input";

    /**
     * Command to play a sound.
     */
    public static final String PLAY_SOUND = "PLAY_SOUND";

    /**
     * Command to quit.
     */
    public static final String QUIT = "quit";

    /**
     * Sound file for winner sound effect.
     */
    public static final String WINNER_SOUND = "winner-sound.wav";

    /**
     * Error message for quiz processing thread interrupted.
     */
    public static final String QUIZ_PROCESSING_THREAD_INTERRUPTED = "Quiz processing thread interrupted";

    /**
     * Error message for loading questions from JSON file.
     */
    public static final String ERROR_LOADING_QUESTIONS_FROM_JSON_FILE = "Error loading questions from the JSON file";

    /**
     * Sound file for wrong answer sound effect.
     */
    public static final String WRONG_ANSWER_SOUND = "wrong-answer.wav";

    /**
     * Sound file for correct answer sound effect.
     */
    public static final String RIGHT_ANSWER_SOUND = "right-answer.wav";

    /**
     * Message prompt to choose an option to continue.
     */
    public static final String CHOOSE_AN_OPTION = "Choose an option to continue";

    /**
     * Error message for invalid answer format.
     */
    public static final String INVALID_ANSWER_FORMAT = "Invalid answer format. Please enter a valid numeric answer (1-4).";

}
