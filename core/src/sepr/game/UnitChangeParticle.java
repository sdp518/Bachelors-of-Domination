package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

public class UnitChangeParticle {
    private final long DISPLAY_DURATION = 1000000000;

    private String amount;
    private GlyphLayout glyphLayout;
    private Vector2 centrePosition;
    private long startTime;
    private BitmapFont font;
    private float overlaySize = 40.0f;
    private Texture overlay;

    public UnitChangeParticle(int amount, Vector2 centrePosition) {
        this.centrePosition = centrePosition;
        this.startTime = System.nanoTime();
        overlay = new Texture("ui/troopCountOverlay.png");

        setupFont();
        if (amount > 0) {
            this.font.setColor(Color.GREEN);
            this.amount = "+" + amount;
        } else {
            this.font.setColor(Color.RED);
            this.amount = "" + amount;
        }
        this.glyphLayout = new GlyphLayout(font, this.amount);
    }

    private void setupFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        font = generator.generateFont(parameter);

        generator.dispose();
    }

    /**
     * returns true if this particle has existed for too long and should be deleted
     * @return if this particle should be deleted
     */
    public boolean toDelete() {
        return System.nanoTime() - startTime > DISPLAY_DURATION;
    }

    public void draw(SpriteBatch batch) {
        int yOffset = (int)Math.pow((double)((System.nanoTime() - startTime) / 8000000), 0.75);
        batch.draw(overlay, centrePosition.x - overlaySize / 2 , centrePosition.y - overlaySize / 2 + yOffset, overlaySize, overlaySize);
        font.draw(batch, glyphLayout, centrePosition.x - glyphLayout.width / 2, centrePosition.y + glyphLayout.height / 2 + yOffset);
    }
}
