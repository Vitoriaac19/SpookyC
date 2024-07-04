package server;

import ascii_art.SpookyCastle;
import ascii_art.Winner;
import castle.Castle;
import exceptions.quiz.QuestionLoadException;
import exceptions.server.ClientHandlingException;
import exceptions.server.ServerInterruptedException;
import exceptions.server.ServerStartupException;
import menus.Menu;
import message.MessageStrings;
import music.Audio;
import resources.QuestionsApp;
import rooms.Key;
import rooms.Room;
import rooms.RoomEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static colors.Colors.*;
import static message.MessageStrings.*;

/**
 * The Server class handles client connections, game initialization, and game interactions.
 */
public class Server {
    private final int MAX_CLIENTS = 2;
    private final List<ClientHandler> clientHandlers;
    private final boolean running;
    private final Castle castle;
    private ServerSocket socket;

    /**
     * Constructs a new Server instance.
     */
    public Server() {
        clientHandlers = new ArrayList<>(MAX_CLIENTS);
        castle = new Castle(this);
        running = true;
    }

    /**
     * The main method to start the server.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.start();
        } catch (ServerStartupException e) {
            System.err.println("Failed to start the server: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private static String invalidChoice() {
        return MessageStrings.INVALID_CHOICE;
    }


    /**
     * Starts the server and accepts client connections.
     */
    public synchronized void start() throws ServerStartupException {

        try {
            socket = new ServerSocket(9000);
            ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);

            while (running) {

                try {
                    Socket clientSocket = socket.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket);
                    if (acceptPlayer(clientHandler)) {
                        pool.submit(clientHandler);
                    } else {
                        clientHandler.close();
                    }

                    if (clientHandlers.size() == MAX_CLIENTS) {
                        System.out.println(MAX_PLAYERS_REACHED);
                        startGame();
                    }
                } catch (IOException e) {
                    throw new ClientHandlingException(CLIENT_CONNECTION_ERROR, e);
                }
            }
        } catch (IOException e) {
            throw new ServerStartupException(SERVER_STARTUP_ERROR, e);
        }
    }

    /**
     * Adds a client to the list of connected clients.
     *
     * @param clientHandler the client handler to add
     */
    public synchronized void addClient(ClientHandler clientHandler) {
        clientHandlers.add(clientHandler);
        System.out.println(getClientHandlers().size());
    }

    /**
     * Starts the game for all connected clients.
     */
    public void startGame() {
        System.out.println(STARTING_GAME);
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.startGame();
        }
    }

    /**
     * Returns the list of connected client handlers.
     *
     * @return the list of client handlers
     */
    public List<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    /**
     * Accepts a player if the maximum number of clients has not been reached.
     *
     * @param clientHandler the client handler to accept
     * @return true if the player was accepted, false otherwise
     */
    public synchronized boolean acceptPlayer(ClientHandler clientHandler) {
        if (clientHandlers.size() < MAX_CLIENTS) {
            addClient(clientHandler);
            System.out.println(PLAYER_CONNECTED + clientHandlers.size());

            clientHandler.send(WAITING_FOR_PLAYERS);

            return true;
        } else {
            clientNotAccepted(clientHandler, "We don't have space yet. Please try again later.");
            return false;
        }
    }

    /**
     * Sends a MessageStrings to a client indicating they were not accepted.
     *
     * @param clientHandler the client handler to notify
     * @param message       the MessageStrings to send
     */
    public void clientNotAccepted(ClientHandler clientHandler, String message) {
        clientHandler.send(message);
    }

    private Castle getCastle() {
        return castle;
    }

    /**
     * Broadcasts a MessageStrings to all clients except the sender.
     *
     * @param name    the name of the sender
     * @param message the MessageStrings to broadcast
     */
    public void broadcast(String name, String message) {
        clientHandlers.stream()
                .filter(clientHandler -> !clientHandler.getName().equals(name))
                .forEach(clientHandler -> clientHandler.send(name + ": " + message));

    }

    public void endGame() {
        clientHandlers.stream()
                .forEach(ClientHandler::close);
    }

    /**
     * The ClientHandler class handles interactions with a connected client.
     */
    public class ClientHandler implements Runnable {
        private final BufferedReader in;
        private final PrintWriter out;
        private final Socket clientSocket;
        private final List<Key> keys;
        private final QuestionsApp questionsApp = new QuestionsApp();
        boolean isConnected;
        private String name;
        private RoomEnum enteredRoom;
        private Audio music;

        /**
         * Constructs a new ClientHandler instance.
         *
         * @param clientSocket the client socket
         */
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;

            this.name = "";
            this.isConnected = false;
            this.keys = new ArrayList<>();
            music = new Audio();

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            } catch (IOException e) {
                throw new ClientHandlingException("Failed to initialize client handler", e);
            }


        }

        /**
         * Returns the list of keys the client has.
         *
         * @return the list of keys
         */
        public List<Key> getKeys() {
            return keys;
        }

        /**
         * Adds a key to the client's key list.
         *
         * @param key the key to add
         */
        public void addKey(Key key) {
            for (Key existingKey : keys) {
                if (existingKey.equals(key)) {
                    send(KEY_ALREADY_OWNED + ANSI_RED + key + ANSI_RESET);
                    return;
                }
            }
            keys.add(key);
            send(RECEIVED_KEY + ANSI_GREEN + key + ANSI_RESET);
        }

        private void displayMenu2() throws QuestionLoadException {
            send(Menu.getMenu2());
            handleMenu2();
        }

        private void displayMenu3() throws QuestionLoadException {
            send(Menu.getMenu3());
            handleMenu3();
        }

        /**
         * Starts the game for the client.
         */
        public void startGame() {
            new Thread(() -> {
                try {
                    URL sound = Audio.class.getResource("creepy-sound.wav");
                    music.keepAudioPlaying(sound);
                    //music.playOnce(sound);
                    send(SpookyCastle.SPOOKY_CASTLE);
                    send("Enter your name: ");
                    name = getAnswer();
                    while (!name.matches("[a-zA-Z]+")) {
                        send("Please, enter your name using only letters: ");
                        name = getAnswer();
                    }
                    System.out.println(name + PLAYER_JOINED_GAME);
                    send(Menu.getWelcomeMessage());
                    navigate();
                } catch (QuestionLoadException e) {
                    send("Error playing background music: " + e.getMessage());
                    // Consider how to handle this error scenario, e.g., closing resources or retrying
                }
            }).start();
        }

        @Override
        public void run() {

            while (!isConnected) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(new ServerInterruptedException("Client handler thread interrupted", e));
                }
            }

        }


        /**
         * Navigates the client through the main menu.
         */
        public void navigate() throws QuestionLoadException {

            send(Menu.getMainMenu());
            handleMainMenu();
        }

        private boolean hasAllKeys() {
            for (RoomEnum room : RoomEnum.values()) {
                if (!keys.contains(room.getKey())) {
                    return false;
                }
            }
            return true;
        }

        private void handleExitMenu() throws QuestionLoadException {
            send(Menu.getExitMenu());
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    leaveCastle();
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);


                        } catch (InterruptedException e) {
                            throw new RuntimeException(new ServerInterruptedException("Exit menu thread interrupted", e));
                        }
                    }).start();

                    break;
                case "2":
                    new Thread(() -> {
                        try {
                            Thread.sleep(1000);
                            navigate();
                        } catch (InterruptedException | QuestionLoadException e) {
                            throw new RuntimeException(new ServerInterruptedException("Exit menu navigation thread interrupted", e));
                        }
                        send(Menu.getMainMenu());
                    }).start();


                    break;
                default:
                    invalidMenuChoice();
                    handleMainMenu();
                    break;
            }
        }

        private void handleMainMenu() throws QuestionLoadException {
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    send(Menu.getBathroomDoorMenu());
                    handleRoomMenu(RoomEnum.BATHROOM);
                    break;
                case "2":
                    displayMenu2();
                    break;
                case "3":
                    send(Menu.getKitchenDoorMenu());
                    handleRoomMenu(RoomEnum.KITCHEN);
                    break;
                case "4":
                    displayKeys();
                    resetInputStream();
                    handleMainMenu();
                    break;
                case "8":
                    displayHelp();
                case "9":
                    handleExitMenu();
                    break;
                default:
                    invalidMenuChoice();
                    handleMainMenu();
                    break;

            }
        }

        private void displayHelp() {
            send(Menu.HELP);
            try {
                handleHelp();
            } catch (IOException e) {
                throw new RuntimeException(new ClientHandlingException(HELP_SENT, e));
            }
        }

        private void leaveCastle() {
            if (hasAllKeys()) {
                URL winnerSound = Audio.class.getResource("winner-sound.wav");
                music.stopAudio();
                music.playOnce(winnerSound);
                send(Winner.WINNER);
                send(ALL_KEYS_OWNED);
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    broadcast(name, PLAYER_WON_GAME);
                    endGame();
                }).start();
            } else {
                send(MISSING_KEYS);
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    send(Menu.getMainMenu());
                    try {
                        handleMainMenu();
                    } catch (QuestionLoadException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        }


        /**
         * Handles client commands from the help menu.
         *
         * @throws IOException if an I/O error occurs
         */
        public void handleHelp() throws IOException {
            int maxString = 70;
            try {
                String message = in.readLine();

                if (isCommand(message)) {
                    String description = message.substring(0, 2);
                    String modified = message.substring(3);

                    switch (description) {

                        case "/s":
                            String upper = modified.toUpperCase();
                            String limitedString;

                            if (upper.length() > maxString) {
                                limitedString = upper.substring(0, maxString);
                            } else {
                                limitedString = upper;
                            }
                            broadcast(name, limitedString);

                            break;

                        case "/p":
                            String lower = modified.toLowerCase();
                            String privateString;

                            if (lower.length() > maxString) {
                                privateString = lower.substring(0, maxString);
                            } else {
                                privateString = lower;
                            }
                            broadcast(name, privateString);

                            break;

                        case "/q":
                            send(MessageStrings.EXIT_GAME);
                            broadcast(name, MessageStrings.PLAYER_LEFT_GAME);
                            clientSocket.close();
                            break;

                        default:
                            send(MessageStrings.INVALID_CHOICE);
                    }
                }
                if (message.isEmpty()) {
                    send(MessageStrings.EMPTY_MESSAGE);
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(new ServerInterruptedException("Help handler thread interrupted", e));
                }
                resetInputStream();

            } catch (Exception e) {
                throw new ClientHandlingException(HELP_COMMAND_ERROR, e);
            }

        }

        private void enteredRoom(RoomEnum roomEnum) {
            Room room = getCastle().getRoom(roomEnum);
            room.enterRoom(this);
            enteredRoom = roomEnum;
        }

        /**
         * Removes a random key from the client's key list.
         *
         * @param clientHandler the client handler whose key will be removed
         */
        public void removeKey(ClientHandler clientHandler) {
            List<Key> playerKeys = clientHandler.getKeys();

            //Check if the player has a key to lose
            if (playerKeys.isEmpty()) {
                send(NO_KEYS_TO_LOSE);
                return;
            }

            //Remove a key from the player
            Random random = new Random();
            int randomIndex = random.nextInt(playerKeys.size());
            Key lostKey = playerKeys.remove(randomIndex);

            String keyName = String.valueOf(Arrays.stream(RoomEnum.values())
                    .map(RoomEnum::getKey)
                    .filter(key -> key == lostKey)
                    .findFirst()
                    .orElse(null));

            send(LOST_KEY + ANSI_RED + keyName + ANSI_RESET);
        }

        private void handleRoomMenu(RoomEnum roomEnum) throws QuestionLoadException {
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    send(ENTERED_ROOM + roomEnum.getName());
                    enteredRoom(roomEnum);
                    questionsApp.quiz(roomEnum, this);
                    break;
                case "2":
                    navigate();
                    break;
                default:
                    invalidChoice();
                    displayRoomMenu(roomEnum);
                    break;
            }
        }


        /**
         * Displays the room menu based on the room type.
         *
         * @param room the room type
         */

        public void displayRoomMenu(RoomEnum room) throws QuestionLoadException {

            switch (room) {
                case BATHROOM:
                    send(Menu.getBathroomDoorMenu());
                    break;
                case KITCHEN:
                    send(Menu.getKitchenDoorMenu());
                    break;
                case GYM:
                    send(Menu.getGymDoorMenu());
                    break;
                case BEDROOM:
                    send(Menu.getBedroomDoorMenu());
                    break;
                case OFFICE:
                    send(Menu.getOfficeDoorMenu());
                    break;
                case LIVINGROOM:
                    send(Menu.getLivingRoomDoorMenu());
                    break;
                default:
                    send(Menu.getMainMenu());
                    handleMainMenu();
                    return;
            }
            handleRoomMenu(room);
        }

        private void resetInputStream() {
            try {
                while (in.ready()) {
                    in.read();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void leaveRoom() {
            if (enteredRoom != null) {
                Room room = getCastle().getRoom(enteredRoom);
                room.leaveRoom(this);
                enteredRoom = null;
                send(LEFT_ROOM);
            }
        }

        void handleMenu2() throws QuestionLoadException {
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    send(Menu.getGymDoorMenu());

                    handleRoomMenu(RoomEnum.GYM);
                    break;
                case "2":
                    displayMenu3();
                    break;
                case "3":
                    send(Menu.getOfficeDoorMenu());

                    handleRoomMenu(RoomEnum.OFFICE);
                    break;
                case "4":
                    navigate();
                    break;
                case "5":
                    displayKeys();
                    resetInputStream();
                    handleMainMenu();
                    break;
                default:
                    invalidMenuChoice();
                    handleMenu2();

                    break;
            }


        }

        private void invalidMenuChoice() {
            send(Server.invalidChoice());
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(new ServerInterruptedException("Invalid menu choice handler thread interrupted", e));
                }
                send(Menu.getMainMenu());

            }).start();
        }

        /**
         * Sends a MessageStrings to the client.
         *
         * @param message the MessageStrings to send
         */
        public void send(String message) {
            out.println(message);
        }

        /**
         * Reads the client's response.
         *
         * @return the client's response
         */
        public String getAnswer() {
            String message = null;
            try {
                message = in.readLine();
            } catch (IOException | NullPointerException e) {
                System.out.println("Erro a ler"); // Alterar
            }

            return message;
        }

        /**
         * Returns the client's name.
         *
         * @return the client's name
         */
        public String getName() {
            return name;
        }

        public void handleMenu3() throws QuestionLoadException {
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    send(Menu.getBedroomDoorMenu());
                    handleRoomMenu(RoomEnum.BEDROOM);
                    break;
                case "2":
                    send(Menu.getLivingRoomDoorMenu());
                    handleRoomMenu(RoomEnum.LIVINGROOM);
                    break;
                case "3":
                    displayMenu2();
                    break;
                case "4":
                    displayKeys();
                    resetInputStream();
                    handleMainMenu();

                    break;
                default:
                    invalidChoice();
                    handleMenu3();
                    break;
            }
        }

        private void displayKeys() {
            if (keys.isEmpty()) {
                send("You don't have any keys.");

            } else {
                StringBuilder message = new StringBuilder("Your keys : ");
                for (Key key : keys) {
                    message.append(key).append("\n");
                }
                send(message.toString());
            }

        }

        /**
         * Closes the client's socket connection.
         */
        public void close() {

            try {
                send(SHUTTING_DOWN_SOCKET);
                clientSocket.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private boolean isCommand(String message) {
            return message.startsWith("/");
        }

    }

}