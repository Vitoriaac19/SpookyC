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

            // List available subjects
            System.out.println("Available subjects:");
            for (String subject : quiz.getSubjects().keySet()) {
                System.out.println("- " + subject);
            }

            // Select a subject , TODO FIND A WAY TO CONNECT THE QUESTIONS PRESENTED TO THE ROOM WHERE THE PLAYER IS
            String selectedSubject = "Bathroom";
            while (selectedSubject == null) {
                System.out.print("Enter a subject: ");
                String inputSubject = scanner.nextLine();
                if (quiz.getSubjects().containsKey(inputSubject)) {
                    selectedSubject = inputSubject;
                } else {
                    System.out.println("Subject not found. Please enter a valid subject.");
                }
            }

            List<Question> questions = quiz.getSubjects().get(selectedSubject);
            int randomIndex = random.nextInt(questions.size());
            Question randomQuestion = questions.get(randomIndex);

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
