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

    public void addPlayer(String playerName) {
        Player player = new Player(playerName);
        this.players.add(player);
        this.gameBoard.setInitialGameFieldForPlayer(player);
        System.out.println(playerName + " was added");
        System.out.println("They are player number " + this.gameBoard.getNumberOfPlayers());
    }

    public void roll(int rolledNumber) {
        Player currentPlayer = getCurrentPlayer();
        System.out.println(currentPlayer.getName() + " is the current player");
        System.out.println("They have rolled a " + rolledNumber);
        if (currentPlayer.isInPenaltyBox()) {
            if (isOdd(rolledNumber)) {
                currentPlayer.setGettingOutOfPenaltyBox(true);
                System.out.println(currentPlayer.getName() + " is getting out of the penalty box");
                gameBoard.setGameFieldForPlayer(currentPlayer, rolledNumber);
                int gameField = gameBoard.getGameFieldByPlayer(currentPlayer);
                System.out.println("The category is " + gameBoard.getCategoryByGameField(gameField));
                askQuestion(gameBoard.getGameFieldByPlayer(currentPlayer));
            } else {
                System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
                currentPlayer.setGettingOutOfPenaltyBox(false);
            }
        } else {
            gameBoard.setGameFieldForPlayer(currentPlayer, rolledNumber);
            int gameField = gameBoard.getGameFieldByPlayer(currentPlayer);
            System.out.println("The category is " + gameBoard.getCategoryByGameField(gameField));
            askQuestion(gameBoard.getGameFieldByPlayer(currentPlayer));
        }
    }

    private boolean isOdd(int rolledNumber) {
        return rolledNumber % 2 != 0;
    }

    private Player getCurrentPlayer() {
        return this.players.get(this.indexOfCurrentPlayer);
    }

    private void askQuestion(int gameField) {
        Category category = gameBoard.getCategoryByGameField(gameField);
        Question askedQuestion = questionStack.removeQuestion(category);
        System.out.println(askedQuestion);
    }

    public boolean wasCorrectlyAnswered() {
        Player currentPlayer = getCurrentPlayer();
        if (currentPlayer.isInPenaltyBox()) {
            if (currentPlayer.isGettingOutOfPenaltyBox()) {
                System.out.println("Answer was correct!!!!");
                currentPlayer.addCoin();
                boolean winner = currentPlayer.didPlayerWin();
                setNewCurrentPlayer();
                return winner;
            } else {
                setNewCurrentPlayer();
                return true;
            }
        } else {
            System.out.println("Answer was corrent!!!!");
            currentPlayer.addCoin();
            boolean winner = currentPlayer.didPlayerWin();
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
        if (this.indexOfCurrentPlayer == this.gameBoard.getNumberOfPlayers()) this.indexOfCurrentPlayer = firstPlacing;
    }
}

