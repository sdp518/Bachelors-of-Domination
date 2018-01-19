package sepr.game;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Dom's Surface Mark 2 on 20/12/2017.
 */
public class PlayerHuman extends Player {

    /**
     * @param id player's unique identifier
     * @param collegeName display name for this player
     * @param sectorColour colour that the sectors owned by this player are coloured
     * @param playerName player's name to be displayed
     */
    public PlayerHuman(int id, GameSetupScreen.CollegeName collegeName, Color sectorColour, String playerName) {
        super(id, collegeName, sectorColour, PlayerType.HUMAN, playerName);
    }
}
