package sepr.game;

import com.badlogic.gdx.Gdx;
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

public class GameSetupScreen implements Screen{

    private Main main;
    private Stage stage;
    private Table table;

    private CheckBox turnTimerSwitch;
    public static final String[] PLAYER_TYPE={"NONE", "HUMAN PLAYER", "NEUTRAL A.I."};
    public int playerChoice;


    public GameSetupScreen (Main main) {

        playerChoice= 0;

        this.main = main;

        this.stage = new Stage();
        this.stage.setViewport(new ScreenViewport());

        this.table = new Table();
        this.table.setFillParent(true); // make ui table fill the entire screen
        this.stage.addActor(table);
        this.table.setDebug(false); // enable table drawing for ui debug
        this.setupUi();

    }


    public int getPlayerChoice() {
        return playerChoice;
    }

    public void setPlayerChoice(int playerChoice) {
        this.playerChoice = playerChoice;
    }

    private Table setUpGameSetupTable() {

        Button player1LeftButton = WidgetFactory.genPlayerLeftButton();
        player1LeftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setPlayerChoice(1);

            }
        });


        Button player1RightButton = WidgetFactory.genPlayerRightButton();
        Label player1Label = WidgetFactory.genPlayerLabel(PLAYER_TYPE[getPlayerChoice()]);
        player1Label.setAlignment(Align.center);

        Button player2LeftButton = WidgetFactory.genPlayerLeftButton();
        Button player2RightButton = WidgetFactory.genPlayerRightButton();
        Label player2Label = WidgetFactory.genPlayerLabel("HUMAN PLAYER");
        player2Label.setAlignment(Align.center);

        Button player3LeftButton = WidgetFactory.genPlayerLeftButton();
        Button player3RightButton = WidgetFactory.genPlayerRightButton();
        Label player3Label = WidgetFactory.genPlayerLabel("NONE");
        player3Label.setAlignment(Align.center);

        Button player4LeftButton = WidgetFactory.genPlayerLeftButton();
        Button player4RightButton = WidgetFactory.genPlayerRightButton();
        Label player4Label = WidgetFactory.genPlayerLabel("NONE");
        player4Label.setAlignment(Align.center);

        Button player5LeftButton = WidgetFactory.genPlayerLeftButton();
        Button player5RightButton = WidgetFactory.genPlayerRightButton();
        Label player5Label = WidgetFactory.genPlayerLabel("NEUTRAL A.I.");
        player5Label.setAlignment(Align.center);

        Table playerTable = new Table();
        playerTable.setDebug(true);

        //Player 1
        playerTable.left();
        playerTable.add(player1LeftButton).height(60).width(50).pad(5);
        playerTable.add(player1Label).height(60).width(320).expandX();
        playerTable.right();
        playerTable.add(player1RightButton).height(60).width(50).pad(5);

        //Player 2
        playerTable.row();
        playerTable.left();
        playerTable.add(player2LeftButton).height(60).width(50).pad(5);
        playerTable.add(player2Label).height(60).width(320);
        playerTable.right();
        playerTable.add(player2RightButton).height(60).width(50).pad(5);

        //Player 3
        playerTable.row();
        playerTable.left();
        playerTable.add(player3LeftButton).height(60).width(50).pad(5);
        playerTable.add(player3Label).height(60).width(320);
        playerTable.right();
        playerTable.add(player3RightButton).height(60).width(50).pad(5);

        //Player 4
        playerTable.row();
        playerTable.left();
        playerTable.add(player4LeftButton).height(60).width(50).pad(5);
        playerTable.add(player4Label).height(60).width(320);
        playerTable.right();
        playerTable.add(player4RightButton).height(60).width(50).pad(5);

        //Player 5
        playerTable.row();
        playerTable.left();
        playerTable.add(player5LeftButton).height(60).width(50).pad(5);
        playerTable.add(player5Label).height(60).width(320);
        playerTable.right();
        playerTable.add(player5RightButton).height(60).width(50).pad(5);

        return playerTable;
        }

        private Table setUpTurnTimerTable(){
            Label turnTimerLabel = WidgetFactory.genMenuBtnLabel("TURN TIMER");
            turnTimerLabel.setAlignment(0, 0);

            CheckBox turnTimerSwitch = WidgetFactory.genOnOffSwitch();


            Table turnTimerTable = new Table();
            turnTimerTable.setDebug(true);

            turnTimerTable.left();
            turnTimerTable.add(turnTimerLabel).height(60).width(420);
            turnTimerTable.right();
            turnTimerTable.add(turnTimerSwitch);
            return turnTimerTable;
        }
        //College 1
        private Table setUpCollegeLogoTable(){
            Button collegeLeftButton = WidgetFactory.genCollegeLeftButton();
            Button collegeRightButton = WidgetFactory.genCollegeRightButton();
            Image derwLogo = WidgetFactory.genDerwLogo();

            Table collegeLogoTable = new Table();
            collegeLogoTable.setDebug(true);

            collegeLogoTable.left();
            collegeLogoTable.add(collegeLeftButton).height(50).width(25);
            collegeLogoTable.add(derwLogo).height(100).width(120);
            collegeLogoTable.right();
            collegeLogoTable.add(collegeRightButton).height(50).width(25);

            return collegeLogoTable;
        }



        private Table setUpCollegeTable(){
            Label nameBoxLabel = WidgetFactory.genNameBoxLabel("Player 1" +"\n"+
                    "Derwent");
            nameBoxLabel.setAlignment(0, 0);


            Table collegeTable = new Table();
            collegeTable.setDebug(true);

            collegeTable.left();
            collegeTable.add(nameBoxLabel).height(100).width(320);
            collegeTable.right();
            collegeTable.add(setUpCollegeLogoTable());

            return collegeTable;
        }



    private void setupUi() {
        TextButton startGameButton = WidgetFactory.genStartGameButton("START GAME");
        startGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.setGameScreen();
            }
        });


        table.background(new TextureRegionDrawable(new TextureRegion(new Texture("ui/background.png"))));

        table.center();
        table.add(WidgetFactory.genTopBarGraphic()).colspan(2).fillX().height(100);

        table.row();
        table.left();
        table.add(setUpGameSetupTable()).expand();
        table.right();
        table.add(setUpCollegeTable()).expand();
        table.row();
        table.left();
        table.add(setUpTurnTimerTable()).expand();
        table.add(startGameButton).height(60).width(420);



        table.row();
        table.center();
        table.add(WidgetFactory.genBottomBarGraphic()).colspan(2).fillX().height(150);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        main.applyPreferences();
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
