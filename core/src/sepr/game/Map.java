package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by Dom's Surface Mark 2 on 16/11/2017.
 */
public class Map{
    private HashMap<Integer, Sector> sectors; // mapping of sector ID to the sector object
    private HashMap<Integer, College> colleges; // mapping of a college ID to the college object
    private List<UnitChangeParticle> particles; // graphics used to display the changes to the amount of units on a sector

    private BitmapFont font; // font for rendering sector unit data
    private GlyphLayout layout = new GlyphLayout();

    private Texture troopCountOverlay = new Texture("ui/troopCountOverlay.png");
    private float overlaySize = 40.0f;

    /**
     * Performs the maps initial setup
     * Initialises the sectors as objects storing them in a HashMap
     * Initialises the sector colours storing them in a HashMap
     */
    public Map() {
        this.loadSectors();
        this.loadColleges();
        this.setupFont();

        particles = new ArrayList<UnitChangeParticle>();
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

    /**
     * converts an array of sector data to a sector object
     * @param sectorData sector data taken from the sectorProperties csv file
     * @return a sector with the properties fo the supplied data
     */
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
     * load the sector properties from the sectorProperties csv file
     */
    private void loadSectors() {
        this.sectors = new HashMap<Integer, Sector>();

        String csvFile = "sectorProperties.csv";
        String line = "";
        Integer ID = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                Sector temp = sectorDataToSector(line.split(","));
                this.sectors.put(temp.getId(), temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * conversion of String type to List<integer> for use in collegeDataToCollege
     * @param stringData
     * @return ListArray
     */
    private List<Integer> strToListInt(String stringData){
        String[] strArray = stringData.split(" ");
        List<Integer> listArray = new ArrayList<Integer>(strArray.length);
        for (int i = 0; i < listArray.size(); i++) {
            if (strArray[i].equals("")) {
                continue; //skip if sector not in college
            }
            listArray.set(i, Integer.parseInt(strArray[i]));
        }
        return listArray;
    }

    /**
     *
     * @param collegeData
     * @return College(collegeId, displayName, reinforcementAmount, sectorIds)
     */
    private College collegeDataToCollege(String[] collegeData){
        int collegeId = Integer.parseInt(collegeData[0]);
        String displayName = collegeData[1];
        int reinforcementAmount = Integer.parseInt(collegeData[2]);
        List<Integer> sectorIds = strToListInt(collegeData[3]);

        return new College(collegeId, displayName, reinforcementAmount, sectorIds);
    }

    /**
     * Accesses collegeProperties.csv to load college data
     */
    private void loadColleges(){
        this.colleges = new HashMap<Integer, College>();

        String csvFile = "collegeProperties.csv";
        String line = "";
        Integer ID = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                College temp = collegeDataToCollege(line.split(","));
                this.colleges.put(temp.getId(), temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     */
    private void setupFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 20;
        font = generator.generateFont(parameter);

        generator.dispose();
    }

    /**
     * Allocate sectors to each player in a balanced manner.
     * Just need the finished csv file so we can calculate Total reinforcements but apart from
     * that the method is finished. The method also has an if statement to catch a divide by zero
     * error in players.size(). This won't be needed as later on when more of the game implementation is
     * introduced this method will only be called when all players have been declared after the intermediate
     * setup menu.
     */
    public void allocateSectors(HashMap<Integer, Player> players) {
        if (players.size() == 0) {
            throw new RuntimeException("Cannot allocate sectors to 0 players");
        }

        PlayerType neutralAI = PlayerType.PLAYER_NEUTRAL_AI;

        for (Integer i : players.keySet()) {
            if (players.containsValue(neutralAI)){
                for (Integer j : this.getSectorIds()){
                    if (this.getSector(j).isNeutral()){
                        this.getSector(j).setOwner(players.get(i));
                    }
                }
            }
        }

        HashMap<Integer, Integer> playerReinforcements = new HashMap<Integer, Integer>(); // mapping of player id to amount of reinforcements they will receive currently
        // set all players to currently be receiving 0 reinforcements
        for (Integer i : players.keySet()) {
            playerReinforcements.put(i, 0);
        }

        int lowestReinforcementId = players.get(0).getId(); // id of player currently receiving the least reinforcements
        for (Integer i : this.getSectorIds()) {
            if (this.getSector(i).isDecor()) {
                continue; // skip allocating sector if it is a decor sector
            }
            this.getSector(i).setOwner(players.get(lowestReinforcementId));
            playerReinforcements.put(lowestReinforcementId, playerReinforcements.get(lowestReinforcementId) + this.getSector(i).getReinforcementsProvided()); // updates player reinforcements hashmap

            // find the new player with lowest reinforcements
            int minReinforcements = Collections.min(playerReinforcements.values()); // get lowest reinforcement amount
            for (Integer j : playerReinforcements.keySet()) {
                if (playerReinforcements.get(j) == minReinforcements) { // if this player has the reinforcements matching the min amount set them to the new lowest player
                    lowestReinforcementId = j;
                    break;
                }
            }
        }
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

    public void addUnitsToSectorAnimated(int sectorId, int amount) {
        //this.sectors.get(sectorId).addUnits(amount);
        this.particles.add(new UnitChangeParticle(amount, new Vector2(sectors.get(sectorId).getSectorCentreX(), sectors.get(sectorId).getSectorCentreY())));
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
     * @return Set of all SectorIds
     */
    public Set<Integer> getSectorIds() { return sectors.keySet() ; }

    /**
     *
     * @param worldX world x coord of mouse click
     * @param worldY world y coord of mouse click
     * @return id of sector that contains point or -1 if no sector contains the point or sector is decor only
     */
    public int detectSectorContainsPoint(int worldX, int worldY) {
        for (Sector sector : sectors.values()) {
            if (worldX < 0 || worldY < 0 || worldX > sector.getSectorTexture().getWidth() || worldY > sector.getSectorTexture().getHeight()) {
                return -1; // return no sector contains the point if it outside of the map bounds
            }
            int pixelValue = sector.getSectorPixmap().getPixel(worldX, worldY); // get pixel value of the point in sector image the mouse is over
            if (pixelValue != -256) { // if pixel is not transparent then it is over the sector
              if (sector.isDecor()) {
                    continue; // sector is decor so continue checking to see if a non-decor sector contains point
                } else {
                    return sector.getId(); // return id of sector which is hovered over
                }
            }
        }
        return -1;
    }

    /**
     * draws the map and the number of units in each sector and the units change particle effect
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        for (Sector sector : sectors.values()) {
            String text = sector.getUnitsInSector() + "";
            batch.draw(sector.getSectorTexture(), 0, 0);
            if (!sector.isDecor()) { // don't need to draw the amount of units on a decor sector
                layout.setText(font, text);
                batch.draw(troopCountOverlay, sector.getSectorCentreX() - overlaySize / 2 , sector.getSectorCentreY() - overlaySize / 2, overlaySize, overlaySize);
                font.draw(batch, layout, sector.getSectorCentreX() - layout.width / 2, sector.getSectorCentreY() + layout.height / 2);
            }
        }

        // render particles
        List<UnitChangeParticle> toDelete = new ArrayList<UnitChangeParticle>();
        for (UnitChangeParticle particle : particles) {
            particle.draw(batch);
            if (particle.toDelete()) {
                toDelete.add(particle);
            }
        }
        particles.removeAll(toDelete);
    }
}
