package game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    List<Player> players = new ArrayList<>();
    QuestionStack questionStack;
    GameBoard gameBoard;
    int indexOfCurrentPlayer = 0;

    public Game() {
        this.questionStack = new QuestionStack();
        this.gameBoard = new GameBoard();
    }

    public boolean addPlayer(String playerName) {
        Player player = new Player(playerName);
        this.players.add(player);
        this.gameBoard.setInitialGameFieldForPlayer(player);
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public void roll(int rolledNumber) {
        System.out.println(getCurrentPlayer().getName() + " is the current player");
        System.out.println("They have rolled a " + rolledNumber);
        if (getCurrentPlayer().isInPenaltyBox()) {
            if (isOdd(rolledNumber)) {
                getCurrentPlayer().setGettingOutOfPenaltyBox(true);
                System.out.println(getCurrentPlayer().getName() + " is getting out of the penalty box");
                gameBoard.setGameFieldByPlayer(getCurrentPlayer(), rolledNumber);
                int gameField = gameBoard.getGameFieldByPlayer(getCurrentPlayer());
                System.out.println("The category is " + gameBoard.getCategoryByGameField(gameField));
                askQuestion(gameBoard.getGameFieldByPlayer(getCurrentPlayer()));
            } else {
                System.out.println(getCurrentPlayer().getName() + " is not getting out of the penalty box");
                getCurrentPlayer().setGettingOutOfPenaltyBox(false);
            }
        } else {
            gameBoard.setGameFieldByPlayer(getCurrentPlayer(), rolledNumber);
            int gameField = gameBoard.getGameFieldByPlayer(getCurrentPlayer());
            System.out.println("The category is " + gameBoard.getCategoryByGameField(gameField));
            askQuestion(gameBoard.getGameFieldByPlayer(getCurrentPlayer()));
        }
    }

    private boolean isOdd(int rolledNumber) {
        return rolledNumber % 2 != 0;
    }

    private Player getCurrentPlayer() {
        return this.players.get(this.indexOfCurrentPlayer);
    }

    private void askQuestion(int place) {
        Category category = gameBoard.getCategoryByGameField(place);
        Question askedQuestion = questionStack.removeQuestion(category);
        System.out.println(askedQuestion);
    }

    public boolean wasCorrectlyAnswered() {
        if (getCurrentPlayer().isInPenaltyBox()) {
            if (getCurrentPlayer().isGettingOutOfPenaltyBox()) {
                System.out.println("Answer was correct!!!!");
                getCurrentPlayer().addCoin();
                boolean winner = getCurrentPlayer().didPlayerWin();
                setNewCurrentPlayer();
                return winner;
            } else {
                setNewCurrentPlayer();
                return true;
            }
        } else {
            System.out.println("Answer was corrent!!!!");
            getCurrentPlayer().addCoin();
            boolean winner = getCurrentPlayer().didPlayerWin();
            setNewCurrentPlayer();
            return winner;
        }
    }

    public boolean wasIncorrectlyAnswered() {
        System.out.println("Question was incorrectly answered");
        System.out.println(getCurrentPlayer().getName() + " was sent to the penalty box");
        getCurrentPlayer().setInPenaltyBox(true);
        setNewCurrentPlayer();
        return true;
    }

    private void setNewCurrentPlayer() {
        int firstPlacing = 0;
        this.indexOfCurrentPlayer++;
        if (this.indexOfCurrentPlayer == players.size()) this.indexOfCurrentPlayer = firstPlacing;
    }
}

