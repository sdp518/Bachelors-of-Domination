package sepr.game;

import SaveLoad.Data;
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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class LoadScreen implements Screen{

    private Main main;
    private Stage stage;
    private Table table;
    private GameScreen gameScreen;
    private GameSetupScreen gameSetupScreen;

    private EntryPoint entryPoint;

    private Texture selectSaveBox;

    private String fileName;

    private Stage loadingWidgetStage;
    private boolean isLoading;
    private boolean loadingWidgetDrawn;

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
                        main.sounds.playSound("menu_sound");
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
                        main.sounds.playSound("menu_sound");
                        main.returnGameScreen();
                    }
                    return super.keyUp(keyCode);
                }
            };
        }

        this.selectSaveBox = new Texture("uiComponents/selectSaveBttn.png");

        this.loadingWidgetStage = new Stage();
        this.isLoading = false;
        this.loadingWidgetDrawn = false;

        this.stage.setViewport(new ScreenViewport());
        this.table = new Table();
        this.stage.addActor(table);
        this.table.setFillParent(true);
        this.table.setDebug(false);
        this.setupUi();

    }

    /**
     * sets up loading widget to be shown when game starts
     */
    @SuppressWarnings("Duplicates")
    private void showLoadingWidget() {
        isLoading = true;
        Table table = new Table();
        table.setDebug(false);
        table.setFillParent(true);
        table.add(new Image(new Texture("uiComponents/loadingBox.png")));
        loadingWidgetStage.addActor(table);
    }

    // TODO Implement setupSelectSaveTable()
    private Table setupSelectSaveTable() {
        Table saveTable = new Table();
        saveTable.setDebug(false);

        Label.LabelStyle smallStyle = new Label.LabelStyle();
        smallStyle.font = WidgetFactory.getFontBig();

        Label.LabelStyle bigStyle = new Label.LabelStyle();
        bigStyle.font = WidgetFactory.getFontSmall();

        Table[] saveTables = new Table[] {new Table(), new Table(), new Table(), new Table()};
        List<Boolean> clickedTables = Arrays.asList(new Boolean[]{false,false,false,false});
        for (int i = 0; i < saveTables.length; i++) {
            Path currentRelativePath = Paths.get("");
            String currentWorkingDir = currentRelativePath.toAbsolutePath().toString();
            String loadFileName = currentWorkingDir + "\\saves\\"+ i + ".data";
            ObjectInputStream ois = null;
            Data loadedSave = null;
            try {
                ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(loadFileName)));
                loadedSave = (Data) ois.readObject();
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            } finally {
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException e) {
                    }
                }
            }
            if ((loadedSave == null) && (this.entryPoint == EntryPoint.MENU_SCREEN)) {
                continue;
            }
            int thisTableNo = i;
            Table t = saveTables[i];
            t.setDebug(false);
            t.setTouchable(Touchable.enabled);
            t.setBackground(new TextureRegionDrawable(new TextureRegion(selectSaveBox, 0,0, 1240, 208)));
            t.addListener(new ClickListener() {
                private Table[] allTables = saveTables;
                private List<Boolean> clickedSave = clickedTables;
                private int tableNo = thisTableNo;

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    for(int i = 0; i < clickedSave.size(); i++) {
                        clickedSave.set(i, false);
                        allTables[i].setBackground(new TextureRegionDrawable(new TextureRegion(selectSaveBox, 0, 0, 1240, 208)));
                    }
                    clickedSave.set(tableNo, true);
                    allTables[tableNo].setBackground(new TextureRegionDrawable(new TextureRegion(selectSaveBox, 0,208, 1240, 208)));
                    fileName = Integer.toString(tableNo) + ".data";
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
//                    super.enter(event, x, y, pointer, fromActor);
                    t.setBackground(new TextureRegionDrawable(new TextureRegion(selectSaveBox, 0,208, 1240, 208)));
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//                    super.exit(event, x, y, pointer, toActor);
                    if (!clickedSave.get(tableNo)) {
                        t.setBackground(new TextureRegionDrawable(new TextureRegion(selectSaveBox, 0, 0, 1240, 208)));
                    }
                }
            });

            if (loadedSave != null) {
                Integer[] keys = loadedSave.getFullPlayers().keySet().toArray(new Integer[loadedSave.getFullPlayers().size()]);
                Player player1 = loadedSave.getFullPlayers().get(keys[0]);
                t.row().left();
                t.add(new Image(WidgetFactory.genCollegeLogoDrawable(player1.getCollegeName()))).width(150).height(120).padRight(10).padLeft(10);
                for (int j = 1; j < loadedSave.getFullPlayers().size(); j++) {
                    t.add(new Label("V", smallStyle));
                    t.add(new Image(WidgetFactory.genCollegeLogoDrawable(loadedSave.getFullPlayers().get(keys[j]).getCollegeName()))).width(150).height(120).padRight(10).padLeft(10);
                }
                t.row().left();
                t.add(new Label(player1.getPlayerName(), bigStyle)).center();
                for (int j = 1; j < loadedSave.getFullPlayers().size(); j++) {
                    t.add();
                    t.add(new Label(loadedSave.getFullPlayers().get(keys[j]).getPlayerName(), bigStyle)).center();
                }

            } else {
            t.row().center();
            t.add(new Label("EMPTY SAVE SLOT", smallStyle));
            }
            stage.addActor(t);

            saveTable.row();
            saveTable.add(t).height(200).padBottom(20);
        }

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

        if (entryPoint == EntryPoint.MENU_SCREEN) {
            table.center();
            table.add(WidgetFactory.genMenusTopBar("LOAD GAME")).colspan(2);
        }
        else {
            table.center();
            table.add(WidgetFactory.genMenusTopBar("SAVE GAME")).colspan(2);
        }

        table.row().padTop(60);
        table.add(setupSelectSaveTable());

        TextButton saveButton = WidgetFactory.genStartGameButton();
        saveButton.setText("SAVE");
        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Save.saveGame(fileName,
                        gameScreen.getCurrentPhase(),
                        gameScreen.getSectors(),
                        gameScreen.getPlayers(),
                        gameScreen.getTurnOrder(),
                        gameScreen.getCurrentPlayerPointer(),
                        gameScreen.isTurnTimerEnabled(),
                        gameScreen.getMaxTurnTime(),
                        gameScreen.getTurnTimeElapsed(),
                        gameScreen.isGamePaused());
                main.updateSaveScreen(new LoadScreen(main, EntryPoint.GAME_SCREEN, gameScreen, gameSetupScreen));
                main.setSaveScreen();
            }
        });

        TextButton loadButton = WidgetFactory.genStartGameButton();
        loadButton.setText("LOAD");
        loadButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showLoadingWidget();
            }
        });

        Table subTable = new Table();
        subTable.setDebug(false);

        if (entryPoint != EntryPoint.MENU_SCREEN) {
            subTable.row();
            subTable.add(saveButton).fill().height(60).width(300).padBottom(20);
        }

        subTable.row();
        subTable.add(loadButton).fill().height(60).width(300);

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
        isLoading = false;
        loadingWidgetDrawn = false;
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        if (loadingWidgetDrawn) {
            try {
                if (entryPoint == EntryPoint.MENU_SCREEN) { }
                Load.loadGame(fileName, gameScreen, main);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                System.out.println("Load Unsuccessful");
            }
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act(Gdx.graphics.getDeltaTime());
        this.stage.draw();
        if (isLoading) {
            loadingWidgetStage.draw();
            loadingWidgetDrawn = true;
        }
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
