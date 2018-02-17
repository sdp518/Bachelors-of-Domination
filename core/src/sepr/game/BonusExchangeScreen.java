package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class BonusExchangeScreen implements Screen {

    private Main main;
    private Stage stage;
    private Table table;
    private GameScreen gameScreen;

    int pizzaAmount;
    int studentAmount;
    Label studentLabel;

    /**
     *
     * @param main for changing to different screens
     */
    public BonusExchangeScreen (final Main main, GameScreen gameScreen) {
        this.main = main;
        this.gameScreen = gameScreen;

        this.studentAmount = 0;

        this.stage = new Stage() {
            @Override
            public boolean keyUp(int keyCode) {
                if (keyCode == Input.Keys.ESCAPE) { // change back to the game screen if the player presses esc
                    main.returnGameScreen();
                }
                return super.keyUp(keyCode);
            }
        };

        this.stage.setViewport(new ScreenViewport());
        this.table = new Table();
        this.stage.addActor(table);
        this.table.setFillParent(true);
        this.table.setDebug(true);
        this.setupUi();

    }

    private Table setupSubTable() {
        Table subTable = new Table();
        subTable.setDebug(true);

        Table btnTable = new Table();
        btnTable.setDebug(false);

        Button btnUp = WidgetFactory.genPizzaUpButton();
        Button btnDown = WidgetFactory.genPizzaDownButton();

        btnUp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                studentAmount++;
            }
        });

        btnDown.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (studentAmount > 0) {
                    studentAmount--;
                }
            }
        });

        btnTable.row();
        btnTable.add(btnUp);
        btnTable.row();
        btnTable.add(btnDown);

        Image arrow = new Image(new Texture("uiComponents/bonusExchange/arrow.png"));
        Image studentIcon = new Image(new Texture("uiComponents/bonusExchange/studentIcon.png"));
        Image pizzaSlice = new Image(new Texture("uiComponents/bonusExchange/pizzaSlice.png"));

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = WidgetFactory.getFontBig();

        studentLabel = new Label("x0", style);

        Button convertButton = WidgetFactory.genConvertButton();
        convertButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.sounds.playSound("menu_sound");
                // CONVERT
            }
        });

        subTable.row();
        subTable.add(btnTable).padRight(20);
        subTable.add(pizzaSlice);
        subTable.add(arrow);
        subTable.add(studentIcon);
        subTable.add(studentLabel);
        subTable.row().padTop(180);
        subTable.add(convertButton).height(60).width(420).colspan(5).center();

        return subTable;
    }

    // TODO Finish implementing setupUI()
    /**
     * sets up the UI for the bonus exchange screen
     */
    //@SuppressWarnings("Duplicates") // As same code is present in other screens
    private void setupUi() {

        // add the menu background
        table.background(new TextureRegionDrawable(new TextureRegion(new Texture("uiComponents/bonusExchange/background.png"))));

        table.center().top();
        table.add(WidgetFactory.genMenusTopBar("PIZZA EXCHANGE")).colspan(2);

        table.row().expand();
        table.add().width(Gdx.graphics.getWidth()/2);
        table.add(setupSubTable()).width(Gdx.graphics.getWidth()/2).padTop(40);

        table.row().bottom();
        table.add(WidgetFactory.genBottomBar("RETURN", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.sounds.playSound("menu_sound");
                main.returnGameScreen();
            }

        })).colspan(2);

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
        studentLabel.setText("x" + Integer.toString(studentAmount));
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
