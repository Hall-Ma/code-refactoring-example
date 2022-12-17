package game;

import java.util.LinkedList;

public class QuestionStack {

    private final LinkedList<Question> popQuestions = new LinkedList<>();
    private final LinkedList<Question> scienceQuestions = new LinkedList<>();
    private final LinkedList<Question> sportsQuestions = new LinkedList<>();
    private final LinkedList<Question> rockQuestions = new LinkedList<>();

    public QuestionStack() {
        generateQuestions();
    }

    private void generateQuestions() {
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

    public Question removeQuestion(Category questionCategory) {
        if (questionCategory == Category.POP) {
            return this.popQuestions.removeFirst();
        } else if (questionCategory == Category.SCIENCE) {
            return this.scienceQuestions.removeFirst();
        } else if (questionCategory == Category.SPORTS) {
            return this.sportsQuestions.removeFirst();
        } else if (questionCategory == Category.ROCK) {
            return this.rockQuestions.removeFirst();
        }
        return null;
    }

}
