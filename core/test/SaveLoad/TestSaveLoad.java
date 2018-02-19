package SaveLoad;

import com.badlogic.gdx.graphics.Color;
import org.junit.Before;
import org.junit.Test;
import sepr.game.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class TestSaveLoad {
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

    @SuppressWarnings("Duplicates")
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
    public void testSave() {
        Save.saveGame("testSave.data",
                this.currentPhase,
                this.sectors,
                this.players,
                this.turnOrder,
                this.currentPlayerPointer,
                this.turnTimerEnabled,
                this.maxTurnTime,
                this.turnTimeElapsed,
                this.isPaused);
        Data loadedSave = this.loadTestSave("testSave.data");
        assertTrue(loadedSave != null);
    }

    @Test
    public void testLoad() {
        Save.saveGame("testSave.data",
                this.currentPhase,
                this.sectors,
                this.players,
                this.turnOrder,
                this.currentPlayerPointer,
                this.turnTimerEnabled,
                this.maxTurnTime,
                this.turnTimeElapsed,
                this.isPaused);
        Data loadedSave = this.loadTestSave("testSave.data");
        assertTrue(loadedSave != null);

        assertEquals(this.testData.getCurrentPhase(), loadedSave.getCurrentPhase());

        Integer[] sectorKeys = loadedSave.getSectors().keySet().toArray(new Integer[loadedSave.getSectors().size()]);
        HashMap<Integer, Sector> savedSectors =  TestShrunkenSector.loadSectors();
        loadedSave.updateSectors(savedSectors, loadedSave.getFullPlayers());
        HashMap<Integer, Sector> unsavedSectors =  TestShrunkenSector.loadSectors();
        testData.updateSectors(unsavedSectors, testData.getFullPlayers());
        for (int i = 0; i < sectorKeys.length; i++) {
            Sector unsavedSector = unsavedSectors.get(sectorKeys[i]);
            Sector savedSector = savedSectors.get(sectorKeys[i]);
            System.out.println(unsavedSector);
            System.out.println(savedSector);
            assertEquals(savedSector.getOwnerId(), unsavedSector.getOwnerId());
            assertEquals(savedSector.getCollege(), unsavedSector.getCollege());
            assertEquals(savedSector.getDisplayName(), unsavedSector.getDisplayName());
            assertEquals(savedSector.getReinforcementsProvided(), unsavedSector.getReinforcementsProvided());
            assertEquals(savedSector.getUnitsInSector(), unsavedSector.getUnitsInSector());
            assertEquals(savedSector.isNeutral(), unsavedSector.isNeutral());
        }

        Integer[] playerKeys = testData.getPlayers().keySet().toArray(new Integer[testData.getPlayers().size()]);
        for (int i = 0; i < playerKeys.length; i++) {
            Player unsavedPlayer = testData.getFullPlayers().get(playerKeys[i]);
            Player savedPlayer = loadedSave.getFullPlayers().get(playerKeys[i]);
            assertEquals(savedPlayer.getId(), unsavedPlayer.getId());
            assertEquals(savedPlayer.getCollegeName(), unsavedPlayer.getCollegeName());
            assertEquals(savedPlayer.getTroopsToAllocate(), unsavedPlayer.getTroopsToAllocate());
            assertEquals(savedPlayer.getSectorColour(), unsavedPlayer.getSectorColour());
            assertEquals(savedPlayer.getPlayerType(), unsavedPlayer.getPlayerType());
            assertEquals(savedPlayer.getPlayerName(), unsavedPlayer.getPlayerName());
        }

        assertEquals(loadedSave.getTurnOrder(), testData.getTurnOrder());
        assertEquals(loadedSave.getCurrentPlayerPointer(), testData.getCurrentPlayerPointer());
        assertEquals(loadedSave.isTurnTimerEnabled(), testData.isTurnTimerEnabled());
        assertEquals(loadedSave.getMaxTurnTime(), testData.getMaxTurnTime());
        assertEquals(loadedSave.getTurnTimeElapsed(), testData.getTurnTimeElapsed());
        assertEquals(loadedSave.isPaused(), testData.isPaused());
    }

    @SuppressWarnings("Duplicates")
    //As load function requires screen, below code is a copy of the read of the file.
    private Data loadTestSave(String fileName) {
        Path currentRelativePath = Paths.get("");
        String currentWorkingDir = currentRelativePath.toAbsolutePath().toString();
        String filePath = currentWorkingDir + "\\assets\\saves\\" + fileName;
        ObjectInputStream ois = null;
        Data loadedSave = null;
        try {
            int test;
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
            loadedSave = (Data) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {

                }
            }
        }
        return loadedSave;
    }
}
