package game;

import java.util.LinkedList;

public class QuestionGenerator {

    private final LinkedList popQuestions = new LinkedList<Category>();
    private final LinkedList scienceQuestions = new LinkedList<Category>();
    private final LinkedList sportsQuestions = new LinkedList<Category>();
    private final LinkedList rockQuestions = new LinkedList<Category>();

    public QuestionGenerator() {
        for (int i = 0; i < 50; i++) {
            this.popQuestions.addLast(Category.POP.getCategoryName() + " Question " + i);
            this.scienceQuestions.addLast(Category.SCIENCE.getCategoryName() + " Question " + i);
            this.sportsQuestions.addLast(Category.SPORTS.getCategoryName() + " Question " + i);
            this.rockQuestions.addLast(Category.ROCK.getCategoryName() + " Question " + i);
        }
    }

    public LinkedList<Category> getPopQuestions() {
        return popQuestions;
    }

    public LinkedList<Category> getScienceQuestions() {
        return scienceQuestions;
    }

    public LinkedList<Category> getSportsQuestions() {
        return sportsQuestions;
    }

    public LinkedList<Category> getRockQuestions() {
        return rockQuestions;
    }

}
