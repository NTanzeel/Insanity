package server;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import server.model.players.Player;
import server.model.players.PlayerHandler;
import server.model.players.PlayerSave;
import server.net.ConnectionHandler;
import server.net.ConnectionThrottleFilter;
import server.util.SimpleTimer;
import server.util.log.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.DecimalFormat;

/**
 * Server.java
 *
 * @author Sanity
 * @author Graham
 * @author Blake
 * @author Ryan Lmctruck30
 */

public class Server {

    private static final int cycleRate;
    public static boolean UpdateServer = false;
    public static PlayerHandler playerHandler = new PlayerHandler();
    private static long lastMassSave = System.currentTimeMillis();
    private static SimpleTimer engineTimer, debugTimer;
    private static long cycles;
    private static long totalCycleTime;
    private static long sleepTime;
    private static DecimalFormat debugPercentFormat;
    private static boolean shutdownServer = false;
    private static int garbageCollectDelay = 40;
    private static int serverlistenerPort;
    private static boolean playerExecuted = false;

    static {
        if (!Config.SERVER_DEBUG) {
            serverlistenerPort = 43594;
        } else {
            serverlistenerPort = 43594;
        }
        cycleRate = 600;
        shutdownServer = false;
        engineTimer = new SimpleTimer();
        debugTimer = new SimpleTimer();
        sleepTime = 0;
        debugPercentFormat = new DecimalFormat("0.0#%");
    }

    public static void main(java.lang.String args[]) throws NullPointerException, IOException {
        /*
          Starting Up Server
         */

        System.setOut(new Logger(System.out));
        System.setErr(new Logger(System.err));
        System.out.println("Launching Project Insanity...");

        /*
          Accepting Connections
         */
        IoAcceptor acceptor = new SocketAcceptor();
        ConnectionHandler connectionHandler = new ConnectionHandler();

        SocketAcceptorConfig sac = new SocketAcceptorConfig();
        sac.getSessionConfig().setTcpNoDelay(false);
        sac.setReuseAddress(true);
        sac.setBacklog(100);

        ConnectionThrottleFilter throttleFilter = new ConnectionThrottleFilter(Config.CONNECTION_DELAY);
        sac.getFilterChain().addFirst("throttleFilter", throttleFilter);
        acceptor.bind(new InetSocketAddress(serverlistenerPort), connectionHandler, sac);

        /*
          Initialise Handlers
         */
        Connection.initialize();

        /*
          Server Successfully Loaded
         */
        System.out.println("Server listening on port 0.0.0.0:" + serverlistenerPort);
        /*
          Main Server Tick
         */
        try {
            while (!Server.shutdownServer) {
                if (sleepTime >= 0) Thread.sleep(sleepTime);
                else Thread.sleep(600);
                engineTimer.reset();
                playerHandler.process();
                long cycleTime = engineTimer.elapsed();
                sleepTime = cycleRate - cycleTime;
                totalCycleTime += cycleTime;
                cycles++;
                debug();
                garbageCollectDelay--;
                if (garbageCollectDelay == 0) {
                    garbageCollectDelay = 40;
                    System.gc();
                }
                if (System.currentTimeMillis() - lastMassSave > 300000) {
                    for (Player p : PlayerHandler.players) {
                        if (p == null) continue;
                        PlayerSave.saveGame((Player) p);
                        System.out.println("Saved game for " + p.playerName + ".");
                        lastMassSave = System.currentTimeMillis();
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("A fatal exception has been thrown!");
            for (Player p : PlayerHandler.players) {
                if (p == null) continue;
                PlayerSave.saveGame((Player) p);
                System.out.println("Saved game for " + p.playerName + ".");
            }
        }
        System.exit(0);
    }

    private static void debug() {
        if (debugTimer.elapsed() > 360 * 1000 || playerExecuted) {
            long averageCycleTime = totalCycleTime / cycles;
            System.out.println("Average Cycle Time: " + averageCycleTime + "ms");
            double engineLoad = ((double) averageCycleTime / (double) cycleRate);
            System.out.println("Players online: " + PlayerHandler.playerCount + ", engine load: " + debugPercentFormat.format(engineLoad));
            totalCycleTime = 0;
            cycles = 0;
            System.gc();
            System.runFinalization();
            debugTimer.reset();
            playerExecuted = false;
        }
    }

    public static long getSleepTimer() {
        return sleepTime;
    }

}
