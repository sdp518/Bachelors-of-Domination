package test.java.sepr.game;
/*


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import sepr.game.*;

import java.util.Arrays;
import java.util.HashMap;

public class CollegeTest extends test.java.sepr.game.GameTest {

    @Test
    public void doesCollegeCaptureWork() throws Exception {
        Player human1 = new PlayerHuman(0, "A", Color.BLACK);
        Player human2 = new PlayerHuman(1, "B", Color.GREEN);
        Sector sector1 = new Sector(0, 0, "", null, null, "Sector 1", 0, 0, "College 1", false, new int[] {}, 0, 0, false);
        Sector sector2 = new Sector(1, 0, "", null, null, "Sector 2", 0, 0, "College 1", false, new int[] {}, 0, 0, false);
        sector1.setOwner(human1.getId());
        sector2.setOwner(human1.getId());
        College college = new College(0, "College 1", 0, Arrays.asList(0, 1));
        HashMap<Integer, Sector> sectorIds = new HashMap<Integer, Sector>();
        sectorIds.put(0, sector1);
        sectorIds.put(1, sector2);
        Assert.assertTrue("College is not owned by player 0 and should be", college.playerOwnsCollege(0, sectorIds));
        Assert.assertFalse("College is owned by player 2 and shouldn't be", college.playerOwnsCollege(1, sectorIds));
        sector1.setOwner(human2.getId());
        Assert.assertFalse("College is owned by player 1 and shouldn't be", college.playerOwnsCollege(0, sectorIds));
        Assert.assertFalse("College is owned by player 2 and shouldn't be", college.playerOwnsCollege(1, sectorIds));
        sector2.setOwner(human2.getId());
        Assert.assertFalse("College is owned by player 1 and shouldn't be", college.playerOwnsCollege(0, sectorIds));
        Assert.assertTrue("College is not owned by player 2 and should be", college.playerOwnsCollege(1, sectorIds));
    }

    @Test
    public void adjacentSectors() throws Exception {
        Sector s1 = new Sector(0, 0, "", null, null, "Sector 1", 0, 0, "College 1", false, new int[] { 1, 2 }, 0, 0, false);
        Sector s2 = new Sector(1, 0, "", null, null, "Sector 2", 0, 0, "College 1", false, new int[] { 0 }, 0, 0, false);
        Sector s3 = new Sector(2, 0, "", null, null, "Sector 3", 0, 0, "College 1", false, new int[] { 0 }, 0, 0, false);
        Assert.assertTrue("S1 should be adjacent to S2 and S3", s1.isAdjacentTo(s2) && s1.isAdjacentTo(s3));
        Assert.assertTrue("S2 should be adjacent to S1 and not S3", s2.isAdjacentTo(s1) && !s2.isAdjacentTo(s3));
        Assert.assertTrue("S3 should be adjacent to S1 and not S2", s3.isAdjacentTo(s1) && !s3.isAdjacentTo(s2));
    }

    @Test
    public void mapLoad() throws Exception {
        Map map = new Map();

    }

}*/