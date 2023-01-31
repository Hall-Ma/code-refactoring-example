package game;

import java.util.*;
import java.util.stream.IntStream;

import static game.QuestionType.*;

enum QuestionType {
    POP("Pop"),
    SCIENCE("Science"),
    SPORTS("Sports"),
    ROCK("Rock");

    private final String label;

    QuestionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

class Question {

    private final QuestionType type;
    private final String content;

    public Question(QuestionType type, String content) {
        this.type = type;
        this.content = content;
    }

    public QuestionType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

}

class Player {

    private final String name;
    private int currentPlace;
    private int purse;
    private boolean inPenaltyBox;
    private boolean isGettingOutOfPenaltyBox;

    public Player(String name) {
        this.name = name;
    }

    public int getCurrentPlace() {
        return currentPlace;
    }

    public int getPurse() {
        return purse;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        this.inPenaltyBox = inPenaltyBox;
    }

    public boolean isGettingOutOfPenaltyBox() {
        return isGettingOutOfPenaltyBox;
    }

    public void setGettingOutOfPenaltyBox(boolean gettingOutOfPenaltyBox) {
        isGettingOutOfPenaltyBox = gettingOutOfPenaltyBox;
    }

    public String getName() {
        return name;
    }

    public void getACoin() {
        purse = purse + 1;
    }

    public boolean isWinner() {
        return (this.purse != 6);
    }

    public void makeAMove(int position) {
        currentPlace = currentPlace + position;
        resetPosition();
    }

    private void resetPosition() {
        if (currentPlace > 11) {
            currentPlace = currentPlace - 12;
        }
    }

}

interface IGame {

    void roll(int roll);

    void add(String playerName);

    boolean answerIsRight();

    boolean answerIsWrong();

}

class GameHandler {
    private final Map<QuestionType, LinkedList<Question>> questionsMap = new EnumMap<>(QuestionType.class);
    private final List<Player> players;

    public GameHandler() {
        questionsMap.put(POP, new LinkedList<>());
        questionsMap.put(ROCK, new LinkedList<>());
        questionsMap.put(SCIENCE, new LinkedList<>());
        questionsMap.put(SPORTS, new LinkedList<>());
        players = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    public Question askQuestion(QuestionType type) {
        LinkedList<Question> questionsByType = getQuestionsByType(type);
        return questionsByType.removeFirst();
    }

    public void addQuestion(Question q) {
        LinkedList<Question> questionsByType = getQuestionsByType(q.getType());
        questionsByType.addLast(q);
    }

    public void getAQuestion(int playerPlace) {
        QuestionType category = getQuestionCategory(playerPlace);
        System.out.println("The category is " + category.getLabel() + "\n" + askQuestion(category).getContent());
    }

    private QuestionType getQuestionCategory(int place) {
        if (place == 0 || place == 4 || place == 8) return POP;
        if (place == 1 || place == 5 || place == 9) return SCIENCE;
        if (place == 2 || place == 6 || place == 10) return SPORTS;
        return ROCK;
    }

    private LinkedList<Question> getQuestionsByType(QuestionType type) {
        switch (type) {
            case POP:
                return questionsMap.get(POP);
            case ROCK:
                return questionsMap.get(ROCK);
            case SPORTS:
                return questionsMap.get(SPORTS);
            case SCIENCE:
                return questionsMap.get(SCIENCE);
        }
        return null;
    }

}

public class Game implements IGame {

    private final GameHandler gameHandler = new GameHandler();
    private int currentPlayer = 0;

    public Game() {
        IntStream.range(0, 50).forEach(this::generateQuestionsForAllCategories);
    }

    public void add(String playerName) {
        gameHandler.addPlayer(new Player(playerName));
        System.out.println(playerName + " was added\nThey are player number " + gameHandler.getNumberOfPlayers());
    }

    public void roll(int roll) {
        Player player = getCurrentPlayer();
        System.out.println(player.getName() + " is the current player\nThey have rolled a " + roll);

        if (player.isInPenaltyBox()) {
            playerTriesToGetOutOfPenalty(player, roll % 2 == 1);
        }

        if (!player.isInPenaltyBox() || player.isGettingOutOfPenaltyBox()) {
            player.makeAMove(roll);
            System.out.println(player.getName() + "'s new location is " + player.getCurrentPlace());

            gameHandler.getAQuestion(player.getCurrentPlace());
        }
    }

    public boolean answerIsRight() {
        Player player = getCurrentPlayer();

        if (!player.isInPenaltyBox() || player.isGettingOutOfPenaltyBox()) {
            addCoin(player);
        }
        moveToNextPlayer();

        return player.isWinner();
    }

    public boolean answerIsWrong() {
        System.out.println("Question was incorrectly answered.");
        sendToPenaltyBox(getCurrentPlayer());
        moveToNextPlayer();
        return true;
    }

    private void addCoin(Player player) {
        player.getACoin();
        System.out.println("Answer was correct!!!!\n" + player.getName() + " now has " + player.getPurse() + " Gold Coins.");
    }

    private void moveToNextPlayer() {
        currentPlayer++;
        if (currentPlayer == gameHandler.getNumberOfPlayers()) {
            currentPlayer = 0;
        }
    }

    private void sendToPenaltyBox(Player player) {
        player.setInPenaltyBox(true);
        System.out.println(player.getName() + " was sent to the penalty box");
    }

    private void playerTriesToGetOutOfPenalty(Player player, boolean gettingOut) {
        player.setGettingOutOfPenaltyBox(gettingOut);
        System.out.println(gettingOut ? player.getName() + " is getting out of the penalty box" : player.getName() + " is not getting out of the penalty box");
    }

    private Player getCurrentPlayer() {
        return gameHandler.getPlayers().get(currentPlayer);
    }

    private void generateQuestionsForAllCategories(int content) {
        Arrays.stream(QuestionType.values()).forEach(j -> gameHandler.addQuestion(new Question(j, j.getLabel() + " Question " + content)));
    }

}