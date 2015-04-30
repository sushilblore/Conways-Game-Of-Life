package com.mygame.gameoflife.gameoflife;

import com.mygame.gameoflife.data.TouchInfo;
import com.mygame.gameoflife.gamelogic.GameLogic;
import com.mygame.gameoflife.gamelogic.GameThread;
import com.mygame.gameoflife.ui.MyGridView;
import com.mygame.gameoflife.util.GameStates;

/**
 * Created by sushil.jha on 27-04-2015.
 */
public class GlobalContext {

    //Singleton Implementation
    private static GlobalContext mInstance;
    //Game states
    private GameStates mGameStates;
    //Game thread
    private final GameThread mGameThread;

    //Handling touch events
    private final TouchInfo mTouchInput;

    //Game logic
    private final GameLogic mGameLogic;

    //GridView
    private MyGridView mGridView;

    public static GlobalContext getInstance() {
        if (mInstance == null) {
            mInstance = new GlobalContext();
        }
        return mInstance;
    }

    private GlobalContext () {
        mGameThread = new GameThread(this);
        mTouchInput = new TouchInfo();
        mGameStates = GameStates.RUNNING;
        mGameLogic = new GameLogic(this);
    }

    public TouchInfo getTouchInput () {
        return mTouchInput;
    }

    public GameLogic getGameLogic() {
        return mGameLogic;
    }

    public GameThread getGameThread () {
        return mGameThread;
    }

    public GameStates getGameState () {
        return mGameStates;
    }

    public void setGameState(GameStates state) {
        this.mGameStates = state;
    }

    public void setGridView(MyGridView view) {
        mGridView = view;
    }

    public MyGridView getGridView() {
        return mGridView;
    }

    public void resetGame() {
        this.mGameStates = GameStates.STOPPED;
        mTouchInput.reset();
        mGameLogic.resetGameLogic();
        mGridView.resetGridView();
    }

}
