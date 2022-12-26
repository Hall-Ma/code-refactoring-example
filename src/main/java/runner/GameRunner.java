package runner;

import game.Dice;
import game.Game;

import java.util.List;
import java.util.Random;

public class GameRunner {
    private static boolean notAWinner;

    public static void main(String[] args) {

        play(args);

    }

    private static void play(String[] args) {
        List<String> playerNames = List.of("Chet", "Pat", "Sue");
        Game aGame = new Game(playerNames);
        Random rand = args.length > 0 ? new Random(Long.parseLong(args[0])) : new Random();
        Dice dice = new Dice(rand);


        int answered;
        do {
            int rolledNumber = dice.roll();
            aGame.roll(rolledNumber);
            answered = rand.nextInt(9);
        } while (aGame.doesGameContinue(answered));
    }

}
