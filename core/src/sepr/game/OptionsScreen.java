package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.HashSet;
import java.util.Set;

/**
 * Options managed by this class:
 *      Music Volume
 *      FX Volume
 *      Resolution Selector
 *      Fullscreen On/Off
 *      Colourblind mode On/Off
 */
public class OptionsScreen implements Screen {
    // names for accessing different preferences in the preferences file
    public static final String PREFERENCES_NAME = "Options";
    public static final String MUSIC_VOL_PREF = "musicVol";
    public static final String FX_VOL_PREF = "fxVol";
    public static final String RESOLUTION_WIDTH_PREF = "screenWidth";
    public static final String RESOLUTION_HEIGHT_PREF = "screenHeight";
    public static final String FULLSCREEN_PREF = "fullscreen";
    public static final String COLOURBLIND_PREF = "colourblind";

    private Main main;
    private Stage stage;
    private Table table;

    // screen UI widgets
    private Slider musicSlider;
    private Slider fxSlider;
    private SelectBox<String> resolutionSelector;
    private CheckBox fullscreenSwitch;
    private CheckBox colourblindModeSwitch;

    private EntryPoint entryPoint;

    /**
     * sets up the screen
     *
     * @param main for changing back to the menu screen
     */
    public OptionsScreen(final Main main, EntryPoint entryPoint) {
        this.main = main;
        this.entryPoint = entryPoint;

        if (entryPoint == EntryPoint.MENU_SCREEN) {
            this.stage = new Stage() {
                @Override
                public boolean keyUp(int keyCode) {
                    if (keyCode == Input.Keys.ESCAPE) { // change back to the menu screen if the player presses esc
                        main.setMenuScreen();
                    }
                    return super.keyUp(keyCode);
                }
            };
        }
        else {
            this.stage = new Stage() {
                @Override
                public boolean keyUp(int keyCode) {
                    if (keyCode == Input.Keys.ESCAPE) { // change back to the menu screen if the player presses esc
                        main.returnGameScreen();
                    }
                    return super.keyUp(keyCode);
                }
            };
        }

        this.stage.setViewport(new ScreenViewport());
        this.table = new Table();

        this.stage.addActor(table);
        this.table.setFillParent(true);
        this.table.setDebug(false);
        this.setupUi();
    }


    /**
     * Method generates a string array of possible resolutions the game could be displayed at on this monitor
     * Used to get selectable elements in the Resolution selector widget
     * Resolutions must be a minimum of 1000 x 1000 pixels
     *
     * @return possible display resolutions in format ScreenWidth x ScreenHeight
     */
    private String[] getPossibleResolutions() {
        DisplayMode[] displayModes = Gdx.graphics.getDisplayModes();
        Set<String> resolutions = new HashSet<String>();

        for (DisplayMode displayMode : displayModes) {
            if (displayMode.width > 1000 && displayMode.height > 1000) { // window must be more than 1000 x 1000 resolution
                resolutions.add(displayMode.width + " x " + displayMode.height);
            }
        }
        String[] resStrings = new String[resolutions.size()];
        resolutions.toArray(resStrings);
        return resStrings;
    }

