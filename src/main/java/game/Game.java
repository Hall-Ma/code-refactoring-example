package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    public static final int SIZE_PLAYGROUND = 12;

    enum TypeOfQuestions {
        POP("Pop"), SCIENCE("Science"), SPORTS("Sports"), ROCK("Rock");
        String stringValue;

        TypeOfQuestions(String stringValue) {
            this.stringValue = stringValue;
        }

        public String toString() {
            return stringValue;
        }
    }

    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    List<String> popQuestions = new LinkedList<>();
    List<String> scienceQuestions = new LinkedList<>();
    List<String> sportsQuestions = new LinkedList<>();
    List<String> rockQuestions = new LinkedList<>();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        for (int i = 0; i < 50; i++) {
            popQuestions.add("Pop Question " + i);
            scienceQuestions.add(("Science Question " + i));
            sportsQuestions.add(("Sports Question " + i));
            rockQuestions.add(createRockQuestion(i));
        }
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {


        players.add(playerName);
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;
        inPenaltyBox[howManyPlayers()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                movePlayer(roll);

                System.out.println(players.get(currentPlayer)
                        + "'s new location is "
                        + places[currentPlayer]);
                System.out.println("The category is " + currentCategory());
                askQuestion();
            } else {
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            movePlayer(roll);

            System.out.println(players.get(currentPlayer)
                    + "'s new location is "
                    + places[currentPlayer]);
            System.out.println("The category is " + currentCategory());
            askQuestion();
        }

    }

    private void movePlayer(int roll) {
        places[currentPlayer] = places[currentPlayer] + roll;
        places[currentPlayer] = places[currentPlayer] % SIZE_PLAYGROUND;
    }

    private void askQuestion() {
        List<String> questions = null;
        switch (currentCategory()) {
            case POP:
                questions = popQuestions;
                break;
            case SCIENCE:
                questions = scienceQuestions;
                break;
            case SPORTS:
                questions = sportsQuestions;
                break;
            case ROCK:
                questions = rockQuestions;
        }
        System.out.println(questions.remove(0));
    }


    private TypeOfQuestions currentCategory() {
        switch (places[currentPlayer]) {
            case 0:
            case 4:
            case 8:
                return TypeOfQuestions.POP;
            case 1:
            case 5:
            case 9:
                return TypeOfQuestions.SCIENCE;
            case 2:
            case 6:
            case 10:
                return TypeOfQuestions.SPORTS;
            case 3:
            case 7:
            case 11:
                return TypeOfQuestions.ROCK;
            default:
                throw new IllegalStateException();
        }
    }

    public boolean currentPlayerGaveCorrectAnswer() {
        if (inPenaltyBox[currentPlayer] && !isGettingOutOfPenaltyBox) {
            nextPlayer();
            return false;
        }
        System.out.println("Answer was correct!!!!");
        purses[currentPlayer]++;
        System.out.println(players.get(currentPlayer)
                + " now has "
                + purses[currentPlayer]
                + " Gold Coins.");

        boolean winner = didPlayerWin();
        nextPlayer();

        return winner;
    }

    public void currentPlayGaveWrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        nextPlayer();
    }

    private void nextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }


    private boolean didPlayerWin() {
        return purses[currentPlayer] >= 6;
    }
}