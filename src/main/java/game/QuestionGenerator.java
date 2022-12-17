package game;

import java.util.LinkedList;

public class QuestionGenerator {

    private final LinkedList popQuestions = new LinkedList<Question>();
    private final LinkedList scienceQuestions = new LinkedList<Question>();
    private final LinkedList sportsQuestions = new LinkedList<Question>();
    private final LinkedList rockQuestions = new LinkedList<Question>();

    public QuestionGenerator() {
        for (int i = 0; i < 50; i++) {
            String question = "Question " + i;
            this.popQuestions.addLast(new Question(Category.POP, question));
            this.scienceQuestions.addLast(new Question(Category.SCIENCE, question));
            this.sportsQuestions.addLast(new Question(Category.SPORTS, question));
            this.rockQuestions.addLast(new Question(Category.ROCK, question));
        }
    }

    public LinkedList<Question> getPopQuestions() {
        return popQuestions;
    }

    public LinkedList<Question> getScienceQuestions() {
        return scienceQuestions;
    }

    public LinkedList<Question> getSportsQuestions() {
        return sportsQuestions;
    }

    public LinkedList<Question> getRockQuestions() {
        return rockQuestions;
    }

}
