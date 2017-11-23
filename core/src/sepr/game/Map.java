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

    public Texture hesEast1, hesEast2, hesEast3, hesEast4;
    public Texture halifax1, halifax2, halifax3, halifax4;
    public Texture derwent1, derwent2, derwent3, derwent4;
    public Texture alcuin1, alcuin2, alcuin3;
    public Texture vanbrugh1, vanbrugh2, vanbrugh3, vanbrugh4;
    public Texture wentworth1, wentworth2;
    public Texture james1, james2, james3, james4;
    public Texture neutral1, neutral2, neutral3, neutral4, neutral5, neutral6;

    public Pixmap hesEastPix1, hesEastPix2, hesEastPix3, hesEastPix4;
    public Pixmap halifaxPix1, halifaxPix2, halifaxPix3, halifaxPix4;
    public Pixmap derwentPix1, derwentPix2, derwentPix3, derwentPix4;
    public Pixmap alcuinPix1, alcuinPix2, alcuinPix3;
    public Pixmap vanbrughPix1, vanbrughPix2, vanbrughPix3, vanbrughPix4;
    public Pixmap wentworthPix1, wentworthPix2;
    public Pixmap jamesPix1, jamesPix2, jamesPix3, jamesPix4;
    public Pixmap neutralPix1, neutralPix2, neutralPix3, neutralPix4, neutralPix5, neutralPix6;

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
     * calculates how many reinforcements the given player should receive based on the sectors they control
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


    }

}
