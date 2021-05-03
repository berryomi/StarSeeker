package com.example.mineseeker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mineseeker.model.Options;

public class OptionsActivity extends AppCompatActivity {

    private Options options;
    private int numGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        options = Options.getInstance();

        createNumMinesOptions();
        createBoardSizeOptions();

        extractDatafromIntent();
        updateTextView();
        setUpEraseButton();

        saveNumGamesPlayed();

    }

    private void createNumMinesOptions() {
        RadioGroup group = findViewById(R.id.radio_group_num_mines);
        int[] numMines = getResources().getIntArray(R.array.num_mines);

        //Create the buttons:
        for (int i = 0; i < numMines.length; i++) {
            final int numMine = numMines[i];

            RadioButton button = new RadioButton(this);
            button.setText(numMine + " mines");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveNumMinesSelected(numMine);
                    options.setNumMines(numMine);
                }
            });
            group.addView(button);

            if (numMine == options.getNumMines()) {
                button.setChecked(true);
            }

        }
    }

    private void saveNumGamesPlayed(){
        SharedPreferences prefs = getSharedPreferences("numGames", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Number of games played", numGames);
        editor.apply();
    }


    private void saveNumMinesSelected(int numMine) {
        SharedPreferences prefs = this.getSharedPreferences("NumMines", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Number of mines selected", numMine);
        editor.apply();
    }

    private void createBoardSizeOptions() {
        RadioGroup group = findViewById(R.id.radio_group_board_size);
        int[] numRows = getResources().getIntArray(R.array.num_rows);
        int[] numCols = getResources().getIntArray(R.array.num_cols);

        //Create the buttons:
        for (int i = 0; i < numRows.length; i++) {
            final int numRow = numRows[i];
            final int numCol = numCols[i];

            RadioButton button = new RadioButton(this);
            button.setText(numRow + " rows by " + numCol + " columns");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveNumRowsSelected(numRow);
                    saveNumColsSelected(numCol);

                    options.setNumRow(numRow);
                    options.setNumCol(numCol);
                }
            });
            group.addView(button);

            if (numRow == options.getNumRow()) {
                button.setChecked(true);
            }
        }
    }

    private void saveNumRowsSelected(int numRow) {
        SharedPreferences prefs = this.getSharedPreferences("NumRows", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Number of rows selected", numRow);
        editor.apply();
    }

    private void saveNumColsSelected(int numCol) {
        SharedPreferences prefs = this.getSharedPreferences("NumCols", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Number of columns selected", numCol);
        editor.apply();
    }

    private void setUpEraseButton() {
        Button button = findViewById(R.id.eraseTimesPlayed);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.textView11);
                numGames = 0;
                textView.setText("Number of games played: " + numGames);

                Intent intent = MainMenuActivity.makeIntent(OptionsActivity.this, numGames);
                startActivity(intent);

            }
        });
    }

    public static Intent makeIntent(Context context, int games) {
        Intent intent = new Intent(context, OptionsActivity.class);
        intent.putExtra("numGames", games);
        return intent;

    }

    private void extractDatafromIntent(){
        Intent intent = getIntent();
        numGames = intent.getIntExtra("numGames",0);
    }

    private void updateTextView(){
        TextView textView = findViewById(R.id.textView11);
        textView.setText("Number of games played: " + numGames);
    }

}
