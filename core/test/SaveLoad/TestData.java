package SaveLoad;

import com.badlogic.gdx.graphics.Color;
import org.junit.Before;
import org.junit.Test;
import sepr.game.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestData {
    private HashMap<Integer, Sector> sectors;
    private HashMap<Integer, Player> players;
    private HashMap<Integer, Player> playersCopy;
    private List<Integer> turnOrder;
    private TurnPhaseType currentPhase;
    private int currentPlayerPointer;
    private boolean turnTimerEnabled;
    private int maxTurnTime;
    private long turnTimeElapsed;
    private boolean isPaused;
    private Data testData;

    @Before
    public void initAll() {
        this.sectors = TestShrunkenSector.loadSectors();
        Player testPlayer1 = new Player(0, GameSetupScreen.CollegeName.UNI_OF_YORK, Color.WHITE, PlayerType.NEUTRAL_AI, "THE NEUTRAL PLAYER");
        Player testPlayer2 = new Player(1, GameSetupScreen.CollegeName.ALCUIN, Color.RED, PlayerType.HUMAN, "TestPlayer2");
        this.players = new HashMap<>();
        this.players.put(0, testPlayer1);
        this.players.put(1, testPlayer2);
        this.playersCopy = new HashMap<>();
        this.playersCopy.put(0, testPlayer1);
        this.playersCopy.put(1, testPlayer2);
        this.turnOrder = new LinkedList<>();
        this.turnOrder.add(0);
        this.turnOrder.add(1);
        this.currentPhase = TurnPhaseType.REINFORCEMENT;
        this.currentPlayerPointer = 0;
        this.turnTimerEnabled = true;
        this.maxTurnTime = 300;
        this.turnTimeElapsed = 0;
        this.isPaused = false;

        this.testData = new Data(currentPhase,
                sectors,
                players,
                turnOrder,
                currentPlayerPointer,
                turnTimerEnabled,
                maxTurnTime,
                turnTimeElapsed,
                isPaused);
    }

    @Test
    public void testConstructor() {
        assertEquals(currentPhase, testData.getCurrentPhase());

        Integer[] sectorKeys = testData.getSectors().keySet().toArray(new Integer[testData.getSectors().size()]);
        for (int i = 0; i < sectorKeys.length; i++) {
            ShrunkenSector smallSector = testData.getSectors().get(sectorKeys[i]);
            Sector fullSector = sectors.get(sectorKeys[i]);
            assertEquals(fullSector.getOwnerId(), smallSector.getOwnerId());
            assertEquals(fullSector.getCollege(), smallSector.getCollege());
            assertEquals(fullSector.getDisplayName(), smallSector.getDisplayName());
            assertEquals(fullSector.getReinforcementsProvided(), smallSector.getReinforcementsProvided());
            assertEquals(fullSector.getUnitsInSector(), smallSector.getUnitsInSector());
            assertEquals(fullSector.isNeutral(), smallSector.isNeutral());
        }

        Integer[] playerKeys = testData.getPlayers().keySet().toArray(new Integer[testData.getPlayers().size()]);
        for (int i = 0; i < playerKeys.length; i++) {
            SavePlayer  savePlayer = testData.getPlayers().get(playerKeys[i]);
            Player fullPlayer = players.get(playerKeys[i]);
            assertEquals(savePlayer.getId(), fullPlayer.getId());
            assertEquals(savePlayer.getCollegeName(), fullPlayer.getCollegeName());
            assertEquals(savePlayer.getTroopsToAllocate(), fullPlayer.getTroopsToAllocate());
            assertEquals(savePlayer.getSectorColor(), fullPlayer.getSectorColour());
            assertEquals(savePlayer.getPlayerType(), fullPlayer.getPlayerType());
            assertEquals(savePlayer.getPlayerName(), fullPlayer.getPlayerName());
        }

        assertEquals(turnOrder, testData.getTurnOrder());
        assertEquals(currentPlayerPointer, testData.getCurrentPlayerPointer());
        assertEquals(turnTimerEnabled, testData.isTurnTimerEnabled());
        if (turnTimerEnabled) {
            assertEquals(maxTurnTime, testData.getMaxTurnTime());
            assertEquals(turnTimeElapsed, testData.getTurnTimeElapsed());
            assertEquals(isPaused, testData.isPaused());
        } else {
            assertEquals(-1, testData.getMaxTurnTime());
            assertEquals(-1, testData.getTurnTimeElapsed());
            assertEquals(false, testData.isPaused());
        }

    }

    @Test
    public void testUpdateSectors() {
        HashMap<Integer, Sector> updatedSectors = TestShrunkenSector.loadSectors();
        this.testData.updateSectors(updatedSectors, this.players);

        Integer[] sectorKeys = updatedSectors.keySet().toArray(new Integer[updatedSectors.size()]);
        for (int i = 0; i < sectorKeys.length; i++) {
            Sector updatedSector = updatedSectors.get(sectorKeys[i]);
            Sector unUpdatedSector = sectors.get(sectorKeys[i]);
            assertEquals(unUpdatedSector.getOwnerId(), updatedSector.getOwnerId());
            assertEquals(unUpdatedSector.getCollege(), updatedSector.getCollege());
            assertEquals(unUpdatedSector.getDisplayName(), updatedSector.getDisplayName());
            assertEquals(unUpdatedSector.getReinforcementsProvided(), updatedSector.getReinforcementsProvided());
            assertEquals(unUpdatedSector.getUnitsInSector(), updatedSector.getUnitsInSector());
            assertEquals(unUpdatedSector.isNeutral(), updatedSector.isNeutral());
        }
    }

    @Test
    public void testGetFullPlayers() {
        HashMap<Integer, Player> fullPlayers = testData.getFullPlayers();
        Integer[] playerKeys = fullPlayers.keySet().toArray(new Integer[fullPlayers.size()]);
        for (int i = 0; i < playerKeys.length; i++) {
            Player fullPlayer = fullPlayers.get(playerKeys[i]);
            Player originalPlayer = this.players.get(playerKeys[i]);
            assertEquals(originalPlayer.getId(), fullPlayer.getId());
            assertEquals(originalPlayer.getCollegeName(), fullPlayer.getCollegeName());
            assertEquals(originalPlayer.getTroopsToAllocate(), fullPlayer.getTroopsToAllocate());
            assertEquals(originalPlayer.getSectorColour(), fullPlayer.getSectorColour());
            assertEquals(originalPlayer.getPlayerType(), fullPlayer.getPlayerType());
            assertEquals(originalPlayer.getPlayerName(), fullPlayer.getPlayerName());
        }
    }

}
