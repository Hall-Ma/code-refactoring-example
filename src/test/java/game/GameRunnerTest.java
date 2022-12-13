package game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import runner.GameRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class GameRunnerTest {

    @Test
    void testIfGameEnds() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteStream);
        System.setOut(printStream);
        String[] args = null;

        GameRunner.main(args);
        String[] splitOutput = byteStream.toString().split("\\n");
        String lastLine = splitOutput[splitOutput.length - 1];

        Assertions.assertTrue(lastLine.contains("now has 6 Gold Coins."));
    }
}
