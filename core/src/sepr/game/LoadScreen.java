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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoadScreen implements Screen{

    private Main main;
    private Stage stage;
    private Table table;
    private GameScreen gameScreen;
    private GameSetupScreen gameSetupScreen;

    private EntryPoint entryPoint;

    private Texture selectSaveBox;

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

        this.selectSaveBox = new Texture("uiComponents/selectSaveBttn.png");

        this.stage.setViewport(new ScreenViewport());
        this.table = new Table();
        this.stage.addActor(table);
        this.table.setFillParent(true);
        this.table.setDebug(false);
        this.setupUi();

    }

    // TODO Implement setupSelectSaveTable()
    private Table setupSelectSaveTable() {
        Table saveTable = new Table();
        saveTable.setDebug(true);

        Table saveOne, saveTwo, saveThree, saveFour;
        saveOne = new Table();
        saveTwo = new Table();
        saveThree = new Table();
        saveFour = new Table();
        Table[] saveTables = new Table[] {saveOne, saveTwo, saveThree, saveFour};

        for (final Table t : saveTables) {
            t.setDebug(false);
            t.setBackground(new TextureRegionDrawable(new TextureRegion(selectSaveBox, 0,0, 1240, 208)));
            t.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    t.setBackground(new TextureRegionDrawable(new TextureRegion(selectSaveBox, 0,208, 1240, 208)));
                }
            });

        }


        /*ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();

        style.imageUp = new TextureRegionDrawable(new TextureRegion(selectSaveBox, 0,0, 1240, 208));
        style.imageDown = new TextureRegionDrawable(new TextureRegion(selectSaveBox, 0,208, 1240, 208));
        style.imageOver = new TextureRegionDrawable(new TextureRegion(selectSaveBox, 0,208, 1240, 208));

        style.font = WidgetFactory.getFontSmall();*/

        saveTable.row();
        saveTable.add(saveOne).height(200).padBottom(20);
        saveTable.row();
        saveTable.add(saveTwo).height(200).padBottom(20);
        saveTable.row();
        saveTable.add(saveThree).height(200).padBottom(20);
        saveTable.row();
        saveTable.add(saveFour).height(200).padBottom(20);

        return saveTable;

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

        table.row().padTop(60);
        table.add(setupSelectSaveTable());

        TextButton saveButton = WidgetFactory.genStartGameButton();
        saveButton.setText("SAVE");
        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Save.saveGame(gameScreen.getCurrentPhase(),
                        gameScreen.getSectors(),
                        gameScreen.getPlayers(),
                        gameScreen.getTurnOrder(),
                        gameScreen.getCurrentPlayerPointer(),
                        gameScreen.isTurnTimerEnabled(),
                        gameScreen.getMaxTurnTime(),
                        gameScreen.getTurnTimeElapsed(),
                        gameScreen.isGamePaused());
            }
        });

        TextButton loadButton = WidgetFactory.genStartGameButton();
        loadButton.setText("LOAD");
        loadButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    if (entryPoint == EntryPoint.MENU_SCREEN) { }
                    Load.loadGame(gameScreen, main);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    System.out.println("Load Unsuccessful");
                }
            }
        });

        Table subTable = new Table();
        subTable.setDebug(true);

        subTable.row();
        subTable.add(saveButton).fill().height(60).width(170).padBottom(20);
        subTable.row();
        subTable.add(loadButton).fill().height(60).width(170);

        table.add(subTable).expandX();

        table.row();
        table.add().expand();

        if (entryPoint == EntryPoint.MENU_SCREEN) {
            table.row();
            table.add(WidgetFactory.genBottomBar("MAIN MENU", new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    main.setMenuScreen();}

            })).colspan(2);
        }
        else {
            table.row();
            table.add(WidgetFactory.genBottomBar("RETURN", new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
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
