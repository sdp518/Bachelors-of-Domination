package SaveLoad;

import sepr.game.Player;
import sepr.game.Sector;
import sepr.game.TurnPhaseType;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Save {
    public static void saveGame(TurnPhaseType currentPhase,
                                HashMap<Integer, Sector> sectors,
                                HashMap<Integer, Player> players,
                                List<Integer> turnOrder,
                                int currentPlayerPointer) {
        Path currentRelativePath = Paths.get("");
        String currentWorkingDir = currentRelativePath.toAbsolutePath().toString();
        String fileName = currentWorkingDir + "\\saves\\TestSave.data";
        Data thisSave = new Data(currentPhase, sectors, players, turnOrder, currentPlayerPointer);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
            oos.writeObject(thisSave);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("Sorry something went wrong! Try restarting the application.");
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {

                }
            }
            System.out.println("Save Successful");
        }
    }
}
