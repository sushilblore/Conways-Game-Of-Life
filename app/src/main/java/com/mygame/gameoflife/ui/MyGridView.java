package com.mygame.gameoflife.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mygame.gameoflife.data.UserTouchEvents;
import com.mygame.gameoflife.gameoflife.GlobalContext;
import com.mygame.gameoflife.util.Utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO: document your custom view class.
 */
public class MyGridView extends View {

    public static final float SCALE = 15f;

    public static final int GRID_WIDTH = 600;
    public static final int GRID_HEIGHT = 600;

    private GlobalContext mGlobalContext;
    private boolean gameRunning;

    private Paint bgPaint;
    private Paint cellPaint;
    private Paint prePaint;

    private int mGridWidth;
    private int mGridHeight;

    public Handler mUpdateHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //Utils.debug(this, "MessageHandler() of MyGridView");
            MyGridView.this.invalidate();
        }
    };

    public MyGridView(Context context) {
        super(context);
        setWillNotDraw(false);
        //init(null, 0);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        //init(attrs, 0);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false);
        //init(attrs, defStyle);
    }

    public void init() {
        Utils.debug(this, "init() of MyGridView");
        setFocusable(true);
        setGridSize(GRID_WIDTH, GRID_HEIGHT);

        bgPaint = new Paint();
        bgPaint.setColor(Color.WHITE);

        cellPaint = new Paint();
        cellPaint.setColor(Color.BLACK);

        prePaint = new Paint();
        prePaint.setColor(Color.BLACK);

        if (!gameRunning) {
            // At this point we can start drawing on our GameView, so let's
            // start the main loop.
            mGlobalContext.getGameThread().start();
            gameRunning = true;
        }

    }

    public void setGameContext(GlobalContext gameContext) {
        mGlobalContext = gameContext;
    }

    public void setGridSize(int width, int height) {
        mGridWidth = width;
        mGridHeight = height;

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_UP) {

            mGlobalContext.getTouchInput().addTouch(
                    event.getX() / SCALE,
                    event.getY() / SCALE);
        } else {
            mGlobalContext.getTouchInput().flush();
        }

        return true;
    }

    public void update() {
        Utils.debug(this, "update() of MyGridView");
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Utils.debug(this, "onDraw() of MyGridView");
        prepareBackground(canvas);
        drawCells(canvas);
        drawUnprocessedInput(canvas);
    }

    private void prepareBackground(Canvas canvas) {
        canvas.drawRect(new Rect(0, 0, mGridWidth, mGridHeight), bgPaint);
    }


    private void drawCells(Canvas canvas) {
        drawCells(canvas, mGlobalContext.getGameLogic().getCells(), cellPaint);
    }


    private void drawUnprocessedInput(Canvas canvas) {
        Collection<Cell> preview = new ArrayList<Cell>();
        for (UserTouchEvents touch : mGlobalContext.getTouchInput().getUnprocessed()) {
            Utils.debug(this, "drawUnprocessedInput() of MyGridView");
            preview.add(new Cell(touch.x, touch.y));
        }
        drawCells(canvas, preview, prePaint);
    }

    private void drawCells(Canvas canvas, Collection<Cell> cells, Paint paint) {
        for (Cell cell : cells) {
            canvas.drawRect(new Rect(
                            Math.round(cell.getRow() * SCALE),
                            Math.round(cell.getCol() * SCALE),
                            Math.round(cell.getRow() * SCALE + SCALE),
                            Math.round(cell.getCol() * SCALE + SCALE)),
                    paint);
        }
    }

    public int getMatrixWidth() {
        return Math.round(mGridWidth / SCALE);
    }
    public int getMatrixHeight() {
        return Math.round(mGridHeight / SCALE);
    }

    public void resetGridView () {
        gameRunning = false;
    }

}
