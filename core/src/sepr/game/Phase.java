package sepr.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Phase implements InputProcessor{

    protected GameScreen gameScreen;
    protected Map map;
    protected Player currentPlayer;

    public Phase(GameScreen gameScreen, Map map) {
        this.gameScreen = gameScreen;
        this.map = map;
    }

    public void enterPhase(Player player) {
        this.currentPlayer = player;
    }

    /**
     * method for tidying up phase for next player to use
     */
    public void endPhase () {
        this.currentPlayer = null;
    }

    public abstract void visualisePhase(SpriteBatch batch);
}
