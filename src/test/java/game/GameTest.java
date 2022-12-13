package game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Test
    public void testPlayerSize() {
        Game game = new Game();
        game.players = new ArrayList();

        assertEquals(0, game.howManyPlayers());
    }
}
