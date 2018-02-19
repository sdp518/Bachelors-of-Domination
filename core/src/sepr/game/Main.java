package sepr.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;

import java.util.HashMap;

//  TODO Add classes to packages

/**
 * executable http://sidmeiers.me/documents/code/BachelorsOfDomination2.0.zip
 *
 * main game class used for controlling what screen is currently being displayed
 */
public class Main extends Game implements ApplicationListener {
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private OptionsScreen optionsScreen;
	private OptionsScreen inGameOptionsScreen;
	private GameSetupScreen gameSetupScreen;
	private LoadScreen loadScreen;
	private LoadScreen saveScreen;
	public  Sounds sounds;


	/**
	 * Setup the screens and set the first screen as the menu
	 */
	@Override
	public void create () {
		new WidgetFactory(); // setup widget factory for generating UI components
		new DialogFactory(); // setup dialog factory for generating dialogs

		this.menuScreen = new MenuScreen(this);
		this.gameScreen = new GameScreen(this);
		this.optionsScreen = new OptionsScreen(this, EntryPoint.MENU_SCREEN);
		this.inGameOptionsScreen = new OptionsScreen(this, EntryPoint.GAME_SCREEN);
		this.gameSetupScreen = new GameSetupScreen(this);
		this.saveScreen = new LoadScreen(this, EntryPoint.GAME_SCREEN, this.gameScreen, this.gameSetupScreen);

        this.sounds = new Sounds();
		applyPreferences();

		this.setMenuScreen();
	}

    /**
	 * NEW
     * changes the screen currently being displayed to the menu
     */
    public void setMinigameScreen(MinigameScreen minigameScreen) {
        this.setScreen(minigameScreen);
    }

    /**
	 * NEW
     * changes the screen currently being displayed to the menu
     */
    public void setBonusExchangeScreen(BonusExchangeScreen bonusExchangeScreen) {
        this.setScreen(bonusExchangeScreen);
    }

	/**
	 * changes the screen currently being displayed to the menu
	 */
	public void setMenuScreen() {
		this.setScreen(menuScreen);
	}

	/**
	 * NEW
	 * changes the screen currently being displayed to the menu and re-instantiates game screen
	 */
	public void exitToMenu() {
		this.setScreen(menuScreen);
		this.gameScreen.dispose();
		this.gameScreen = new GameScreen(this);
	}

	/**
	 * displays the game screen and starts a game with the passed properties
	 *
	 * @param players hashmap of players who should be present in the game
	 * @param turnTimerEnabled whether or not this game should have a turn timer on
	 * @param maxTurnTime the maximum time of a turn, in seconds, if the turn tumer is enabled
	 * @param allocateNeutralPlayer should the neutral player be given sectors to start with
	 */
	public void setGameScreen(HashMap<Integer, Player> players, boolean turnTimerEnabled, int maxTurnTime, boolean allocateNeutralPlayer) {
		gameScreen.setupGame(players, turnTimerEnabled, maxTurnTime, allocateNeutralPlayer);
		this.saveScreen = new LoadScreen(this, EntryPoint.GAME_SCREEN, this.gameScreen, this.gameSetupScreen);
		this.setScreen(gameScreen);
		gameScreen.startGame();
	}

	/**
	 * NEW
	 * returns to the game screen in the state it was left in resuming the game
	 */
	public void returnGameScreen() {
	    this.setScreen(gameScreen);
		gameScreen.resetCameraPosition();
		gameScreen.resume();
	}

	/**
	 * NEW
	 * returns to the game screen resuming the timer only
	 */
	public void returnGameScreenUnpaused() {
		this.setScreen(gameScreen);
		gameScreen.resetCameraPosition();
		gameScreen.resumeTimer();
	}

	/**
	 * change the screen currently being displayed to the options screen
	 */
	public void setOptionsScreen() {
		this.setScreen(optionsScreen);
	}

	/**
	 * change the screen currently being displayed to the in-game options screen
	 */
	public void setInGameOptionsScreen() {
		this.setScreen(inGameOptionsScreen);
	}

	/**
	 * change the screen currently being displayed to the game setup screen
	 */
	public void setGameSetupScreen() {
		this.setScreen(gameSetupScreen);
	}

	/**
	 * NEW
	 * change the screen currently being displayed to the load screen
	 */
	public void setLoadScreen() {
		this.loadScreen = new LoadScreen(this, EntryPoint.MENU_SCREEN, this.gameScreen, this.gameSetupScreen);
		this.setScreen(loadScreen);
	}

	/**
	 * NEW
	 * change the screen currently being displayed to the save screen
	 */
	public void setSaveScreen() {
		this.setScreen(saveScreen);
	}

	/**
	 * NEW
	 * updates the save screen
	 */
	public void updateSaveScreen(LoadScreen saveScreen) {
		this.saveScreen = saveScreen;
	}

	/**
	 *
	 * @return the saveScreen of the game.
	 */
	public LoadScreen getSaveScreen() {
		return this.saveScreen;
	}

	/**
	 * Applies the players options preferences
	 * Sets the
	 *      Music Volume
	 *      FX Volume
	 *      Screen Resolution
	 *      Fullscreen
	 * A default setting should be applied for any missing preferences
	 */
	public void applyPreferences() {
		Preferences prefs = Gdx.app.getPreferences(OptionsScreen.PREFERENCES_NAME);

		int screenWidth = prefs.getInteger(OptionsScreen.RESOLUTION_WIDTH_PREF, 1920);
		int screenHeight = prefs.getInteger(OptionsScreen.RESOLUTION_HEIGHT_PREF, 1080);
		Gdx.graphics.setWindowedMode(screenWidth, screenHeight);

		if (prefs.getBoolean(OptionsScreen.FULLSCREEN_PREF)) {
			// change game to fullscreen
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		}

		sounds.setMusicVolume(prefs.getFloat(OptionsScreen.MUSIC_VOL_PREF));
		sounds.setFxVolume(prefs.getFloat(OptionsScreen.FX_VOL_PREF));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		menuScreen.dispose();
		optionsScreen.dispose();
		gameSetupScreen.dispose();
		gameScreen.dispose();
		saveScreen.dispose();
		inGameOptionsScreen.dispose();
	}

}
