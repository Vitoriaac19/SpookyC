package resources;

import java.util.List;

/**
 * The {@code Question} class represents a single question in a quiz.
 * It contains the question text, a list of possible answers, and the index of the correct answer.
 */
public class Question {

    /**
     * The text of the question.
     */
    private String question;
    /**
     * A list of possible answers to the question.
     */
    private List<String> answers;
    /**
     * The index of the correct answer in the {@code answers} list.
     * This index is zero-based.
     */
    private int correctAnswer;

    /**
     * Returns the text of the question.
     *
     * @return a {@code String} representing the question text.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the list of possible answers to the question.
     *
     * @return a {@code List<String>} containing the possible answers.
     */
    public List<String> getAnswers() {
        return answers;
    }

    /**
     * Returns the index of the correct answer in the {@code answers} list.
     *
     * @return an {@code int} representing the zero-based index of the correct answer.
     */
    public int getCorrectAnswerIndex() {
        return correctAnswer;
    }
}
