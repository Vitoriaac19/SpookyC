package resources;

import java.util.List;
import java.util.Map;

public class Quiz {

    /**
     * Variable defined that is a map, that links strings (room names) to a list of questions
     */
    private Map<String, List<Question>>
            subjects;

    /**
     * Getter for the map to have access to the questions
     */
    public Map<String, List<Question>> getSubjects() {
        return subjects;
    }
}
