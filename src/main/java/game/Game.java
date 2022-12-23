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
    private static final int MAX_NUMBER_OF_QUESTIONS_PER_CATEGORY = 50;
    private final LinkedList<QuestionCard> popQuestion = new LinkedList();
    private final LinkedList<QuestionCard> scienceQuestion = new LinkedList();
    private final LinkedList<QuestionCard> sportsQuestion = new LinkedList();
    private final LinkedList<QuestionCard> rockQuestion = new LinkedList();

    QuestionStack() {
        generateQuestionsByCategory();
    }

    private void generateQuestionsByCategory() {
        for (int i = 0; i < MAX_NUMBER_OF_QUESTIONS_PER_CATEGORY; i++) {
            String question = "Question";
            popQuestion.addLast(new QuestionCard(Category.POP, question, i));
            scienceQuestion.addLast(new QuestionCard(Category.SCIENCE, question, i));
            sportsQuestion.addLast(new QuestionCard(Category.SPORTS, question, i));
            rockQuestion.addLast(new QuestionCard(Category.ROCK, question, i));
        }
    }

    public void removeQuestionFromStack(Category category) {
        QuestionCard questionCard;
        if (category == Category.POP) {
            questionCard = popQuestion.removeFirst();
            System.out.println(questionCard);
        }
        if (category == Category.SCIENCE) {
            questionCard = scienceQuestion.removeFirst();
            System.out.println(questionCard);
        }
        if (category == Category.SPORTS) {
            questionCard = sportsQuestion.removeFirst();
            System.out.println(questionCard);
        }
        if (category == Category.ROCK) {
            questionCard = rockQuestion.removeFirst();
            System.out.println(questionCard);
        }
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
        if (gameFieldToMove >= NUMBER_OF_GAME_FIELDS) {
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

    public boolean playerReachedNotMaxCoins(int positionOfPlayer) {
        return playersPurse[positionOfPlayer] != COINS_NEEDED_TO_WIN;
    }
}

public class Game {
    private final QuestionStack questionStack = new QuestionStack();
    private final GameBoard gameBoard = new GameBoard();
    private final Treasurer treasurer = new Treasurer();
    private final PenaltyBox penaltyBox = new PenaltyBox();
    private final ArrayList<Player> players = new ArrayList<>();
    private Player playerInTurn;

    public void addPlayer(String playerName) {
        Player player = new Player(playerName, players.size());
        players.add(player);
        gameBoard.setPlayerToStartField(players.size());
        penaltyBox.movePlayerToPenaltyBox(players.size(), false);
        treasurer.setPlayersInitialCoins(players.size());
        System.out.println(player + " was added");
        System.out.println("They are player number " + players.size());
        playerInTurn = players.get(0);
    }

    public void handlePlayersTurn(int rolledNumber) {
        System.out.println(playerInTurn + " is the current player");
        System.out.println("They have rolled a " + rolledNumber);
        if (penaltyBox.isPlayerInPenaltyBox(playerInTurn.getNumber())) {
            if (isOdd(rolledNumber)) {
                playerInTurn.allowToAnswer(true);
                System.out.println(playerInTurn + " is getting out of the penalty box");
                moveAndAskPlayer(rolledNumber);
            } else {
                playerInTurn.allowToAnswer(false);
                System.out.println(playerInTurn + " is not getting out of the penalty box");
            }
        } else {
            moveAndAskPlayer(rolledNumber);
        }
    }

    public boolean playerAnsweredCorrectlyAndIsNotAWinner() {
        if (penaltyBox.isPlayerInPenaltyBox(playerInTurn.getNumber())) {
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

    public boolean playerAnsweredIncorrectly() {
        System.out.println("Question was incorrectly answered");
        System.out.println(playerInTurn + " was sent to the penalty box");
        penaltyBox.movePlayerToPenaltyBox(playerInTurn.getNumber(), true);
        selectNextPlayerInTurn();
        return true;
    }

    private void moveAndAskPlayer(int rolledNumber) {
        gameBoard.movePlayer(rolledNumber, playerInTurn.getNumber());
        int gameFieldOfPlayer = gameBoard.getGameFieldOfPlayer(playerInTurn.getNumber());
        System.out.println(playerInTurn
                + "'s new location is "
                + gameFieldOfPlayer);
        Category category = gameBoard.getCategoryByGameField(gameFieldOfPlayer);
        System.out.println("The category is " + category);
        questionStack.removeQuestionFromStack(category);
    }

    private boolean isOdd(int rolledNumber) {
        return rolledNumber % 2 != 0;
    }

    private boolean addCoinsAndCheckWinner() {
        treasurer.addCoinsToPlayer(playerInTurn.getNumber());
        System.out.println(playerInTurn
                + " now has "
                + treasurer.getPlayersCoins(playerInTurn.getNumber())
                + " Gold Coins.");
        boolean isNotWinner = treasurer.playerReachedNotMaxCoins(playerInTurn.getNumber());
        selectNextPlayerInTurn();
        return isNotWinner;
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
