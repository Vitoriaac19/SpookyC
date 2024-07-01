package server;

import castle.Castle;
import menus.Menu;
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
            socket = new ServerSocket(9000);
            ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);


            while (running) {
                Socket clientSocket = socket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                acceptPlayer(clientHandler);
                pool.submit(clientHandler);


            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void addClient(ClientHandler clientHandler) {
        clientHandlers.add(clientHandler);
        System.out.println(getClientHandlers().size());
    }


    public List<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }


    public void acceptPlayer(ClientHandler clientHandler) {
        if (clientHandlers.size() < MAX_CLIENTS) {
            addClient(clientHandler);
            clientHandler.isconnected = true;
        } else {
            clientNotAccepted(clientHandler, "We dont have space yet. Please try again later.");
            clientHandler.close();
        }
    }

    public void clientNotAccepted(ClientHandler clientHandler, String message) {
        clientHandler.send(message);
    }

    private Castle getCastle() {
        return castle;
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


        @Override
        public void run() {

            send("enter your name: ");
            name = getAnswer();
            System.out.println(name + " has joined the game");

            //   clientHandlers.add(this); //VERIFICAR

            send(Menu.getWelcomeMessage());
            navigate();

        }

        private void navigate() {
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
                case "9":
                    handleExitMenu();
                    break;
                default:
                    invalidMenuChoice();
                    handleMainMenu();
                    break;

            }
        }

        private void leaveCastle() {
            if (hasAllKeys()) {
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
            send(Menu.getRockPaperScissorsMenu());
            opponent.send(Menu.getRockPaperScissorsMenu());

            String playerChoice = getAnswer();
            send("You have chosen " + choiceToString(playerChoice));
            opponent.send("Other player has already made a choice");

            String opponentChoice = opponent.getAnswer();
            opponent.send("You have chosen " + choiceToString(opponentChoice));
            send("Other player has already made a choice");

            int result = determineWinner(playerChoice, opponentChoice);
            if (result == 1) {
                send("You won! You receive a key from this room as a reward.");
                opponent.send("You lost! Your opponent receives a key from this room as a reward.");
                addKey(RoomEnum.valueOf(enteredRoom.name()).getKey());
                opponent.leaveRoom();
            } else if (result == -1) {
                send("You lost! Your opponent receives a key from this room as a reward.");
                opponent.send("You won! You receive a key from this room as a reward.");
                opponent.addKey(RoomEnum.valueOf(enteredRoom.name()).getKey());
                leaveRoom();
            } else {
                send("It's a draw!");
                opponent.send("It's a draw!");
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startRockPaperScissors(opponent);
                }).start();
                return;
            }

            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (result == 1) {
                    displayRoomMenu(enteredRoom);
                } else {
                    send(Menu.getMainMenu());
                    resetInputStream();
                    handleMainMenu();
                }
            }).start();

            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (result == -1) {
                    opponent.displayRoomMenu(opponent.enteredRoom);
                } else {
                    opponent.send(Menu.getMainMenu());
                    opponent.resetInputStream();
                    opponent.handleMainMenu();
                }
            }).start();
        }

        private void handleRoomMenu(RoomEnum roomEnum) {
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    send("You entered the " + roomEnum.getName());
                    enteredRoom(roomEnum);
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

        private void displayRoomMenu(RoomEnum room) {
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


    }

}