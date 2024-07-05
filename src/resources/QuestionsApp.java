package resources;

import com.google.gson.Gson;
import exceptions.quiz.InvalidAnswerException;
import exceptions.quiz.QuestionLoadException;
import exceptions.quiz.QuizProcessingException;
import message.MessageStrings;
import music.Audio;
import rooms.RoomEnum;
import server.Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import static message.MessageStrings.*;

/**
 * The QuestionsApp class is responsible for handling quiz questions from a JSON file,
 * presenting them to a user, and validating user responses.
 */
public class QuestionsApp {

    private final Gson gson = new Gson();
    private final Random random = new Random();
    private Audio music = new Audio();

    /**
     * Prompts the user for an answer and validates the input.
     *
     * @param sender The client handler that interacts with the user.
     * @return The user's answer as an integer between 1 and 4, inclusive.
     * @throws InvalidAnswerException If the user's answer is invalid.
     */
    private int getUserAnswer(Server.ClientHandler sender) throws InvalidAnswerException {
        int userAnswer = -1; //Default value
        boolean validInput = false;
        while (!validInput) {
            try {
                sender.send(MessageStrings.PROMPT_USER_ANSWER);
                String userInput = sender.getAnswer().trim();

                if (userInput.isEmpty()) {
                    sender.send(MessageStrings.INVALID_NUMERIC_ANSWER);
                    continue; // Prompt again for input
                }

                userAnswer = Integer.parseInt(userInput);

                if (userAnswer >= 1 && userAnswer <= 4) {
                    validInput = true;
                } else {
                    sender.send(MessageStrings.INVALID_NUMERIC_ANSWER);
                }
            } catch (NumberFormatException e) {
                sender.send(MessageStrings.INVALID_ANSWER_FORMAT);
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
     * @throws QuestionLoadException If there is an error loading questions from the JSON file.
     */
    public void quiz(RoomEnum roomEnum, Server.ClientHandler sender) throws QuestionLoadException {
        try (FileReader fileReader = new FileReader("src/resources/questions.json");
             BufferedReader reader = new BufferedReader(fileReader)) {

            Quiz quiz = gson.fromJson(reader, Quiz.class);
            List<Question> questions = quiz.getSubjects().get(roomEnum.getName().toUpperCase());

            if (questions == null || questions.isEmpty()) {
                sender.send(String.format(MessageStrings.NO_QUESTIONS_AVAILABLE, roomEnum.getName()));
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
                URL correctSound = Audio.class.getResource(RIGHT_ANSWER_SOUND);
                music.playOnce(correctSound);
                sender.send(MessageStrings.CORRECT_ANSWER);
                sender.addKey(RoomEnum.valueOf(roomEnum.name()).getKey());
            } else {
                URL incorrectSound = Audio.class.getResource(WRONG_ANSWER_SOUND);
                music.playOnce(incorrectSound);
                sender.send(MessageStrings.INCORRECT_ANSWER);
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
                } catch (InterruptedException | QuestionLoadException e) {
                    throw new RuntimeException(new QuizProcessingException(QUIZ_PROCESSING_THREAD_INTERRUPTED, e));
                }
            }).start();

        } catch (IOException e) {
            throw new QuestionLoadException(ERROR_LOADING_QUESTIONS_FROM_JSON_FILE, e);
        } catch (InvalidAnswerException e) {
            sender.send(e.getMessage());
        }
    }
}
