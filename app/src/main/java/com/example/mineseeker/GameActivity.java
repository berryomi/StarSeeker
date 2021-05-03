package com.example.mineseeker;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.mineseeker.model.GameLogic;
import com.example.mineseeker.model.Options;


public class GameActivity extends AppCompatActivity {

    private GameLogic game = new GameLogic();
    private Options options = Options.getInstance();

    private int NUM_ROWS = options.getNumRow(); // default 4
    private int NUM_COLS = options.getNumCol(); // default 6
    private int NUM_MINES = options.getNumMines(); // default 6

    private int hiddenStars[][] = new int[NUM_ROWS][NUM_COLS];

    Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
    Boolean starArray[][] = game.getStarArray();
    Boolean isClicked[][] = game.getIsClicked();
    Boolean isRevealed[][] = game.getIsRevealed();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        game.initialize();
        populateButtons();
        updateTextView();
    }

    private void populateButtons() {
        TableLayout table = findViewById(R.id.table4buttons);
        for (int row = 0; row < NUM_ROWS; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);


            for (int col = 0; col < NUM_COLS; col++) {
                final int FINAL_COL = col;
                final int FINAL_ROW = row;

                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT, 1.0f));

                //Make text not clip on small buttons
                button.setPadding(0, 0, 0, 0);

                button.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(FINAL_COL, FINAL_ROW);
                        scanningAnimation(FINAL_COL, FINAL_ROW);
                        updateTextView();
                    }
                });
                tableRow.addView(button);
                buttons[row][col] = button;

            }
        }
    }

    private void updateTextView() {
        TextView textView = findViewById(R.id.textView10);
        textView.setText("# Scans used: " + game.getClickCount());

        TextView textView2 = findViewById(R.id.textView9);
        textView2.setText("Found " + game.getMineFound() + " of " + NUM_MINES + " stars!");

    }

    private void scanningAnimation(int col, int row) {
        for (int i = 0; i < NUM_ROWS; i++) {

            buttons[i][col].startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
            buttons[i][col].startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));

        }

        for (int j = 0; j < NUM_COLS; j++) {
            buttons[row][j].startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
            buttons[row][j].startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void gridButtonClicked(int col, int row) {
        final MediaPlayer scanSound = MediaPlayer.create(this, R.raw.scan);
        final MediaPlayer starSound = MediaPlayer.create(this, R.raw.multimedia_button_click_029);

        int starsHidden = 0;
        Button button = buttons[row][col];
        lockButtonSizes();


        //allocate either a star or no star to this cell
        if (starArray[row][col] == true && isClicked[row][col] == false && isRevealed[row][col] == false) {
            //scale image to button;
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.comet);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));

            starSound.start();

            game.increaseMineFound();
            game.setIsRevealed(row, col, true);
            updateNumHiddenStar(row, col);

            if (game.getMineFound() == NUM_MINES){
                setUpGameEndMessage();
            }

        }
        else if (starArray[row][col] == false && isClicked[row][col] == false) {
            scanSound.start();

            game.increaseClickCount();
            game.setIsClicked(row, col, true);

            starsHidden = game.countHiddenStars(row, col);
            hiddenStars[row][col] = starsHidden;
            button.setText("" + starsHidden);
        }
        else if (starArray[row][col] == true && isClicked[row][col] == false && isRevealed[row][col] == true) {
            scanSound.start();

            starsHidden = game.countHiddenStars(row, col);
            hiddenStars[row][col] = starsHidden;
            button.setText("" + starsHidden);

            game.increaseClickCount();
            game.setIsClicked(row, col, true);
        }

    }

    private void lockButtonSizes() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinimumWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinimumHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    private void updateNumHiddenStar(int row, int col) {
        int starsHidden = 0;

        for (int i = 0; i < NUM_ROWS; i++) {
            if (isClicked[i][col] == true) {
                if (i == row) {
                    continue;
                }
                hiddenStars[i][col] = hiddenStars[i][col] - 1;
                buttons[i][col].setText("" + hiddenStars[i][col]);
            }
        }

        for (int j = 0; j < NUM_COLS; j++) {
            if (isClicked[row][j] == true) {
                if (j == col) { // do not count the cell itself
                    continue;
                }
                hiddenStars[row][j] = hiddenStars[row][j] - 1;
                buttons[row][j].setText("" + hiddenStars[row][j]);
            }
        }

    }

    private void setUpGameEndMessage() {
        FragmentManager manager = getSupportFragmentManager();
        CongratulationsMessage dialog = new CongratulationsMessage();
        dialog.show(manager, "MessageDialog");
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, GameActivity.class);
        return intent;

    }
}
