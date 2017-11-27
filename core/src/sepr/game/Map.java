package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

/**
 * Created by Dom's Surface Mark 2 on 16/11/2017.
 */
public class Map {
    private HashMap<Integer, Sector> sectors; // mapping of sector ID to the sector object
    private HashMap<Integer, Pixmap> mapPix;
    private HashMap<Integer, Texture> mapText;
    private HashMap<String, Color> mapColor;
    private HashMap<Integer, String> mapImage;

    public Texture hesEast1, hesEast2, hesEast3, hesEast4;
    public Texture halifax1, halifax2, halifax3, halifax4;
    public Texture derwent1, derwent2, derwent3, derwent4;
    public Texture alcuin1, alcuin2, alcuin3;
    public Texture vanbrugh1, vanbrugh2, vanbrugh3, vanbrugh4;
    public Texture wentworth1, wentworth2;
    public Texture james1, james2, james3, james4;
    public Texture neutral1, neutral2, neutral3, neutral4, neutral5, neutral6;
    public Texture lake1, lake2;

    public Pixmap hesEastPix1, hesEastPix2, hesEastPix3, hesEastPix4;
    public Pixmap halifaxPix1, halifaxPix2, halifaxPix3, halifaxPix4;
    public Pixmap derwentPix1, derwentPix2, derwentPix3, derwentPix4;
    public Pixmap alcuinPix1, alcuinPix2, alcuinPix3;
    public Pixmap vanbrughPix1, vanbrughPix2, vanbrughPix3, vanbrughPix4;
    public Pixmap wentworthPix1, wentworthPix2;
    public Pixmap jamesPix1, jamesPix2, jamesPix3, jamesPix4;
    public Pixmap neutralPix1, neutralPix2, neutralPix3, neutralPix4, neutralPix5, neutralPix6;

    Color changeGreen = new Color(0.5f, 0, 1f, 0f);
    Color changeBlue = new Color(0.8f, 0.5f, 0f, 0f);
    Color changeWhite =  new Color(0,0,0,0);


