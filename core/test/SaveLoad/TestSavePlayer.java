package SaveLoad;

import com.badlogic.gdx.graphics.Color;
import org.junit.Test;
import sepr.game.GameSetupScreen;
import sepr.game.Player;
import sepr.game.PlayerType;
import static org.junit.Assert.assertEquals;


public class TestSavePlayer {

    @Test
    public void testContructor() {
        Player testPlayer1 = new Player(0, GameSetupScreen.CollegeName.UNI_OF_YORK, Color.WHITE, PlayerType.NEUTRAL_AI, "THE NEUTRAL PLAYER");
        Player testPlayer2 = new Player(1, GameSetupScreen.CollegeName.ALCUIN, Color.RED, PlayerType.HUMAN, "TestPlayer2");
        SavePlayer saveTestPlayer1 = new SavePlayer(testPlayer1);
        SavePlayer saveTestPlayer2 = new SavePlayer(testPlayer2);

        assertEquals(testPlayer1.getId(), saveTestPlayer1.getId());
        assertEquals(testPlayer1.getCollegeName(), saveTestPlayer1.getCollegeName());
        assertEquals(testPlayer1.getTroopsToAllocate(), saveTestPlayer1.getTroopsToAllocate());
        assertEquals(testPlayer1.getSectorColour(), saveTestPlayer1.getSectorColor());
        assertEquals(testPlayer1.getPlayerType(), saveTestPlayer1.getPlayerType());
        assertEquals(testPlayer1.getPlayerName(), saveTestPlayer1.getPlayerName());

        assertEquals(testPlayer2.getId(), saveTestPlayer2.getId());
        assertEquals(testPlayer2.getCollegeName(), saveTestPlayer2.getCollegeName());
        assertEquals(testPlayer2.getTroopsToAllocate(), saveTestPlayer2.getTroopsToAllocate());
        assertEquals(testPlayer2.getSectorColour(), saveTestPlayer2.getSectorColor());
        assertEquals(testPlayer2.getPlayerType(), saveTestPlayer2.getPlayerType());
        assertEquals(testPlayer2.getPlayerName(), saveTestPlayer2.getPlayerName());
    }

    @Test
    public void testPlayerReconstruction() {
        Player testPlayer1 = new Player(0, GameSetupScreen.CollegeName.UNI_OF_YORK, Color.WHITE, PlayerType.NEUTRAL_AI, "THE NEUTRAL PLAYER");
        Player testPlayer2 = new Player(1, GameSetupScreen.CollegeName.ALCUIN, Color.RED, PlayerType.HUMAN, "TestPlayer2");

        SavePlayer saveTestPlayer1 = new SavePlayer(testPlayer1);
        SavePlayer saveTestPlayer2 = new SavePlayer(testPlayer2);
        Player remakeTestPlayer1 = new Player(saveTestPlayer1.getId(),
                                    saveTestPlayer1.getCollegeName(),
                                    saveTestPlayer1.getTroopsToAllocate(),
                                    saveTestPlayer1.getSectorColor(),
                                    saveTestPlayer1.getPlayerType(),
                                    saveTestPlayer1.getPlayerName());

        Player remakeTestPlayer2 = new Player(saveTestPlayer2.getId(),
                saveTestPlayer2.getCollegeName(),
                saveTestPlayer2.getTroopsToAllocate(),
                saveTestPlayer2.getSectorColor(),
                saveTestPlayer2.getPlayerType(),
                saveTestPlayer2.getPlayerName());

        assertEquals(testPlayer1.getId(), remakeTestPlayer1.getId());
        assertEquals(testPlayer1.getCollegeName(), remakeTestPlayer1.getCollegeName());
        assertEquals(testPlayer1.getTroopsToAllocate(), remakeTestPlayer1.getTroopsToAllocate());
        assertEquals(testPlayer1.getSectorColour(), remakeTestPlayer1.getSectorColour());
        assertEquals(testPlayer1.getPlayerType(), remakeTestPlayer1.getPlayerType());
        assertEquals(testPlayer1.getPlayerName(), remakeTestPlayer1.getPlayerName());

        assertEquals(testPlayer2.getId(), remakeTestPlayer2.getId());
        assertEquals(testPlayer2.getCollegeName(), remakeTestPlayer2.getCollegeName());
        assertEquals(testPlayer2.getTroopsToAllocate(), remakeTestPlayer2.getTroopsToAllocate());
        assertEquals(testPlayer2.getSectorColour(), remakeTestPlayer2.getSectorColour());
        assertEquals(testPlayer2.getPlayerType(), remakeTestPlayer2.getPlayerType());
        assertEquals(testPlayer2.getPlayerName(), remakeTestPlayer2.getPlayerName());
    }
}
