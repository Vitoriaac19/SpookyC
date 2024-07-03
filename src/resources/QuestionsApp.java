package resources;

import com.google.gson.Gson;
import rooms.RoomEnum;
import server.Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class QuestionsApp {

    private Gson gson = new Gson();
    private Random random = new Random();
    private Server server;

    private int getUserAnswer(Server.ClientHandler sender) {
        int userAnswer = -1;
        try {
            while (userAnswer < 1 || userAnswer > 4) { // Assuming answers range from 1 to 4
                sender.send("Write your answer: ");
                String userInput = sender.getAnswer(); // Read input from client
                userAnswer = Integer.parseInt(userInput.trim());
                if (userAnswer < 1 || userAnswer > 4) {
                    sender.send("Please enter a valid answer (1-4).");
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
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
            sender.send(randomQuestion.getQuestion());
            List<String> answers = randomQuestion.getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                sender.send((i + 1) + ". " + answers.get(i));
            }

            // Get user's answer
            int userAnswer = getUserAnswer(sender);

            // Validate user's answer
            int correctAnswerIndex = randomQuestion.getCorrectAnswerIndex();
            boolean isCorrect = (userAnswer - 1 == correctAnswerIndex); // Adjusting to zero-based index

            // Process the result
            if (isCorrect) {
                sender.send("Correct answer!");
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        sender.displayRoomMenu(roomEnum);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

            } else {
                sender.send("Wrong answer! You'll be kicked out from the room.");
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        sender.navigate();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            return isCorrect;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
        }
    }

}
