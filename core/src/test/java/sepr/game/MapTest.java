package test.java.sepr.game;
/*
import com.badlogic.gdx.graphics.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sepr.game.*;

import java.util.HashMap;

/**
 * Created by jackr on 17/01/2018.
 *//*
public class MapTest extends GameTest{

    private HashMap<Integer, Player> players;
    private Map map;

    @Before
    public void setupMap(){
        Player p1 = new PlayerHuman(0, GameSetupScreen.CollegeName.ALCUIN, Color.BLACK, "Player 1");
        Player p2 = new PlayerHuman(1, GameSetupScreen.CollegeName.WENTWORTH, Color.RED, "Player 2");
        players = new HashMap<Integer, Player>();
        players.put(0, p1);
        players.put(1, p2);
        map = new Map(players);
    }

    @Test
    public void areAllSectorsAllocated() throws Exception {
        map.allocateSectors(players);
        for (Integer i : map.getSectorIds()){
            Sector s = map.getSector(i);
            Assert.assertTrue("Sector " + s.getDisplayName() + " is not allocated and should be", s.isAllocated() || s.isDecor());
        }
    }


}*/
