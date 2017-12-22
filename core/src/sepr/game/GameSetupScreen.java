package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import javafx.util.Pair;

import java.util.HashMap;


public class GameSetupScreen implements Screen{

    private Main main;
    private Stage stage;
    private Table table;

    private CheckBox turnTimerSwitch;
    private CheckBox neutralPlayerSwitch;
    final int MAX_NUMBER_OF_PLAYERS = 4;

    private Label[] playerTypes; // array of player types, index n -> player n's type
    private TextField[] playerNames; // array of textfields for players to enter names, index n -> player n's name
    private Pair<Label, Image>[] playerColleges; // array of pairs of college name and college logo, index n -> player n's college

    private Texture collegeTableBackground;

    /**
     *
     */
    public enum PlayerType {
        NONE("NONE"),
        HUMAN("HUMAN PLAYER"),
        AI("A.I."),
        NEUTRAL_AI("NEUTAL A.I.");

        private final String shortCode;

        PlayerType(String code){
            this.shortCode = code;
        }

        public String getPlayerType(){
            return this.shortCode;
        }
    }

    /**
     *
     */
    public enum CollegeName {
        ALCUIN("ALCUIN"),
        DERWENT("DERWENT"),
        HALIFAX("HALIFAX"),
        HES_EAST("HESLINGTON EAST"),
        JAMES("JAMES"),
        UNI_OF_YORK("UNIVERSITY OF YORK"),
        VANBRUGH("VANBRUGH"),
        WENTWORTH("WENTWORTH");

        private final String shortCode;

        CollegeName(String code){
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

        this.collegeTableBackground = new Texture("ui/HD-assets/Game-Setup-Name-Box.png");

        this.setupUi();
    }

    /**
     *
     * @param playerLabel
     */
    private void selectPlayerLeft(Label playerLabel){
        if (playerLabel.getText().toString().equals(PlayerType.NONE.getPlayerType())){
            playerLabel.setText(PlayerType.AI.getPlayerType());
        }else if (playerLabel.getText().toString().equals(PlayerType.HUMAN.getPlayerType())){
            playerLabel.setText(PlayerType.NONE.getPlayerType());
        }else {
            playerLabel.setText(PlayerType.HUMAN.getPlayerType());
        }
    }

    /**
     *
     * @param playerLabel
     */
    private void selectPlayerRight(Label playerLabel){
        if (playerLabel.getText().toString().equals(PlayerType.NONE.getPlayerType())){
            playerLabel.setText(PlayerType.HUMAN.getPlayerType());
        }else if (playerLabel.getText().toString().equals(PlayerType.HUMAN.getPlayerType())){
            playerLabel.setText(PlayerType.AI.getPlayerType());
        }else {
            playerLabel.setText(PlayerType.NONE.getPlayerType());
        }
    }

    /**
     *
     * @param collegeLabel
     * @return
     */
    private CollegeName selectCollegeLeft(Label collegeLabel){
        if (collegeLabel.getText().toString().equals(CollegeName.ALCUIN.getCollegeName())){
            return CollegeName.WENTWORTH;
        }else if(collegeLabel.getText().toString().equals(CollegeName.DERWENT.getCollegeName())){
            return CollegeName.ALCUIN;
        }else if(collegeLabel.getText().toString().equals(CollegeName.HALIFAX.getCollegeName())){
            return CollegeName.DERWENT;
        }else if(collegeLabel.getText().toString().equals(CollegeName.HES_EAST.getCollegeName())){
            return CollegeName.HALIFAX;
        }else if(collegeLabel.getText().toString().equals(CollegeName.JAMES.getCollegeName())){
            return CollegeName.HES_EAST;
        }else if(collegeLabel.getText().toString().equals(CollegeName.UNI_OF_YORK.getCollegeName())){
            return CollegeName.JAMES;
        }else if(collegeLabel.getText().toString().equals(CollegeName.VANBRUGH.getCollegeName())){
            return CollegeName.UNI_OF_YORK;
        }else {
            return CollegeName.VANBRUGH;
        }
    }

