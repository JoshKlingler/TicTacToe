package edu.wmich.android.finalexamq2;

import android.support.v4.app.Fragment;

public class TicTacToeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new TicTacToeFragment();
    }
}
