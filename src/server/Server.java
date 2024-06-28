package server;

import castle.Castle;
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

    public synchronized void start() {
        try {
            socket = new ServerSocket(9000);
            ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);


            while (running) {
                Socket clientSocket = socket.accept();
                clientHandler = new ClientHandler(clientSocket);
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

  /*  public void enterRoom(RoomType room) {
        clientHandler.enteredRoom = KITCHEN;wqdqdcqew
za    }*/

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

    //CLIENT HANDLER
    public class ClientHandler implements Runnable {
        private static BufferedReader in;
        private static PrintWriter out;
        private Socket clientSocket;
        private String name;
        private String message;
        private RoomEnum enteredRoom;
        private boolean isconnected;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            this.name = "";
            this.isconnected = false;

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

            send(welcomeToGame());
            displayMenu();

        }

        public void send(String message) {
            out.println(message);
        }

        public String welcomeToGame() {
            String message = "Welcome to the game " + name + "\nYou just entered in the Spooky Castle.";
            return message;
        }

        private void displayMenu() {
            out.println("+--------------------------------------------------+")
            ;
            out.println("| Choose your path. |");
            out.println("+--------------------------------------------------+");
            out.println("| [1] Bathroom |");
            out.println("| [2] Bedroom |");
            out.println("| [3] Kitchen |");
            out.println("| [4] Living room |");
            out.println("| [5] Office |");
            out.println("+--------------------------------------------------+");
            out.println("| [6] EXIT |");
            out.println("+--------------------------------------------------+");
            out.println("> ");
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

        public void close() {
//olas
            try {
                send("shutting down your socket");
                clientSocket.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
}