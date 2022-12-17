package game;

import org.junit.jupiter.api.Test;
import runner.GameRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.approvaltests.Approvals.verify;

class GameRunnerTest {

    @Test
    void testGamePlay() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteStream);
        System.setOut(printStream);
        String[] args = new String[]{"1"};

        GameRunner.main(args);

        verify(byteStream.toString());
    }
}