    public Map() {
        this.sectors = new HashMap<Integer, Sector>();

        this.hesEastPix1 = new Pixmap(Gdx.files.internal("hesEast1.png"));
        this.hesEastPix2 = new Pixmap(Gdx.files.internal("hesEast2.png"));
        this.hesEastPix3 = new Pixmap(Gdx.files.internal("hesEast3.png"));
        this.hesEastPix4 = new Pixmap(Gdx.files.internal("hesEast4.png"));

        this.hesEast1 = new Texture(hesEastPix1);
        this.hesEast2 = new Texture(hesEastPix2);
        this.hesEast3 = new Texture(hesEastPix3);
        this.hesEast4 = new Texture(hesEastPix4);

        this.halifaxPix1 = new Pixmap(Gdx.files.internal("halifax1.png"));
        this.halifaxPix2 = new Pixmap(Gdx.files.internal("halifax2.png"));
        this.halifaxPix3 = new Pixmap(Gdx.files.internal("halifax3.png"));
        this.halifaxPix4 = new Pixmap(Gdx.files.internal("halifax4.png"));
        this.halifax1 = new Texture(halifaxPix1);
        this.halifax2 = new Texture(halifaxPix2);
        this.halifax3 = new Texture(halifaxPix3);
        this.halifax4 = new Texture(halifaxPix4);

        this.derwentPix1 = new Pixmap(Gdx.files.internal("derwent1.png"));
        this.derwentPix2 = new Pixmap(Gdx.files.internal("derwent2.png"));
        this.derwentPix3 = new Pixmap(Gdx.files.internal("derwent3.png"));
        this.derwentPix4 = new Pixmap(Gdx.files.internal("derwent4.png"));
        this.derwent1 = new Texture(derwentPix1);
        this.derwent2 = new Texture(derwentPix2);
        this.derwent3 = new Texture(derwentPix3);
        this.derwent4 = new Texture(derwentPix4);

        this.alcuinPix1 = new Pixmap(Gdx.files.internal("alcuin1.png"));
        this.alcuinPix2 = new Pixmap(Gdx.files.internal("alcuin2.png"));
        this.alcuinPix3 = new Pixmap(Gdx.files.internal("alcuin3.png"));
        this.alcuin1 = new Texture(alcuinPix1);
        this.alcuin2 = new Texture(alcuinPix2);
        this.alcuin3 = new Texture(alcuinPix3);

        this.vanbrughPix1 = new Pixmap(Gdx.files.internal("vanbrugh1.png"));
        this.vanbrughPix2 = new Pixmap(Gdx.files.internal("vanbrugh2.png"));
        this.vanbrughPix3 = new Pixmap(Gdx.files.internal("vanbrugh3.png"));
        this.vanbrughPix4 = new Pixmap(Gdx.files.internal("vanbrugh4.png"));
        this.vanbrugh1 = new Texture(vanbrughPix1);
        this.vanbrugh2 = new Texture(vanbrughPix2);
        this.vanbrugh3 = new Texture(vanbrughPix3);
        this.vanbrugh4 = new Texture(vanbrughPix4);

        this.wentworthPix1 = new Pixmap(Gdx.files.internal("wentworth1.png"));
        this.wentworthPix2 = new Pixmap(Gdx.files.internal("wentworth2.png"));
        this.wentworth1 = new Texture(wentworthPix1);
        this.wentworth2 = new Texture(wentworthPix2);

        this.jamesPix1 = new Pixmap(Gdx.files.internal("james1.png"));
        this.jamesPix2 = new Pixmap(Gdx.files.internal("james2.png"));
        this.jamesPix3 = new Pixmap(Gdx.files.internal("james3.png"));
        this.jamesPix4 = new Pixmap(Gdx.files.internal("james4.png"));
        this.james1 = new Texture(jamesPix1);
        this.james2 = new Texture(jamesPix2);
        this.james3 = new Texture(jamesPix3);
        this.james4 = new Texture(jamesPix4);

        this.neutralPix1 = new Pixmap(Gdx.files.internal("neutral1.png"));
        this.neutralPix2 = new Pixmap(Gdx.files.internal("neutral2.png"));
        this.neutralPix3 = new Pixmap(Gdx.files.internal("neutral3.png"));
        this.neutralPix4 = new Pixmap(Gdx.files.internal("neutral4.png"));
        this.neutralPix5 = new Pixmap(Gdx.files.internal("neutral5.png"));
        this.neutralPix6 = new Pixmap(Gdx.files.internal("neutral6.png"));
        this.neutral1 = new Texture(neutralPix1);
        this.neutral2 = new Texture(neutralPix2);
        this.neutral3 = new Texture(neutralPix3);
        this.neutral4 = new Texture(neutralPix4);
        this.neutral5 = new Texture(neutralPix5);
        this.neutral6 = new Texture(neutralPix6);

        this.lake1 = new Texture("lake1.png");
        this.lake2 = new Texture("lake2.png");

        mapPix = new HashMap<Integer, Pixmap>(){{
            put(0, hesEastPix1); put(1, hesEastPix2); put(2, hesEastPix3); put(3, hesEastPix4);
            put(4, halifaxPix1); put(5, halifaxPix2); put(6, halifaxPix3); put(7, halifaxPix4);
            put(8, derwentPix1); put(9, derwentPix2); put(10, derwentPix3); put(11, derwentPix4);
            put(12, alcuinPix1); put(13, alcuinPix2); put(14, alcuinPix3);
            put(15, vanbrughPix1); put(16, vanbrughPix2); put(17, vanbrughPix3); put(18, vanbrughPix4);
            put(19, wentworthPix1); put(20, wentworthPix2);
            put(21, jamesPix1); put(22, jamesPix2); put(23, jamesPix3); put(24, jamesPix4);
            put(25, neutralPix1); put(26, neutralPix2); put(27, neutralPix3); put(28, neutralPix4);put(29, neutralPix5); put(30, neutralPix6);
        }};
        mapText = new HashMap<Integer, Texture>(){{
            put(0, hesEast1); put(1, hesEast2); put(2, hesEast3); put(3, hesEast4);
            put(4, halifax1); put(5, halifax2); put(6, halifax3); put(7, halifax4);
            put(8, derwent1); put(9, derwent2); put(10, derwent3); put(11, derwent4);
            put(12, alcuin1); put(13, alcuin2); put(14, alcuin3);
            put(15, vanbrugh1); put(16, vanbrugh2); put(17, vanbrugh3); put(18, vanbrugh4);
            put(19, wentworth1); put(20, wentworth2);
            put(21, james1); put(22, james2); put(23, james3); put(24, james4);
            put(25, neutral1); put(26, neutral2); put(27, neutral3); put(28, neutral4);put(29, neutral5); put(30, neutral6);
        }};
        mapImage = new HashMap<Integer, String>(){{
            put(0, "hesEast1.png"); put(1, "hesEast2.png"); put(2, "hesEast3.png"); put(3, "hesEast4.png");
            put(4, "halifax1.png"); put(5, "halifax2.png"); put(6, "halifax3.png"); put(7, "halifax4.png");
            put(8, "derwent1.png"); put(9, "derwent2.png"); put(10, "derwent3.png"); put(11, "derwent4.png");
            put(12, "alcuin1.png"); put(13, "alcuin2.png"); put(14, "alcuin3.png");
            put(15, "vanbrugh1.png"); put(16, "vanbrugh2.png"); put(17, "vanbrugh3.png"); put(18, "vanbrugh4.png");
            put(19, "wentworth1.png"); put(20, "wentworth2.png");
            put(21, "james1.png"); put(22, "james2.png"); put(23, "james3.png"); put(24, "james4.png");
            put(25, "neutral1.png"); put(26, "neutral2.png"); put(27, "neutral3.png"); put(28, "neutral4.png");put(29, "neutral5.png"); put(30, "neutral6.png");
        }};
        mapColor = new HashMap<String, Color>(){{
            put("blue", changeBlue);
            put("green", changeGreen);
            put("white", changeWhite);
        }};
    }

