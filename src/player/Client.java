package player;

import exceptions.client.ClientConnectionException;
import exceptions.client.ClientShutdownException;
import music.Audio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

import static message.MessageStrings.*;

/**
 * The Client class represents a client that connects to a server socket,
 * sends messages to the server, and receives messages from the server.
 */
public class Client {

    private static final int PORT = 9000;
    private final Socket client;
    private final BufferedReader in;
    private final PrintWriter out;
    private final Audio audio;
    private boolean running = true;

    /**
     * Creates a new Client instance and starts the connection.
     *
     * @throws ClientConnectionException if there is an error connecting to the server.
     */
    public Client() throws ClientConnectionException {
        try {
            client = new Socket("localhost", PORT);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            audio = new Audio();

            Input input = new Input();
            Thread thread = new Thread(input);
            thread.start();

            listenForMessages();

        } catch (IOException e) {
            throw new ClientConnectionException(ERROR_CONNECTING_TO_SERVER, e);
        }
    }

    /**
     * Listens for messages from the server and handles them appropriately.
     */
    private void listenForMessages() {
        String text;
        try {
            while ((text = in.readLine()) != null) {
                System.out.println(text);
                if (text.startsWith(PLAY_SOUND)) {
                    String[] parts = text.split(" ");
                    if (parts.length > 1) {
                        String soundFile = parts[1];
                        URL sound = Audio.class.getResource(soundFile);
                        if (sound != null) {
                            audio.keepAudioPlaying(sound);
                        } else {
                            System.err.println(SOUND_FILE_NOT_FOUND + soundFile);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shuts down the client by closing the input and output streams and the socket.
     *
     * @throws ClientShutdownException if there is an error shutting down the client.
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
            throw new ClientShutdownException(ERROR_SHUTTING_DOWN_CLIENT, e);
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
                    if (message.equals(QUIT)) {
                        out.println(QUIT);
                        shutdown();
                    } else {
                        out.println(message);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(new ClientConnectionException(ERROR_READING_INPUT, e));
            } catch (ClientShutdownException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
