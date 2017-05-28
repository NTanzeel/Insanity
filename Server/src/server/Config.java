package server;

public class Config {

    public static final boolean SERVER_DEBUG = false;
    public static final String SERVER_NAME = "Project Insanity";

    public static final int BANK_SIZE = 352;
    public static final int MAX_PLAYERS = 1024;
    public static final int CONNECTION_DELAY = 100;
    public static final int IPS_ALLOWED = 3; // how many ips are allowed
    public static final boolean WORLD_LIST_FIX = false;
    public static final int START_LOCATION_X = 3087; // start here
    public static final int START_LOCATION_Y = 3500;
    public static final int RESPAWN_X = 3087; // when dead respawn here
    public static final int RESPAWN_Y = 3500;
    public static final int DUELING_RESPAWN_X = 3362;
    public static final int DUELING_RESPAWN_Y = 3263;
    public static final int RANDOM_DUELING_RESPAWN = 5;
    public static final int NO_TELEPORT_WILD_LEVEL = 20;
    public static final double SERVER_EXP_BONUS = 1.5;
    public static final int INCREASE_SPECIAL_AMOUNT = 17500;
    public static final int SAVE_TIMER = 120; // save every 1 minute
    public static final int TIMEOUT = 20;
    public static final int CYCLE_TIME = 600;
    public static final int BUFFER_SIZE = 10000;

}
