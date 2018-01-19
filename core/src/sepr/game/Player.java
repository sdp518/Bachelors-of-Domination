package sepr.game;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Dom's Surface Mark 2 on 16/11/2017.
 */
public abstract class Player {
    private int id;
    private GameSetupScreen.CollegeName collegeName;
    private String playerName;
    private int troopsToAllocate; // how many troops the player has to allocate at the start of their next reinforcement phase
    private Color sectorColour; // what colour to shade sectors owned by the player
    private PlayerType playerType;

    /**
     * @param id player's unique identifier
     * @param collegeName display name for this player
     * @param sectorColour colour that the sectors owned by this player are coloured
     * @param playerType is this player a Human, AI or Neutral AI
     * @param playerName player's name to be displayed
     */
    public Player(int id, GameSetupScreen.CollegeName collegeName, Color sectorColour, PlayerType playerType, String playerName) {
        this.id = id;
        this.collegeName = collegeName;
        this.troopsToAllocate = 0;
        this.sectorColour = sectorColour;
        this.playerType = playerType;
        this.playerName = playerName;
    }

    public int getId() {
        return id;
    }

    public GameSetupScreen.CollegeName getCollegeName() {
        return collegeName;
    }

    public Color getSectorColour() {
        return sectorColour;
    }

    /**
     * sets the number of troops this player has to allocate to this value
     * @param troopsToAllocate number of troops to allocate
     */
    public void setTroopsToAllocate(int troopsToAllocate) {
        this.troopsToAllocate = troopsToAllocate;
    }

    /**
     * increases the number of troops to allocate by the the given amount
     * @param troopsToAllocate amount to increase allocation by
     */
    public void addTroopsToAllocate(int troopsToAllocate) {
        this.troopsToAllocate += troopsToAllocate;
    }

    /**
     * fetches number of troops this player can allocate in their next turn
     * @return amount troops to allocate
     */
    public int getTroopsToAllocate() {
        return troopsToAllocate;
    }

    public String getPlayerName() {
        return playerName;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }
}
