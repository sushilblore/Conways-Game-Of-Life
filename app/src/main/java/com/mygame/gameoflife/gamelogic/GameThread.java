package com.mygame.gameoflife.gamelogic;

import com.mygame.gameoflife.gameoflife.GlobalContext;
import com.mygame.gameoflife.util.GameStates;
import com.mygame.gameoflife.util.Utils;

/**
 * Created by sushil.jha on 27-04-2015.
 */
public class GameThread implements Runnable {

    private final GlobalContext mContext;

    public final static long MIN_TICK_TIME = 200;

    //Game thread
    private Thread mThread;
    private long lastUpdate;

    public GameThread(GlobalContext context) {
        mContext = context;
    }

    @Override
    public void run() {
        //Utils.debug(this, "Starting game loop");
        if (Thread.interrupted()) {
            return;
        }
        while (mContext.getGameState() != GameStates.STOPPED) {

            while (mContext.getGameState() == GameStates.PAUSED) {
                Utils.sleep(100);
            }

            update();
        }

        Utils.debug(this, "Stopping game loop");
    }

    /**
     * Gives the spark to the main loop.
     * @see #run()
     */
    public void start() {
        Utils.debug(this, "start() of GameThread");
        if (mThread != null) {
            mContext.setGameState(GameStates.RUNNING);
        }
        Utils.debug(this, "Starting game loop thread");
        mThread = new Thread(this);
        mThread.start();
    }

    public void stopThread() {
        if (null != mThread) {
            mThread.interrupt();
            mThread = null;
        }
    }

    private void update() {
        //Utils.debug(this, "update() of GameThread");
        try {
            mContext.getGameLogic().tick();

            mContext.getGridView().mUpdateHandler.sendEmptyMessage(0);

            // Limits game speed on faster devices.
            limitFPS();

            // Let's protect ourselves from a disaster when device draws the
            // exception popup.
        } catch (Exception e) {

        }
    }

    private void limitFPS() {
        long now = System.currentTimeMillis();
        if (lastUpdate > 0) {
            long delta = now - lastUpdate;
            if (delta < MIN_TICK_TIME) {
                Utils.sleep(MIN_TICK_TIME - delta);
            }
        }
        lastUpdate = System.currentTimeMillis();
    }

}
