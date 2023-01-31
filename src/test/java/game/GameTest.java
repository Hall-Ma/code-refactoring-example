package game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.approvaltests.Approvals.NAMES;
import static org.approvaltests.Approvals.verify;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
class GameTest {

    ByteArrayOutputStream byteStream;

    @BeforeEach
    void setUp() {
        byteStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteStream));
    }

    @AfterEach
    public void teardown() {
        System.setOut(System.out);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testRollOutSidePenaltyBox(int parameter) {
        Game game = new Game();
        game.add("David");

        game.roll(parameter);

        verify(byteStream.toString(), NAMES.withParameters(String.valueOf(parameter)));
    }

    @Test
    void testRollAndResetPlaces() {
        Game game = new Game();
        game.add("David");

        // Only for test purposes a 12 is rolled here, this will not happen in production
        game.roll(12);

        verify(byteStream.toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4})
    void testRollDontGetOutOfPenaltyBox(int parameter) {
        Game game = new Game();
        game.add("David");
        game.wrongAnswer();

        game.roll(parameter);

        verify(byteStream.toString(), NAMES.withParameters(String.valueOf(parameter)));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5})
    void testRollGetOutOfPenaltyBox(int parameter) {
        Game game = new Game();
        game.add("David");
        game.wrongAnswer();

        game.roll(parameter);

        verify(byteStream.toString(), NAMES.withParameters(String.valueOf(parameter)));
    }

    @Test
    void testRollGetOutOfPenaltyBoxAndResetPlaces() {
        Game game = new Game();
        game.add("David");
        game.wrongAnswer();

        // Only for test purposes a 13 is rolled here, this will not happen in production
        game.roll(13);

        verify(byteStream.toString());
    }

    @Test
    void testCorrectAnswer() {
        Game game = new Game();
        game.add("David");

        boolean hasPlayerNotWon = game.wasCorrectlyAnswered();

        assertTrue(hasPlayerNotWon);
        verify(byteStream.toString());
    }

    @Test
    void testIsPlayerWinner() {
        Game game = new Game();
        game.add("David");
        boolean hasPlayerNotWon = true;

        for (int i = 0; i < 6; i++) {
            hasPlayerNotWon = game.wasCorrectlyAnswered();
        }

        assertFalse(hasPlayerNotWon);
        verify(byteStream.toString());
    }

    @Test
    void testPlayerCannotAnswerQuestion() {
        Game game = new Game();
        game.add("David");
        game.wrongAnswer();
        game.roll(2);

        boolean hasPlayerNotWon = game.wasCorrectlyAnswered();

        assertTrue(hasPlayerNotWon);
        verify(byteStream.toString());
    }

    @Test
    void testAnswerCorrectQuestionAfterGettingOutOfPenaltyBox() {
        Game game = new Game();
        game.add("David");
        game.wrongAnswer();
        game.roll(3);

        boolean hasPlayerNotWon = game.wasCorrectlyAnswered();

        assertTrue(hasPlayerNotWon);
        verify(byteStream.toString());
    }

    @Test
    void testCurrentPlayerGetsCoinAfterCorrectAnswer() {
        Game game = new Game();
        game.add("David");
        game.add("Julia");

        boolean hasPlayerNotWon = game.wasCorrectlyAnswered();

        assertTrue(hasPlayerNotWon);
        verify(byteStream.toString());
    }
}
