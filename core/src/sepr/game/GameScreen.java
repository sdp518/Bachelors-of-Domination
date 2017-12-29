package sepr.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Dom's Surface Mark 2 on 16/11/2017.
 */
public class GameScreen implements Screen, InputProcessor{
    private Main main;

    private HashMap<TurnPhase, HUD> stages;

    private Map map;
    private SpriteBatch gameplayBatch;
    private OrthographicCamera gameplayCamera;
    private Viewport gameplayViewport;
    private Texture mapBackground;

    private HashMap<Integer, Player> players; // player id mapping to the relevant player
    private HashMap<Integer, Boolean> keysDown; // mapping from key, (Input.Keys), to whether it has been pressed down

    private boolean turnTimerEnabled;
    private boolean turnTimerPaused;
    private int maxTurnTime;
    private int turnTimeElapsed;
    private List<Integer> turnOrder; // array of player ids in order of players' turns;
    private int currentPlayer; // index of current player in turnOrder list
    private TurnPhase currentPhase = TurnPhase.REINFORCEMENT; // first phase of game is reinforcement

    private TextureRegion arrow; // TextureRegion for rendering attack visualisation
    private Sector attackingSector; // Stores the sector being used to attack in the attack phase (could store as ID and lookup object each time to save memory)
    private Sector defendingSector; // Stores the sector being attacked in the attack phase (could store as ID and lookup object each time to save memory)

    private float mousePositionX; // Stores the relative x coordinate of the cursor when it moves. 0,0 is bottom left
    private float mousePositionY; // Stores the relative y coordinate of the cursor when it moves. 0,0 is bottom left

    private Vector2 arrowPositionsBase; // Vector x,y for the base of the arrow
    private Vector2 arrowPositionsPoint; // Vector x,y for the point of the arrow

    /**
     * Performs the game's initial setup
     * @param main used to change screen
     * @param players HashMap of the players in this game
     * @param turnTimerEnabled should players turns be limited
     * @param maxTurnTime time elapsed in current turn, irrelevant if turn timer not enabled
     */

    public GameScreen(Main main, HashMap<Integer, Player> players, boolean turnTimerEnabled, int maxTurnTime) {
        this.main = main;

        this.map = new Map();
        this.gameplayBatch = new SpriteBatch();
        this.gameplayCamera = new OrthographicCamera();
        this.gameplayViewport = new ScreenViewport(gameplayCamera);
        this.mapBackground = new Texture("ui/mapBackground.png");

        this.stages = new HashMap<TurnPhase, HUD>();
        this.stages.put(TurnPhase.REINFORCEMENT, new HUDReinforcement(this));
        this.stages.put(TurnPhase.ATTACK, new HUDAttack(this));
        this.stages.put(TurnPhase.MOVEMENT, new HUDMovement(this));


        this.players = players;

        this.keysDown = new HashMap<Integer, Boolean>();
        this.keysDown.put(Input.Keys.UP, false);
        this.keysDown.put(Input.Keys.LEFT, false);
        this.keysDown.put(Input.Keys.DOWN, false);
        this.keysDown.put(Input.Keys.RIGHT, false);

        this.turnTimerEnabled = turnTimerEnabled;
        this.turnTimerPaused = false;
        this.maxTurnTime = maxTurnTime;
        this.turnTimeElapsed = 0;
        this.turnOrder = new ArrayList<Integer>(players.keySet());
        this.currentPlayer = 0;

        this.arrow = new TextureRegion(new Texture(Gdx.files.internal("arrow.png")));
        this.attackingSector = null;
        this.defendingSector = null;

        this.arrowPositionsPoint = new Vector2();
        this.arrowPositionsBase = new Vector2();
        gameplayCamera.translate(new Vector3(0, 0, 0));

        setupUi();
            
        map.allocateSectors(players);
    }

