package resources;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class QuestionsApp {
    public static void main(String[] args) {
        Gson gson = new Gson();
        Scanner scanner = new Scanner(System.in);

        try (FileReader reader = new FileReader("src/resources/questions.json")) {
            Quiz quiz = gson.fromJson(reader, Quiz.class);

            for (int i = 0; i < quiz.getQuiz().size(); i++) {
                Question question = quiz.getQuiz().get(i);
                System.out.println("Question " + (i + 1) + ": " + question.getQuestion());
                List<String> answers = question.getAnswers();
                for (int j = 0; j < answers.size(); j++) {
                    System.out.println((j + 1) + ". " + answers.get(j));
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

                if (userAnswer - 1 == question.getCorrectAnswer()) {
                    System.out.println("Correct!\n");
                } else {
                    System.out.println("Incorrect.\n");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
