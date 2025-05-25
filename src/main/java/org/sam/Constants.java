package org.sam;
import org.powbot.api.Area;
import org.powbot.api.Tile;

import java.util.ArrayList;

public class Constants {

    public Constants() {
        super();
    }

    public ArrayList<String> userTaskList = new ArrayList<String>();

//  ISLAND CONSTNATS
    public static final String ISLAND_BANK_CHEST = "Bank Chest-wreck";
    public static final String ISLAND_ROWBOAT = "Rowboat";
//    Dive, unequip main hand and off hand if there
    public static Area ON_ISLAND = new Area(new Tile(3768,3990,0), new Tile(3771,3897,0));

//  Underwater CONSTANTS
    public static final Integer TUNNEL = 31845;

//    Enter Tunnel

    public static final Integer NPC_CETO = 7783;
    public static final Integer NPC_ANNETTE = 31843;
//    Pay, check if Numulite in inventory if not already withdrawn from the bank
//    Tap here to continue, option(0, or 1), Okay, here's 200 numulites., Tap here to continue, (auto enters area)

    public static Tile ROWBOAT_LOCATION = new Tile(3724, 3808, 0);
    public static Tile DRIFT_ENTRANCE_TILE = new Tile(3729, 10294, 1);
    public static Area DRIFT_ENTRANCE_CHECKPOINT_ONE = new Area(new Tile(3730, 10287, 1), new Tile(3731, 10310, 1));
    public static Area BARGE_LOCATION = new Area(new Tile (3358,3446,0), new Tile(3363,3444, 0));
    public static Area UNDERWATER = new Area(new Tile(3726, 10261, 1), new Tile(3752, 10320, 1));
    public static Area IN_GAME = new Area(new Tile(5000, 1000, 1), new Tile(25000, 30000, 1));

    public static final String SEAWEEDSPORE = "Seaweed spore";
    public static final String NET_NAME = "Drift net";

    public static final Integer DRIFT_NET_FISHING_WIDGET = 607;
    public static final Integer DRIFT_NET_COLLECTION_WIDGET = 607;

// ITEMS
    public static final String FISHBOWL_HELMET = "Fishbowl helmet";
    public static final String DIVING_APPARATUS = "Diving apparatus";
    public static final String FLIPPERS = "Flippers";
    public static final String STAMINA_FOUR = "Stamina potion(4)";
    public static final String STAMINA_THREE = "Stamina potion(3)";
    public static final String STAMINA_TWO = "Stamina potion(2)";
    public static final String STAMINA_ONE = "Stamina potion(1)";
    public static final String NUMULITE = "Numulite";
    public static final String DRIFT_NET = "Drift net";

    public static final Integer DRIFT_NET_EMPTY = 30952;
    public static final Integer DRIFT_NET_NET = 30953;
    public static final Integer DRIFT_NET_AND_FSH = 30954;
    public static final Integer DRIFT_NET_FULL = 30955;
    public static final Integer FISH_SHOAL = 7782;
}