    /**
     * Checks to see if there is one player who controls every sector
     * @return -1 if there is no winner or the ID of the player that controlls all the sectors
     */
    private int checkForWinner() {
        return -1;
    }

    /**
     * Tranfers units from one sector to another
     * @throws IllegalArgumentException if the sector are not both owned by the same player
     * @throws IllegalArgumentException if the amount exceeds the number of units on the source sector
     * @throws IllegalArgumentException if the sectors are not connected
     * @param sourceSectorId where to move the units from
     * @param targetSectorId where to move the units to
     * @param amount how many units to move
     */
    private void moveUnits(int sourceSectorId, int targetSectorId, int amount) {

    }

    /**
     * calculates how many reinforcements the given player should receive based on the sectors they control by summing reinforcementsProvided for each Sector they control
     * @param playerId player who calculation is for
     * @return returns the amount of reinforcements the player should be allocated
     */
    private int calculateReinforcementAmount(int playerId) {
        return 0;
    }

    public void render(SpriteBatch batch) {
        batch.draw(hesEast1, 0, 0);
        batch.draw(hesEast2, 0, 0);
        batch.draw(hesEast3, 0, 0);
        batch.draw(hesEast4, 0, 0);

        batch.draw(halifax1, 0, 0);
        batch.draw(halifax2, 0, 0);
        batch.draw(halifax3, 0, 0);
        batch.draw(halifax4, 0, 0);

        batch.draw(derwent1, 0, 0);
        batch.draw(derwent2, 0, 0);
        batch.draw(derwent3, 0, 0);
        batch.draw(derwent4, 0, 0);

        batch.draw(alcuin1, 0, 0);
        batch.draw(alcuin2, 0, 0);
        batch.draw(alcuin3, 0, 0);

        batch.draw(vanbrugh1, 0, 0);
        batch.draw(vanbrugh2, 0, 0);
        batch.draw(vanbrugh3, 0, 0);
        batch.draw(vanbrugh4, 0, 0);

        batch.draw(wentworth1, 0, 0);
        batch.draw(wentworth2, 0, 0);

        batch.draw(james1, 0, 0);
        batch.draw(james2, 0, 0);
        batch.draw(james3, 0, 0);
        batch.draw(james4, 0, 0);

        batch.draw(neutral1, 0, 0);
        batch.draw(neutral2, 0, 0);
        batch.draw(neutral3, 0, 0);
        batch.draw(neutral4, 0, 0);
        batch.draw(neutral5, 0, 0);
        batch.draw(neutral6, 0, 0);

        batch.draw(lake1, 0, 0);
        batch.draw(lake2, 0, 0);
    }

    public void changeSectorColor(int sectorRef, String newColor){
        Color tempColor = new Color(0,0,0,0);
        Pixmap temp = new Pixmap(Gdx.files.internal(mapImage.get(sectorRef)));
        for (int x = 0; x < mapPix.get(sectorRef).getWidth(); x++){
            for (int y = 0; y < mapPix.get(sectorRef).getHeight(); y++){
                if(temp.getPixel(x, y) != -256){
                    Color.rgba8888ToColor(tempColor, temp.getPixel(x, y));
                    tempColor.sub(mapColor.get(newColor));
                    temp.drawPixel(x, y, Color.rgba8888(tempColor));
                }
            }
        }
        mapText.get(sectorRef).draw(temp, 0, 0);
        temp.dispose();
    }

}
