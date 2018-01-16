package sepr.game;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Dom's Surface Mark 2 on 20/12/2017.
 */
public class PlayerAI extends Player {

    /**
     * @param id           player's unique identifier
     * @param collegeName  display name for this player
     * @param sectorColour
     */
    public PlayerAI(int id, GameSetupScreen.CollegeName collegeName, Color sectorColour, String playerName) {
        super(id, collegeName, sectorColour, GameSetupScreen.PlayerType.AI, playerName);
    }

    @Override
    protected void processAttackPhase() {

    }

    @Override
    protected void processMovePhase() {

    }

    @Override
    protected int processAllocationPhase(int amount) {
        return 0;
    }
}
