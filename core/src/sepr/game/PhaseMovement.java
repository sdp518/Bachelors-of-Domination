package sepr.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PhaseMovement extends Phase {

    public PhaseMovement(GameScreen gameScreen, Map map) {
        super(gameScreen, map, TurnPhaseType.MOVEMENT);
    }

    @Override
    public void phaseAct() {

    }

    @Override
    protected void visualisePhase(SpriteBatch batch) {

    }
}
