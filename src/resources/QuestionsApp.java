package resources;

import com.google.gson.Gson;
import rooms.RoomEnum;
import server.Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QuestionsApp {

    // Helper method to track the first correct answer for each room
    private static final Map<RoomEnum, Boolean> firstCorrectAnswerMap = new HashMap<>();
    private final Gson gson = new Gson();
    private final Random random = new Random();

    private int getUserAnswer(Server.ClientHandler sender) {
        int userAnswer = -1;
        boolean validInput = false;
        while (!validInput) {
            try {
                sender.send("Write your answer: ");
                String userInput = sender.getAnswer().trim(); // Read input from client and trim whitespace
                userAnswer = Integer.parseInt(userInput);

                if (userAnswer >= 1 && userAnswer <= 4) {
                    validInput = true;
                } else {
                    sender.send("Please enter a valid answer (1-4).");
                }
            } catch (NumberFormatException e) {
                sender.send("Please enter a valid numeric answer (1-4).");
            }
        }
        return userAnswer;
    }

    public boolean quiz(RoomEnum roomEnum, Server.ClientHandler sender) {
        try (FileReader fileReader = new FileReader("src/resources/questions.json");
             BufferedReader reader = new BufferedReader(fileReader)) {

            Quiz quiz = gson.fromJson(reader, Quiz.class);
            List<Question> questions = quiz.getSubjects().get(roomEnum.getName().toUpperCase());

            if (questions == null || questions.isEmpty()) {
                sender.send("No questions available for the " + roomEnum.getName() + " room.");
                return false;
            }

            int randomIndex = random.nextInt(questions.size());
            Question randomQuestion = questions.get(randomIndex);

            // Display the question and options
            sender.send(ascii_art.Question.QUESTION);
            sender.send(randomQuestion.getQuestion());
            List<String> answers = randomQuestion.getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                sender.send((i + 1) + ". " + answers.get(i));
            }

            // Get user's answer
            int userAnswer = getUserAnswer(sender);

            // Validate user's answer
            int correctAnswerIndex = randomQuestion.getCorrectAnswerIndex();
            boolean isCorrect = (userAnswer - 1 == correctAnswerIndex); // Adjusting the index

            // Process the result
            if (isCorrect) {
                // Check if this is the first correct answer
                if (isFirstCorrectAnswer(roomEnum)) {
                    sender.send("Correct answer! You win a key!");

                    // Give the player a key
                    sender.addKey(RoomEnum.valueOf(roomEnum.name()).getKey());
                } else {
                    sender.send("Correct answer! You win nothing, someone already won a key.");
                }

                // Set that a correct answer has been given for this room
                setFirstCorrectAnswer(roomEnum);

            } else {
                sender.send("Your answer is incorrect! You'll be kicked out from the room.");
            }

            // Navigate or display room menu after a delay
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    if (isCorrect) {
                        sender.displayRoomMenu(roomEnum);
                        ;
                    } else {
                        sender.navigate();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private synchronized boolean isFirstCorrectAnswer(RoomEnum roomEnum) {
        return !firstCorrectAnswerMap.containsKey(roomEnum) || !firstCorrectAnswerMap.get(roomEnum);
    }

    private synchronized void setFirstCorrectAnswer(RoomEnum roomEnum) {
        firstCorrectAnswerMap.put(roomEnum, true);
    }

}
