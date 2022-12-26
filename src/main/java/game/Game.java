package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

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

class QuestionCard {
    private final Category category;
    private final String question;
    private final int number;

    public QuestionCard(Category category, String question, int number) {
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
    private static final int QUESTIONS_PER_CATEGORY = 50;
    private final List<QuestionCard> popQuestion = new LinkedList<>();
    private final List<QuestionCard> scienceQuestion = new LinkedList<>();
    private final List<QuestionCard> sportsQuestion = new LinkedList<>();
    private final List<QuestionCard> rockQuestion = new LinkedList<>();

    QuestionStack() {
        generateQuestionsByCategory();
    }

    private void generateQuestionsByCategory() {
        for (int i = 0; i < QUESTIONS_PER_CATEGORY; i++) {
            String question = "Question";
            popQuestion.add(new QuestionCard(Category.POP, question, i));
            scienceQuestion.add(new QuestionCard(Category.SCIENCE, question, i));
            sportsQuestion.add(new QuestionCard(Category.SPORTS, question, i));
            rockQuestion.add(new QuestionCard(Category.ROCK, question, i));
        }
    }

    public void askQuestion(Category category) {
        if (category == Category.POP) {
            removeFirstQuestionFromStack(popQuestion);
        }
        if (category == Category.SCIENCE) {
            removeFirstQuestionFromStack(scienceQuestion);
        }
        if (category == Category.SPORTS) {
            removeFirstQuestionFromStack(sportsQuestion);
        }
        if (category == Category.ROCK) {
            removeFirstQuestionFromStack(rockQuestion);
        }
    }

    private void removeFirstQuestionFromStack(List<QuestionCard> questions) {
        QuestionCard question = questions.remove(0);
        System.out.println(question);
    }
}

class Player {
    private final String name;
    private boolean isAllowedToAnswer;
    private final int number;

    public Player(String name, int number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isAllowedToAnswer() {
        return isAllowedToAnswer;
    }

    public void allowToAnswer(boolean allowedToAnswer) {
        isAllowedToAnswer = allowedToAnswer;
    }

    public int getNumber() {
        return number;
    }
}

class PenaltyBox {
    private final boolean[] playersInPenaltyBox = new boolean[6];

    public boolean isPlayerInPenaltyBox(int positionOfPlayer) {
        return playersInPenaltyBox[positionOfPlayer];
    }

    public void movePlayerToPenaltyBox(int positionOfPlayer) {
        playersInPenaltyBox[positionOfPlayer] = true;
    }
}

class GameBoard {
    private static final int MAX_GAME_FIELDS = 12;
    private final int[] playersPosition = new int[6];
    private final Map<Integer, Category> gameFieldByCategory = Map.ofEntries(
            entry(0, Category.POP),
            entry(1, Category.SCIENCE),
            entry(2, Category.SPORTS),
            entry(3, Category.ROCK),
            entry(4, Category.POP),
            entry(5, Category.SCIENCE),
            entry(6, Category.SPORTS),
            entry(7, Category.ROCK),
            entry(8, Category.POP),
            entry(9, Category.SCIENCE),
            entry(10, Category.SPORTS),
            entry(11, Category.ROCK)
    );

    public int getGameFieldOfPlayer(int positionOfPlayer) {
        return playersPosition[positionOfPlayer];
    }

    public void movePlayer(int rolledNumber, int positionOfPlayer) {
        int gameFieldToMove = getGameFieldOfPlayer(positionOfPlayer) + rolledNumber;
        if (gameFieldToMove >= MAX_GAME_FIELDS) {
            gameFieldToMove -= MAX_GAME_FIELDS;
        }
        playersPosition[positionOfPlayer] = gameFieldToMove;
    }

    public Category getCategoryByGameField(int gameField) {
        return gameFieldByCategory.get(gameField);
    }
}


class Treasurer {
    private static final int COINS_NEEDED_TO_WIN = 6;
    private final int[] playersPurse = new int[6];


