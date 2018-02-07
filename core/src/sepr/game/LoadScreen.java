package sepr.game;

import SaveLoad.Load;
import SaveLoad.Save;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoadScreen implements Screen{

    private Main main;
    private Stage stage;
    private Table table;
    private GameScreen gameScreen;
    private GameSetupScreen gameSetupScreen;

    private EntryPoint entryPoint;

    /**
     *
     * @param main for changing to different screens
     */
    public LoadScreen (final Main main, EntryPoint entryPoint, GameScreen gameScreen, GameSetupScreen gameSetupScreen) {
        this.main = main;
        this.entryPoint = entryPoint;
        this.gameScreen = gameScreen;
        this.gameSetupScreen = gameSetupScreen;

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
                    if (keyCode == Input.Keys.ESCAPE) { // change back to the game screen if the player presses esc
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

    // TODO Implement setupSelectSaveTable()
    private Table setupSelectSaveTable() {
        return null;
    }

    // TODO Finish implementing setupUI()
    /**
     * sets up the UI for the load screen
     */
    @SuppressWarnings("Duplicates") // As same code is present in other screens
    private void setupUi() {

        // add the menu background
        table.background(new TextureRegionDrawable(new TextureRegion(new Texture("uiComponents/menuBackground.png"))));

        table.center();
        table.add(WidgetFactory.genMenusTopBar("LOAD GAME")).colspan(2);

        TextButton saveButton = WidgetFactory.genEndPhaseButton();
        saveButton.setText("SAVE");
        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.sounds.playSound("menu_sound");
                Save.saveGame(gameScreen.getCurrentPhase(),
                        gameScreen.getSectors(),
                        gameScreen.getPlayers(),
                        gameScreen.getTurnOrder(),
                        gameScreen.getCurrentPlayerPointer());
            }
        });

        TextButton loadButton = WidgetFactory.genEndPhaseButton();
        loadButton.setText("LOAD");
        loadButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    if (entryPoint == EntryPoint.MENU_SCREEN) {
                    }
                    main.sounds.playSound("menu_sound");
                    Load.loadGame(gameScreen, main);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    System.out.println("Nope");
                }
            }
        });

        table.row();
        table.add(saveButton).fill().height(60).width(170);
        table.row();
        table.add(loadButton).fill().height(60).width(170);


        table.row();
        table.add().expand();

        if (entryPoint == EntryPoint.MENU_SCREEN) {
            table.row();
            table.add(WidgetFactory.genBottomBar("MAIN MENU", new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    main.sounds.playSound("menu_sound");
                    main.setMenuScreen();}

            })).colspan(2);
        }
        else {
            table.row();
            table.add(WidgetFactory.genBottomBar("RETURN", new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    main.sounds.playSound("menu_sound");
                    main.returnGameScreen();}

            })).colspan(2);
        }

    }

    /**
     * change the input processing to be handled by this screen's stage
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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
