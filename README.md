
#  Spooky Castle

This code implements a server for a multiplayer text-based game set in a spooky castle. The server manages client connections, game initialization, and interactions between players and the game environment. Here’s a breakdown of its main components and functionalities:

## Authors

- [@ruitiago23](https://github.com/ruitiago23)
- [@PauloSoVieira](https://github.com/PauloSoVieira)
- [@Vitoriaac19](https://github.com/Vitoriaac19)



# Spooky Castle Game Server

This project implements a multiplayer text-based game server where players explore a haunted castle, solve quizzes, and collect keys to escape. The server manages client connections and game logic, ensuring a seamless and engaging experience for up to two players simultaneously.

## Overview of the Code

This code implements a server for a multiplayer text-based game set in a spooky castle. The server manages client connections, game initialization, and interactions between players and the game environment. Here’s a breakdown of its main components and functionalities:

### Server Class:

**Attributes:**
- **`MAX_CLIENTS`:** Maximum number of clients allowed to connect (set to 2).
- **`clientHandlers`:** List to keep track of connected clients.
- **`running`:** Boolean indicating if the server is running.
- **`castle`:** Instance of the `Castle` class that represents the game environment.
- **`socket`:** The server socket used to accept client connections.

**Methods:**
- **`main()`:** Starts the server.
- **`start()`:** Initializes the server socket, accepts client connections, and manages client handlers.
- **`addClient()`:** Adds a client handler to the list.
- **`startGame()`:** Starts the game for all connected clients.
- **`acceptPlayer()`:** Accepts a new player if the maximum number of clients hasn't been reached.
- **`clientNotAccepted()`:** Sends a message to clients who cannot be accepted.
- **`broadcast()`:** Sends a message to all clients except the sender.

### ClientHandler Class:

**Attributes:**
- **`in`:** `BufferedReader` for reading client input.
- **`out`:** `PrintWriter` for sending messages to the client.
- **`clientSocket`:** The client’s socket.
- **`keys`:** List of keys the client has collected.
- **`questionsApp`:** Instance of `QuestionsApp` for handling quiz questions.
- **`isConnected`:** Boolean indicating if the client is connected.
- **`name`:** Client’s name.
- **`enteredRoom`:** Enum indicating the room the client has entered.
- **`music`:** Instance of `Audio` for playing background music.

**Methods:**
- **`startGame()`:** Initiates the game for the client.
- **`navigate()`:** Guides the client through the main menu.
- **`handleMainMenu()`:** Handles the client’s choices in the main menu.
- **`displayRoomMenu()`:** Displays the room-specific menu.
- **`handleRoomMenu()`:** Handles interactions in a specific room.
- **`leaveCastle()`:** Handles the logic for leaving the castle.
- **`send()`:** Sends a message to the client.
- **`getAnswer()`:** Reads the client’s response.
- **`close()`:** Closes the client’s socket connection.
## Summary for README

This project implements a multiplayer text-based game server where players explore a spooky castle, solve quizzes, and collect keys to escape. The server manages client connections and game logic, ensuring a seamless and engaging experience for up to two players simultaneously.

**Features:**
- **Handles multiple client connections.**
- **Manages game state and player interactions.**
- **Provides a rich game environment with different rooms and challenges.**
- **Includes audio playback for an immersive game experience.**
- **Implements robust error handling and client-server communication.**
