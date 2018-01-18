package sepr.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PhaseReinforce extends Phase {

    public PhaseReinforce(GameScreen gameScreen, Map map) {
        super(gameScreen, map, TurnPhaseType.REINFORCEMENT);
    }

    @Override
    public void endPhase() {
        currentPlayer.setTroopsToAllocate(0); // any unallocated units are ignored
        super.endPhase();
    }

    @Override
    public void phaseAct() {

    }

    @Override
    public void visualisePhase(SpriteBatch batch) {

    }

    @Override
    public boolean keyDown(int keycode) {
        if (super.keyDown(keycode)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (super.keyUp(keycode)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if (super.keyTyped(character)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (super.touchDown(screenX, screenY, pointer, button)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (super.touchUp(screenX, screenY, pointer, button)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (super.touchDragged(screenX, screenY, pointer)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if (super.scrolled(amount)) {
            return true;
        }
        return false;
    }
}
