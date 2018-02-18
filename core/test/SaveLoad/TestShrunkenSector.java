package SaveLoad;

import org.junit.Before;
import org.junit.Test;

import sepr.game.Sector;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class TestShrunkenSector {
    private HashMap<Integer, Sector> sectors;
    private Integer[] keys;
    private HashMap<Integer, ShrunkenSector> smallSectors;

    @Before
    public void initAll() {
        this.sectors = loadSectors();
        this.keys = sectors.keySet().toArray(new Integer[sectors.size()]);
        this.smallSectors = new HashMap<Integer, ShrunkenSector>();
        for (int i = 0; i < keys.length; i++) {
            this.smallSectors.put(keys[i], new ShrunkenSector(sectors.get(keys[i])));
        }
    }

    @Test
    public void testContructor() {
        for (int i = 0; i < keys.length; i++) {
            assertEquals(sectors.get(keys[i]).getOwnerId(), smallSectors.get(keys[i]).getOwnerId());
            assertEquals(sectors.get(keys[i]).getCollege(), smallSectors.get(keys[i]).getCollege());
            assertEquals(sectors.get(keys[i]).getDisplayName(), smallSectors.get(keys[i]).getDisplayName());
            assertEquals(sectors.get(keys[i]).getReinforcementsProvided(), smallSectors.get(keys[i]).getReinforcementsProvided());
            assertEquals(sectors.get(keys[i]).getUnitsInSector(), smallSectors.get(keys[i]).getUnitsInSector());
            assertEquals(sectors.get(keys[i]).isNeutral(), smallSectors.get(keys[i]).isNeutral());
        }
    }

    @Test
    public void testSectorReconstruction() {
        for(int i = 0; i < sectors.size(); i++) {
            Sector fullSector = sectors.get(keys[i]);
            ShrunkenSector smallSector = this.smallSectors.get(keys[i]);
            fullSector.setOwnerId(smallSector.getOwnerId());
            fullSector.setDisplayName(smallSector.getDisplayName());
            fullSector.setUnitsInSector(smallSector.getUnitsInSector());
            fullSector.setReinforcementsProvided(smallSector.getReinforcementsProvided());
            fullSector.setCollege(smallSector.getCollege());
            fullSector.setNeutral(smallSector.isNeutral());

            assertEquals(fullSector.getOwnerId(), sectors.get(keys[i]).getOwnerId());
            assertEquals(fullSector.getCollege(), sectors.get(keys[i]).getCollege());
            assertEquals(fullSector.getDisplayName(), sectors.get(keys[i]).getDisplayName());
            assertEquals(fullSector.getReinforcementsProvided(), sectors.get(keys[i]).getReinforcementsProvided());
            assertEquals(fullSector.getUnitsInSector(), sectors.get(keys[i]).getUnitsInSector());
            assertEquals(fullSector.isNeutral(), sectors.get(keys[i]).isNeutral());
        }
    }

    public static HashMap<Integer,Sector> loadSectors() {
        HashMap<Integer, Sector> sectors = new HashMap<Integer, Sector>();
        Path currentRelativePath = Paths.get("");
        String currentWorkingDir = currentRelativePath.toAbsolutePath().toString();
        String csvFile = currentWorkingDir + "\\assets\\mapData\\sectorProperties.csv";
        String line = "";
        Integer ID = 0;
        Random random = new Random();
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] sectorData = line.split(",");
                Sector temp = new Sector (Integer.parseInt(sectorData[0]),
                            -1,
                            currentWorkingDir + "\\assets\\mapData\\" + sectorData[1],
                            null, //Can't initialize Texture without OpenGL
                        null, //Can't initialize Pixmap without OpenGL
                            sectorData[2],
                            1 + random.nextInt(3),
                            Integer.parseInt(sectorData[4]),
                            sectorData[5],
                            Boolean.parseBoolean(sectorData[6]),
                            strToIntArray(sectorData[7]),
                            Integer.parseInt(sectorData[8]),
                            Integer.parseInt(sectorData[9]),
                            Boolean.parseBoolean(sectorData[10]));
                sectors.put(temp.getId(), temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // csv file no present
        } catch (IOException e) {
            e.printStackTrace(); // error occurred whilst reading the file
        }
        return sectors;
    }

    private static int[] strToIntArray(String stringData) {
        String[] strArray = stringData.split(" ");
        int[] intArray = new int[strArray.length];
        for (int i = 0; i < intArray.length; i++) {
            if (strArray[i].equals("")) {
                continue; // skip if string is empty
            }
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        return intArray;
    }
}
