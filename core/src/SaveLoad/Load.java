package SaveLoad;

import sepr.game.GameScreen;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Load {
    public static void loadGame(GameScreen gameScreen) throws IOException{
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
            gameScreen.setCurrentPhase(loadedSave.getCurrentPhase());
            loadedSave.updatePlayers(gameScreen.getPlayers());
            loadedSave.updateSectors(gameScreen.getSectors(), gameScreen.getPlayers());
            gameScreen.setTurnOrder(loadedSave.getTurnOrder());
            gameScreen.setCurrentPlayerPointer(loadedSave.getCurrentPlayerPointer());
            gameScreen.getPhases().get(gameScreen.getCurrentPhase()).enterPhase(gameScreen.getCurrentPlayer());
            System.out.println("Load Successful");
        } else {
            throw new IOException("Load Error");
        }
    }
}
