package SaveLoad;

import sepr.game.*;
import java.util.HashMap;
import java.util.List;

/**
 * Data class stores all of the necessary data to save the game to be played another time.
 * All data is passed through the constructor and stored as variables.
 * The data is then retrieved through getters.
 */
public class Data implements java.io.Serializable {
    private TurnPhaseType currentPhase;
    private HashMap<Integer, ShrunkenSector> sectors;
    private HashMap<Integer, SavePlayer> players;
    private List<Integer> turnOrder;
    private int currentPlayerPointer;
    private boolean turnTimerEnabled;
    private int maxTurnTime;
    private long turnTimeElapsed;
    private boolean isPaused;

    /**
     * Constructor takes all the necessary data of the current game in progress.
     * This also transfers players and sectors to a serializable version of themselves.
     * @param currentPhase the current phase the game is in.
     * @param sectors the HashMap<Integer, Sector> storing all the sector data pertaining to the players and who owns what.
     * @param players the players that are in the game.
     * @param turnOrder the turn order of the game, i.e. whose turn is after whose.
     * @param currentPlayerPointer specifies the current players turn.
     * @param turnTimerEnabled whether or not the turn timer is enabled.
     * @param maxTurnTime the max time each player gets per turn.
     * @param turnTimeElapsed the time elapsed in the current turn.
     * @param isPaused whether the turn is paused or not.
     */
    public Data(TurnPhaseType currentPhase,
                HashMap<Integer, Sector> sectors,
                HashMap<Integer, Player> players,
                List<Integer> turnOrder,
                int currentPlayerPointer,
                boolean turnTimerEnabled,
                int maxTurnTime,
                long turnTimeElapsed,
                boolean isPaused) {
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
        this.turnTimerEnabled = turnTimerEnabled;
        if (turnTimerEnabled) {
            this.maxTurnTime = maxTurnTime;
            this.turnTimeElapsed = turnTimeElapsed;
            this.isPaused = isPaused;
        } else {
            this.maxTurnTime = -1;
            this.turnTimeElapsed = -1;
            this.isPaused = false;
        }
    }

    /**
     * To return the TurnPhaseType of the current phase of the game.
     * @return the current phase of the game that was stored.
     */
    public TurnPhaseType getCurrentPhase() {
        return this.currentPhase;
    }

    /**
     *
     * @return the HashMap of sectors that was saved, includes the smaller version of sector
     */
    public HashMap<Integer, ShrunkenSector> getSectors() { //possibly remove, redundant?
        return this.sectors;
    }

    /**
     * Re-writes the setup version of sectors and players that the game created with the versions that were stored.
     * This sets up most of the data that was part of the save game.
     * @param sectors the HashMap of sectors to be updated with sectors that were saved.
     * @param players the HashMap of players update the sectors with the correct owners.
     */
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

    public HashMap<Integer, SavePlayer> getPlayers() { //possibly remove, redundant?
        return this.players;
    }

    public HashMap<Integer, Player> getFullPlayers() {
        HashMap<Integer,Player> fullPlayers = new HashMap<Integer, Player>();
        Integer[] keys = this.players.keySet().toArray(new Integer[this.players.size()]);
        for(int i = 0; i < keys.length; i++) {
            SavePlayer smallPlayer = this.players.get(keys[i]);
            fullPlayers.put(keys[i], new Player(smallPlayer.getId(),
                    smallPlayer.getCollegeName(),
                    smallPlayer.getTroopsToAllocate(),
                    smallPlayer.getSectorColor(),
                    smallPlayer.getPlayerType(),
                    smallPlayer.getPlayerName()));
            fullPlayers.get(keys[i]).changeBonus(smallPlayer.getBonus());
        }
        return fullPlayers;
    }

    /**
     * Overwrites the setup players with the list of players that were playing the saved game.
     * @param players the HashMap of players to be updated with the HashMap of players that were saved.
     * //@param gameScreen the GameScreen being rewritten with the saved data.
     */
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
            players.get(keys[i]).changeBonus(smallPlayer.getBonus());
        }
        gameScreen.setPlayers(players);
    }

    /**
     * Returns the turn order of the game
     * @return the list of integers representing the order of turns of players.
     */
    public List<Integer> getTurnOrder() {
        return this.turnOrder;
    }

    /**
     * Returns the value of the current player in the turn order to resume play.
     * @return the current player pointer, pointing to the current players turn.
     */
    public int getCurrentPlayerPointer() {
        return this.currentPlayerPointer;
    }

    /**
     * True if there was a turn timer active in the saved game, false otherwise.
     * @return a boolean of whether there was a turn timer active or not.
     */
    public boolean isTurnTimerEnabled() {
        return this.turnTimerEnabled;
    }

    /**
     * The integer of the maximum amount of time a player gets per full turn.
     * @return the max time a player gets per turn.
     */
    public int getMaxTurnTime() {
        return this.maxTurnTime;
    }

    /**
     * Returns the realtime run time of the current players turn.
     * @return the time the current player has elapsed on their turn.
     */
    public long getTurnTimeElapsed() {
        return this.turnTimeElapsed;
    }

    /**
     * True if the game was paused at same, false otherwise.
     * @return a boolean of whether the game was paused or not.
     */
    public boolean isPaused() { //possibly remove, redundant? i.e. duh it was paused.
        return this.isPaused;
    }
}
