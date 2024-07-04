package player;

import exceptions.client.ClientConnectionException;
import exceptions.client.ClientShutdownException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The Client class represents a client that connects to a server socket,
 * sends messages to the server, and receives messages from the server.
 */
public class Client {

    private static final int PORT = 9000;
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean running = true;

    /**
     * The main method to run the Client application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.run();
        } catch (ClientConnectionException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Starts the client, connects to the server, and handles incoming and outgoing messages.
     */

    public void run() throws ClientConnectionException {

        try {
            client = new Socket("localhost", PORT);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));


            Input input = new Input();
            Thread thread = new Thread(input);
            thread.start();

            String text;
            while ((text = in.readLine()) != null) {
                System.out.println(text);
            }

        } catch (IOException e) {
            throw new ClientConnectionException("Error while connecting to the server", e);
        }
    }


    /**
     * Shuts down the client by closing the input and output streams and the socket.
     */
    public void shutdown() throws ClientShutdownException {

        running = false;
        try {
            in.close();
            out.close();
            if (!client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            throw new ClientShutdownException("Error during shutdown", e);
        }
    }

    /**
     * The Input class handles user input from the console and sends messages to the server.
     */
    class Input implements Runnable {
        @Override
        public void run() {
            try {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                while (running) {
                    String message = inputReader.readLine();
                    if (message.equals("quit")) {
                        out.println("quit");

                        shutdown();
                    } else {
                        out.println(message);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(new ClientConnectionException("Error while reading input", e));
            } catch (ClientShutdownException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
