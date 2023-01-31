package runner;

import game.Game;

import java.util.Random;

public class GameRunner {
    private static boolean notAWinner;

    public static void main(String[] args) {
        Game aGame = new Game();

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        Random rand = args.length > 0 ? new Random(Long.parseLong(args[0])) : new Random();

        do {

            aGame.roll(rand.nextInt(5) + 1);

            if (rand.nextInt(9) == 7) {
                notAWinner = aGame.answerIsWrong();
            } else {
                notAWinner = aGame.answerIsRight();
            }



        } while (notAWinner);

    }
}
