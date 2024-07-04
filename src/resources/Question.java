package resources;

import java.util.List;

public class Question {
    /**
     * This class is used to manage the questions and their answers
     */
    private String question;
    private List<String> answers;
    private int correctAnswer;

    /**
     * A string that holds the text of the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * A list for the possible answers of the question
     */
    public List<String> getAnswers() {
        return answers;
    }

    /**
     * An integer that holds the index of the correct answer
     */
    public int getCorrectAnswerIndex() {
        return correctAnswer;
    }
}
