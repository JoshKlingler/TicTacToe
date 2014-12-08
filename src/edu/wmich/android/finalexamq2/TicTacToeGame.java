package edu.wmich.android.finalexamq2;

import java.util.Random;

import android.util.Log;


public class TicTacToeGame {
	public static final int SHAPE_X = 1;
	public static final int SHAPE_O = 2;
	public static final int SHAPE_NONE = 0;
	
	public static final int WIN_NONE = 3;
	public static final int WIN_PLAYER = 4;
	public static final int WIN_COMPUTER = 5;
	private int[] moves = new int[9];
	
	private int winner;
	
	public TicTacToeGame(){
		newGame();
	}
	
	public int[] getMoves(){
		return moves;
	}
	
	public int addMove(int space, int shape){
		if(isValidMove(space) && winner == this.WIN_NONE){
			moves[space] = shape;
			winner = checkWin();
			
			
			Random rand = new Random();
			 
			if(!allSpacesFilled() && winner == this.WIN_NONE){
				int computerMove = rand.nextInt(9);
				while( moves[computerMove] != TicTacToeGame.SHAPE_NONE){
					computerMove = rand.nextInt(9);
				}
				
				moves[computerMove] = TicTacToeGame.SHAPE_O;
				checkWin();
			}
		}
		return winner;
	}
	public boolean isValidMove(int space){
		if(moves[space] != TicTacToeGame.SHAPE_NONE){
			return false;
		}
		else{
			return true;
		}
	}
	
	public int checkWin(){

		for(int i = 0; i < 3; i++){
			Log.d("checkWin", moves[(i * 3)] + " " + moves[(i * 3) + 1] + " "  + moves[(i * 3) + 2]);


			// If all three are the same horizontally and have moves there is a winner
			if(moves[i*3] != TicTacToeGame.SHAPE_NONE && moves[(i * 3)] == moves[(i * 3) + 1] && moves[(i * 3) + 1] == moves[(i * 3) + 2]){
				Log.d("CheckWin", "Winner horizontally");
				winner = determineWinnerFromShape(moves[i*3]);
				return determineWinnerFromShape(moves[i*3]);
			}
			// If all three are the same vertically and have moves there is a winner
			else if(moves[i] != TicTacToeGame.SHAPE_NONE && moves[i] == moves[i + 3] && moves[i+3] == moves[i+6]){
				Log.d("CheckWin", "Winner vertically");
				winner = determineWinnerFromShape(moves[i]);
				return determineWinnerFromShape(moves[i]);
			}			
		}

		// Diagonal top left to bottom right
		if(moves[0] != this.SHAPE_NONE && moves[0] == moves[4] && moves[4] == moves[8]){
			Log.d("CheckWin", "Winner diagonally");
			winner = determineWinnerFromShape(moves[0]);
			return determineWinnerFromShape(moves[0]);
		}
		// Diagonal bottom left to top right
		if(moves[6] != this.SHAPE_NONE && moves[6] == moves[4] && moves[4] == moves[2]){
			Log.d("CheckWin", "Winner diagonally");
			winner = determineWinnerFromShape(moves[6]);
			return determineWinnerFromShape(moves[6]);
		}
		return TicTacToeGame.WIN_NONE;
	}
	
	public int getWinner(){
		return winner;
	}
	
	private int determineWinnerFromShape(int shape){
		switch (shape) {
		case SHAPE_O:
			return this.WIN_COMPUTER;
		case SHAPE_X:
			return this.WIN_PLAYER;
		default:
			return this.WIN_NONE;
		}
	}
	
	public boolean allSpacesFilled(){
		boolean spacesFilled = true;
		for(int i = 0; i < 9; i++){
			if(moves[i] == TicTacToeGame.SHAPE_NONE){
				return false;
			}
		}
		return spacesFilled;
	}
	
	public void newGame(){
		for(int i = 0; i < moves.length; i++){
			moves[i] = SHAPE_NONE;
		}
		winner = this.WIN_NONE;
	}
}
