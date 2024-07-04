package resources;

import com.google.gson.Gson;
import music.Audio;
import rooms.RoomEnum;
import server.Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class QuestionsApp {

    private final Gson gson = new Gson();
    private final Random random = new Random();
    private Audio music = new Audio();

    /**
     * Method to get answers from the player, we will need a value between 1 and 4
     * Try-catch to manage the invalid inputs the player can give
     * Read input from client and trim whitespaces
     * If the answer is not between 1 and 4, send an error message
     * If the input is not a number, send an error message
     * Return player's answer
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
     * This method have the purpose of displaying the questions on each room
     * Using the FileReader and BufferedReader it's possible to read the questions from a JSON file
     * Use the Gson library to parse the JSON file
     * Selection of a random question from the room the player enters
     * Display the question animation, the question and options available
     * Appears the numbers before the answer options and are the ones the player can choose
     * Get user's answer
     * Validate user's answer, adjusting the index to the index in the file
     * Process the answer result and give to the player a key if the answer is correct or remove a random key if the answer is wrong
     * If the player gives a wrong answer will be kicked to the main menu
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
                URL correctSound = Audio.class.getResource("right-answer.wav");
                music.playOnce(correctSound);
                sender.send("Correct answer!");
                sender.addKey(RoomEnum.valueOf(roomEnum.name()).getKey());
            } else {
                URL incorrectSound = Audio.class.getResource("wrong-answer.wav");
                music.playOnce(incorrectSound);
                sender.send("Your answer is incorrect! You'll lose a key and be kicked out from the room.");
                sender.removeKey(sender);
            }

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
    }
}