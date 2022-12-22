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
    private static final int MAX_NUMBER_OF_QUESTIONS_PER_CATEGORY = 50;
    private final LinkedList<Question> popQuestions = new LinkedList();
    private final LinkedList<Question> scienceQuestions = new LinkedList();
    private final LinkedList<Question> sportsQuestions = new LinkedList();
    private final LinkedList<Question> rockQuestions = new LinkedList();

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

    public void askQuestion(Category category) {
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
    private final boolean[] playersInPenaltyBox = new boolean[6];

    public boolean isPlayerInPenaltyBox(int positionOfPlayer) {
        return playersInPenaltyBox[positionOfPlayer];
    }

    public void movePlayerToPenaltyBox(int positionOfPlayer, boolean hasToMove) {
        playersInPenaltyBox[positionOfPlayer] = hasToMove;
    }
}

class GameBoard {
    private static final int NUMBER_OF_GAME_FIELDS = 12;
    private final int[] playersPosition = new int[6];

    public void setPlayerToStartField(int positionOfPlayer) {
        playersPosition[positionOfPlayer] = 0;
    }

    public int getGameFieldOfPlayer(int positionOfPlayer) {
        return playersPosition[positionOfPlayer];
    }

    public void movePlayer(int rolledNumber, int positionOfPlayer) {
        int gameFieldToMove = getGameFieldOfPlayer(positionOfPlayer) + rolledNumber;
        if (gameFieldToMove > 11) {
            gameFieldToMove -= NUMBER_OF_GAME_FIELDS;
        }
        playersPosition[positionOfPlayer] = gameFieldToMove;
    }

    public Category getCategoryByGameField(int gameField) {
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
    private final int[] playersPurse = new int[6];

    public void setPlayersInitialCoins(int positionOfPlayer) {
        playersPurse[positionOfPlayer] = COINS_TO_START;
    }

    public void addCoinsToPlayer(int positionOfPlayer) {
        playersPurse[positionOfPlayer]++;
    }

    public int getPlayersCoins(int positionOfPlayer) {
        return playersPurse[positionOfPlayer];
    }

    public boolean didPlayerWin(int positionOfPlayer) {
        return !(playersPurse[positionOfPlayer] == COINS_NEEDED_TO_WIN);
    }
}

public class Game {
    private final QuestionStack questionStack = new QuestionStack();
    private final GameBoard gameBoard = new GameBoard();
    private final Treasurer treasurer = new Treasurer();
    private final PenaltyBox penaltyBox = new PenaltyBox();
    private final ArrayList<Player> players = new ArrayList();
    private Player playerInTurn;

    public Game() {
    }

    public void addPlayer(String playerName) {
        Player player = new Player(playerName);
        players.add(player);
        gameBoard.setPlayerToStartField(players.size());
        penaltyBox.movePlayerToPenaltyBox(players.size(), false);
        treasurer.setPlayersInitialCoins(players.size());
        System.out.println(player + " was added");
        System.out.println("They are player number " + players.size());
        playerInTurn = players.get(0);
    }

    public void roll(int rolledNumber) {
        System.out.println(playerInTurn + " is the current player");
        System.out.println("They have rolled a " + rolledNumber);
        if (penaltyBox.isPlayerInPenaltyBox(getPositionOfPlayerInTurn())) {
            if (isOdd(rolledNumber)) {
                playerInTurn.setAllowedToAnswer(true);
                System.out.println(playerInTurn + " is getting out of the penalty box");
                moveAndAskPlayer(rolledNumber);
            } else {
                playerInTurn.setAllowedToAnswer(false);
                System.out.println(playerInTurn + " is not getting out of the penalty box");
            }
        } else {
            moveAndAskPlayer(rolledNumber);
        }
    }

    private void moveAndAskPlayer(int rolledNumber) {
        gameBoard.movePlayer(rolledNumber, getPositionOfPlayerInTurn());
        int gameFieldOfPlayer = gameBoard.getGameFieldOfPlayer(getPositionOfPlayerInTurn());
        System.out.println(playerInTurn
                + "'s new location is "
                + gameFieldOfPlayer);
        Category category = gameBoard.getCategoryByGameField(gameFieldOfPlayer);
        System.out.println("The category is " + category);
        questionStack.askQuestion(category);
    }

    private boolean isOdd(int rolledNumber) {
        return rolledNumber % 2 != 0;
    }

    public boolean playerAnsweredCorrectly() {
        if (penaltyBox.isPlayerInPenaltyBox(getPositionOfPlayerInTurn())) {
            if (playerInTurn.isAllowedToAnswer()) {
                System.out.println("Answer was correct!!!!");
                return addCoinsAndCheckWinner();
            } else {
                selectNextPlayerInTurn();
                return true;
            }
        } else {
            System.out.println("Answer was corrent!!!!");
            return addCoinsAndCheckWinner();
        }
    }

    private int getPositionOfPlayerInTurn() {
        return players.indexOf(playerInTurn);
    }

    private boolean addCoinsAndCheckWinner() {
        treasurer.addCoinsToPlayer(getPositionOfPlayerInTurn());
        System.out.println(playerInTurn
                + " now has "
                + treasurer.getPlayersCoins(getPositionOfPlayerInTurn())
                + " Gold Coins.");
        boolean isWinner = treasurer.didPlayerWin(getPositionOfPlayerInTurn());
        selectNextPlayerInTurn();
        return isWinner;
    }

    public boolean playerAnsweredIncorrectly() {
        System.out.println("Question was incorrectly answered");
        System.out.println(playerInTurn + " was sent to the penalty box");
        penaltyBox.movePlayerToPenaltyBox(getPositionOfPlayerInTurn(), true);
        selectNextPlayerInTurn();
        return true;
    }

    private void selectNextPlayerInTurn() {
        int indexOfNextPlayer = getPositionOfPlayerInTurn() + 1;
        if (indexOfNextPlayer == players.size()) {
            playerInTurn = players.get(0);
        } else {
            playerInTurn = players.get(indexOfNextPlayer);
        }
    }

}
