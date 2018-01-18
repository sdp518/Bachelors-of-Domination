package sepr.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * stores the game map and the sectors within it
 */
public class Map{
    private HashMap<Integer, Sector> sectors; // mapping of sector ID to the sector object
    private HashMap<Integer, College> colleges; // mapping of a college ID to the college object
    private List<UnitChangeParticle> particles; // graphics used to display the changes to the amount of units on a sector

    private BitmapFont font; // font for rendering sector unit data
    private GlyphLayout layout = new GlyphLayout();

    private Texture troopCountOverlay = new Texture("uiComponents/troopCountOverlay.png");
    private float overlaySize = 40.0f;

    /**
     * Performs the maps initial setup
     * Initialises the sectors as objects storing them in a HashMap
     * Initialises the sector colours storing them in a HashMap
     */
    public Map(HashMap<Integer, Player> players) {
        this.loadSectors();
        this.loadColleges();
        font = WidgetFactory.getFontSmall();

        particles = new ArrayList<UnitChangeParticle>();
        this.allocateSectors(players);
    }

    /**
     *
     * @param stringData space separated integers e.g. '1 2 3 4 5'
     * @return the integers in the data in an array
     */
    private int[] strToIntArray(String stringData) {
        String[] strArray = stringData.split(" ");
        int[] intArray = new int[strArray.length];
        for (int i = 0; i < intArray.length; i++) {
            if (strArray[i].equals("")) {
                continue; // skip if no values in array
            }
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        return intArray;
    }

    /**
     * load the sector properties from the sectorProperties csv file
     */
    private void loadSectors() {
        this.sectors = new HashMap<Integer, Sector>();

        String csvFile = "mapData/sectorProperties.csv";
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
     * converts an array of sector data to a sector object
     * @param sectorData sector data taken from the sectorProperties csv file
     * @return a sector with the properties fo the supplied data
     */
    private Sector sectorDataToSector(String[] sectorData) {
        int sectorId = Integer.parseInt(sectorData[0]);
        int ownerId = -1;
        String filename = "mapData/" + sectorData[1];
        Texture sectorTexture = new Texture("mapData/" + sectorData[1]);
        Pixmap sectorPixmap = new Pixmap(Gdx.files.internal("mapData/" + sectorData[1]));
        String displayName = sectorData[2];
        int unitsInSector = Integer.parseInt(sectorData[3]);
        int reinforcementsProvided = Integer.parseInt(sectorData[4]);
        String college = sectorData[5];
        boolean neutral = Boolean.parseBoolean(sectorData[6]);
        int[] adjacentSectors = strToIntArray(sectorData[7]);
        int sectorX = Integer.parseInt(sectorData[8]);
        int sectorY = Integer.parseInt(sectorData[9]);
        boolean decor = Boolean.parseBoolean(sectorData[10]);

        return new Sector(sectorId, ownerId, filename, sectorTexture, sectorPixmap, displayName, unitsInSector, reinforcementsProvided, college, neutral, adjacentSectors, sectorX, sectorY, decor);
    }

    /**
     * Accesses collegeProperties.csv to load college data
     */
    private void loadColleges(){
        this.colleges = new HashMap<Integer, College>();

        String csvFile = "mapData/collegeProperties.csv";
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
     * @param collegeData a string array containing the properties of a college in the format <College name> <Bonus> <Sector IDs in college>
     * @return College(collegeId, displayName, reinforcementAmount, sectorIds)
     */
    private College collegeDataToCollege(String[] collegeData){
        int collegeId = Integer.parseInt(collegeData[0]);
        String displayName = collegeData[1];
        int reinforcementAmount = Integer.parseInt(collegeData[2]);
        int[] sectorIds = strToIntArray(collegeData[3]);

        return new College(collegeId, displayName, reinforcementAmount, sectorIds);
    }

    /**
     * allocates sectors in the map to the players in a semi-random fashion
     * if there is a neutral player then the default neutral sectors are allocated to them
     * @param players the sectors are to be allocated to
     * @throws RuntimeException if the players hashmap is empty
     */
    public void allocateSectors(HashMap<Integer, Player> players) {
        if (players.size() == 0) {
            throw new RuntimeException("Cannot allocate sectors to 0 players");
        }

        // search for neutral player
        int neutralPlayerId = -1;
        for (Player player : players.values()) {
            if (player.getPlayerType().equals(PlayerType.NEUTRAL_AI)) {
                neutralPlayerId = player.getId();
                break;
            }
        }

        // set any default neutral sectors to the neutral player
        if (neutralPlayerId != -1) {
            for (Sector sector : sectors.values()) {
                if (sector.isNeutral()  && !sector.isDecor()) {
                    sector.setOwner(players.get(neutralPlayerId));
                }
            }
        }

        HashMap<Integer, Integer> playerReinforcements = new HashMap<Integer, Integer>(); // mapping of player id to amount of reinforcements they will receive currently
        // set all players to currently be receiving 0 reinforcements
        for (Integer i : players.keySet()) {
            if (i != neutralPlayerId) playerReinforcements.put(i, 0);
        }

        int lowestReinforcementId = players.get(0).getId(); // id of player currently receiving the least reinforcements
        List<Integer> sectorIdsRandOrder = new ArrayList<Integer>(getSectorIds());
        Collections.shuffle(sectorIdsRandOrder);

        for (Integer i : sectorIdsRandOrder) {
            if (!sectors.get(i).isAllocated()) {
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
    }

    /**
     * Checks to see if there is one player who controls every sector
     * @return -1 if there is no winner or the ID of the player that controls all the sectors
     */
    public int checkForWinner() {
        Set<Integer> owners = new HashSet<Integer>();
        int lastOwner = -1;
        for (Sector sector : sectors.values()) {
            if (!sector.isDecor()) { // ignore decor sectors
                owners.add(sector.getOwnerId()); // add the owner of the sector to the set of owners
                lastOwner = sector.getOwnerId(); // keep track of the last owner, will be returned if it is the only one
            }
        }
        if (owners.size() > 1) {
            return -1;
        } else {
            return lastOwner;
        }
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

    public void attackSector(int attackingSectorId, int defendingSectorId, int attackersLost, int defendersLost, Player attacker, Player defender, Player neutral, Stage stage) {
        addUnitsToSectorAnimated(attackingSectorId, attackersLost);
        addUnitsToSectorAnimated(defendingSectorId, defendersLost);

        if (sectors.get(attackingSectorId).getUnitsInSector() == 0) { // attacker lost all troops
            DialogFactory.sectorOwnerChangeDialog(attacker.getPlayerName(), neutral.getPlayerName(), sectors.get(attackingSectorId).getDisplayName(), stage);
            if (sectors.get(defendingSectorId).getUnitsInSector() == 0) { // both players wiped each other out
                DialogFactory.sectorOwnerChangeDialog(defender.getPlayerName(), neutral.getPlayerName(), sectors.get(attackingSectorId).getDisplayName(), stage);
            }
        } else if (sectors.get(defendingSectorId).getUnitsInSector() == 0 && sectors.get(attackingSectorId).getUnitsInSector() > 1) { // territory conquered
            DialogFactory.attackSuccessDialogBox(sectors.get(defendingSectorId).getReinforcementsProvided(), sectors.get(attackingSectorId).getUnitsInSector() - 1, stage);
        } else if (sectors.get(defendingSectorId).getUnitsInSector() == 0 && sectors.get(attackingSectorId).getUnitsInSector() == 1) { // territory conquered but only one attacker remaining

        }



    }

    private void addUnitsToSectorAnimated(int sectorId, int amount) {
        this.sectors.get(sectorId).addUnits(amount);
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
