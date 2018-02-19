package SaveLoad;

import sepr.game.Player;
import sepr.game.Sector;
import sepr.game.TurnPhaseType;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

/**
 * NEW CLASS
 * Class Save to handle all functions associated with saving the important data to be playable another time.
 * Starts by setting the file name, then writing the data object to file.
 */
public class Save {
    public static void saveGame(String fileName,
                                TurnPhaseType currentPhase,
                                HashMap<Integer, Sector> sectors,
                                HashMap<Integer, Player> players,
                                List<Integer> turnOrder,
                                int currentPlayerPointer,
                                boolean turnTimerEnabled,
                                int maxTurnTime,
                                long turnTimeElapsed,
                                boolean isPaused) {
        Path currentRelativePath = Paths.get("");
        String currentWorkingDir = currentRelativePath.toAbsolutePath().toString();
        if (!currentWorkingDir.contains("assets")) {
            currentWorkingDir += "\\assets";
        }
        String filePath = currentWorkingDir + "\\saves\\" + fileName;
        Data thisSave = new Data(currentPhase,
                sectors,
                players,
                turnOrder,
                currentPlayerPointer,
                turnTimerEnabled,
                maxTurnTime,
                turnTimeElapsed,
                isPaused);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
            oos.writeObject(thisSave);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("Sorry something went wrong! Try restarting the application."); //Not a good error message, need to mitigate this.
        } finally {
            if (oos != null) {
                System.out.println("Save Successful");
                try {
                    oos.close();
                } catch (IOException e) {

                }
            }
        }
    }
}
