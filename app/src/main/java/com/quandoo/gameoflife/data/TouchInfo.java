package com.quandoo.gameoflife.data;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sushil.jha on 27-04-2015.
 */
public class TouchInfo {

    //Set to maintain unprocessed events
    private Set<UserTouchEvents> mUnprocessedEvents = new HashSet<UserTouchEvents>();

    //Set to maintain processed events
    private Set<UserTouchEvents> mProcessedEvents = new HashSet<UserTouchEvents>();

    public void addTouch(float x, float y) {
        synchronized (mUnprocessedEvents) {
            mUnprocessedEvents.add(new UserTouchEvents(x, y));
        }
    }


    public void flush() {
        synchronized (mUnprocessedEvents) {
            synchronized (mProcessedEvents) {
                mProcessedEvents = new HashSet<UserTouchEvents>(mUnprocessedEvents);
            }
            mUnprocessedEvents.clear();
        }
    }

    public Set<UserTouchEvents> getUnprocessed() {
        synchronized (mUnprocessedEvents) {
            return new HashSet<UserTouchEvents>(mUnprocessedEvents);
        }
    }

    public Set<UserTouchEvents> getProcessed() {
        synchronized (mProcessedEvents) {
            Set<UserTouchEvents> response = new HashSet<UserTouchEvents>(mProcessedEvents);
            mProcessedEvents.clear();
            return response;
        }
    }

    public void reset() {
        mProcessedEvents.clear();
        mUnprocessedEvents.clear();
    }
}
