package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.approvaltests.Approvals.NAMES;
import static org.approvaltests.Approvals.verify;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    ByteArrayOutputStream baos;

    @BeforeEach
    void setUp() {
        baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
    }

    @Test
    public void testPlayerSize() {
        Game game = new Game();
        game.players = new ArrayList();

        assertEquals(0, game.howManyPlayers());
    }

    @Test
    public void testAddPlayer() {
        Game game = new Game();

        assertEquals(game.howManyPlayers(), 0);

        assertTrue(game.add("David"));
        assertEquals(game.howManyPlayers(), 1);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    public void testRollOutSidePenaltyBox(int parameter) {
        Game game = new Game();
        game.add("David");

        game.roll(parameter);

        verify(baos.toString(), NAMES.withParameters(String.valueOf(parameter)));
    }

    @Test
    public void testRollAndResetPlaces() {
        Game game = new Game();
        game.add("David");

        // Only for test purposes a 12 is rolled here, this will not happen in production
        game.roll(12);

        verify(baos.toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4})
    public void testRollDontGetOutOfPenaltyBox(int parameter) {
        Game game = new Game();
        game.add("David");
        game.wrongAnswer();

        game.roll(parameter);

        verify(baos.toString(), NAMES.withParameters(String.valueOf(parameter)));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5})
    public void testRollGetOutOfPenaltyBox(int parameter) {
        Game game = new Game();
        game.add("David");
        game.wrongAnswer();

        game.roll(parameter);

        verify(baos.toString(), NAMES.withParameters(String.valueOf(parameter)));
    }

    @Test
    public void testRollGetOutOfPenaltyBoxAndResetPlaces() {
        Game game = new Game();
        game.add("David");
        game.wrongAnswer();

        // Only for test purposes a 13 is rolled here, this will not happen in production
        game.roll(13);

        verify(baos.toString());
    }

    @Test
    public void testCorrectAnswer() {
        Game game = new Game();
        game.add("David");

        boolean hasPlayerNotWon = game.wasCorrectlyAnswered();

        assertTrue(hasPlayerNotWon);
        verify(baos.toString());
    }

    @Test
    public void testIsPlayerWinner() {
        Game game = new Game();
        game.add("David");
        boolean hasPlayerNotWon = true;

        for (int i = 0; i < 6; i++) {
            hasPlayerNotWon = game.wasCorrectlyAnswered();
        }

        assertFalse(hasPlayerNotWon);
        verify(baos.toString());
    }

    @Test
    public void testPlayerCannotAnswerQuestion() {
        Game game = new Game();
        game.add("David");
        game.wrongAnswer();
        game.roll(2);

        boolean hasPlayerNotWon = game.wasCorrectlyAnswered();

        assertTrue(hasPlayerNotWon);
        verify(baos.toString());
    }

    @Test
    public void testAnswerCorrectQuestionAfterGettingOutOfPenaltyBox() {
        Game game = new Game();
        game.add("David");
        game.wrongAnswer();
        game.roll(3);

        boolean hasPlayerNotWon = game.wasCorrectlyAnswered();

        assertTrue(hasPlayerNotWon);
        verify(baos.toString());
    }
}
