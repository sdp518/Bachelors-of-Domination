package sepr.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

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
        //this.playerName = playerName;
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

    /**
     * abstract method for selecting the amount of troops to attack with for this player
     * return 0 if the attack is cancelled
     * @param stage stage to draw dialogs to for getting how many units a human player is attacking with
     * @param maxAttackers number of troops on the sector used to attack with
     * @param defenders number of troops on the defending sector
     * @return number of troops to attack with or 0 if the attack is cancelled
     */
    protected abstract void processAttackPhase(Stage stage, int maxAttackers, int defenders, int[] numOfAttackers);

    /**
     * Let the player choose where to allocate their additional reinforcements
     * @param amount thr amount of toops available to allocate
     * @return how many troops remaining they may allocate in a future turn
     */
    protected abstract void processAllocationPhase(int amount);

    public String getPlayerName() {
        return playerName;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }
}
