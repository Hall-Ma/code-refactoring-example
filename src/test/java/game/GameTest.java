/*
package game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.approvaltests.Approvals.NAMES;
import static org.approvaltests.Approvals.verify;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        List<String> playerNames = List.of("David");
        Game game = new Game(playerNames);

        game.roll(parameter);

        verify(byteStream.toString(), NAMES.withParameters(String.valueOf(parameter)));
    }

    @Test
    void testRollAndResetPlaces() {
        List<String> playerNames = List.of("David");
        Game game = new Game(playerNames);

        // Only for test purposes a 12 is rolled here, this will not happen in production
        game.roll(12);

        verify(byteStream.toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4})
    void testRollDontGetOutOfPenaltyBox(int parameter) {
        List<String> playerNames = List.of("David");
        Game game = new Game(playerNames);
        game.wasIncorrectlyAnswered();

        game.roll(parameter);

        verify(byteStream.toString(), NAMES.withParameters(String.valueOf(parameter)));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5})
    void testRollGetOutOfPenaltyBox(int parameter) {
        List<String> playerNames = List.of("David");
        Game game = new Game(playerNames);
        game.wasIncorrectlyAnswered();

        game.roll(parameter);

        verify(byteStream.toString(), NAMES.withParameters(String.valueOf(parameter)));
    }

    @Test
    void testRollGetOutOfPenaltyBoxAndResetPlaces() {
        List<String> playerNames = List.of("David");
        Game game = new Game(playerNames);
        game.wasIncorrectlyAnswered();

        // Only for test purposes a 13 is rolled here, this will not happen in production
        game.roll(13);

        verify(byteStream.toString());
    }

    @Test
    void testCorrectAnswer() {
        List<String> playerNames = List.of("David");
        Game game = new Game(playerNames);
        int answered = 7;

        boolean hasPlayerNotWon = game.doesGameContinue(answered);

        assertTrue(hasPlayerNotWon);
        verify(byteStream.toString());
    }

    @Test
    void testIsPlayerWinner() {
        List<String> playerNames = List.of("David");
        Game game = new Game(playerNames);
        boolean hasPlayerNotWon = true;

        for (int i = 0; i < 6; i++) {
            hasPlayerNotWon = game.wasCorrectlyAnswered();
        }

        assertFalse(hasPlayerNotWon);
        verify(byteStream.toString());
    }

    @Test
    void testPlayerCannotAnswerQuestion() {
        List<String> playerNames = List.of("David");
        Game game = new Game(playerNames);
        game.wasIncorrectlyAnswered();
        game.roll(2);
        int answered = 1;

        boolean hasPlayerNotWon = game.doesGameContinue(answered);

        assertTrue(hasPlayerNotWon);
        verify(byteStream.toString());
    }

    @Test
    void testAnswerCorrectQuestionAfterGettingOutOfPenaltyBox() {
        List<String> playerNames = List.of("David");
        Game game = new Game(playerNames);
        game.wasIncorrectlyAnswered();
        game.roll(3);

        boolean hasPlayerNotWon = game.wasCorrectlyAnswered();

        assertTrue(hasPlayerNotWon);
        verify(byteStream.toString());
    }

    @Test
    void testCurrentPlayerGetsCoinAfterCorrectAnswer() {
        List<String> playerNames = List.of("David", "Julia");
        Game game = new Game(playerNames);
        int answered = 1;
        boolean hasPlayerNotWon = game.doesGameContinue(answered);

        assertTrue(hasPlayerNotWon);
        verify(byteStream.toString());
    }
}
*/
