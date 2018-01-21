package sepr.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * class for rendering a particle effect showing the change in number of troops on a sector
 */
public class UnitChangeParticle {
    private GlyphLayout glyphLayout; // layout for calculating text rendering position
    private Vector2 centrePosition; // where the particle is to be initially drawn
    private long startTime; // time when particle created
    private BitmapFont font; // font for rendering the amount
    private Texture overlay;

    /**
     *
     * @param amount the amount of troops that the sector is changing by
     * @param centrePosition initial position of the particle
     */
    public UnitChangeParticle(int amount, Vector2 centrePosition) {
        this.centrePosition = centrePosition;
        this.startTime = System.currentTimeMillis();
        overlay = new Texture("uiComponents/troopCountOverlay.png");

        font = WidgetFactory.getFontSmall();

        String signedAmount;
        if (amount > 0) { // if increase make text green and add a plus sign
            this.font.setColor(Color.GREEN);
            signedAmount = "+" + amount;
        } else { // if decrease make text red and add a minus sign
            this.font.setColor(Color.RED);
            signedAmount = "" + amount;
        }
        this.glyphLayout = new GlyphLayout(font, signedAmount);
    }


    /**
     * returns true if this particle has existed for too long and should be deleted
     *
     * @return if this particle should be deleted
     */
    public boolean toDelete() {
        long DISPLAY_DURATION = 1000; // display particle for 1000ms i.e. 1 second
        return System.currentTimeMillis() - startTime > DISPLAY_DURATION;
    }

    /**
     * draws the particle to the passed spritebatch
     *
     * @param batch sprite batch to draw the particle to
     */
    public void draw(SpriteBatch batch) {
        int yOffset = (int)Math.pow((double)((System.currentTimeMillis() - startTime) / 8), 0.75); // calculate how far to offset the Y-Coord of the particle

        float overlaySize = 40.0f;
        batch.draw(overlay, centrePosition.x - overlaySize / 2 , centrePosition.y - overlaySize / 2 + yOffset, overlaySize, overlaySize); // draw overlay
        font.draw(batch, glyphLayout, centrePosition.x - glyphLayout.width / 2, centrePosition.y + glyphLayout.height / 2 + yOffset); // draw text
    }
}
