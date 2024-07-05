package resources;

import java.util.List;
import java.util.Map;

/**
 * The {@code Quiz} class represents a collection of subjects,
 * where each subject is associated with a list of questions.
 */
public class Quiz {

    /**
     * A map linking subject names to their corresponding list of questions.
     * The key is a {@code String} representing the name of the subject, and the value
     * is a {@code List<Question>} representing the questions for that subject.
     */
    private Map<String, List<Question>>
            subjects;

    /**
     * Returns the map of subjects to their corresponding list of questions.
     *
     * @return a {@code Map<String, List<Question>>} containing the subjects and their questions.
     */
    public Map<String, List<Question>> getSubjects() {
        return subjects;
    }
}
