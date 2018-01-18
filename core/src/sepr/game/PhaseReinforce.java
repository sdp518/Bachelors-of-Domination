package sepr.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * class for managing the player input during the reinforcement phase
 */
public class PhaseReinforce extends Phase {
    private int[] allocateUnits; // 2 index array storing : [0] number of troops to allocate ; [1] id of sector to allocate to

    public PhaseReinforce(GameScreen gameScreen, Map map) {
        super(gameScreen, map, TurnPhaseType.REINFORCEMENT);
    }

    @Override
    void enterPhase(Player player, String additionalInformation) {
        super.enterPhase(player, "");
        currentPlayer.addTroopsToAllocate(5 + map.calculateCollegeReinforcements(currentPlayer.getId())); // players get a basic reinforcement of 5 troops every turn and additional if they own all sectors in a college
        setAdditionalInformation("Troop Allocation: " + currentPlayer.getTroopsToAllocate());
        DialogFactory.nextTurnDialogBox(currentPlayer.getPlayerName(), currentPlayer.getTroopsToAllocate(), this);
    }

    @Override
    public void endPhase() {
        currentPlayer.setTroopsToAllocate(0); // any unallocated units are removed
        super.endPhase();
    }

    /**
     * checks if the user has completed the unit allocation dialog
     */
    private void detectUnitAllocation() {
        if (allocateUnits != null) { // check that an allocation has been initiated
            if (allocateUnits[1] == -1 || allocateUnits[0] == 0) { // cancel allocation if sector id set to -1 or 0 units are allocated
                allocateUnits = null;
            } else if (allocateUnits[0] != -1) { // dialog complete : perform the allocation
                map.addUnitsToSectorAnimated(allocateUnits[1], allocateUnits[0]);
                currentPlayer.addTroopsToAllocate(-allocateUnits[0]);
                allocateUnits = null;
                setAdditionalInformation("Troop Allocation: " + currentPlayer.getTroopsToAllocate());
            }
        }
    }

    @Override
    public void phaseAct() {
        detectUnitAllocation();
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

        Vector2 worldCoord = gameScreen.screenToWorldCoord(screenX, screenY);

        int sectorId = map.detectSectorContainsPoint((int)worldCoord.x, (int)worldCoord.y);
        if (sectorId != -1) { // If selected a sector
            if (currentPlayer.getTroopsToAllocate() <= 0) { // check the player still has units to allocate
                DialogFactory.basicDialogBox("Allocation Problem", "You have no more troops to allocate", this);
            } else if (map.getSector(sectorId).getOwnerId() != currentPlayer.getId()) { // check the player has chosen to add units to their own sector
                DialogFactory.basicDialogBox("Allocation Problem", "Cannot allocate units to a sector you do not own", this);
            } else {
                // setup allocation form
                allocateUnits = new int[2];
                allocateUnits[0] = -1;
                allocateUnits[1] = sectorId;
                DialogFactory.allocateUnitsDialog(currentPlayer.getTroopsToAllocate(), allocateUnits, map.getSector(sectorId).getDisplayName(), this);
            }
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
