package server;

import castle.Castle;
import menus.Menu;
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
    private ClientHandler clientHandler;

    public Server() {
        clientHandlers = new ArrayList<>(MAX_CLIENTS);
        castle = new Castle(this);
        running = true;
        System.out.println(castle.getRooms());
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();

    }

    private static void invalidChoice() {
        System.out.println(("Invalid choice.Please try again"));
    }

    public synchronized void start() {
        try {
            socket = new ServerSocket(9000);
            ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);


            while (running) {
                Socket clientSocket = socket.accept();
                clientHandler = new ClientHandler(clientSocket);
                addClient(clientHandler);
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

  /*  public void enterRoom(RoomType room) {
        clientHandler.enteredRoom = KITCHEN;

    }

   */

    public List<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    private void displayMenu2() {
        clientHandler.send(Menu.getMenu2());
        clientHandler.handleMenu2();
    }

    private void displayBathroomMenu() {
        clientHandler.send(Menu.getBathroomDoorMenu());
        clientHandler.handleBathroomMenu();

    }

    private void displayKitchenMenu() {
        clientHandler.send(Menu.getKitchenDoorMenu());
        clientHandler.handleKitchenDoorMenu();
    }

    private void displayGymMenu() {
        clientHandler.send(Menu.getGymDoorMenu());
        clientHandler.handleGymDoorMenu();
    }

    private void displayMenu3() {
        clientHandler.send(Menu.getMenu3());
        clientHandler.handleMenu3();
    }

    private void displayBedroomMenu() {
        clientHandler.send(Menu.getBedroomDoorMenu());
        clientHandler.handleBedroomDoorMenu();
    }

    private void displayOfficeMenu() {
        clientHandler.send(Menu.getOfficeDoorMenu());
        clientHandler.handleOfficeDoorMenu();
    }

    private void displayLivingRoomMenu() {
        clientHandler.send(Menu.getLivingRoomDoorMenu());
        clientHandler.handleLivingRoomDoorMenu();
    }

    //CLIENT HANDLER
    public class ClientHandler implements Runnable {
        private static BufferedReader in;
        private static PrintWriter out;
        private Socket clientSocket;
        private String name;
        private String message;
        private RoomEnum enteredRoom;


        //TODO String mais compacta do que String message
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            this.name = "";

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public void run() {
            send("enter your name: ");
            name = getAnswer();
            System.out.println(name + " has joined the game");

            clientHandlers.add(this); //VERIFICAR

            send(Menu.getWelcomeMessage());
            navigate();

        }

        private void navigate() {
            send(Menu.getMainMenu());
            handleMainMenu();
        }

        private void handleMainMenu() {
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    displayBathroomMenu();
                    break;
                case "2":
                    displayMenu2();
                    break;
                case "3":
                    displayKitchenMenu();
                    break;
                default:
                    invalidChoice();
                    handleMainMenu();
                    break;

            }
        }

        private void handleKitchenDoorMenu() {
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    send("You entered in Kitchen");
                    //Metodo de question
                    break;
                case "2":
                    navigate();

                    break;
                default:
                    invalidChoice();
                    displayKitchenMenu();
                    break;
            }
        }


        private void handleBathroomMenu() {
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    send("You entered in the Bathroom");
                    // Falta metodo de chamar question
                    break;
                case "2":
                    navigate();
                    break;
                default:
                    invalidChoice();
                    displayBathroomMenu();
                    break;
            }
        }


        private void handleMenu2() {
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    displayGymMenu();
                    break;
                case "2":
                    displayMenu3();
                    break;
                case "3":
                    displayOfficeMenu();
                    break;
                case "4":
                    navigate();
                    break;
                default:
                    invalidChoice();
                    handleMenu2();
                    break;
            }


        }

        public void send(String message) {
            out.println(message);
        }

        public String getAnswer() {
            String message = null;
            try {
                message = in.readLine();
            } catch (IOException | NullPointerException e) {
                System.out.println("aa"); // Alterar
            } finally {
                if (message == null) {
                    System.out.println("aa"); // Alterar
                }
            }
            return message;
        }

        public String getName() {
            return name;
        }

        public void handleGymDoorMenu() {
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    send("You entered in the Gym");
                    //Metodo de question
                    break;
                case "2":
                    displayMenu2();
                    break;
                default:
                    invalidChoice();
                    displayGymMenu();
                    break;
            }
        }

        public void handleMenu3() {
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    displayBedroomMenu();
                    break;
                case "2":
                    displayLivingRoomMenu();
                    break;
                case "3":
                    displayMenu2();
                    break;
                default:
                    invalidChoice();
                    displayMenu3();
                    break;
            }
        }

        public void handleBedroomDoorMenu() {
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    send("You entered in the Bedroom");
                    //Metodo de question
                    break;
                case "2":
                    displayMenu3();
                    break;
                default:
                    invalidChoice();
                    displayBedroomMenu();
                    break;
            }
        }

        public void handleLivingRoomDoorMenu() {
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    send("You entered in the Living Room");
                    //Metodo de question
                    break;
                case "2":
                    displayMenu3();
                    break;
                default:
                    invalidChoice();
                    displayLivingRoomMenu();
                    break;
            }
        }

        public void handleOfficeDoorMenu() {
            String choice = getAnswer();
            switch (choice) {
                case "1":
                    send("You entered in the Office");
                    //Metodo de question
                    break;
                case "2":
                    displayMenu2();
                    break;
                default:
                    invalidChoice();
                    displayOfficeMenu();
                    break;
            }
        }
    }

}