package game;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    LinkedList<Category> popQuestions;
    LinkedList<Category> scienceQuestions;
    LinkedList<Category> sportsQuestions;
    LinkedList<Category> rockQuestions;

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        QuestionGenerator questionGenerator = new QuestionGenerator();
        this.popQuestions = questionGenerator.getPopQuestions();
        this.scienceQuestions = questionGenerator.getScienceQuestions();
        this.sportsQuestions = questionGenerator.getSportsQuestions();
        this.rockQuestions = questionGenerator.getRockQuestions();
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
        int numberOutOfPlaces = 11;
        int maxNumberOfPlaces = 12;

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                places[currentPlayer] = places[currentPlayer] + roll;
                if (places[currentPlayer] > numberOutOfPlaces)
                    places[currentPlayer] = places[currentPlayer] - maxNumberOfPlaces;

                System.out.println(players.get(currentPlayer)
                        + "'s new location is "
                        + places[currentPlayer]);
                System.out.println("The category is " + currentCategory().getCategoryName());
                askQuestion();
            } else {
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > numberOutOfPlaces)
                places[currentPlayer] = places[currentPlayer] - maxNumberOfPlaces;

            System.out.println(players.get(currentPlayer)
                    + "'s new location is "
                    + places[currentPlayer]);
            System.out.println("The category is " + currentCategory().getCategoryName());
            askQuestion();
        }

    }

    private void askQuestion() {
        if (currentCategory() == Category.POP)
            System.out.println(popQuestions.removeFirst());
        if (currentCategory() == Category.SCIENCE)
            System.out.println(scienceQuestions.removeFirst());
        if (currentCategory() == Category.SPORTS)
            System.out.println(sportsQuestions.removeFirst());
        if (currentCategory() == Category.ROCK)
            System.out.println(rockQuestions.removeFirst());
    }


    private Category currentCategory() {
        if (Category.POP.getNumberOfCategory().contains(places[currentPlayer])) return Category.POP;
        if (Category.SCIENCE.getNumberOfCategory().contains(places[currentPlayer])) return Category.SCIENCE;
        if (Category.SPORTS.getNumberOfCategory().contains(places[currentPlayer])) return Category.SPORTS;
        return Category.ROCK;
    }

    public boolean wasCorrectlyAnswered() {
        int firstPlacing = 0;
        if (inPenaltyBox[this.currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                purses[this.currentPlayer]++;
                System.out.println(players.get(this.currentPlayer)
                        + " now has "
                        + purses[this.currentPlayer]
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                this.currentPlayer++;
                if (this.currentPlayer == players.size()) this.currentPlayer = firstPlacing;

                return winner;
            } else {
                this.currentPlayer++;
                if (this.currentPlayer == players.size()) this.currentPlayer = firstPlacing;
                return true;
            }



        } else {

            System.out.println("Answer was corrent!!!!");
            purses[this.currentPlayer]++;
            System.out.println(players.get(this.currentPlayer)
                    + " now has "
                    + purses[this.currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            this.currentPlayer++;
            if (this.currentPlayer == players.size()) this.currentPlayer = firstPlacing;

            return winner;
        }
    }

    public boolean wrongAnswer(){
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;
        int firstPlacing = 0;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = firstPlacing;
        return true;
    }


    private boolean didPlayerWin() {
        int numberOfCoinsToWin = 6;
        return !(purses[currentPlayer] == numberOfCoinsToWin);
    }
}
