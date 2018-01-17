package sepr.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;

/**
 * Created by Dom's Surface Mark 2 on 20/12/2017.
 */
public class PlayerAI extends Player {
    Random random;
    /**
     * @param id player's unique identifier
     * @param collegeName display name for this player
     * @param sectorColour colour that the sectors owned by this player are coloured
     * @param playerName player's name to be displayed
     */
    public PlayerAI(int id, GameSetupScreen.CollegeName collegeName, Color sectorColour, String playerName) {
        super(id, collegeName, sectorColour, PlayerType.AI, playerName);
        random = new Random();
    }

    @Override
    protected void processAttackPhase(Stage stage, int maxAttackers, int defenders, int[] numOfAttackers) {
        numOfAttackers[0] = random.nextInt(maxAttackers); // randomly choose to attack with up to the max number of troops
    }

    @Override
    protected void processMovePhase() {

    }

    @Override
    protected int processAllocationPhase(int amount) {
        return 0;
    }
}
