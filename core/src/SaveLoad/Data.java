package SaveLoad;

import sepr.game.*;

import java.util.HashMap;
import java.util.List;

public class Data implements java.io.Serializable {
    private TurnPhaseType currentPhase;
    private HashMap<Integer, ShrunkenSector> sectors;
    private HashMap<Integer, SavePlayer> players;
    private List<Integer> turnOrder;
    private int currentPlayerPointer;

    public Data(TurnPhaseType currentPhase,
                HashMap<Integer, Sector> sectors,
                HashMap<Integer, Player> players,
                List<Integer> turnOrder,
                int currentPlayerPointer) {
        this.currentPhase = currentPhase;

        HashMap<Integer, ShrunkenSector> smallSectors = new HashMap<Integer, ShrunkenSector>();
        Integer[] keys = sectors.keySet().toArray(new Integer[sectors.size()]);
        for(int i = 0; i < sectors.size(); i++) {
            smallSectors.put(keys[i], new ShrunkenSector(sectors.get(keys[i])));
        }
        this.sectors = smallSectors;

        HashMap<Integer, SavePlayer> savePlayers = new HashMap<Integer, SavePlayer>();
        keys = players.keySet().toArray(new Integer[players.size()]);
        for(int i = 0; i < players.size(); i++) {
            savePlayers.put(keys[i], new SavePlayer(players.get(keys[i])));
        }
        this.players = savePlayers;
        this.turnOrder = turnOrder;
        this.currentPlayerPointer = currentPlayerPointer;
    }

    public TurnPhaseType getCurrentPhase() {
        return this.currentPhase;
    }

    public HashMap<Integer, ShrunkenSector> getSectors() {
        return this.sectors;
    }

    public void updateSectors(HashMap<Integer, Sector> sectors, HashMap<Integer, Player> players) {
        Integer[] keys = sectors.keySet().toArray(new Integer[sectors.size()]);
        Integer[] playerKeys = players.keySet().toArray(new Integer[players.size()]);
        for(int i = 0; i < sectors.size(); i++) {
            Sector fullSector = sectors.get(keys[i]);
            ShrunkenSector smallSector = this.sectors.get(keys[i]);
            fullSector.setOwnerId(smallSector.getOwnerId());
            for(int j = 0; j < playerKeys.length; j++) {
                if (smallSector.getOwnerId() == players.get(playerKeys[j]).getId()) {
                    fullSector.setOwner(players.get(playerKeys[j]));
                }
            }
            fullSector.setDisplayName(smallSector.getDisplayName());
            fullSector.setUnitsInSector(smallSector.getUnitsInSector());
            fullSector.setReinforcementsProvided(smallSector.getReinforcementsProvided());
            fullSector.setCollege(smallSector.getCollege());
            fullSector.setNeutral(smallSector.isNeutral());
        }
    }

    public HashMap<Integer, SavePlayer> getPlayers() {
        return this.players;
    }

    public void updatePlayers(HashMap<Integer, Player> players, GameScreen gameScreen) {
        Integer[] keys;
        if (players != null) {
            keys = players.keySet().toArray(new Integer[players.size()]);
            for (int i = 0; i < players.size(); i++) {
                players.remove(keys[i]);
            }
        } else {
            players = new HashMap<Integer, Player>();
        }
        keys = this.players.keySet().toArray(new Integer[this.players.size()]);
        for(int i = 0; i < keys.length; i++) {
            SavePlayer smallPlayer = this.players.get(keys[i]);
            players.put(keys[i], new Player(smallPlayer.getId(),
                    smallPlayer.getCollegeName(),
                    smallPlayer.getTroopsToAllocate(),
                    smallPlayer.getSectorColor(),
                    smallPlayer.getPlayerType(),
                    smallPlayer.getPlayerName()));
        }
        gameScreen.setPlayers(players);
    }

    public List<Integer> getTurnOrder() {
        return this.turnOrder;
    }

    public int getCurrentPlayerPointer() {
        return this.currentPlayerPointer;
    }
}
