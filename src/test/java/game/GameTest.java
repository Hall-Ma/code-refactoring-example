package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.approvaltests.Approvals.NAMES;
import static org.approvaltests.Approvals.verify;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {

    ByteArrayOutputStream baos;

    @BeforeEach
    void setUp() {
        baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testRollOutSidePenaltyBox(int parameter) {
        Game game = new Game();
        game.addPlayer("David");

        game.handlePlayersTurn(parameter);

        verify(baos.toString(), NAMES.withParameters(String.valueOf(parameter)));
    }

    @Test
    void testRollAndResetPlaces() {
        Game game = new Game();
        game.addPlayer("David");

        // Only for test purposes a 12 is rolled here, this will not happen in production
        game.handlePlayersTurn(12);

        verify(baos.toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4})
    void testRollDontGetOutOfPenaltyBox(int parameter) {
        Game game = new Game();
        game.addPlayer("David");
        game.playerAnsweredIncorrectly();

        game.handlePlayersTurn(parameter);

        verify(baos.toString(), NAMES.withParameters(String.valueOf(parameter)));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5})
    void testRollGetOutOfPenaltyBox(int parameter) {
        Game game = new Game();
        game.addPlayer("David");
        game.playerAnsweredIncorrectly();

        game.handlePlayersTurn(parameter);

        verify(baos.toString(), NAMES.withParameters(String.valueOf(parameter)));
    }

    @Test
    void testRollGetOutOfPenaltyBoxAndResetPlaces() {
        Game game = new Game();
        game.addPlayer("David");
        game.playerAnsweredIncorrectly();

        // Only for test purposes a 13 is rolled here, this will not happen in production
        game.handlePlayersTurn(13);

        verify(baos.toString());
    }

    @Test
    void testCorrectAnswer() {
        Game game = new Game();
        game.addPlayer("David");

        boolean hasPlayerNotWon = game.playerAnsweredCorrectlyAndIsNotAWinner();

        assertTrue(hasPlayerNotWon);
        verify(baos.toString());
    }

    @Test
    void testIsPlayerWinner() {
        Game game = new Game();
        game.addPlayer("David");
        boolean hasPlayerNotWon = true;

        for (int i = 0; i < 6; i++) {
            hasPlayerNotWon = game.playerAnsweredCorrectlyAndIsNotAWinner();
        }

        assertFalse(hasPlayerNotWon);
        verify(baos.toString());
    }

    @Test
    void testPlayerCannotAnswerQuestion() {
        Game game = new Game();
        game.addPlayer("David");
        game.playerAnsweredIncorrectly();
        game.handlePlayersTurn(2);

        boolean hasPlayerNotWon = game.playerAnsweredCorrectlyAndIsNotAWinner();

        assertTrue(hasPlayerNotWon);
        verify(baos.toString());
    }

    @Test
    void testAnswerCorrectQuestionAfterGettingOutOfPenaltyBox() {
        Game game = new Game();
        game.addPlayer("David");
        game.playerAnsweredIncorrectly();
        game.handlePlayersTurn(3);

        boolean hasPlayerNotWon = game.playerAnsweredCorrectlyAndIsNotAWinner();

        assertTrue(hasPlayerNotWon);
        verify(baos.toString());
    }

    @Test
    void testCurrentPlayerGetsCoinAfterCorrectAnswer() {
        Game game = new Game();
        game.addPlayer("David");
        game.addPlayer("Julia");

        boolean hasPlayerNotWon = game.playerAnsweredCorrectlyAndIsNotAWinner();

        assertTrue(hasPlayerNotWon);
        verify(baos.toString());
    }
}
