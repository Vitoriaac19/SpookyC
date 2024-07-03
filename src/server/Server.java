package server;

import ascii_art.BigWinner;
import ascii_art.SpookyCastle;
import castle.Castle;
import menus.Menu;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int MAX_CLIENTS = 2;
    private List<ClientHandler> clientHandlers;
    private ServerSocket socket;
    private boolean running;
    private Castle castle;

    public Server() {
        clientHandlers = new ArrayList<>(MAX_CLIENTS);
        castle = new Castle(this);
        running = true;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();

    }

    private static String invalidChoice() {
        return "A cold shiver runs down your spine... You've wandered astray. Return to the entrance hall before the darkness takes hold...";
    }


    public synchronized void start() {
        try {
            socket = new ServerSocket(9002);
            ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);


            while (running) {
                Socket clientSocket = socket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                if (acceptPlayer(clientHandler)) {
                    pool.submit(clientHandler);
                } else {
                    clientHandler.close();
                }

                if (clientHandlers.size() == MAX_CLIENTS) {
                    System.out.println("Maximum number of players reached");
                    startGame();
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void addClient(ClientHandler clientHandler) {
        clientHandlers.add(clientHandler);
        System.out.println(getClientHandlers().size());
    }


    public void startGame() {
        System.out.println("Starting game");
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.startGame();
        }
    }

    public List<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }


    public synchronized boolean acceptPlayer(ClientHandler clientHandler) {
        if (clientHandlers.size() < MAX_CLIENTS) {
            addClient(clientHandler);
            System.out.println("Player connected . Total players: " + clientHandlers.size());

            clientHandler.send("Waiting for other players");

            return true;
        } else {
            clientNotAccepted(clientHandler, "We dont have space yet. Please try again later.");
            return false;
        }
    }

    public void clientNotAccepted(ClientHandler clientHandler, String message) {
        clientHandler.send(message);
    }

    private Castle getCastle() {
        return castle;
    }

    public void broadcast(String name, String message) {
        clientHandlers.stream()
                .filter(clientHandler -> !clientHandler.getName().equals(name))
                .forEach(clientHandler -> clientHandler.send(name + ": " + message));
    }

    //CLIENT HANDLER
    public class ClientHandler implements Runnable {
        boolean isconnected;
        private BufferedReader in;
        private PrintWriter out;
        private Socket clientSocket;
        private String name;
        private List<Key> keys;
        private RoomEnum enteredRoom;
        private Server server;
        private String message;
        private QuestionsApp questionsApp = new QuestionsApp();

        //TODO String mais compacta do que String message
        public ClientHandler(Socket clientSocket, Server server) {
            this.clientSocket = clientSocket;

            this.name = "";
            this.isconnected = false;
            this.server = server;
            this.keys = new ArrayList<>();

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }


        public List<Key> getKeys() {
            return keys;
        }

        public void addKey(Key key) {
            keys.add(key);
        }

        private void displayMenu2() {
            send(Menu.getMenu2());
            handleMenu2();
        }

        private void displayMenu3() {
            send(Menu.getMenu3());
            handleMenu3();
        }


        public void startGame() {
            new Thread(() -> {
                send(SpookyCastle.SPOOKY_CASTLE);
                send("Enter your name: ");
                name = getAnswer();
                while (!name.matches("[a-zA-Z]+")) {
                    send("Please, enter your name using only letters: ");
                    name = getAnswer();
                }
                System.out.println(name + " has joined the game");
                send(Menu.getWelcomeMessage());
                navigate();
            }).start();
        }


        @Override
        public void run() {

            while (!isconnected) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        public void navigate() {
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

        private void handleExitMenu() {
            send(Menu.getExitMenu());
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    leaveCastle();
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);


                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();

                    break;
                case "2":
                    new Thread(() -> {
                        try {
                            Thread.sleep(1000);
                            navigate();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
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

        private void handleMainMenu() {
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
                throw new RuntimeException(e);
            }
        }

        private void leaveCastle() {
            if (hasAllKeys()) {
                send(BigWinner.BIG_WINNER);
                send("You have successfully left the castle. Congratulations , you won!");
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    close();
                }).start();
            } else {
                send("You cannot leave the castle. You are missing some keys");
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    send(Menu.getMainMenu());
                    handleMainMenu();
                }).start();

            }
        }

        public void handleHelp() throws IOException {
            int maxString = 70;
            try {
                message = in.readLine();

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
                            send("You are ending your game. See you again soon. Bye!");
                            broadcast(name, "left the game");
                            clientSocket.close();
                            break;

                        default:
                            invalidChoice();
                    }
                }
                if (message.equals("")) {
                    send("Empty message!");
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                resetInputStream();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        private void enteredRoom(RoomEnum roomEnum) {
            Room room = server.getCastle().getRoom(roomEnum);
            room.enterRoom(this);
            enteredRoom = roomEnum;
            List<ClientHandler> clientsInRoom = room.getClients();
            if (clientsInRoom.size() > 1) {
                for (ClientHandler client : clientsInRoom) {
                    if (client != this) {
                        send("Another player has entered in the room . Prepare for a game of Rock-Paper-Scissors");
                        client.send("Another player has entered in the room . Prepare for a game of Rock-Paper-Scissors");
                        resetInputStream();
                        startRockPaperScissors(client);
                        break;
                    }
                }
            }
         /*   send("Ola");
            send("You entered " + roomEnum.getName());
            send("DEBUG: " + name + " entered room " + roomEnum.getName());
            send("DEBUG: Current room clients: " + room.getClients());


          */

        }

        private void startRockPaperScissors(ClientHandler opponent) {
            try {
                resetInputStream();
                // opponent.resetInputStream();

                send(Menu.getRockPaperScissorsMenu());
                opponent.send(Menu.getRockPaperScissorsMenu());

                String playerChoice = getAnswer();
                send("You have chosen " + choiceToString(playerChoice));

                opponent.send("Other player has already made a choice");
                String opponentChoice = opponent.getAnswer();
                opponent.send("You have chosen " + choiceToString(opponentChoice));

                int result = determineWinner(playerChoice, opponentChoice);

                if (result == 1) {
                    send("You won! You receive a key from this room as a reward.");
                    opponent.send("You lost! Your opponent receives a key from this room as a reward.");
                    //addKey(RoomEnum.valueOf(enteredRoom.name()).getKey());
                    stealKey(opponent);
                    opponent.leaveRoom();
                } else if (result == -1) {
                    send("You lost! Your opponent receives a key from this room as a reward.");
                    opponent.send("You won! You receive a key from this room as a reward.");
                    //opponent.addKey(RoomEnum.valueOf(enteredRoom.name()).getKey());
                    leaveRoom();
                } else {
                    send("It's a draw!");
                    opponent.send("It's a draw!");
                }

                Thread.sleep(3000);

                if (result == 1) {
                    displayRoomMenu(enteredRoom);
                } else if (result == -1) {
                    send(Menu.getMainMenu());
                    resetInputStream();
                    handleMainMenu();
                } else {
                    new Thread(() -> {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        startRockPaperScissors(opponent);
                    }).start();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void stealKey(ClientHandler clientHandler) {
            Random random = new Random();
            List<Key> opponentKeys = clientHandler.getKeys();
            if (opponentKeys.size() == 0) {
                send("You don't have any keys to steal.");
                return;
            }
            int randIndex = random.nextInt(opponentKeys.size());
            Key stolenKey = opponentKeys.remove(randIndex);

            send("You have stolen a key from " + clientHandler.getName() + ": " + stolenKey);
            clientHandler.send("Your key has been stolen: " + stolenKey);
        }

        private void handleRoomMenu(RoomEnum roomEnum) {
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    send("You entered the " + roomEnum.getName());
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

        public void displayRoomMenu(RoomEnum room) {
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

        private int determineWinner(String playerChoice, String opponentChoice) {

            int playerChoiceInt = Integer.parseInt(playerChoice);
            int opponentChoiceInt = Integer.parseInt(opponentChoice);

            //Rock vai ser 1 , paper vai ser 2 e Scissors vai ser 3
            if (playerChoiceInt == opponentChoiceInt) {

                return 0;  //empate
            }

            if (playerChoiceInt == 1 && opponentChoiceInt == 3 || playerChoiceInt == 2 && opponentChoiceInt == 1 || playerChoiceInt == 3 && opponentChoiceInt == 2) {
                return 1; //venceu
            }
            return -1; //perdeu
        }

        private String choiceToString(String choice) {
            switch (choice) {
                case "1":
                    return "Rock";
                case "2":
                    return "Paper";
                case "3":
                    return "Scissors";
                default:
                    return "Invalid choice";
            }
        }

        private void leaveRoom() {
            if (enteredRoom != null) {
                Room room = server.getCastle().getRoom(enteredRoom);
                room.leaveRoom(this);
                enteredRoom = null;
                send("You left the room");
            }
        }

        void handleMenu2() {
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
                    throw new RuntimeException(e);
                }
                send(Menu.getMainMenu());

            }).start();
        }

        public void send(String message) {
            out.println(message);
        }

        public String getAnswer() {
            String message = null;
            try {
                message = in.readLine();
            } catch (IOException | NullPointerException e) {
                System.out.println("Erro a ler"); // Alterar
            }

            return message;
        }

        public String getName() {
            return name;
        }

        public void handleMenu3() {
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
                send("You don't have any keys , you going back to the main menu");

                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    send(Menu.getMainMenu());

                }).start();
            } else {
                String message = "Your keys : ";
                for (Key key : keys) {
                    message = message + key + "\n";
                }
                send(message);
            }

        }

        public void close() {

            try {
                send("shutting down your socket");
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