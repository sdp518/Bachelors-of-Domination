package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import javax.swing.text.Style;
import java.util.Scanner;


public class GameSetupScreen implements Screen{

    private Main main;
    private Stage stage;
    private Table table;

    private CheckBox turnTimerSwitch;


    public enum playerType{
        NONE("NONE"),
        HUMAN("HUMAN PLAYER"),
        AI("NEUTRAL A.I.");

        private final String shortCode;

        playerType(String code){
            this.shortCode = code;
        }

        public String getPlayerType(){
            return this.shortCode;
        }
    }

    public enum collegeName{
        ALCUIN("ALCUIN"),
        DERWENT("DERWENT"),
        HALIFAX("HALIFAX"),
        HES_EAST("HESLINGTON EAST"),
        JAMES("JAMES"),
        UNI_OF_YORK("UNIVERSITY OF YORK"),
        VANBRUGH("VANBRUGH"),
        WENTWORTH("WENTWORTH");

        private final String shortCode;

        collegeName(String code){
            this.shortCode=code;
        }

        public String getCollegeName(){
            return this.shortCode;
        }
    }


    public GameSetupScreen (Main main) {

        this.main = main;

        this.stage = new Stage();
        this.stage.setViewport(new ScreenViewport());
        this.table = new Table();
        this.table.setFillParent(true); // make ui table fill the entire screen
        this.stage.addActor(table);
        this.table.setDebug(false); // enable table drawing for ui debug
        this.setupUi();

    }

    public void selectPlayerLeft(Label playerLabel){
        if (playerLabel.getText().toString().equals(playerType.NONE.getPlayerType())){
            playerLabel.setText(playerType.AI.getPlayerType());
        }else if (playerLabel.getText().toString().equals(playerType.HUMAN.getPlayerType())){
            playerLabel.setText(playerType.NONE.getPlayerType());
        }else {
            playerLabel.setText(playerType.HUMAN.getPlayerType());
        }
    }

    public void selectPlayerRight(Label playerLabel){
        if (playerLabel.getText().toString().equals(playerType.NONE.getPlayerType())){
            playerLabel.setText(playerType.HUMAN.getPlayerType());
        }else if (playerLabel.getText().toString().equals(playerType.HUMAN.getPlayerType())){
            playerLabel.setText(playerType.AI.getPlayerType());
        }else {
            playerLabel.setText(playerType.NONE.getPlayerType());
        }
    }

    public String selectCollegeLeft(Label collegeLabel){
        if (collegeLabel.getText().toString().equals(collegeName.ALCUIN.getCollegeName())){
            return collegeName.WENTWORTH.getCollegeName();
        }else if(collegeLabel.getText().toString().equals(collegeName.DERWENT.getCollegeName())){
            return collegeName.ALCUIN.getCollegeName();
        }else if(collegeLabel.getText().toString().equals(collegeName.HALIFAX.getCollegeName())){
            return collegeName.DERWENT.getCollegeName();
        }else if(collegeLabel.getText().toString().equals(collegeName.HES_EAST.getCollegeName())){
            return collegeName.HALIFAX.getCollegeName();
        }else if(collegeLabel.getText().toString().equals(collegeName.JAMES.getCollegeName())){
            return collegeName.HES_EAST.getCollegeName();
        }else if(collegeLabel.getText().toString().equals(collegeName.UNI_OF_YORK.getCollegeName())){
            return collegeName.JAMES.getCollegeName();
        }else if(collegeLabel.getText().toString().equals(collegeName.VANBRUGH.getCollegeName())){
            return collegeName.UNI_OF_YORK.getCollegeName();
        }else {
            return collegeName.VANBRUGH.getCollegeName();

        }

    }

    public String selectCollegeRight(Label collegeLabel){
        if (collegeLabel.getText().toString().equals(collegeName.ALCUIN.getCollegeName())){
            return collegeName.DERWENT.getCollegeName();
        }else if(collegeLabel.getText().toString().equals(collegeName.DERWENT.getCollegeName())){
            return collegeName.HALIFAX.getCollegeName();
        }else if(collegeLabel.getText().toString().equals(collegeName.HALIFAX.getCollegeName())){
            return collegeName.HES_EAST.getCollegeName();
        }else if(collegeLabel.getText().toString().equals(collegeName.HES_EAST.getCollegeName())){
            return collegeName.JAMES.getCollegeName();
        }else if(collegeLabel.getText().toString().equals(collegeName.JAMES.getCollegeName())){
            return collegeName.UNI_OF_YORK.getCollegeName();
        }else if(collegeLabel.getText().toString().equals(collegeName.UNI_OF_YORK.getCollegeName())){
            return collegeName.VANBRUGH.getCollegeName();
        }else if(collegeLabel.getText().toString().equals(collegeName.VANBRUGH.getCollegeName())){
            return collegeName.WENTWORTH.getCollegeName();
        }else {
            return collegeName.ALCUIN.getCollegeName();

        }

    }

    private Table setUpGameSetupTable() {


        final Label player1Label = WidgetFactory.genPlayerLabel(playerType.NONE.getPlayerType());
        player1Label.setAlignment(Align.center);
        Button player1LeftButton = WidgetFactory.genPlayerLeftButton();
        player1LeftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectPlayerLeft(player1Label);
            }
        });
        Button player1RightButton = WidgetFactory.genPlayerRightButton();
        player1RightButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectPlayerRight(player1Label);
            }
        });



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
        private Table setUpCollegeTable(){

            final Label nameBoxLabel = WidgetFactory.genNameBoxLabel("ALCUIN");
            nameBoxLabel.setAlignment(Align.center);
            final Label collegeLogo = new Label("", WidgetFactory.genCollegeLabelStyle(nameBoxLabel.getText().toString()));
            TextField playerName = WidgetFactory.playerNameTextField("Player 1");

            Button collegeLeftButton = WidgetFactory.genCollegeLeftButton();
            collegeLeftButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    nameBoxLabel.setText(selectCollegeLeft(nameBoxLabel));
                    collegeLogo.setStyle(WidgetFactory.genCollegeLabelStyle(nameBoxLabel.getText().toString()));
                }
            });
            Button collegeRightButton = WidgetFactory.genCollegeRightButton();
            collegeRightButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    nameBoxLabel.setText(selectCollegeRight(nameBoxLabel));
                    collegeLogo.setStyle(WidgetFactory.genCollegeLabelStyle(nameBoxLabel.getText().toString()));
                }
            });



            Table collegeTable = new Table();
            collegeTable.background(new TextureRegionDrawable(new TextureRegion(new Texture("ui/HD-assets/Game-Setup-Name-Box.png")))).setWidth(420);
            collegeTable.setDebug(true);

            collegeTable.left();
            collegeTable.add(playerName);
            collegeTable.row();
            collegeTable.add(nameBoxLabel).height(20).width(200);

            collegeTable.add(collegeLeftButton).height(50).width(25);
            collegeTable.add(collegeLogo).height(80).width(100);
            collegeTable.add(collegeRightButton).height(50).width(25);

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


        table.background(new TextureRegionDrawable(new TextureRegion(new Texture("ui/HD-assets/Menu-Background.png"))));

        table.center();
        table.add(WidgetFactory.genMainMenuTopBarGraphic()).height(60).colspan(2).fillX().height(100);

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