    private Table setupOptionsTable() {
        // setup widgets for selecting the options
        musicSlider = WidgetFactory.genStyledSlider();
        fxSlider = WidgetFactory.genStyledSlider();
        resolutionSelector = WidgetFactory.genStyledSelectBox(getPossibleResolutions());
        fullscreenSwitch = WidgetFactory.genOnOffSwitch();
        colourblindModeSwitch = WidgetFactory.genOnOffSwitch();

        // setup labels
        Label musicVolumeLabel = WidgetFactory.genMenuLabel("MUSIC VOLUME");
        musicVolumeLabel.setAlignment(Align.center);
        Label fxVolumeLabel = WidgetFactory.genMenuLabel("FX VOLUME");
        fxVolumeLabel.setAlignment(Align.center);
        Label resolutionSelectorLabel = WidgetFactory.genMenuLabel("RESOLUTION");
        resolutionSelectorLabel.setAlignment(Align.center);
        Label fullscreenSwitchLabel = WidgetFactory.genMenuLabel("FULLSCREEN");
        fullscreenSwitchLabel.setAlignment(Align.center);
        Label colourblindModeSwitchLabel = WidgetFactory.genMenuLabel("COLOURBLIND MODE");
        colourblindModeSwitchLabel.setAlignment(Align.center);

        // add the setup widgets to a table
        Table table = new Table();
        table.setDebug(false);
        table.left();
        table.add(musicVolumeLabel).height(60).width(420).pad(20);
        table.right();
        table.add(musicSlider).padLeft(150);

        table.row();
        table.left();
        table.add(fxVolumeLabel).height(60).width(420).pad(20);
        table.right();
        table.add(fxSlider).padLeft(150);

        table.row();
        table.left();
        table.add(resolutionSelectorLabel).height(60).width(420).pad(20);
        table.right();
        table.add(resolutionSelector).padLeft(150);

        table.row();
        table.left();
        table.add(fullscreenSwitchLabel).height(60).width(420).pad(20);
        table.right();
        table.add(fullscreenSwitch).padLeft(150);

        table.row();
        table.left();
        table.add(colourblindModeSwitchLabel).height(60).width(420).pad(20);
        table.right();
        table.add(colourblindModeSwitch).padLeft(150);

        TextButton acceptButton = WidgetFactory.genBasicButton("CONFIRM CHANGES");
        acceptButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                acceptChanges(); // save and apply changes when CONFIRM CHANGES button is pressed
                if (entryPoint == EntryPoint.MENU_SCREEN) {
                    main.setMenuScreen(); // revert to the menu screen
                }
                else {
                    main.returnGameScreen(); // revert to the game screen
                }
            }
        });

        table.row();
        table.left();

        table.add(acceptButton).height(60).width(420).pad(20);

        return table;
    }

    @SuppressWarnings("Duplicates") // As same code is present in other screens
    private void setupUi() {
        table.background(new TextureRegionDrawable(new TextureRegion(new Texture("uiComponents/menuBackground.png"))));

        table.center();
        table.add(WidgetFactory.genMenusTopBar("OPTIONS")).colspan(2);

        table.row();
        table.add(setupOptionsTable()).expand();

        table.add(WidgetFactory.genOptionsGraphic()).height(700).width(540).pad(30);

        if (entryPoint == EntryPoint.MENU_SCREEN) {
            table.row();
            table.add(WidgetFactory.genBottomBar("MAIN MENU", new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    main.setMenuScreen();
                }

            })).colspan(2);
        }
        else {
            table.row();
            table.add(WidgetFactory.genBottomBar("RETURN", new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    main.returnGameScreen();
                }

            })).colspan(2);
        }
    }

    /**
     * Called when accept button clicked in options menu
     * applies the changes to the game settings made by the player
     * saves the updated preferences to file
     */
    private void acceptChanges() {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES_NAME);
        prefs.putFloat(MUSIC_VOL_PREF, musicSlider.getPercent());
        prefs.putFloat(FX_VOL_PREF, fxSlider.getPercent());

        // split the selected resolution into width and height values
        int screenWidth = Integer.parseInt(resolutionSelector.getSelected().split(" x ")[0]);
        int screenHeight = Integer.parseInt(resolutionSelector.getSelected().split(" x ")[1]);

        prefs.putInteger(RESOLUTION_WIDTH_PREF, screenWidth);
        prefs.putInteger(RESOLUTION_HEIGHT_PREF, screenHeight);

        prefs.putBoolean(FULLSCREEN_PREF, fullscreenSwitch.isChecked());
        prefs.putBoolean(COLOURBLIND_PREF, colourblindModeSwitch.isChecked());

        prefs.flush(); // save the updated preferences to file
        main.applyPreferences(); // apply the changes to the game
    }

    /**
     * reads the preferences file so that the options screen may be set to the current settings
     */
    private void readPreferences() {
        Preferences prefs = Gdx.app.getPreferences(OptionsScreen.PREFERENCES_NAME);

        musicSlider.setValue(prefs.getFloat(MUSIC_VOL_PREF, 1f));
        fxSlider.setValue(prefs.getFloat(FX_VOL_PREF, 1f));

        if (prefs.getInteger(OptionsScreen.RESOLUTION_WIDTH_PREF, -1) == -1 || prefs.getInteger(OptionsScreen.RESOLUTION_HEIGHT_PREF, -1) == -1) {
            resolutionSelector.setSelected(Gdx.graphics.getWidth() + " x " + Gdx.graphics.getHeight());
        } else {
            resolutionSelector.setSelected(prefs.getInteger(RESOLUTION_WIDTH_PREF) + " x " + prefs.getInteger(RESOLUTION_HEIGHT_PREF));
        }

        fullscreenSwitch.setChecked(prefs.getBoolean(OptionsScreen.FULLSCREEN_PREF, Gdx.graphics.isFullscreen()));
        colourblindModeSwitch.setChecked(prefs.getBoolean(OptionsScreen.COLOURBLIND_PREF, false));

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        readPreferences();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act(Gdx.graphics.getDeltaTime());
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
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
