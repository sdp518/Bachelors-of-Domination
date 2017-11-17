package sepr.game;

import com.badlogic.gdx.Screen;

import java.util.HashMap;

/**
 * Created by Dom's Surface Mark 2 on 16/11/2017.
 */
public class GameScreen implements Screen{
    private Main main;
    private Map map;
    private HashMap<Integer, Player> players; // player id mapping to the relevant player
    private boolean turnTimerEnabled;
    private boolean turnTimerPaused;
    private int maxTurnTime;
    private int turnTimeElapsed;
    private Integer[] turnOrder; // list of player ids in order of players turn;
    private int currentPlayer; // index of current player

    /**
     * Performs the game's initial setup
     * @param main used to change screen
     * @param players hashmap of the players in this game
     * @param turnTimerEnabled should players turn be limitted
     * @param maxTurnTime time elapsed in current turn, irrelevant if turn timer not enabled
     */
    public GameScreen(Main main, HashMap<Integer, Player> players, boolean turnTimerEnabled, int maxTurnTime) {
        this.main = main;
        this.players = players;
        this.map = new Map();
        this.turnTimerEnabled = turnTimerEnabled;
        this.turnTimerPaused = false;
        this.maxTurnTime = maxTurnTime;
        this.turnTimeElapsed = 0;
        this.turnOrder = this.players.keySet().toArray(new Integer[0]);
        this.currentPlayer = 0;

        allocateSectors();
        playGame();
    }


    private void playGame() {

    }

    /**
     * Allocate sectors to each player in a balanced mannor
     */
    private void allocateSectors() {

    }

    /**
     *
     * @param playerId player's whos turn it is to be carried out
     */
    private void executePlayerTurn(int playerId) {

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

    }

    /**
     * Reads the given string and setsup the game state from this
     * @param gameState
     */
    private void loadGameState(String gameState) {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //all game content to be drawn here
    }

    @Override
    public void resize(int width, int height) {

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
}