    public void addCoinToPlayer(int positionOfPlayer) {
        playersPurse[positionOfPlayer]++;
    }

    public int getPlayersCoins(int positionOfPlayer) {
        return playersPurse[positionOfPlayer];
    }

    public boolean hasPlayerNotReachedMaxCoins(int positionOfPlayer) {
        return playersPurse[positionOfPlayer] != COINS_NEEDED_TO_WIN;
    }
}

public class Game {
    private final QuestionStack questionStack = new QuestionStack();
    private final GameBoard gameBoard = new GameBoard();
    private final Treasurer treasurer = new Treasurer();
    private final PenaltyBox penaltyBox = new PenaltyBox();
    private final List<Player> players = new ArrayList<>();
    private Player playerInTurn;

    public void addPlayer(String playerName) {
        Player player = new Player(playerName, players.size());
        players.add(player);
        System.out.println(player + " was added");
        System.out.println("They are player number " + players.size());
        playerInTurn = players.get(0);
    }

    public void handlePlayersTurn(int rolledNumber) {
        System.out.println(playerInTurn + " is the current player");
        System.out.println("They have rolled a " + rolledNumber);
        if (penaltyBox.isPlayerInPenaltyBox(playerInTurn.getNumber())) {
            handlePlayerInPenaltyBox(rolledNumber);
        } else {
            handlePlayerNotInPenaltyBox(rolledNumber);
        }
    }

    private void handlePlayerInPenaltyBox(int rolledNumber) {
        if (isOdd(rolledNumber)) {
            playerInTurn.allowToAnswer(true);
            System.out.println(playerInTurn + " is getting out of the penalty box");
            movePlayerAndAskQuestion(rolledNumber);
        } else {
            playerInTurn.allowToAnswer(false);
            System.out.println(playerInTurn + " is not getting out of the penalty box");
        }
    }

    private void handlePlayerNotInPenaltyBox(int rolledNumber) {
        movePlayerAndAskQuestion(rolledNumber);
    }

    public boolean playerAnsweredCorrectlyAndIsNotAWinner() {
        boolean isNotAWinner = true;
        if (!penaltyBox.isPlayerInPenaltyBox(playerInTurn.getNumber())) {
            System.out.println("Answer was corrent!!!!");
            isNotAWinner = giveCoinAndCheckWinCondition();
        } else if (playerInTurn.isAllowedToAnswer()) {
            System.out.println("Answer was correct!!!!");
            isNotAWinner = giveCoinAndCheckWinCondition();
        }
        selectNextPlayerInTurn();
        return isNotAWinner;
    }

    public boolean playerAnsweredIncorrectly() {
        System.out.println("Question was incorrectly answered");
        System.out.println(playerInTurn + " was sent to the penalty box");
        penaltyBox.movePlayerToPenaltyBox(playerInTurn.getNumber());
        selectNextPlayerInTurn();
        return true;
    }

    private void movePlayerAndAskQuestion(int rolledNumber) {
        gameBoard.movePlayer(rolledNumber, playerInTurn.getNumber());
        int gameFieldOfPlayer = gameBoard.getGameFieldOfPlayer(playerInTurn.getNumber());
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

    private boolean giveCoinAndCheckWinCondition() {
        treasurer.addCoinToPlayer(playerInTurn.getNumber());
        System.out.println(playerInTurn
                + " now has "
                + treasurer.getPlayersCoins(playerInTurn.getNumber())
                + " Gold Coins.");
        return treasurer.hasPlayerNotReachedMaxCoins(playerInTurn.getNumber());
    }

    private void selectNextPlayerInTurn() {
        int indexOfNextPlayer = playerInTurn.getNumber() + 1;
        if (indexOfNextPlayer == players.size()) {
            playerInTurn = players.get(0);
        } else {
            playerInTurn = players.get(indexOfNextPlayer);
        }
    }

}
