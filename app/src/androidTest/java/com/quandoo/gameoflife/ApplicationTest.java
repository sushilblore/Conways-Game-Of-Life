package com.quandoo.gameoflife;

import android.app.Activity;
import android.app.Application;
import android.test.ApplicationTestCase;

import com.quandoo.gameoflife.gamelogic.GameLogic;
import com.quandoo.gameoflife.gameoflife.GlobalContext;
import com.quandoo.gameoflife.ui.MyGridView;
import com.quandoo.gameoflife.util.GameStates;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testGlobalContextNotNull(){
        GlobalContext mGlobalContext = GlobalContext.getInstance();
        assertNotNull(mGlobalContext);
    }


    //Test game logic - Detecting cell out of Grid bound
    public void testCellOutOfBound(){
        int x = 800;
        int y = 800;
        Activity tempActivity = new Activity();
        MyGridView testGridView = new MyGridView(tempActivity);
        testGridView.setGridSize(600, 600);
        GameLogic mGameLogic = null;
        GlobalContext mGlobalContext = GlobalContext.getInstance();
        mGameLogic = new GameLogic(mGlobalContext);
        assertTrue(mGameLogic.isOutOfBounds(x,y, testGridView));

    }

    //Testing game simulation - checking is game state is RUNNING mode
    public void testGameStateRunning(){
        GlobalContext mGlobalContext = GlobalContext.getInstance();
        GameStates curState = mGlobalContext.getGameState();
        assertEquals(curState, GameStates.RUNNING);
    }

    public void testThatFails(){
        // all NOK
        assert false;
    }
}