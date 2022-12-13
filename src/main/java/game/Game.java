package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    List<Player> players = new ArrayList<>();
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
        this.players.add(new Player(playerName));

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        System.out.println(players.get(this.currentPlayer).getName() + " is the current player");
        System.out.println("They have rolled a " + roll);
        int numberOutOfPlaces = 11;
        int maxNumberOfPlaces = 12;
        int newPlace;

        if (players.get(this.currentPlayer).isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(this.players.get(this.currentPlayer).getName() + " is getting out of the penalty box");
                newPlace = this.players.get(this.currentPlayer).getPlace() + roll;
                this.players.get(this.currentPlayer).setPlace(newPlace);
                if (this.players.get(this.currentPlayer).getPlace() > numberOutOfPlaces)
                    newPlace -= maxNumberOfPlaces;
                this.players.get(this.currentPlayer).setPlace(newPlace);

                System.out.println(players.get(this.currentPlayer).getName()
                        + "'s new location is "
                        + this.players.get(this.currentPlayer).getPlace());
                System.out.println("The category is " + currentCategory().getCategoryName());
                askQuestion();
            } else {
                System.out.println(this.players.get(this.currentPlayer).getName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {
            newPlace = this.players.get(this.currentPlayer).getPlace() + roll;
            this.players.get(this.currentPlayer).setPlace(newPlace);
            if (this.players.get(this.currentPlayer).getPlace() > numberOutOfPlaces)
                newPlace -= maxNumberOfPlaces;
            this.players.get(0).setPlace(newPlace);

            System.out.println(players.get(this.currentPlayer).getName()
                    + "'s new location is "
                    + this.players.get(this.currentPlayer).getPlace());
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
        if (Category.POP.getNumberOfCategory().contains(this.players.get(this.currentPlayer).getPlace()))
            return Category.POP;
        if (Category.SCIENCE.getNumberOfCategory().contains(this.players.get(this.currentPlayer).getPlace()))
            return Category.SCIENCE;
        if (Category.SPORTS.getNumberOfCategory().contains(this.players.get(this.currentPlayer).getPlace()))
            return Category.SPORTS;
        return Category.ROCK;
    }

    public boolean wasCorrectlyAnswered() {
        int firstPlacing = 0;
        int newPurse;
        if (this.players.get(this.currentPlayer).isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                newPurse = this.players.get(this.currentPlayer).getPurse() + 1;
                this.players.get(this.currentPlayer).setPurse(newPurse);
                System.out.println(players.get(this.currentPlayer).getName()
                        + " now has "
                        + this.players.get(currentPlayer).getPurse()
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
            newPurse = this.players.get(this.currentPlayer).getPurse() + 1;
            this.players.get(this.currentPlayer).setPurse(newPurse);

            System.out.println(players.get(this.currentPlayer).getName()
                    + " now has "
                    + this.players.get(this.currentPlayer).getPurse()
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            this.currentPlayer++;
            if (this.currentPlayer == players.size()) this.currentPlayer = firstPlacing;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(this.currentPlayer).getName() + " was sent to the penalty box");
        this.players.get(this.currentPlayer).setInPenaltyBox(true);
        int firstPlacing = 0;

        this.currentPlayer++;
        if (this.currentPlayer == players.size()) this.currentPlayer = firstPlacing;
        return true;
    }


    private boolean didPlayerWin() {
        int numberOfCoinsToWin = 6;
        return !(this.players.get(this.currentPlayer).getPurse() == numberOfCoinsToWin);
    }
}
