package sepr.game;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Dom's Surface Mark 2 on 20/12/2017.
 */
public class PlayerNeutralAI extends PlayerAI{

    /**
     * @param id           player's unique identifier
     * @param collegeName  display name for this player
     */
    public PlayerNeutralAI(int id, GameSetupScreen.CollegeName collegeName) {
        super(id, collegeName, Color.WHITE);
    }
}
