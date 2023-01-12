package game;

import java.util.*;

class Questions {

    private final LinkedList<String> questions;

    public Questions() {
        this.questions = new LinkedList<>();
    }

    public void add(String question) {
        questions.add(question);
    }

    public String removeFirstQuestion() {
        return questions.removeFirst();
    }
}

enum QuestionType {
    SCIENCE("Science"), POP("Pop"), ROCK("Rock"), SPORTS("Sports");

    private final String printableName;

    QuestionType(String name) {
        this.printableName = name;
    }

    public String getPrintableName() {
        return printableName;
    }
}

class QuestionGenerator {

    private final Map<QuestionType, Questions> questionTypes = new HashMap<>();

    {
        questionTypes.put(QuestionType.POP, new Questions());
        questionTypes.put(QuestionType.SCIENCE, new Questions());
        questionTypes.put(QuestionType.SPORTS, new Questions());
        questionTypes.put(QuestionType.ROCK, new Questions());
    }

    public QuestionGenerator() {

        for (int i = 0; i < 50; i++) {
            questionTypes.get(QuestionType.POP).add("Pop Question " + i);
            questionTypes.get(QuestionType.SCIENCE).add("Science Question " + i);
            questionTypes.get(QuestionType.SPORTS).add("Sports Question " + i);
            questionTypes.get(QuestionType.ROCK).add("Rock Question " + i);
        }
    }

    public String getQuestion(QuestionType type) {
        return questionTypes.get(type).removeFirstQuestion();
    }
}

class BoardQuestionMapper {

    private final Map<Integer, QuestionType> questionTypeMap = new HashMap<>();

    {
        questionTypeMap.put(0, QuestionType.POP);
        questionTypeMap.put(1, QuestionType.SCIENCE);
        questionTypeMap.put(2, QuestionType.SPORTS);
        questionTypeMap.put(3, QuestionType.ROCK);
    }

    public QuestionType getQuestionTypeForPosition(int postion) {
        return questionTypeMap.get(postion % 4);
    }
}

class Player {

    private static final int MAX_ALLOWED_POSITION = 11;
    private static final int BOARD_POSITION_AMOUNT = 12;
    private boolean receivedPenalty;
    private boolean isGettingOutOfPenaltyBox;
    private int playerPosition;
    private int coins;
    private final String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition += playerPosition;
        if (this.playerPosition > MAX_ALLOWED_POSITION)
            this.playerPosition += -BOARD_POSITION_AMOUNT;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public int getCoins() {
        return coins;
    }

    public void setReceivedPenalty(boolean receivedPenalty) {
        this.receivedPenalty = receivedPenalty;
    }

    public boolean hasReceivedPenalty() {
        return receivedPenalty;
    }

    public boolean isGettingOutOfPenaltyBox() {
        return isGettingOutOfPenaltyBox;
    }

    public void setGettingOutOfPenaltyBox(boolean gettingOutOfPenaltyBox) {
        isGettingOutOfPenaltyBox = gettingOutOfPenaltyBox;
    }
}

class PositionShiftingHandler {

    private final Player currentPlayer;
    private final MessageHandler messageHandler;

    public PositionShiftingHandler(Player currentPlayer, PlayerTurnHandler playerTurnHandler) {
        this.currentPlayer = currentPlayer;
        this.messageHandler = new MessageHandler(playerTurnHandler);
    }

    public boolean isShiftable(int roll) {
        return !currentPlayer.hasReceivedPenalty() || handlePenaltyBox(roll);
    }

    private boolean handlePenaltyBox(int roll) {
        boolean condition = roll % 2 != 0;
        messageHandler.penaltyBoxMessage(condition);
        currentPlayer.setGettingOutOfPenaltyBox(condition);
        return condition;
    }
}

class PlayerTurnHandler {

    private final List<Player> players;
    private int currentPlayerIndex;

    public PlayerTurnHandler(List<Player> players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void setNextPlayerTurn() {
        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) {
            currentPlayerIndex = 0;
        }
    }

    public int getPlayersAmount() {
        return players.size();
    }
}

class MessageHandler {

    private final PlayerTurnHandler playerTurnHandler;

    public MessageHandler(PlayerTurnHandler playerTurnHandler) {
        this.playerTurnHandler = playerTurnHandler;
    }

    public void incorrectAnswerMessage() {
        System.out.println("Question was incorrectly answered");
        System.out.println(getCurrentPlayer().getName() + " was sent to the penalty box");
    }


