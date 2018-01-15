package sepr.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dom's Surface Mark 2 on 16/11/2017.
 */
public class GameScreen implements Screen, InputProcessor{
    private Main main;

    private TurnPhaseType currentPhase = TurnPhaseType.REINFORCEMENT; // first phase of game is reinforcement

    private HashMap<TurnPhaseType, Phase> phases;

    private Map map;
    private SpriteBatch gameplayBatch;
    protected OrthographicCamera gameplayCamera;
    private Viewport gameplayViewport;
    private Texture mapBackground;

    private HashMap<Integer, Player> players; // player id mapping to the relevant player
    private HashMap<Integer, Boolean> keysDown; // mapping from key, (Input.Keys), to whether it has been pressed down

    private boolean turnTimerEnabled;
    private boolean turnTimerPaused;
    private int maxTurnTime;
    private long turnTimeStart;
    private List<Integer> turnOrder; // array of player ids in order of players' turns;
    private int currentPlayer; // index of current player in turnOrder list

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
        this.mapBackground = new Texture("ui/HD-assets/Background.png");

        this.phases = new HashMap<TurnPhaseType, Phase>();
        this.phases.put(TurnPhaseType.REINFORCEMENT, new PhaseReinforce(this, map));
        this.phases.put(TurnPhaseType.ATTACK, new PhaseAttack(this, map));
        this.phases.put(TurnPhaseType.MOVEMENT, new PhaseMovement(this, map));

        this.players = players;

        this.keysDown = new HashMap<Integer, Boolean>();
        this.keysDown.put(Input.Keys.UP, false);
        this.keysDown.put(Input.Keys.LEFT, false);
        this.keysDown.put(Input.Keys.DOWN, false);
        this.keysDown.put(Input.Keys.RIGHT, false);

        this.turnTimerEnabled = turnTimerEnabled;
        this.turnTimerPaused = false;
        this.maxTurnTime = maxTurnTime;
        this.currentPlayer = 0;

        map.allocateSectors(players);
    }

    /**
     * sets up a new game
     * @param players
     * @param turnTimerEnabled
     * @param maxTurnTime
     */
    public void setupGame(HashMap<Integer, Player> players, boolean turnTimerEnabled, int maxTurnTime) {
        this.players = players;
        this.turnOrder = new ArrayList<Integer>(players.keySet());
        this.turnTimerEnabled = turnTimerEnabled;
        this.maxTurnTime = maxTurnTime * 1000; // Seconds to milliseconds
        this.turnTimeStart = System.currentTimeMillis();

        this.map.allocateSectors(this.players);

        resetCameraPosition();
    }

    /**
     * Input keys for controlling the game camera
     */
    private void updateInputProcessor() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(phases.get(currentPhase));
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    /**
     * This method is used for progression through the phases of a turn evaluating the currentPhase case label
     */
    protected void advancePhase() {
        this.phases.get(currentPhase).endPhase();
        switch (currentPhase) {
            case REINFORCEMENT:
                currentPhase = TurnPhaseType.ATTACK;
                break;
            case ATTACK:
                if (map.checkForWinner() != -1) {
                    // gameover a player has won
                    gameOver(map.checkForWinner());
                }
                currentPhase = TurnPhaseType.MOVEMENT;
                break;
            case MOVEMENT:
                nextPlayer();
                break;
        }
        this.updateInputProcessor();
        this.phases.get(currentPhase).enterPhase(players.get(currentPlayer));
    }

    /**
     *
     * @return time remaining in turn in seconds
     */
    public int getTurnTimeRemaining(){
        return maxTurnTime - (int)((System.currentTimeMillis() - turnTimeStart)/1000);
    }

    /**
     * Called when the player ends the MOVEMENT phase of their turn to advance the game to the next Player's turn
     */
    private void nextPlayer() {
        currentPhase = TurnPhaseType.REINFORCEMENT;
        currentPlayer++;
        if (currentPlayer == turnOrder.size()) {
            currentPlayer = 0;
        }
      
        resetCameraPosition();

        // add the next player dialog box here

        if (this.turnTimerEnabled) {
            this.turnTimeStart = System.currentTimeMillis();
        }      
    }

    protected SpriteBatch getGameplayBatch() {
        return this.gameplayBatch;
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
     * changes the screen currently being displayed to the menu
     */
    public void openMenu() {
        main.setMenuScreen();
    }

    private void resetCameraPosition() {
        this.gameplayCamera.position.x = 1920/2;
        this.gameplayCamera.position.y = 1080/2;
        this.gameplayCamera.zoom = 1;
    }

    @Override
    public void show() {
        this.updateInputProcessor();
    }

    @Override
    public void render(float delta) {
        this.controlCamera();

        gameplayCamera.update();
        gameplayBatch.setProjectionMatrix(gameplayCamera.combined);
        gameplayBatch.begin();

        renderBackground();

        map.draw(gameplayBatch);
        gameplayBatch.end();

        if (this.turnTimerEnabled) {
            this.phases.get(currentPhase).setTimerValue(getTurnTimeRemaining());
        }
        this.phases.get(currentPhase).act(delta);
        this.phases.get(currentPhase).draw();
        if (this.turnTimerEnabled && (System.currentTimeMillis() - this.turnTimeStart >= this.maxTurnTime)) {
            nextPlayer();
        }
    }

    @Override
    public void resize(int width, int height) {
        for (Stage stage : phases.values()) {
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

    }

    /**
     *
     * @param screenX
     * @param screenY
     * @return
     */
    public Vector2 screenToWorldCoord(int screenX, int screenY) {
        float worldX = gameplayCamera.unproject(new Vector3(screenX, screenY, 0)).x;
        float worldY = (gameplayCamera.unproject(new Vector3(screenX, screenY, 0)).y - Gdx.graphics.getHeight()) * -1;
        return new Vector2(worldX, worldY);
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
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector2 worldCoords = screenToWorldCoord(screenX, screenY);

        Sector hoveredSector = map.getSector(map.detectSectorContainsPoint((int)worldCoords.x, (int)worldCoords.y));
        phases.get(currentPhase).setBottomBarText(hoveredSector);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if ((gameplayCamera.zoom > 0.5 && amount < 0) || (gameplayCamera.zoom < 1.5 && amount > 0)) {
            gameplayCamera.zoom += amount * 0.03f;
        }
        return true;
    }
}