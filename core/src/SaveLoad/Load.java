package SaveLoad;

import sepr.game.GameScreen;
import sepr.game.Main;
import sepr.game.TurnPhaseType;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
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
        if (loadedSave != null) {
            loadedSave.updatePlayers(gameScreen.getPlayers(), gameScreen);
            boolean allocateNeutralPlayer = false;
            if (gameScreen.getPlayers().keySet().contains(4)) {
                allocateNeutralPlayer = true;
            }
            gameScreen.setupGame(gameScreen.getPlayers(),
                    loadedSave.isTurnTimerEnabled(),
                    loadedSave.getMaxTurnTime(),
                    allocateNeutralPlayer);
            loadedSave.updateSectors(gameScreen.getSectors(), gameScreen.getPlayers());
            gameScreen.setTurnOrder(loadedSave.getTurnOrder());
            gameScreen.setCurrentPlayerPointer(loadedSave.getCurrentPlayerPointer());
            if (loadedSave.getCurrentPhase() == TurnPhaseType.REINFORCEMENT) {
                gameScreen.getCurrentPlayer().addTroopsToAllocate(-5);
            }
            gameScreen.updateBonus();
            gameScreen.setCurrentPhase(loadedSave.getCurrentPhase());
            if (loadedSave.isTurnTimerEnabled()) {
                System.out.println("On load: " + loadedSave.getTurnTimeElapsed() / 1000);
                gameScreen.setTurnTimeStart(System.currentTimeMillis() - loadedSave.getTurnTimeElapsed());
            }
            main.returnGameScreen();
            gameScreen.getPhases().get(gameScreen.getCurrentPhase()).enterPhase(gameScreen.getCurrentPlayer());
            gameScreen.resetPausedTime(); // To reset pausedTime to 0 now load is finished
            System.out.println("Load Successful");
        } else {
            throw new IOException("Load Error");
        }
    }
}
