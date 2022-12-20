package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

enum Category {
    POP("Pop"), SCIENCE("Science"), SPORTS("Sports"), ROCK("Rock");
    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}

class Question {
    private final Category category;
    private final String question;
    private final int number;

    public Question(Category category, String question, int number) {
        this.category = category;
        this.question = question;
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", category, question, number);
    }
}

class QuestionStack {
    public static final int MAX_NUMBER_OF_QUESTIONS_PER_CATEGORY = 50;
    LinkedList<Question> popQuestions = new LinkedList();
    LinkedList<Question> scienceQuestions = new LinkedList();
    LinkedList<Question> sportsQuestions = new LinkedList();
    LinkedList<Question> rockQuestions = new LinkedList();

    QuestionStack() {
        generateQuestionsByCategory();
    }

    private void generateQuestionsByCategory() {
        for (int i = 0; i < MAX_NUMBER_OF_QUESTIONS_PER_CATEGORY; i++) {
            popQuestions.addLast(new Question(Category.POP, "Question", i));
            scienceQuestions.addLast(new Question(Category.SCIENCE, "Question", i));
            sportsQuestions.addLast(new Question(Category.SPORTS, "Question", i));
            rockQuestions.addLast(new Question(Category.ROCK, "Question", i));
        }
    }

    void askQuestion(Category category) {
        Question question;
        if (category == Category.POP) {
            question = popQuestions.removeFirst();
            System.out.println(question);
        }
        if (category == Category.SCIENCE) {
            question = scienceQuestions.removeFirst();
            System.out.println(question);
        }
        if (category == Category.SPORTS) {
            question = sportsQuestions.removeFirst();
            System.out.println(question);
        }
        if (category == Category.ROCK) {
            question = rockQuestions.removeFirst();
            System.out.println(question);
        }
    }
}

class Player {
    private final String playerName;
    private final String playerID;
    private boolean isAllowedToAnswer;

    public Player(String playerName) {
        this.playerName = playerName;
        this.playerID = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return playerName;
    }

    public String getPlayerID() {
        return playerID;
    }

    public boolean isAllowedToAnswer() {
        return isAllowedToAnswer;
    }

    public void setAllowedToAnswer(boolean allowedToAnswer) {
        isAllowedToAnswer = allowedToAnswer;
    }
}

class PenaltyBox {
    private final List<String> playersInPenaltyBox = new ArrayList<>();

    public boolean isPlayerInPenaltyBox(String playerID) {
        return playersInPenaltyBox.contains(playerID);
    }

    public void movePlayerToPenaltyBox(String playerID) {
        playersInPenaltyBox.add(playerID);
    }
}

class GameBoard {
    private static final int NUMBER_OF_GAME_FIELDS = 12;
    private final int[] places = new int[6];

    public void setPlayerToStartField(int currentPlayer) {
        places[currentPlayer] = 0;
    }

    public int getGameFieldOfPlayer(int currentPlayer) {
        return places[currentPlayer];
    }

    public void movePlayer(int rolledNumber, int currentPlayer) {
        int gameFieldToMove = getGameFieldOfPlayer(currentPlayer) + rolledNumber;
        if (gameFieldToMove > 11) {
            gameFieldToMove -= NUMBER_OF_GAME_FIELDS;
        }
        places[currentPlayer] = gameFieldToMove;
    }
}

public class Game {
    private static final int COINS_NEEDED_TO_WIN = 6;
    final QuestionStack questionStack = new QuestionStack();
    final GameBoard gameBoard = new GameBoard();
    private final PenaltyBox penaltyBox = new PenaltyBox();
    ArrayList<Player> players = new ArrayList();
    int[] purses = new int[6];
    int currentPlayer = 0;

    public Game(List<String> playerNames) {
        add(playerNames);
    }

    public void add(List<String> playerNames) {
        for (String playerName : playerNames) {
            Player player = new Player(playerName);
            players.add(player);
            gameBoard.setPlayerToStartField(players.size());
            purses[players.size()] = 0;
            System.out.println(player + " was added");
            System.out.println("They are player number " + players.size());
        }
    }

    public void roll(int rolledNumber) {
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + rolledNumber);
        if (penaltyBox.isPlayerInPenaltyBox(players.get(currentPlayer).getPlayerID())) {
            if (isOdd(rolledNumber)) {
                players.get(currentPlayer).setAllowedToAnswer(true);
                System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                gameBoard.movePlayer(rolledNumber, currentPlayer);
                System.out.println(players.get(currentPlayer)
                        + "'s new location is "
                        + gameBoard.getGameFieldOfPlayer(currentPlayer));
                System.out.println("The category is " + currentCategory(gameBoard.getGameFieldOfPlayer(currentPlayer)));
                questionStack.askQuestion(currentCategory(gameBoard.getGameFieldOfPlayer(currentPlayer)));
            } else {
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                players.get(currentPlayer).setAllowedToAnswer(false);
            }
        } else {
            gameBoard.movePlayer(rolledNumber, currentPlayer);
            System.out.println(players.get(currentPlayer)
                    + "'s new location is "
                    + gameBoard.getGameFieldOfPlayer(currentPlayer));
            System.out.println("The category is " + currentCategory(gameBoard.getGameFieldOfPlayer(currentPlayer)));
            questionStack.askQuestion(currentCategory(gameBoard.getGameFieldOfPlayer(currentPlayer)));
        }
    }

    private boolean isOdd(int rolledNumber) {
        return rolledNumber % 2 != 0;
    }

    private Category currentCategory(int gameField) {
        if (gameField == 0) return Category.POP;
        if (gameField == 4) return Category.POP;
        if (gameField == 8) return Category.POP;
        if (gameField == 1) return Category.SCIENCE;
        if (gameField == 5) return Category.SCIENCE;
        if (gameField == 9) return Category.SCIENCE;
        if (gameField == 2) return Category.SPORTS;
        if (gameField == 6) return Category.SPORTS;
        if (gameField == 10) return Category.SPORTS;
        return Category.ROCK;
    }

    public boolean wasCorrectlyAnswered() {
        if (penaltyBox.isPlayerInPenaltyBox(players.get(currentPlayer).getPlayerID())) {
            if (players.get(currentPlayer).isAllowedToAnswer()) {
                System.out.println("Answer was correct!!!!");
                purses[currentPlayer]++;
                System.out.println(players.get(currentPlayer)
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.");
                boolean winner = didPlayerWin();
                selectNextPlayerInTurn();
                return winner;
            } else {
                selectNextPlayerInTurn();
                return true;
            }
        } else {
            System.out.println("Answer was corrent!!!!");
            purses[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");
            boolean winner = didPlayerWin();
            selectNextPlayerInTurn();
            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        penaltyBox.movePlayerToPenaltyBox(players.get(currentPlayer).getPlayerID());
        selectNextPlayerInTurn();
        return true;
    }

    private void selectNextPlayerInTurn() {
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }

    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == COINS_NEEDED_TO_WIN);
    }

}
