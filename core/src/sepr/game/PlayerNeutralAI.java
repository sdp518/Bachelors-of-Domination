package sepr.game;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Dom's Surface Mark 2 on 20/12/2017.
 */
public class PlayerNeutralAI extends Player{

    /**
     * @param id player's unique identifier
     */
    public PlayerNeutralAI(int id) {
        super(id, GameSetupScreen.CollegeName.UNI_OF_YORK, Color.WHITE, PlayerType.NEUTRAL_AI, "THE NEUTRAL PLAYER");
    }
}
