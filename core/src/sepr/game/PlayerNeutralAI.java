package sepr.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Dom's Surface Mark 2 on 20/12/2017.
 */
public class PlayerNeutralAI extends Player{

    /**
     * @param id player's unique identifier
     * @param collegeName display name for this player
     */
    public PlayerNeutralAI(int id, GameSetupScreen.CollegeName collegeName) {
        super(id, collegeName, Color.WHITE, PlayerType.NEUTRAL_AI, "THE NEUTRAL PLAYER");
    }

    @Override
    protected void processAttackPhase(Stage stage, int maxAttackers, int defenders, int[] numOfAttackers) {
        // doesn't attack
    }

    @Override
    protected void processAllocationPhase(int amount) {

    }
}
