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
    protected PlayerType playerType;

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

    public void setTroopsToAllocate(int troopsToAllocate) {
        this.troopsToAllocate = troopsToAllocate;
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
     * Let player move troops between adjacent friendly territories
     */
    protected abstract void processMovePhase();

    /**
     * Let the player choose where to allocate their additional reinforcements
     * @param amount thr amount of toops available to allocate
     * @return how many troops remaining they may allocate in a future turn
     */
    protected abstract int processAllocationPhase(int amount);

    /**
     * resolves a conflict between two territories
     *
     * @param attackingSectorId       id of sector carrying out the attack
     * @param defendingSectorId       id of sector being attacked
     * @param amountOfTroopsAttacking number of troops that are being used to attack with
     * @throws IllegalArgumentException if a player is attacking a sector it owns
     * @throws IllegalArgumentException if sectors are not connected
     */
    private static void resolveCombat(int attackingSectorId, int defendingSectorId, int amountOfTroopsAttacking) {

    }

    public String getPlayerName() {
        return playerName;
    }
}
