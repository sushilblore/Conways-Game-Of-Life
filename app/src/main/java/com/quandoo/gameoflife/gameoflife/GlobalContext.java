package com.quandoo.gameoflife.gameoflife;

import com.quandoo.gameoflife.data.TouchInfo;
import com.quandoo.gameoflife.gamelogic.GameLogic;
import com.quandoo.gameoflife.gamelogic.GameThread;
import com.quandoo.gameoflife.ui.MyGridView;
import com.quandoo.gameoflife.util.GameStates;

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