    /**
     *
     * @param collegeLabel
     * @return
     */
    private CollegeName selectCollegeRight(Label collegeLabel){
        if (collegeLabel.getText().toString().equals(CollegeName.ALCUIN.getCollegeName())){
            return CollegeName.DERWENT;
        }else if(collegeLabel.getText().toString().equals(CollegeName.DERWENT.getCollegeName())){
            return CollegeName.HALIFAX;
        }else if(collegeLabel.getText().toString().equals(CollegeName.HALIFAX.getCollegeName())){
            return CollegeName.HES_EAST;
        }else if(collegeLabel.getText().toString().equals(CollegeName.HES_EAST.getCollegeName())){
            return CollegeName.JAMES;
        }else if(collegeLabel.getText().toString().equals(CollegeName.JAMES.getCollegeName())){
            return CollegeName.UNI_OF_YORK;
        }else if(collegeLabel.getText().toString().equals(CollegeName.UNI_OF_YORK.getCollegeName())){
            return CollegeName.VANBRUGH;
        }else if(collegeLabel.getText().toString().equals(CollegeName.VANBRUGH.getCollegeName())){
            return CollegeName.WENTWORTH;
        }else {
            return CollegeName.ALCUIN;
        }
    }

    /**
     *
     * @param numberOfPlayers
     * @return
     */
    private Label[] playerLabels(int numberOfPlayers){
        Label[] labelList = new Label[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++){
            final Label playerLabel = WidgetFactory.genPlayerLabel(PlayerType.NONE.getPlayerType());
            playerLabel.setAlignment(Align.center);
           
            labelList[i] = playerLabel;
        }
        return labelList;
    }

