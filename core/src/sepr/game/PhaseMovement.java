package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/** NEW CLASS (class existed following changeover though was not implemented)
 * handles input, updating and rendering for the movement phase
 */
public class PhaseMovement extends Phase {

    private TextureRegion arrow; // TextureRegion for rendering movement visualisation
    private Sector sourceSector; // Stores the sector that units are being moved from
    private Sector targetSector; // Stores the sector that units are being moved to
    private int[] movedUnits; // Stores the number of units being moved

    private Vector2 arrowTailPosition; // Vector x,y for the base of the arrow
    private Vector2 arrowHeadPosition; // Vector x,y for the point of the arrow

    public PhaseMovement(GameScreen gameScreen, Main main) {

        super(gameScreen, TurnPhaseType.MOVEMENT, main);
        this.arrow = new TextureRegion(new Texture(Gdx.files.internal("uiComponents/arrow.png")));
        this.sourceSector = null;
        this.targetSector = null;

        this.arrowHeadPosition = new Vector2();
        this.arrowTailPosition = new Vector2();
    }

    /**
     * NEW (Copied across from phaseAttack)
     * Creates an arrow between coordinates
     * @param gameplayBatch The main sprite batch
     * @param startX Base of the arrow x
     * @param startY Base of the arrow y
     * @param endX Tip of the arrow x
     * @param endY Tip of the arrow y
     */
    private void generateArrow(SpriteBatch gameplayBatch, float startX, float startY, float endX, float endY) {
        int thickness = 30;
        double angle = Math.toDegrees(Math.atan((endY - startY) / (endX - startX)));
        double height = (endY - startY) /  Math.sin(Math.toRadians(angle));
        gameplayBatch.draw(arrow, startX, (startY - thickness/2), 0, thickness/2, (float)height, thickness,1, 1, (float)angle);
    }

    /**
     * NEW
     * creates a dialog asking the player how many units they want to move
     *
     * @throws RuntimeException if the source sector or target sector are set to null
     */
    private void getMovedUnits() throws RuntimeException {
        if (sourceSector == null || targetSector == null) {
            throw new RuntimeException("Cannot move units unless both an source and target sector have been selected");
        }
        movedUnits = new int[3];
        movedUnits[0] = -1;
        DialogFactory.moveUnitsDialog(sourceSector.getUnitsInSector() - 1, movedUnits, targetSector,this);
    }

    /**
     * NEW
     * Applies the movement to the map
     */
    private void moveUnits() {
        if (gameScreen.getMap().executeMove(sourceSector.getId(), targetSector.getId(), movedUnits[0])) {
            updateTroopReinforcementLabel();
        }
    }

    /**
     * NEW
     * Detects when a move is being initiated
     */
    @Override
    public void phaseAct() {
        if (sourceSector != null && targetSector != null && movedUnits[0] != -1) {
            if (movedUnits[0] == 0) {
                main.sounds.playSound("menu_sound");
                // cancel move
            } else {
                main.sounds.playSound("move_sound");
                moveUnits();
            }
            // reset move
            sourceSector = null;
            targetSector = null;
            movedUnits = null;
        }
    }

    /**
     * NEW (Copied across from phaseAttack)
     * render graphics specific to the attack phase
     * @param batch the sprite batch to render to
     */
    @Override
    protected void visualisePhase(SpriteBatch batch) {
        if (this.sourceSector != null) { // If moving
            Vector2 screenCoords = gameScreen.screenToWorldCoords(Gdx.input.getX(), Gdx.input.getY());
            if (this.targetSector == null) { // In mid move
                generateArrow(batch, this.arrowTailPosition.x, this.arrowTailPosition.y, screenCoords.x, screenCoords.y);
            } else if (this.targetSector != null) { // Move confirmed
                generateArrow(batch, this.arrowTailPosition.x, this.arrowTailPosition.y, this.arrowHeadPosition.x, this.arrowHeadPosition.y);
            }
        }
    }

    /**
     * NEW (Copied across from phaseAttack)
     * @param screenX the x position of the mouse
     * @param screenY the y position of the mouse
     * @param pointer pointer to the event
     * @param button the button clicked on the mouse
     * @return if the event has been handled
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (super.touchDown(screenX, screenY, pointer, button)) {
            return true;
        }
        return false;
    }

    /**
     * NEW (Copied across from phase attack)
     * @param screenX mouse x position on screen when clicked
     * @param screenY mouse y position on screen when clicked
     * @param pointer pointer to the event
     * @param button which button was pressed
     * @return if the event has been handled
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (super.touchUp(screenX, screenY, pointer, button)) {
            return true;
        }

        Vector2 worldCoord = gameScreen.screenToWorldCoords(screenX, screenY);

        int sectorId = gameScreen.getMap().detectSectorContainsPoint((int)worldCoord.x, (int)worldCoord.y);
        if (sectorId != -1) { // If selected a sector
            main.sounds.playSound("menu_sound");
            Sector selected = gameScreen.getMap().getSectorById(sectorId); // Current sector
            boolean notAlreadySelected = this.sourceSector == null && this.targetSector == null; // T/F if the move sequence is complete

            if (this.sourceSector != null && this.targetSector == null) { // If its the second selection in the move phase

                if (this.sourceSector.isAdjacentTo(selected) && selected.getOwnerId() == this.currentPlayer.getId()) { // check the player owns the target sector and that it is adjacent
                    this.arrowHeadPosition.set(worldCoord.x, worldCoord.y); // Finalise the end position of the arrow
                    this.targetSector = selected;

                    getMovedUnits(); // source and target sector selected so find out how many units the player wants to attack with
                } else { // cancel move as selected target sector cannot be moved to: may not be adjacent or may be owned owner of source
                    this.sourceSector = null;
                }

            } else if (selected.getOwnerId() == this.currentPlayer.getId() && selected.getUnitsInSector() > 1 && notAlreadySelected) { // First selection, is owned by the player and has enough troops
                this.sourceSector = selected;
                this.arrowTailPosition.set(worldCoord.x, worldCoord.y); // set arrow tail position
            } else {
                this.sourceSector = null;
                this.targetSector = null;
            }
        } else { // mouse pressed and not hovered over a sector to move to therefore cancel any move in progress
            this.sourceSector = null;
            this.targetSector = null;
        }

        return true;
    }
}
