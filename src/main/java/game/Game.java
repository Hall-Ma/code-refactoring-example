package game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    List<Player> players = new ArrayList<>();
    QuestionStack questionStack;
    GameBoard gameBoard;
    Player currentPlayer;

    public Game(List<String> playerNames) {
        initGame(playerNames);
    }

    private void initGame(List<String> playerNames) {
        this.questionStack = new QuestionStack();
        this.gameBoard = new GameBoard();
        for (String playerName : playerNames) {
            Player player = new Player(playerName);
            this.players.add(player);
            System.out.println(playerName + " was added");
            this.gameBoard.setInitialGameFieldForPlayer(player);
            System.out.println("They are player number " + this.gameBoard.getNumberOfPlayers());
        }
        this.currentPlayer = this.players.get(0);
    }

    public void roll(int rolledNumber) {
        System.out.println(currentPlayer.getName() + " is the current player");
        System.out.println("They have rolled a " + rolledNumber);
        if (currentPlayer.isInPenaltyBox()) {
            if (isOdd(rolledNumber)) {
                currentPlayer.setGettingOutOfPenaltyBox(true);
                System.out.println(currentPlayer.getName() + " is getting out of the penalty box");
                gameBoard.setGameFieldForPlayer(currentPlayer, rolledNumber);
                int gameField = gameBoard.getGameFieldForPlayer(currentPlayer);
                System.out.println("The category is " + gameBoard.getCategoryByGameField(gameField));
                askQuestion(gameField);
            } else {
                System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
                currentPlayer.setGettingOutOfPenaltyBox(false);
            }
        } else {
            gameBoard.setGameFieldForPlayer(currentPlayer, rolledNumber);
            int gameField = gameBoard.getGameFieldForPlayer(currentPlayer);
            System.out.println("The category is " + gameBoard.getCategoryByGameField(gameField));
            askQuestion(gameField);
        }
    }

    private boolean isOdd(int rolledNumber) {
        return rolledNumber % 2 != 0;
    }

    private void askQuestion(int gameField) {
        Category category = gameBoard.getCategoryByGameField(gameField);
        Question askedQuestion = questionStack.removeQuestion(category);
        System.out.println(askedQuestion);
    }

    public boolean wasCorrectlyAnswered() {
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
        System.out.println(this.currentPlayer.getName() + " was sent to the penalty box");
        this.currentPlayer.setInPenaltyBox(true);
        setNewCurrentPlayer();
        return true;
    }

    private void setNewCurrentPlayer() {
        int indexOfNextPlayer = this.players.indexOf(this.currentPlayer) + 1;
        this.currentPlayer = indexOfNextPlayer == this.players.size() ? this.players.get(0) : this.players.get(indexOfNextPlayer);
    }
}

