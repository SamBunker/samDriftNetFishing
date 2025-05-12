package org.sam;
import org.powbot.api.Area;
import org.powbot.api.Tile;

import java.util.ArrayList;

public class Constants {

    public Constants() {
        super();
    }

    public ArrayList<String> userTaskList = new ArrayList<String>();

    public static Area BUGGED_AREA = new Area(new Tile(0,0,0), new Tile(0,0,0));
    public static Area DRIFT_NET_AREA = new Area(new Tile(0,0,0), new Tile(0,0,0));
    public static Area SOUTH_NET = new Area(new Tile(13598, 15452, 0), new Tile(13595, 15448, 0));
    public static Area EAST_NET = new Area(new Tile(13599, 15457, 0), new Tile(13605, 15461, 0));
    public static Area STALE_AREA = new Area(new Tile(13598, 15460, 0), new Tile(13593, 15453, 0));
    public static Area SOUTH_NET_COLLECTION = new Area(new Tile(13598, 15452, 0), new Tile(13598, 15448, 0));
    public static Area EAST_NET_COLLECTION = new Area(new Tile(13602, 15457, 0), new Tile(13605, 15457, 0));
    public static final String SeaweedSpore = "Seaweed spore";
    public static final String NET_NAME = "Drift net";
}