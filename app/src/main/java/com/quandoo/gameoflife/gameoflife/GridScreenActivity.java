package com.quandoo.gameoflife.gameoflife;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.quandoo.gameoflife.R;
import com.quandoo.gameoflife.ui.MyGridView;
import com.quandoo.gameoflife.util.GameStates;
import com.quandoo.gameoflife.util.Utils;


public class GridScreenActivity extends Activity implements View.OnClickListener {

    private GlobalContext mGlobalContext;
    private MyGridView mGridView;
    private Button mPlayPause;
    private Button mNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.debug(this, "onCreate() of GridScreenActivity");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_grid_screen);
        mPlayPause = (Button)findViewById(R.id.button_pause_play);
        mPlayPause.setOnClickListener(this);
        mNewGame = (Button)findViewById(R.id.button_newgame);
        mNewGame.setOnClickListener(this);

        mGlobalContext = GlobalContext.getInstance();

        //MyGridView gridView = new MyGridView(this);
        mGridView = (MyGridView)findViewById(R.id.mygridview);
        mGridView.setGameContext(mGlobalContext);
        mGlobalContext.setGridView(mGridView);

        mGridView.init();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_pause_play:
                if(GameStates.RUNNING == mGlobalContext.getGameState())
                    mGlobalContext.setGameState(GameStates.PAUSED);
                else if(GameStates.PAUSED == mGlobalContext.getGameState())
                    mGlobalContext.setGameState(GameStates.RUNNING);
                break;
            case R.id.button_newgame:
                mGlobalContext.resetGame();
                mGridView.init();
                mGlobalContext.setGameState(GameStates.RUNNING);
                break;
        }
    }

    @Override
    protected void onResume() {
        Utils.debug(this, "onResume()");
        super.onResume();
        mGlobalContext.setGameState(GameStates.RUNNING);
    }

    @Override
    protected void onPause() {
        Utils.debug(this, "onPause()");
        super.onPause();
        mGlobalContext.setGameState(GameStates.PAUSED);
    }


    @Override
    protected void onStop() {
        Utils.debug(this, "onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Utils.debug(this, "onDestroy()");
        mGlobalContext.resetGame();
        mGlobalContext = null;
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
