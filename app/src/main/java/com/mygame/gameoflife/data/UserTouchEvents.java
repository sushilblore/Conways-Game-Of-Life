package com.mygame.gameoflife.data;

/**
 * Created by sushil.jha on 27-04-2015.
 */
public class UserTouchEvents {

    public int x;
    public int y;

    public UserTouchEvents(float x, float y) {
        this.x = Math.round(x);
        this.y = Math.round(y);
    }

}