    /**
     *
     * @param numberOfPlayers
     * @param playerLabels
     * @return
     */
    private Button[][] playerButtons(int numberOfPlayers, final Label[] playerLabels){
        Button[][] buttonList = new Button[numberOfPlayers][2];
        for(int i =0; i<numberOfPlayers; i++){
            Button playerLeftButton = WidgetFactory.genPlayerLeftButton();
            final int finalI = i;
            playerLeftButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    selectPlayerLeft(playerLabels[finalI]);
                }
            });
            Button playerRightButton = WidgetFactory.genPlayerRightButton();
            playerRightButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    selectPlayerRight(playerLabels[finalI]);
                }
            });
            
            buttonList[i][0] = playerLeftButton;
            buttonList[i][1] = playerRightButton;
        }
        return buttonList;
    }

    /**
     *
     * @return
     */
    private Table setUpPlayerTypesTable() {
        playerTypes = new Label[MAX_NUMBER_OF_PLAYERS];
        Button[] leftButtons = new Button[MAX_NUMBER_OF_PLAYERS]; // array of left selector buttons
        Button[] rightButtons = new Button[MAX_NUMBER_OF_PLAYERS]; // array of right selector buttons
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            Button leftButton = WidgetFactory.genPlayerLeftButton();
            Button rightButton = WidgetFactory.genPlayerRightButton();


            final int finalI = i;
            leftButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    selectPlayerLeft(playerTypes[finalI]);
                }
            });

            rightButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    selectPlayerRight(playerTypes[finalI]);
                }
            });

            playerTypes[i] = WidgetFactory.genPlayerLabel(PlayerType.NONE.getPlayerType());
            leftButtons[i] = leftButton;
            rightButtons[i] = rightButton;
        }

        Table leftTable = new Table();

        for(int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++){
            leftTable.left();
            leftTable.add(leftButtons[i]).height(60).width(50).pad(5);
            leftTable.add(playerTypes[i]).height(60).width(320).expandX().padTop(20).padBottom(20);
            leftTable.right();
            leftTable.add(rightButtons[i]).height(60).width(50).pad(5);
            leftTable.row();
        }

        return leftTable;
    }

    /**
     * Sets up the table for the switch based part of the game setup
     * Setup options for: neutral player and turn timer
     * @return
     */
    private Table setupSwitchTable(){
        // neutral player
        Label neutralPlayerLabel = WidgetFactory.genMenuBtnLabel("NEUTRAL PLAYER");
        neutralPlayerLabel.setAlignment(0, 0);

        neutralPlayerSwitch = WidgetFactory.genOnOffSwitch();

        // turn timer
        Label turnTimerLabel = WidgetFactory.genMenuBtnLabel("TURN TIMER");
        turnTimerLabel.setAlignment(0, 0);

        turnTimerSwitch = WidgetFactory.genOnOffSwitch();

        /* Add components to table */
        Table switchTable = new Table();
        switchTable.setDebug(true);

        switchTable.left();
        switchTable.add(neutralPlayerLabel).height(60).width(420);
        switchTable.right();
        switchTable.add(neutralPlayerSwitch).padLeft(50);

        switchTable.row().padTop(20).padBottom(20);

        switchTable.left();
        switchTable.add(turnTimerLabel).height(60).width(420);
        switchTable.right();
        switchTable.add(turnTimerSwitch).padLeft(50);

        return switchTable;
    }

    /**
     * Generates the UI table for setting player's colleges and names
     * Populates the playerColleges and playerNames arrays to be accessed later
     * @return the UI table to be added to the stage
     */
    private Table setUpCollegeTable(){
        playerNames = new TextField[MAX_NUMBER_OF_PLAYERS];
        playerColleges = new Pair[MAX_NUMBER_OF_PLAYERS];

        // generate the textfields, college labels and college logos
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            Label collegeName = WidgetFactory.genNameBoxLabel("ALCUIN");
            Image collegeImage = WidgetFactory.genCollegeLogoImage(CollegeName.ALCUIN);

            playerColleges[i] = new Pair<Label, Image>(collegeName, collegeImage);

            playerNames[i] = WidgetFactory.playerNameTextField("PLAYER" + (i + 1));
        }

        Table[] collegeTables = new Table[MAX_NUMBER_OF_PLAYERS]; // array of tables containing UI config widgets for college and player name setup
        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            Table textTable = new Table(); // table for storing the college name and player name textfield widgets
            Table logoTable = new Table(); // table for storing the college logo and selector buttons

            textTable.add(playerNames[i]).padBottom(10).width(220).left(); // add the textfields to the text table
            textTable.row();
            textTable.add(playerColleges[i].getKey()).height(20).width(220);

            Button leftButton = WidgetFactory.genCollegeLeftButton();
            Button rightButton = WidgetFactory.genCollegeRightButton();

            final int finalI = i;
            leftButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    CollegeName nextCollege = selectCollegeLeft(playerColleges[finalI].getKey());

                    playerColleges[finalI].getKey().setText(nextCollege.getCollegeName());
                    playerColleges[finalI].getValue().setDrawable(WidgetFactory.genCollegeLogoDrawable(nextCollege));
                }
            });

            rightButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    CollegeName nextCollege = selectCollegeRight(playerColleges[finalI].getKey());

                    playerColleges[finalI].getKey().setText(nextCollege.getCollegeName());
                    playerColleges[finalI].getValue().setDrawable(WidgetFactory.genCollegeLogoDrawable(nextCollege));
                }
            });


            logoTable.add(leftButton).height(50).width(25);
            logoTable.add(playerColleges[i].getValue()).height(80).width(100);
            logoTable.add(rightButton).height(50).width(25);

            Table temp = new Table();
            temp.background(new TextureRegionDrawable(new TextureRegion(collegeTableBackground)));
            temp.setDebug(true);
            temp.add(textTable).expand().left().padLeft(20);
            temp.add(logoTable).padRight(60);

            collegeTables[i] = temp;
        }

        Table rightTable = new Table();
        for(int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++){
            rightTable.add(collegeTables[i]).padBottom(10).padTop(10);
            rightTable.row();
        }

        return rightTable;
    }

    private static CollegeName stringToCollege(String collegeName) {
        if (collegeName.equals(CollegeName.ALCUIN.getCollegeName())) {
            return CollegeName.ALCUIN;
        } else if (collegeName.equals(CollegeName.DERWENT.getCollegeName())) {
            return CollegeName.DERWENT;
        } else if (collegeName.equals(CollegeName.HALIFAX.getCollegeName())) {
            return CollegeName.HALIFAX;
        } else if (collegeName.equals(CollegeName.HES_EAST.getCollegeName())) {
            return CollegeName.HES_EAST;
        } else if (collegeName.equals(CollegeName.JAMES.getCollegeName())) {
            return CollegeName.JAMES;
        } else if (collegeName.equals(CollegeName.UNI_OF_YORK.getCollegeName())) {
            return CollegeName.UNI_OF_YORK;
        } else if (collegeName.equals(CollegeName.VANBRUGH.getCollegeName())) {
            return CollegeName.VANBRUGH;
        } else if (collegeName.equals(CollegeName.WENTWORTH.getCollegeName())) {
            return CollegeName.WENTWORTH;
        }
        return CollegeName.WENTWORTH;
    }

    private static Color getCollegeColor (CollegeName collegeName) {
        switch (collegeName) {
            case ALCUIN:
                return Color.RED;
            case DERWENT:
                return Color.GREEN;
            case HALIFAX:
                return Color.BLUE;
            case HES_EAST:
                return Color.YELLOW;
            case JAMES:
                return Color.MAGENTA;
            case UNI_OF_YORK:
                return Color.WHITE;
            case VANBRUGH:
                return Color.PURPLE;
            case WENTWORTH:
                return Color.CYAN;
        }
        return Color.BLACK;
    }

    private HashMap<Integer, Player> generatePlayerHashmaps() {
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();

        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS; i++) {
            if (playerTypes[i].getText().toString().equals(PlayerType.HUMAN.getPlayerType())) {
                // create human player
                players.put(i, new PlayerHuman(i, playerColleges[i].getKey().getText().toString(), getCollegeColor(stringToCollege(playerColleges[i].getKey().getText().toString()))));
            } else if (playerTypes[i].getText().toString().equals(PlayerType.AI.getPlayerType())) {
                // create AI player
                players.put(i, new PlayerAI(i, playerColleges[i].getKey().getText().toString(), getCollegeColor(stringToCollege(playerColleges[i].getKey().getText().toString()))));
            }
        }
        return players;
    }

    /**
     * Checks if:
     *  there are no duplicate player names
     *  the names are at least three characters long
     *  the names contain numbers and digits only
     * @throws GameSetupException if name conditions are not met
     */
    private void validatePlayerNames() throws GameSetupException{

    }

    /**
     * Checks if:
     *  any college has been selected more than once
     * @throws GameSetupException if college has been chosen more than once
     */
    private void validateCollegeSelection() throws GameSetupException{

    }

    /**
     * Checks if:
     *  there's at least 2 players
     *  there's at least 1 human player
     *  there's only 2 players then the neutral player is enabled
     * @throws GameSetupException if player configuration conditions are not met
     */
    private void validatePlayerConfiguration() throws GameSetupException{

    }

    /**
     * Method attempts to start the game
     * Following conditions must be met for a game to be able to start
     *  At least 2 players
     *  At least 1 human player
     *  Neutral player must be enabled if there are only 2 players
     *  No duplicate player names
     *  No duplicate college selections
     *  Player names must be at least three characters long
     *  Player names contain numbers and digits only
     */
    private void startGame() {
        try {
            validatePlayerNames();
            validateCollegeSelection();
            validatePlayerConfiguration();
        } catch (GameSetupException e) {
            Dialog errorDialog = new Dialog("Game Setup error", new Window.WindowStyle());
            errorDialog.text(WidgetFactory.genPlayerLabel(e.getExceptionType().getErrorMessage()));
            return;
        }

        main.setGameScreen(generatePlayerHashmaps(), turnTimerSwitch.isChecked(), 120);
    }

    private void setupUi() {
        TextButton startGameButton = WidgetFactory.genStartGameButton("START GAME");
        startGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startGame();
            }
        });

        // add the menu background
        table.background(new TextureRegionDrawable(new TextureRegion(new Texture("ui/HD-assets/Menu-Background.png"))));


        table.center();
        table.add(WidgetFactory.genTopBar("GAME SETUP")).colspan(2);

        table.row();
        table.left();
        table.add(setUpPlayerTypesTable()).expand();
        table.right();
        table.add(setUpCollegeTable()).expand();
        table.row();
        table.left();
        table.add(setupSwitchTable()).expand();
        table.add(startGameButton).height(60).width(420);

        table.row();
        table.center();
        table.add(WidgetFactory.genBottomBar("MAIN MENU", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.setMenuScreen();}

        })).colspan(2);
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
