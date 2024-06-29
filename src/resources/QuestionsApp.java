package resources;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class QuestionsApp {
    public static void main(String[] args) {
        Gson gson = new Gson();
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        try (FileReader reader = new FileReader("src/resources/questions.json")) {
            Quiz quiz = gson.fromJson(reader, Quiz.class);

            //Get a random question index
            int randomIndex = random.nextInt(quiz.getQuiz().size());
            Question randomQuestion = quiz.getQuiz().get(randomIndex);

            //Display the question on the console
            System.out.println("Question " + randomQuestion.getQuestion());
            List<String> answers = randomQuestion.getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                System.out.println((i + 1) + ". " + answers.get(i));
            }

            int userAnswer = -1;
            while (userAnswer < 1 || userAnswer > answers.size()) {
                try {
                    System.out.print("Write your answer: ");
                    userAnswer = scanner.nextInt();
                    if (userAnswer < 1 || userAnswer > answers.size()) {
                        System.out.println("Please enter a valid answer (1-" + answers.size() + ").");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Clear invalid input from the scanner
                }
            }

            if (userAnswer - 1 == randomQuestion.getCorrectAnswer()) {
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect.");
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
