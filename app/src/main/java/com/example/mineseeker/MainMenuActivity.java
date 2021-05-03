package com.example.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    private int numGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setUpPlayGameButton();
        setUpOptionsButton();
        setUpHelpButton();
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, MainMenuActivity.class);
    }

    private void setUpPlayGameButton(){
        Button button = findViewById(R.id.playGame);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the game
                numGames++;
                Intent intent = GameActivity.makeIntent(MainMenuActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setUpOptionsButton() {
        Button button = findViewById(R.id.options);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch options
                Intent intent = OptionsActivity.makeIntent(MainMenuActivity.this, numGames);
                startActivity(intent);
            }
        });
    }

    private void setUpHelpButton() {
        Button button = findViewById(R.id.help);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch help
                Intent intent = HelpActivity.makeIntent(MainMenuActivity.this);
                startActivity(intent);
            }
        });
    }

    //https://stackoverflow.com/questions/21253303/exit-android-app-on-back-pressed
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public static Intent makeIntent(Context context, int games) {
        Intent intent = new Intent(context, MainMenuActivity.class);
        intent.putExtra("numGames", games);
        return intent;

    }


}
