package org.sam;
import org.powbot.api.Area;
import org.powbot.api.Tile;

import java.util.ArrayList;

public class Constants {

    public Constants() {
        super();
    }

    public ArrayList<String> userTaskList = new ArrayList<String>();

//  Traveling to FOSSIL Island from the Barge
    public static Area BARGE_BOAT_ENTRANCE = new Area(new Tile(3358, 3446, 0), new Tile(3364, 3443, 0));
    public static final String BARGE_GUARD = "Barge guard";
//    Quick-Travel
    public static Area GLIDER_AREA = new Area(new Tile(3318, 3434, 0), new Tile(3327, 3424, 0));
    public static final String ROYAL_SEED_POD = "Royal seed pod";
//    Commune
    public static final String LADDER = "Ladder";
//    Climb-up, wait for animation, Climb-up, wait for animation, Climb-up, wait for animation,
    public static final String GNOME = "Captain Errdo";
//    Glider
    public static final String GNOME_FLY_LOCATION = "Lemanto Andra";

//

//  Traveling to the
//    Keep trying to access to boat until user is new location. (since crab aggros)
    public static final String ROW_OPTION = "Row out to sea.";
    public static Area NORTHERN_ROW_BOAT = new Area(new Tile(3730, 3896, 0), new Tile(3736, 3891, 0));
    public static Area SANDY_BEACH_TO_ROWBOAT = new Area(new Tile(3714, 3880, 0), new Tile(3719, 3876, 0));
    public static Area SANDY_BEACH_NEAR_FARMING_PATCH = new Area(new Tile(3719, 3844, 0), new Tile(3723, 3841, 0));
    public static Area FOSSIL_ISLAND_CAMP = new Area(new Tile(3735, 3816, 0), new Tile(3739, 3811, 0));
    public static final String FOSSIL_ISLAND_BANK = "Bank chest";

//  ISLAND CONSTNATS
    public static final String ISLAND_BANK_CHEST = "Bank Chest-wreck";
    public static final String ISLAND_ROWBOAT = "Rowboat";
//    Dive, unequip main hand and off hand if there
    public static Area ON_ISLAND = new Area(new Tile(3768,3990,0), new Tile(3771,3897,0));

//  Underwater CONSTANTS
    public static final String ANCHOR_ROPE = "Anchor rope";
//    Climb
    public static Area UNDER_WATER_NEXT_TO_ANCHOR = new Area(new Tile(3737, 10277, 1), new Tile(3725, 10283, 1));
    public static Area UNDER_WATER_NEXT_TO_TUNNEL = new Area(new Tile(3730, 10292, 1), new Tile(3728, 10294, 1));
    public static final String TUNNEL = "Tunnel";

//    Enter Tunnel

    public static final String TUNNEL_ENTRANCE_NPC = "Ceto";
//    Pay, check if Numulite in inventory if not already withdrawn from the bank
//    Tap here to continue, option(0, or 1), Okay, here's 200 numulites., Tap here to continue, (auto enters area)



    public static Area OUTSIDE_ENTRANCE = new Area(new Tile(0,0,0), new Tile(0,0,0));
    public static Area DRIFT_ENTRANCE = new Area(new Tile(11473,1440,1), new Tile(11479,1443,1));
    public static Area DRIFT_NET_AREA = new Area(new Tile(0,0,0), new Tile(0,0,0));
    public static Area SOUTH_NET = new Area(new Tile(13598, 15452, 0), new Tile(13595, 15448, 0));
    public static Area EAST_NET = new Area(new Tile(13599, 15457, 0), new Tile(13605, 15461, 0));
    public static Area STALE_AREA = new Area(new Tile(13598, 15460, 0), new Tile(13593, 15453, 0));
    public static Area SOUTH_NET_COLLECTION = new Area(new Tile(13598, 15452, 0), new Tile(13598, 15448, 0));
    public static Area EAST_NET_COLLECTION = new Area(new Tile(13602, 15457, 0), new Tile(13605, 15457, 0));
    public static final String SEAWEEDSPORE = "Seaweed spore";
    public static final String NET_NAME = "Drift net";
    public static final String PLANT_DOOR = "Plant door";
//    Navigate to begin the minigame

// ITEMS
    public static final String FISHBOWL_HELMET = "Fishbowl helmet";
    public static final String DIVING_APPARATUS = "Diving apparatus";
    public static final String FLIPPERS = "Flippers";
    public static final String STAMINA_FOUR = "Stamina potion(4)";
    public static final String STAMINA_THREE = "Stamina potion(3)";
    public static final String STAMINA_TWO = "Stamina potion(2)";
    public static final String STAMINA_ONE = "Stamina potion(1)";
    public static final String NUMULITE = "Numulite";
}