    /**
     * Performs the games UI setup
     */
    private void setupUi() {
        Texture buttons = new Texture("ui/buttons.png"); // texture sheet for buttons
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); // create style for buttons to use
        style.up = new TextureRegionDrawable(new TextureRegion(buttons, 0, 0, 400, 150)); // image for button to use in default state
        style.down = new TextureRegionDrawable(new TextureRegion(buttons, 0, 150, 400, 150)); // image for button to use when pressed down
        style.font = new BitmapFont(); // set button font to the default Bitmap Font
    }

    /**
     * This method is used for progression through the phases of a turn evaluating the currentPhase case label
     */
    protected void advancePhase() {
        switch (currentPhase) {
            case REINFORCEMENT:
                currentPhase = TurnPhase.ATTACK;
                break;
            case ATTACK:
                if (map.checkForWinner() != -1) {
                    // gameover a player has won
                    gameOver(map.checkForWinner());
                }
                currentPhase = TurnPhase.MOVEMENT;
                break;
            case MOVEMENT:
                nextPlayer();
                break;
        }
        this.updateInputProcessor();
        System.out.println(currentPhase);
        System.out.println(currentPlayer);
    }

    /**
     * Called when the player ends the MOVEMENT phase of their turn to advance the game to the next Player's turn
     */
    private void nextPlayer() {
        currentPhase = TurnPhase.REINFORCEMENT;
        currentPlayer++;
        if (currentPlayer == turnOrder.size()) {
            currentPlayer = 0;
        }
    }

    /**
     * Method called when map class returns a winner when checkForWinner called
     * @param winnerId id of the winning player
     */
    private void gameOver(int winnerId) {

    }

    /**
     * Writes the game state to a file
     */
    private void saveGameState() {
        // not part of this assessment
    }

    /**
     * Reads the given string and setsup the game state from this
     * @param gameState
     */
    private void loadGameState(String gameState) {
        // not part of this assessment
    }

    /**
     * Input keys for controlling the game camera
     */
    private void updateInputProcessor() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stages.get(currentPhase));
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }
  
    private void controlCamera() {
        if (this.keysDown.get(Input.Keys.UP)) {
            this.gameplayCamera.translate(0, 4, 0);
        }
        if (this.keysDown.get(Input.Keys.DOWN)) {
            this.gameplayCamera.translate(0, -4, 0);
        }
        if (this.keysDown.get(Input.Keys.LEFT)) {
            this.gameplayCamera.translate(-4, 0, 0);
        }
        if (this.keysDown.get(Input.Keys.RIGHT)) {
            this.gameplayCamera.translate(4, 0, 0);
        }

    }

    private void renderBackground() {
        Vector3 mapDrawPos = gameplayCamera.unproject(new Vector3(0, Gdx.graphics.getHeight(), 0));
        gameplayBatch.draw(mapBackground, mapDrawPos.x, mapDrawPos.y, gameplayCamera.viewportWidth * gameplayCamera.zoom, gameplayCamera.viewportHeight * gameplayCamera.zoom );
    }

    /**
     *
     * @param players
     * @param turnTimerEnabled
     * @param maxTurnTime
     */
    public void setupGame(HashMap<Integer, Player> players, boolean turnTimerEnabled, int maxTurnTime) {
        this.players = players;
        this.turnTimerEnabled = turnTimerEnabled;
        this.maxTurnTime = maxTurnTime;

        this.map.allocateSectors(this.players);
    }

    @Override
    public void show() {
        this.updateInputProcessor();
    }

    @Override
    public void render(float delta) {
        /* Gameplay */
        // update gameplay
        this.controlCamera();

        //render gameplay
        gameplayCamera.update();
        gameplayBatch.setProjectionMatrix(gameplayCamera.combined);
        gameplayBatch.begin();

        renderBackground();
        map.draw(gameplayBatch);
        attackVisualisation(gameplayBatch);
        gameplayBatch.end();

        /* UI */
        // update UI
        this.stages.get(currentPhase).act(delta);

        // render UI
        this.stages.get(currentPhase).draw();
    }

    /**
     * handles mouse clicks during the attack phase
     * @param worldX
     * @param worldY
     */
    private void attackPhaseTouchUp(float worldX, float worldY) {
        int sectorid = map.detectSectorContainsPoint((int)worldX, (int)worldY);
        if (sectorid != -1) { // If selected a sector
            Sector selected = map.getSector(sectorid); // Current sector
            boolean notAlreadySelected = this.attackingSector == null && this.defendingSector == null; // T/F if the attack sequence is complete
            if (this.attackingSector != null && this.defendingSector == null) { // If its the second selection in the attack phase
                if (this.attackingSector.isAdjacentTo(selected) && selected.getOwnerId() != this.currentPlayer) { // If not own sector and its adjacent
                    this.arrowPositionsPoint.set(this.mousePositionX, this.mousePositionY); // Finalise the end position of the arrow
                    this.defendingSector = selected;
                    // Call to initiate attack + advance phase
                    //this.attackingSector = null; // Add back once ^ is complete
                    //this.defendingSector = null;
                } else { // Cancel attack as not attackable
                    this.attackingSector = null;
                }
            } else if (selected.getOwnerId() == this.currentPlayer && selected.getUnitsInSector() > 1 && notAlreadySelected) { // First selection, is owned by the player and has enough troops
                this.attackingSector = selected;
                this.arrowPositionsBase.set(this.mousePositionX, this.mousePositionY); // Finalise start position of arrow
            } else {
                this.attackingSector = null;
                this.defendingSector = null;
            }
        } else {
            this.attackingSector = null;
            this.defendingSector = null;
        }
    }

    /**
     * Function used to create the attack visualisations
     * @param gameplayBatch The main sprite batch
     */
    private void attackVisualisation(SpriteBatch gameplayBatch) {
        if (this.attackingSector != null) { // If attacking
            if (this.defendingSector == null) { // In mid attack
                generateArrow(gameplayBatch, this.arrowPositionsBase.x, this.arrowPositionsBase.y, this.mousePositionX, this.mousePositionY);
            } else if (this.defendingSector != null) { // Attack confirmed
                generateArrow(gameplayBatch, this.arrowPositionsBase.x, this.arrowPositionsBase.y, this.arrowPositionsPoint.x, this.arrowPositionsPoint.y);
            }
        }
    }

    /**
     * Creates an arrow between coordinates
     * @param gameplayBatch The main sprite batch
     * @param startX Base of the arrow x
     * @param startY Base of the arrow y
     * @param endX Tip of the arrow x
     * @param endY Tip of the arrow y
     */
    private void generateArrow(SpriteBatch gameplayBatch, float startX, float startY, float endX, float endY) {
        int thickness = 30;
        // Calculates the transformations to apply to the sprite - had to refresh my GCSE maths knowledge lol
        double angle = Math.toDegrees(Math.atan((endY - startY) / (endX - startX)));
        double height = (endY - startY) /  Math.sin(Math.toRadians(angle));
        gameplayBatch.draw(arrow, startX, (startY - thickness/2), 0, thickness/2, (float)height, thickness,1, 1, (float)angle);
    }

   /**
     * handles mouse clicks during the reinforcement phase
     * @param worldX
     * @param worldY
     */
    private void reinforcePhaseTouchUp(float worldX, float worldY) {

    }

    /**
     * handles mouse clicks during the movement phase
     * @param worldX
     * @param worldY
     */
    private void movementPhaseTouchUp(float worldX, float worldY) {
        // not part of this assessment
    }

    @Override
    public void resize(int width, int height) {
        for (Stage stage : stages.values()) {
            stage.getViewport().update(width, height);
            stage.getCamera().viewportWidth = width;
            stage.getCamera().viewportHeight = height;
            stage.getCamera().position.x = width/2;
            stage.getCamera().position.y = height/2;
            stage.getCamera().update();
        }

        this.gameplayViewport.update(width, height);
        this.gameplayCamera.viewportWidth = width;
        this.gameplayCamera.viewportHeight = height;
        this.gameplayCamera.translate(1920/2, 1080/2, 0);
        this.gameplayCamera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        for (Stage stage : stages.values()) {
            stage.dispose();
        }
    }

    /* Input Processor */

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.UP) {
            keysDown.put(Input.Keys.UP, true);
        }
        if (keycode == Input.Keys.DOWN) {
            keysDown.put(Input.Keys.DOWN, true);
        }
        if (keycode == Input.Keys.LEFT) {
            keysDown.put(Input.Keys.LEFT, true);
        }
        if (keycode == Input.Keys.RIGHT) {
            keysDown.put(Input.Keys.RIGHT, true);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.UP) {
            keysDown.put(Input.Keys.UP, false);
        }
        if (keycode == Input.Keys.DOWN) {
            keysDown.put(Input.Keys.DOWN, false);
        }
        if (keycode == Input.Keys.LEFT) {
            keysDown.put(Input.Keys.LEFT, false);
        }
        if (keycode == Input.Keys.RIGHT) {
            keysDown.put(Input.Keys.RIGHT, false);
        }
        return true;
    }

    /**
     *
     * @param screenX
     * @param screenY
     * @return
     */
    private Vector2 screenToWorldCoord(int screenX, int screenY) {
        float worldX = gameplayCamera.unproject(new Vector3(screenX, screenY, 0)).x;
        float worldY = (gameplayCamera.unproject(new Vector3(screenX, screenY, 0)).y - Gdx.graphics.getHeight()) * -1;
        return new Vector2(worldX, worldY);
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 worldCoords = screenToWorldCoord(screenX, screenY);

        switch (currentPhase) {
            case REINFORCEMENT:
                reinforcePhaseTouchUp(worldCoords.x, worldCoords.y);
                break;
            case ATTACK:
                attackPhaseTouchUp(worldCoords.x, worldCoords.y);
                break;
            case MOVEMENT:
                movementPhaseTouchUp(worldCoords.x, worldCoords.y);
                break;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        this.mousePositionX = (gameplayCamera.unproject(new Vector3(screenX, screenY, 0)).x);
        this.mousePositionY = (gameplayCamera.unproject(new Vector3(screenX, screenY, 0)).y);

        Vector2 worldCoords = screenToWorldCoord(screenX, screenY);

        Sector hoveredSector = map.getSector(map.detectSectorContainsPoint((int)worldCoords.x, (int)worldCoords.y));
        stages.get(currentPhase).setBottomBarText(hoveredSector);
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        if ((gameplayCamera.zoom > 0.5 && amount < 0) || (gameplayCamera.zoom < 1.5 && amount > 0)) {
            gameplayCamera.zoom += amount * 0.03f;
        }
        return false;
    }
}