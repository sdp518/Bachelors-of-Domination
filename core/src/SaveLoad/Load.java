package SaveLoad;

import com.badlogic.gdx.scenes.scene2d.Stage;
import sepr.game.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * NEW CLASS
 * Class Load to handle all functions associated with loading the save file to be playable.
 * Starts by retrieving the file name, then reading the object stored into a variable.
 * Then sets up the GameScreen class as well as updating it with all of the data saved.
 */
public class Load {
    public static void loadGame(String fileName, GameScreen gameScreen, Main main) throws IOException{
        Path currentRelativePath = Paths.get("");
        String currentWorkingDir = currentRelativePath.toAbsolutePath().toString();
        String filePath = currentWorkingDir + "\\saves\\" + fileName;
        ObjectInputStream ois = null;
        Data loadedSave = null;
        try {
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
        if (loadedSave != null) {
            HashMap<Integer, Player> players = loadedSave.getFullPlayers();
            players.put(4, Player.createNeutralPlayer(4));
            boolean allocateNeutralPlayer = false;
            if (players.keySet().contains(4)) {
                allocateNeutralPlayer = true;
            }
            gameScreen.setupGame(players,
                    loadedSave.isTurnTimerEnabled(),
                    loadedSave.getMaxTurnTime(),
                    allocateNeutralPlayer);
            loadedSave.updateSectors(gameScreen.getSectors(), gameScreen.getPlayers());
            gameScreen.setTurnOrder(loadedSave.getTurnOrder());
            gameScreen.setCurrentPlayerPointer(loadedSave.getCurrentPlayerPointer());
            if (loadedSave.getCurrentPhase() == TurnPhaseType.REINFORCEMENT) {
                gameScreen.getCurrentPlayer().addTroopsToAllocate(-3);
            }
            gameScreen.updateBonus();
            gameScreen.setCurrentPhase(loadedSave.getCurrentPhase());
            if (loadedSave.isTurnTimerEnabled()) {
                gameScreen.setTurnTimeStart(System.currentTimeMillis() - loadedSave.getTurnTimeElapsed());
            }
            main.returnGameScreen();
            gameScreen.getPhases().get(gameScreen.getCurrentPhase()).enterPhase(gameScreen.getCurrentPlayer());
            gameScreen.resetPausedTime(); // To reset pausedTime to 0 now load is finished
            Stage stage = main.getSaveScreen().getStage();
            DialogFactory.basicDialogBox("Load Successful", "The game has been loaded successfully.", stage);
        } else {
            throw new IOException("Load Error");
        }
    }
}
