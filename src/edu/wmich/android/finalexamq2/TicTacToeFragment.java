package edu.wmich.android.finalexamq2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import edu.wmich.draw.R;

public class TicTacToeFragment extends Fragment {
	TicTacToeView ticTacToe;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, 
            Bundle savedInstanceState) {
        TicTacToeView v = (TicTacToeView) inflater.inflate(R.layout.fragment_tic_tac_toe, parent, false);
        ticTacToe = v;
        
        return v; 
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Log.d("TicTacToeFragment", "item selected");
		
		
		// NEW GAME GOES HERE
		ticTacToe.newGame();
		
		return true;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_tic_tac_toe, menu);
	}
}
