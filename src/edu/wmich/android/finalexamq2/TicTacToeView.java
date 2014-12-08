package edu.wmich.android.finalexamq2;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class TicTacToeView extends View {
    private static final int MOVE_TEXT_SIZE = 200;

	private static final String TAG = "TicTacToeView";

    private ArrayList<Box> mBoxen = new ArrayList<Box>();
    private Box mCurrentBox;
    private Paint mDrawXPaint;
    private Paint mDrawOPaint;
    private Paint mBoardPaint;
    private Paint mWinnerTextPaint;
    private Paint mBackgroundPaint;
    private Context mContext;
    private int mWidth;
    private int mHeight;
    private TicTacToeGame mGame;

    // used when creating the view in code
    public TicTacToeView(Context context) {
        this(context, null);
    }

    // used when inflating the view from XML
    public TicTacToeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        mContext = context;
        
        mGame = new TicTacToeGame();
        
        // Get size of screen to know where/how big to draw Tic Tac Toe board
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mWidth = size.x;
        mHeight = size.y;
        
        mDrawXPaint = new Paint();
        mDrawXPaint.setTextSize(MOVE_TEXT_SIZE);
        mDrawXPaint.setColor(Color.RED);
        mDrawXPaint.setTextAlign(Align.CENTER);
        
        mWinnerTextPaint = new Paint();
        mWinnerTextPaint.setTextSize(50);
        mWinnerTextPaint.setTextAlign(Align.CENTER);
        
        
        mDrawOPaint = new Paint();
        mDrawOPaint.setTextSize(MOVE_TEXT_SIZE);
        mDrawOPaint.setColor(Color.BLUE);
        mDrawOPaint.setTextAlign(Align.CENTER);
        
        // Draw Board
        mBoardPaint = new Paint();
        mBoardPaint.setColor(Color.BLACK);
        mBoardPaint.setStrokeWidth(3);

        // paint the background off-white
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // fill the background
        canvas.drawPaint(mBackgroundPaint);
        
        drawBoard(canvas);
        
        drawMoves(mGame.getMoves(), canvas);
        
        drawWinner(mGame.getWinner(), canvas);
    }
    
    private void drawWinner(int winner, Canvas canvas){
    	switch (winner) {
		case TicTacToeGame.WIN_PLAYER:
			mWinnerTextPaint.setColor(Color.RED);
			canvas.drawText("Player Wins!", (float) (mWidth * .5), (float) (mHeight * .8), mWinnerTextPaint);
			break;
		case TicTacToeGame.WIN_COMPUTER:
			mWinnerTextPaint.setColor(Color.BLUE);
			canvas.drawText("Computer Wins!", (float) (mWidth * .5), (float) (mHeight * .8), mWinnerTextPaint);
		default:
			break;
		}
    }
    
    private void drawMoves(int[] moves, Canvas canvas){
    	for(int i = 0; i < moves.length; i++){
    		int x = i % 3;
    		int y = i / 3;
    		
    		//Log.d("DrawMoves", "X: " + x + "Y: " + y + " Val: " + moves[i]);
    		
    		if(moves[i] == TicTacToeGame.SHAPE_O){
    			drawO(x, y, canvas);
    		}
    		else if(moves[i] == TicTacToeGame.SHAPE_X){
    			drawX(x,y,canvas);
    		}
    		else{
    			// Don't draw anything
    		}
    	}
    }
    
    public void newGame(){
    	mGame.newGame();
    	invalidate();
    }
    
    private void drawBoard(Canvas canvas){
        // Draw Board
        canvas.drawLine((float) (mWidth * .333), 0, (float) (mWidth * .333), (float) (mWidth), mBoardPaint);
        canvas.drawLine((float) (mWidth * .666), 0, (float) (mWidth * .666), (float) (mWidth), mBoardPaint);
        canvas.drawLine(10, (float) (mWidth * .333), (float) (mWidth -10), (float) (mWidth * .333), mBoardPaint);
        canvas.drawLine(10, (float) (mWidth * .666), (float) (mWidth -10), (float) (mWidth * .666), mBoardPaint);
    }
    
    private void drawX(int x, int y, Canvas canvas){
    	float ptX = (float) ((float) (.16666 * mWidth) + x * (.3333 * mWidth));
    	float ptY = (float) ((float) (.25 * mWidth) + y * (.3333 * mWidth));
    	
    	canvas.drawText("X", ptX, ptY, mDrawXPaint);
    }
    
    private void drawO(int x, int y, Canvas canvas){
    	float ptX = (float) ((float) (.16666 * mWidth) + x * (.3333 * mWidth));
    	float ptY = (float) ((float) (.25 * mWidth) + y * (.3333 * mWidth));
    	
    	canvas.drawText("O", ptX, ptY, mDrawOPaint);
    }    

    public boolean onTouchEvent(MotionEvent event) {
        PointF curr = new PointF(event.getX(), event.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // reset our drawing state
                mCurrentBox = new Box(curr);
                mBoxen.add(mCurrentBox);
                break;

            case MotionEvent.ACTION_MOVE:
                if (mCurrentBox != null) {
                    mCurrentBox.setCurrent(curr);
                    invalidate();
                }

                break;

            case MotionEvent.ACTION_UP:
            	
            	int x = determineZone(curr.x);
            	int y = determineZone(curr.y);
            	Log.d("TicTacToeView", "X: " + x + " Y: " + y + " Num: " + convertXYtoOneNum(x, y) + " Game Val: " + mGame.getMoves()[convertXYtoOneNum(x, y)]);
            	
            	int winner = mGame.addMove(convertXYtoOneNum(x, y), TicTacToeGame.SHAPE_X);
            	
            	
            	invalidate();
            	
            	break;
            case MotionEvent.ACTION_CANCEL:
                mCurrentBox = null;
                break;
        }
        
        return true;
    }
    
    
    private int convertXYtoOneNum(int x, int y){
    	return (3 * y) + x;
    }
    
    // Determines which spot was touched on the board
    private int determineZone(float point){
    	if(point < (mWidth * .333)){
    		return 0;
    	}
    	else if(point < (mWidth * .666)){
    		return 1;
    	}
    	else{
    		return 2;
    	}
    }
}

