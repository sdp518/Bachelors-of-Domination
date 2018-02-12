package sepr.game;

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

/**
 * controls the UI for the main menu screen
 */
public class MenuScreen implements Screen {
    private Main main;
    private Stage stage;
    private Table table; // table for inserting ui widgets into

    /**
     * sets up the menu screen
     *
     * @param main for changing which screen is currently being displayed
     */
    public MenuScreen(final Main main) {
        this.main = main;

        this.stage = new Stage() {
            @Override
            public boolean keyUp(int keyCode) {
                if (keyCode == Input.Keys.ESCAPE) { // ask player if they would like to exit the game if they press escape
                    main.sounds.playSound("menu_sound");
                    DialogFactory.exitProgramDialogBox(stage);
                }
                return super.keyUp(keyCode);
            }
        };
        this.stage.setViewport(new ScreenViewport());

        this.table = new Table();
        this.table.setFillParent(true); // make ui table fill the entire screen
        this.stage.addActor(table);
        this.table.setDebug(false); // enable table drawing for ui debug
        this.setupUi();
    }

    /**
     * generates a table containing the start game, load game and options buttons
     * pressing;
     *      Start Game  --> Takes player to setup game screen
     *      Load Game   --> Not yet implemented
     *      Options     --> Takes player to options screen
     * @return a table of buttons
     */
    private Table setupMenuTable() {
        final TextButton startGameBtn = WidgetFactory.genBasicButton("START NEW GAME");
        final TextButton loadGameBtn = WidgetFactory.genBasicButton("LOAD GAME");
        final TextButton optionsBtn = WidgetFactory.genBasicButton("OPTIONS");

        /* Create sub-table for all the menu buttons */
        Table btnTable = new Table();
        btnTable.setDebug(false);
        btnTable.left();
        btnTable.add(startGameBtn).height(60).width(420).pad(30);

        btnTable.row();
        btnTable.left();
        btnTable.add(loadGameBtn).height(60).width(420).pad(30);

        btnTable.row();
        btnTable.left();
        btnTable.add(optionsBtn).height(60).width(420).pad(30);

        startGameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.sounds.playSound("menu_sound");
                main.setGameSetupScreen();
            }
        });

        loadGameBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.sounds.playSound("menu_sound");
                main.setLoadScreen();
            }
        });

        // TODO Matt fix this

        optionsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.sounds.playSound("menu_sound");
                main.setOptionsScreen();
            }
        });

        /* Sub-table complete */
        return btnTable;
    }

    /**
     * sets up the UI tables for the menu screen
     */
    private void setupUi() {
        table.background(new TextureRegionDrawable(new TextureRegion(new Texture("uiComponents/menuBackground.png"))));

        table.center();
        table.add(WidgetFactory.genMenusTopBar("MAIN MENU")).colspan(2);

        table.row();
        table.left();
        table.add(setupMenuTable()).expand();

        table.right();
        table.add(WidgetFactory.genMapGraphic()).height(657).width(811).pad(30);

        table.row();
        table.center();
        table.add(WidgetFactory.genBottomBar("QUIT", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.sounds.playSound("menu_sound");
                DialogFactory.exitProgramDialogBox(stage);}

        })).colspan(2);
    }

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