    public void printCurrentPlayerStatus() {
        System.out.println("Answer was correct!!!!");
        System.out.println(getCurrentPlayer().getName()
                + " now has "
                + getCurrentPlayer().getCoins()
                + " Gold Coins.");
    }

    public void printCurrentPlayerRollNumber(int roll) {
        System.out.println(getCurrentPlayer().getName() + " is the current player");
        System.out.println("They have rolled a " + roll);
    }

    public void printCurrentPlayerLocationAndCategoryStatus(QuestionType questionType) {
        System.out.println(getCurrentPlayer().getName()
                + "'s new location is "
                + getCurrentPlayer().getPlayerPosition());
        System.out.println("The category is " + questionType.getPrintableName());
    }

    public void playerAdditionMessage(Player player) {
        System.out.println(player.getName() + " was added");
        System.out.println("They are player number " + playerTurnHandler.getPlayersAmount());
    }

    public void penaltyBoxMessage(boolean condition) {
        String s = condition ? "" : "not ";
        System.out.println(getCurrentPlayer().getName() + " is " + s + "getting out of the penalty box");
    }

    private Player getCurrentPlayer() {
        return playerTurnHandler.getCurrentPlayer();
    }
}

class AnswerResolver {

    private static final int MAX_COINS_AMOUNT = 6;

    private final PlayerTurnHandler playerTurnHandler;
    private final MessageHandler messageHandler;

    public AnswerResolver(PlayerTurnHandler playerTurnHandler) {
        this.playerTurnHandler = playerTurnHandler;
        this.messageHandler = new MessageHandler(playerTurnHandler);
    }

    public boolean wrongAnswer() {
        messageHandler.incorrectAnswerMessage();
        getCurrentPlayer().setReceivedPenalty(true);
        playerTurnHandler.setNextPlayerTurn();
        return false;
    }

    public boolean wasCorrectlyAnswered() {
        if (getCurrentPlayer().hasReceivedPenalty()) {
            if (getCurrentPlayer().isGettingOutOfPenaltyBox()) {
                return correctAnswer();
            } else {
                playerTurnHandler.setNextPlayerTurn();
                return false;
            }
        } else {
            return correctAnswer();
        }
    }

    private boolean correctAnswer() {
        getCurrentPlayer().addCoins(1);
        messageHandler.printCurrentPlayerStatus();
        boolean winner = didPlayerWin();
        playerTurnHandler.setNextPlayerTurn();
        return winner;
    }

    private boolean didPlayerWin() {
        return getCurrentPlayer().getCoins() == MAX_COINS_AMOUNT;
    }

    private Player getCurrentPlayer() {
        return playerTurnHandler.getCurrentPlayer();
    }
}

public class Game {
    private final QuestionGenerator questionGenerator;
    private final BoardQuestionMapper boardQuestionMapper;
    private final PlayerTurnHandler playerTurnHandler;
    private final AnswerResolver answerResolver;
    private final ArrayList<Player> players = new ArrayList<>();
    private final MessageHandler messageHandler;

    public Game() {
        this.questionGenerator = new QuestionGenerator();
        this.boardQuestionMapper = new BoardQuestionMapper();
        this.playerTurnHandler = new PlayerTurnHandler(players);
        this.answerResolver = new AnswerResolver(playerTurnHandler);
        this.messageHandler = new MessageHandler(playerTurnHandler);
    }

    public boolean wasCorrectlyAnswered() {
        return answerResolver.wasCorrectlyAnswered();
    }

    public boolean wrongAnswer() {
        return answerResolver.wrongAnswer();
    }

    public boolean add(String playerName) {
        Player player = new Player(playerName);
        players.add(player);
        messageHandler.playerAdditionMessage(player);
        return true;
    }

    public void roll(int roll) {
        messageHandler.printCurrentPlayerRollNumber(roll);

        boolean shiftable = new PositionShiftingHandler(getCurrentPlayer(), playerTurnHandler).isShiftable(roll);
        if (shiftable) {
            setNewPosition(roll);
            askQuestion();
        }

    }

    private void setNewPosition(int roll) {
        getCurrentPlayer().setPlayerPosition(roll);
        messageHandler.printCurrentPlayerLocationAndCategoryStatus(currentCategory());
    }

    private void askQuestion() {
        System.out.println(questionGenerator.getQuestion(currentCategory()));
    }

    private QuestionType currentCategory() {
        return boardQuestionMapper.getQuestionTypeForPosition(getCurrentPlayer().getPlayerPosition());
    }

    private Player getCurrentPlayer() {
        return playerTurnHandler.getCurrentPlayer();
    }
}