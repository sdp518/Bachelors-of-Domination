package SaveLoad;

import sepr.game.GameScreen;
import sepr.game.Main;
import sepr.game.TurnPhaseType;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Load {
    public static void loadGame(GameScreen gameScreen, Main main) throws IOException{
        Path currentRelativePath = Paths.get("");
        String currentWorkingDir = currentRelativePath.toAbsolutePath().toString();
        String fileName = currentWorkingDir + "\\saves\\TestSave.data";
        ObjectInputStream ois = null;
        Data loadedSave = null;
        try {
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
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
            gameScreen.setupGame(gameScreen.getPlayers(), false, 0 , true);
            loadedSave.updateSectors(gameScreen.getSectors(), gameScreen.getPlayers());
            gameScreen.setTurnOrder(loadedSave.getTurnOrder());
            gameScreen.setCurrentPlayerPointer(loadedSave.getCurrentPlayerPointer());
            if (gameScreen.getCurrentPhase() == TurnPhaseType.REINFORCEMENT) {
                gameScreen.getCurrentPlayer().addTroopsToAllocate(-5);
            }
            gameScreen.getPhases().get(gameScreen.getCurrentPhase()).enterPhase(gameScreen.getCurrentPlayer());
            gameScreen.setCurrentPhase(loadedSave.getCurrentPhase());
            System.out.println("before input: " + gameScreen.getPhases());
            main.returnGameScreen();
            System.out.println("Load Successful");
        } else {
            throw new IOException("Load Error");
        }
    }
}
