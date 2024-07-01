package resources;

import java.util.List;

public class Question {
    private String question;
    private List<String> answers;
    private int correctAnswer;

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}
