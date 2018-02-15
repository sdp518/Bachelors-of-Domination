package SaveLoad;

import com.badlogic.gdx.graphics.Color;
import sepr.game.GameSetupScreen;
import sepr.game.Player;
import sepr.game.PlayerType;

/**
 * This is class is the same as the ordinary Player class in sepr.game.
 * The only difference is to make the class serializable.
 * Changes the class Color to the float equivalent.
 * This change is not visible from outside the class as the output of this variable is still of type Color.
 */
public class SavePlayer implements java.io.Serializable {
    private int id; // player's unique id
    private GameSetupScreen.CollegeName collegeName; // college this player chose
    private String playerName;
    private int troopsToAllocate; // how many troops the player has to allocate at the start of their next reinforcement phase
    private float[] sectorColour; // what colour to shade sectors owned by the player
    private PlayerType playerType; // Human or Neutral player
    private int bonus; // the bonus held by the player

    /**
     * Constructor which takes the plain player class and transforms it to serializable.
     * @param player The sepr.game player class storing the information of the colleges playing
     */
    public SavePlayer(Player player) {
        this.id = player.getId();
        this.collegeName = player.getCollegeName();
        this.playerName = player.getPlayerName();
        this.troopsToAllocate = player.getTroopsToAllocate();
        this.sectorColour = new float[] {player.getSectorColour().r,
                                        player.getSectorColour().g,
                                        player.getSectorColour().b,
                                        player.getSectorColour().a};
        this.playerType = player.getPlayerType();
        this.bonus = player.getBonus();
    }

    /**
     *
     * @return the integer ID of the player.
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return the enum of the college of this player.
     */
    public GameSetupScreen.CollegeName getCollegeName() {
        return this.collegeName;
    }

    /**
     *
     * @return the String name of the college.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     *
     * @return the integer of the troops the player has left to allocate.
     */
    public int getTroopsToAllocate() {
        return troopsToAllocate;
    }

    /**
     *
     * @return the Color of the college which will colour the sectors.
     */
    public Color getSectorColor() {
        return new Color(sectorColour[0], sectorColour[1],
                        sectorColour[2], sectorColour[3]);
    }

    /**
     * Determines whether or not the player is human or AI.
     * @return the PlayerType of the player.
     */
    public PlayerType getPlayerType() {
        return playerType;
    }

    /**
     * @return the bonus allocated to the player
     */
    public int getBonus() {
        return this.bonus;
    }
}
