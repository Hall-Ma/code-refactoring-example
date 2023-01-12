package game;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

class Player {

    private final String playerName;
    private int place;
    private int purse;
    private boolean inPenaltyBox;
    private boolean isGettingOutOfPenaltyBox;

    public Player(String playerName) {
        this.playerName = playerName;
        place = 0;
        purse = 0;
        inPenaltyBox = false;
        isGettingOutOfPenaltyBox = false;
    }

    public String getName() {
        return playerName;
    }

    public int getPlace() {
        return place;
    }

    public int getPurse() {
        return purse;
    }

    public void addPlace(int roll) {
        place = (place + roll) % 12;
    }

    public void addPurse() {
        purse++;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public boolean isGettingOutOfPenaltyBox() {
        return isGettingOutOfPenaltyBox;
    }

    public void putIntoPenaltyBox() {
        inPenaltyBox = true;
    }

    public void setGetOutOfPenaltyBox(int roll) {
        isGettingOutOfPenaltyBox = roll % 2 != 0;
    }

    boolean didPlayerWin() {
        return getPurse() == 6;
    }
}

class PlayerPool {
    private final ArrayList<Player> players;
    private int currentPlayerIndex;

    public PlayerPool() {
        this.players = new ArrayList<Player>();
        currentPlayerIndex = 0;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public int howManyPlayers() {
        return players.size();
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == howManyPlayers())
            currentPlayerIndex = 0;
    }

}

enum QuestionCategory {
    ROCK, SPORTS, SCIENCE, POP
}

public class Game {
    // duplicate
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    PlayerPool playerPool = new PlayerPool();

    private final PrintStream out;

    public Game() {
        this(System.out);
    }

    Game(PrintStream printStream) {
        out = printStream;
        for (int i = 0; i < 50; i++) {
            // duplicate
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i)); // Inline
        }

    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean add(String playerName) {
        Player player = new Player(playerName);
        playerPool.addPlayer(player);

        out.println(playerName + " was added");
        out.println("They are player number " + playerPool.howManyPlayers());
        return true;
    }

    public void roll(int roll) {
        Player currentPlayer = playerPool.getCurrentPlayer();
        out.println(currentPlayer.getName() + " is the current player");
        out.println("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            currentPlayer.setGetOutOfPenaltyBox(roll);
            if (roll % 2 != 0) {
                out.println(currentPlayer.getName()
                        + " is getting out of the penalty box");
                advancePlayerAndAskQuestion(roll);
            } else {
                out.println(currentPlayer.getName()
                        + " is not getting out of the penalty box");
            }
        } else {
            advancePlayerAndAskQuestion(roll);
        }
    }

    private void advancePlayerAndAskQuestion(int roll) {
        Player currentPlayer = playerPool.getCurrentPlayer();
        currentPlayer.addPlace(roll);

        out.println(currentPlayer.getName() + "'s new location is "
                + currentPlayer.getPlace());
        out.println("The category is " + currentCategory());
        askQuestion();
    }

    private void askQuestion() {
        // duplicate zweizeiler
        if (currentCategory() == QuestionCategory.POP)
            out.println(popQuestions.removeFirst());
        if (currentCategory() == QuestionCategory.SCIENCE)
            out.println(scienceQuestions.removeFirst());
        if (currentCategory() == QuestionCategory.SPORTS)
            out.println(sportsQuestions.removeFirst());
        if (currentCategory() == QuestionCategory.ROCK)
            out.println(rockQuestions.removeFirst());
    }

    private QuestionCategory currentCategory() {
        int place = playerPool.getCurrentPlayer().getPlace();
        if (place % 4 == 0)
            return QuestionCategory.POP;
        if (place % 4 == 1)
            return QuestionCategory.SCIENCE;
        if (place % 4 == 2)
            return QuestionCategory.SPORTS;
        return QuestionCategory.ROCK;
    }

    public boolean wasCorrectlyAnswered() {
        Player currentPlayer = playerPool.getCurrentPlayer();
        if (currentPlayer.isInPenaltyBox()
                && !currentPlayer.isGettingOutOfPenaltyBox()) {
            playerPool.nextPlayer();
            return true;
        }
        return addPursesAndDidPlayerWin();
    }

    private boolean addPursesAndDidPlayerWin() {
        out.println("Answer was correct!!!!");
        Player currentPlayer = playerPool.getCurrentPlayer();
        currentPlayer.addPurse();
        out.println(currentPlayer.getName() + " now has "
                + currentPlayer.getPurse() + " Gold Coins.");

        boolean notWinner = !currentPlayer.didPlayerWin();
        playerPool.nextPlayer();

        return notWinner;
    }

    public boolean wrongAnswer() {
        out.println("Question was incorrectly answered");
        out.println(playerPool.getCurrentPlayer().getName()
                + " was sent to the penalty box");
        playerPool.getCurrentPlayer().putIntoPenaltyBox();

        playerPool.nextPlayer();
        return true;
    }
}