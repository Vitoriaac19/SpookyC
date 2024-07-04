package resources;

import com.google.gson.Gson;
import rooms.RoomEnum;
import server.Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * The QuestionsApp class is responsible for handling quiz questions from a JSON file,
 * presenting them to a user, and validating user responses.
 */
public class QuestionsApp {

    private final Gson gson = new Gson();
    private final Random random = new Random();

    /**
     * Prompts the user for an answer and validates the input.
     *
     * @param sender The client handler that interacts with the user.
     * @return The user's answer as an integer between 1 and 4, inclusive.
     */
    private int getUserAnswer(Server.ClientHandler sender) {
        int userAnswer = -1; //Default value
        boolean validInput = false;
        while (!validInput) {
            try {
                sender.send("Write your answer: ");
                String userInput = sender.getAnswer().trim();
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

    /**
     * Conducts a quiz for the specified room. Loads questions from a JSON file,
     * selects a random question, presents it to the user, and validates the response.
     *
     * @param roomEnum The room enumeration specifying the subject of the quiz.
     * @param sender   The client handler that interacts with the user.
     */
    public void quiz(RoomEnum roomEnum, Server.ClientHandler sender) {
        try (FileReader fileReader = new FileReader("src/resources/questions.json");
             BufferedReader reader = new BufferedReader(fileReader)) {

            Quiz quiz = gson.fromJson(reader, Quiz.class);
            List<Question> questions = quiz.getSubjects().get(roomEnum.getName().toUpperCase());

            if (questions == null || questions.isEmpty()) {
                sender.send("No questions available for the " + roomEnum.getName() + " room.");
                return;
            }

            int randomIndex = random.nextInt(questions.size());
            Question randomQuestion = questions.get(randomIndex);

            sender.send(ascii_art.Question.QUESTION);
            sender.send(randomQuestion.getQuestion());
            List<String> answers = randomQuestion.getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                sender.send((i + 1) + ". " + answers.get(i));
            }

            int userAnswer = getUserAnswer(sender);

            int correctAnswerIndex = randomQuestion.getCorrectAnswerIndex();
            boolean isCorrect = (userAnswer - 1 == correctAnswerIndex);

            if (isCorrect) {
                sender.send("Correct answer!");
                sender.addKey(RoomEnum.valueOf(roomEnum.name()).getKey());
            } else {
                sender.send("Your answer is incorrect! You'll lose a key and be kicked out from the room.");
                sender.removeKey(sender);
            }

            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    if (isCorrect) {
                        sender.leaveRoom();
                        sender.displayRoomMenu(roomEnum);
                    } else {
                        sender.leaveRoom();
                        sender.navigate();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
