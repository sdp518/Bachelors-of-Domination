package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Dom's Surface Mark 2 on 16/11/2017.
 */
public class Map{
    private HashMap<Integer, Sector> sectors; // mapping of sector ID to the sector object
    private HashMap<Integer, College> colleges; // mapping of a college ID to the college object
    private HashMap<String, Color> colors; // mapping of color name to color ***NOT QUITE TRUE***

    private BitmapFont font;
    private GlyphLayout layout = new GlyphLayout();

    private Texture troopCountOverlay = new Texture("ui/troopCountOverlay.png");
    private float overlaySize = 40.0f;

    Color changeGreen = new Color(0.5f, 0, 1f, 0f);
    Color changeBlue = new Color(0.8f, 0.5f, 0f, 0f);
    Color changeWhite =  new Color(0,0,0,0);

    /**
     * Performs the maps initial setup
     * Initialises the sectors as objects storing them in a HashMap
     * Initialises the sector colours storing them in a HashMap
     */
    public Map() {
        this.sectors = new HashMap<Integer, Sector>();
        this.colleges = new HashMap<Integer, College>();

        this.sectors = new HashMap<Integer, Sector>();

        String csvFile = "sectorProperties.csv";
        String line = "";
        Integer ID = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("sectorProperties.csv"));
            while ((line = br.readLine()) != null) {
                Sector temp = sectorDataToSector(line.split(","));
                this.sectors.put(temp.getId(), temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Colleges
        this.colleges.put(0, new College(0, "Hes East", 0, Arrays.asList(0, 1, 2, 3)));
        this.colleges.put(1, new College(0, "Halifax", 0, Arrays.asList(4,5,6,7)));
        this.colleges.put(2, new College(0, "Derwent", 0, Arrays.asList(8,9,10,11)));
        this.colleges.put(3, new College(0, "Alcuin", 0, Arrays.asList(12,13,14)));
        this.colleges.put(4, new College(0, "Vanbrugh", 0, Arrays.asList(18,19,20)));
        this.colleges.put(5, new College(0, "Wentworth", 0, Arrays.asList(22,23)));
        this.colleges.put(6, new College(0, "James", 0, Arrays.asList(24,25,26,27)));
        this.colleges.put(7, new College(0, "Neutral", 0, Arrays.asList(15,16,17,28,29,30)));


        this.colors = new HashMap<String, Color>();
        this.colors.put("green", changeGreen);
        this.colors.put("blue", changeBlue);
        this.colors.put("white", changeWhite);


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 20;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    /**
     *
     * @param stringData
     * @return
     */
    private int[] strToIntArray(String stringData) {
        String[] strArray = stringData.split(" ");
        int[] intArray = new int[strArray.length];
        for (int i = 0; i < intArray.length; i++) {
            if (strArray[i].equals("")) {
                continue; // skip if no adjacent sectors
            }
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        return intArray;
    }

    private Sector sectorDataToSector(String[] sectorData) {
        int sectorId = Integer.parseInt(sectorData[0]);
        int ownerId = -1;
        String filename = sectorData[1];
        Texture sectorTexture = new Texture(sectorData[1]);
        Pixmap sectorPixmap = new Pixmap(Gdx.files.internal(sectorData[1]));
        String displayName = sectorData[2];
        int unitsInSector = Integer.parseInt(sectorData[3]);
        int reinforcementsProvided = Integer.parseInt(sectorData[4]);
        String college = sectorData[5];
        boolean neutral = Boolean.getBoolean(sectorData[6]);
        int[] adjacentSectors = strToIntArray(sectorData[7]);
        int sectorX = Integer.parseInt(sectorData[8]);
        int sectorY = Integer.parseInt(sectorData[9]);
        boolean decor = Boolean.parseBoolean(sectorData[10]);

        return new Sector(sectorId, ownerId, filename, sectorTexture, sectorPixmap, displayName, unitsInSector, reinforcementsProvided, college, neutral, adjacentSectors, sectorX, sectorY, decor);
    }

    /**
     * Checks to see if there is one player who controls every sector
     * @return -1 if there is no winner or the ID of the player that controls all the sectors
     */
    public int checkForWinner() {
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
    public int calculateReinforcementAmount(int playerId) {
        int count = 0;
        for (Sector s : sectors.values()){
            // Checks whether the tile is able to be captured and just captured that turn
            if (!s.isDecor() && s.justCapturedBy(playerId)) {
                count += s.getReinforcementsProvided();
                s.updateOwnerId();
            }
        }
        // Checks all the colleges to see if the player owns any
        for (College c : colleges.values())
            if (c.playerOwnsCollege(playerId, sectors))
                count += c.getReinforcementAmount();
        return count;
    }

  /**
     *
     * @param sectorId id of the desired sector
     * @return Sector object with the corresponding id in hashmap sectors if no sector matches with the supplied id then null is returned
     */
    public Sector getSector(int sectorId) {
        if (sectors.containsKey(sectorId)) {
            return sectors.get(sectorId);
        } else {
            return null;
        }
    }

    /**
     * @return Integer value of total number of sectors in sectors HashMap
     */
    public int getNumOfSectors() { return sectors.values().size(); }

    /**
     * @return Set of all SectorIds
     */
    public Set<Integer> getSectorIds() { return sectors.keySet() ; }

    /**
     * @param sectorId Id of sector
     * @param player sets owner of sector as Player object
     */
    public void setSectorOwner(int sectorId, Player player) { sectors.get(sectorId).setOwner(player); }

    /**
     *
     * @param worldX world x coord of mouse click
     * @param worldY world y coord of mouse click
     * @return id of sector that was clicked on or -1 if no sector was clicked or the clicked sector is decor only
     */
    public int detectSectorContainsPoint(int worldX, int worldY) {
        for (Sector sector : sectors.values()) {
            if (worldX < 0 || worldY < 0 || worldX > sector.getSectorTexture().getWidth() || worldY > sector.getSectorTexture().getHeight()) {
                continue;
            }
            int pixelValue = sector.getSectorPixmap().getPixel(worldX, worldY);
            if (pixelValue != -256) {
              if (sector.isDecor()) {
                    continue; // sector clicked is decor so continue checking to see if a non-decor sector was clicked
                } else {
                    return sector.getId();
                }
            }
        }
        return -1;
    }
  
    public void draw(SpriteBatch batch) {
        for (Sector sector : sectors.values()) {
            String text = sector.getUnitsInSector() + "";
            layout.setText(font, text);
            batch.draw(sector.getSectorTexture(), 0, 0);
            batch.draw(troopCountOverlay, sector.getSectorCentreX() - overlaySize / 2, sector.getSectorCentreY() - overlaySize / 2, overlaySize, overlaySize);
            font.draw(batch, layout, sector.getSectorCentreX() - layout.width / 2, sector.getSectorCentreY() + layout.height / 2);
        }
        //renderSectorUnitData(batch);
    }
}
