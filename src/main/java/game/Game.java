package game;

import java.util.ArrayList;
import java.util.LinkedList;

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
    private boolean isAllowedToAnswer;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return playerName;
    }

    public boolean isAllowedToAnswer() {
        return isAllowedToAnswer;
    }

    public void setAllowedToAnswer(boolean allowedToAnswer) {
        isAllowedToAnswer = allowedToAnswer;
    }
}

class PenaltyBox {
    private boolean[] inPenaltyBox = new boolean[6];

    public boolean isPlayerInPenaltyBox(int currentPlayer) {
        return inPenaltyBox[currentPlayer];
    }

    public void movePlayerToPenaltyBox(int currentPlayer, boolean hasToMove) {
        inPenaltyBox[currentPlayer] = hasToMove;
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

    Category getCategory(int gameField) {
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
}

class Treasurer {
    private static final int COINS_NEEDED_TO_WIN = 6;
    private static final int COINS_TO_START = 0;
    private final int[] purses = new int[6];

    public void setPlayersInitialCoins(int currentPlayer) {
        purses[currentPlayer] = COINS_TO_START;
    }

    public void addCoinsToPlayer(int currentPlayer, Player player) {
        purses[currentPlayer]++;
        System.out.println(player
                + " now has "
                + purses[currentPlayer]
                + " Gold Coins.");
    }

    public boolean didPlayerWin(int currentPlayer) {
        return !(purses[currentPlayer] == COINS_NEEDED_TO_WIN);
    }
}

public class Game {
    final QuestionStack questionStack = new QuestionStack();
    final GameBoard gameBoard = new GameBoard();
    final Treasurer treasurer = new Treasurer();
    private final PenaltyBox penaltyBox = new PenaltyBox();
    ArrayList<Player> players = new ArrayList();
    Player currentPlayer;

    public Game() {
    }

    public void add(String playerName) {
        Player player = new Player(playerName);
        players.add(player);
        gameBoard.setPlayerToStartField(players.size());
        penaltyBox.movePlayerToPenaltyBox(players.size(), false);
        treasurer.setPlayersInitialCoins(players.size());
        System.out.println(player + " was added");
        System.out.println("They are player number " + players.size());
        currentPlayer = players.get(0);
    }

    public void roll(int rolledNumber) {
        System.out.println(currentPlayer + " is the current player");
        System.out.println("They have rolled a " + rolledNumber);
        if (penaltyBox.isPlayerInPenaltyBox(players.indexOf(currentPlayer))) {
            if (isOdd(rolledNumber)) {
                currentPlayer.setAllowedToAnswer(true);
                System.out.println(currentPlayer + " is getting out of the penalty box");
                moveAndAskPlayer(rolledNumber);
            } else {
                System.out.println(currentPlayer + " is not getting out of the penalty box");
                currentPlayer.setAllowedToAnswer(false);
            }
        } else {
            moveAndAskPlayer(rolledNumber);
        }
    }

    private void moveAndAskPlayer(int rolledNumber) {
        gameBoard.movePlayer(rolledNumber, players.indexOf(currentPlayer));
        int gameFieldOfPlayer = gameBoard.getGameFieldOfPlayer(players.indexOf(currentPlayer));
        System.out.println(currentPlayer
                + "'s new location is "
                + gameFieldOfPlayer);
        System.out.println("The category is " + gameBoard.getCategory(gameFieldOfPlayer));
        questionStack.askQuestion(gameBoard.getCategory(gameFieldOfPlayer));
    }

    private boolean isOdd(int rolledNumber) {
        return rolledNumber % 2 != 0;
    }

    public boolean wasCorrectlyAnswered() {
        if (penaltyBox.isPlayerInPenaltyBox(players.indexOf(currentPlayer))) {
            if (currentPlayer.isAllowedToAnswer()) {
                System.out.println("Answer was correct!!!!");
                treasurer.addCoinsToPlayer(players.indexOf(currentPlayer), currentPlayer);
                boolean winner = treasurer.didPlayerWin(players.indexOf(currentPlayer));
                selectNextPlayerInTurn();
                return winner;
            } else {
                selectNextPlayerInTurn();
                return true;
            }
        } else {
            System.out.println("Answer was corrent!!!!");
            treasurer.addCoinsToPlayer(players.indexOf(currentPlayer), currentPlayer);
            boolean winner = treasurer.didPlayerWin(players.indexOf(currentPlayer));
            selectNextPlayerInTurn();
            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer + " was sent to the penalty box");
        penaltyBox.movePlayerToPenaltyBox(players.indexOf(currentPlayer), true);
        selectNextPlayerInTurn();
        return true;
    }

    private void selectNextPlayerInTurn() {
        int indexOfNextPlayer = players.indexOf(currentPlayer) + 1;
        if (indexOfNextPlayer == players.size()) {
            currentPlayer = players.get(0);
        } else {
            currentPlayer = players.get(indexOfNextPlayer);
        }
    }

}
