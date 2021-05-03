package com.example.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    TextView HyperLink;
    TextView HyperLink2;
    Spanned Text;
    Spanned Text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        createHyperLink();
    }

    private void createHyperLink() {
        HyperLink = findViewById(R.id.textView7);
        Text = Html.fromHtml("Written by Jiwon Jun and Melody Lan, CMPT 276 students at Simon Fraser University <br />" +
                "<a href='http://opencoursehub.cs.sfu.ca/bfraser/grav-cms/cmpt276/home//'>CMPT 276 Home Page</a>");
        HyperLink.setMovementMethod(LinkMovementMethod.getInstance());
        HyperLink.setText(Text);

        HyperLink2 = findViewById(R.id.textView8);
        Text2 = Html.fromHtml("Images from <br />" +
                "<a href='https://pixabay.com/photos/night-photograph-flashlight-ray-2183637//'>Background Image</a><br />" +
                "<a href='https://pixabay.com/vectors/comet-falling-star-shooting-star-149438//'>Star Image</a><br />" +
                "<a href='https://www.zapsplat.com/music/user-interface-tone-select-digital-button//'>Sound 1</a><br />" +
                "<a href='https://www.zapsplat.com/music/multimedia-button-click-29//'>Sound 2</a><br />");
        HyperLink2.setMovementMethod(LinkMovementMethod.getInstance());
        HyperLink2.setText(Text2);

    }


    public static Intent makeIntent(Context context) {
        return new Intent(context, HelpActivity.class);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HelpActivity.this, MainMenuActivity.class);
        startActivity(intent);

    }



}
