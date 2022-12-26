package game;

public class Question {

    private final Category typeOfCategory;
    private final String questionSet;

    public Question(Category typeOfCategory, String questionSet) {
        this.typeOfCategory = typeOfCategory;
        this.questionSet = questionSet;
    }

    @Override
    public String toString() {
        return String.format("%s %s", typeOfCategory, questionSet);
    }
}
