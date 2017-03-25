package app.andy.aminoacids1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class MainActivity extends ActivityWithLocales {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RelativeLayout reLayMain = (RelativeLayout) findViewById(R.id.relayMain);


        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        //adRequest.

        final AdView adView = new AdView(this);

        final RelativeLayout.LayoutParams adViewParams = new RelativeLayout.LayoutParams(
                AdView.LayoutParams.WRAP_CONTENT,
                AdView.LayoutParams.WRAP_CONTENT);
        // align bottom
        adViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        // align center
        adViewParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);


        // reLay.addView(adView, adViewParams);
        adView.setAdUnitId("ca-app-pub-2783112053927174/3180286441");
        adView.setAdSize(AdSize.BANNER);
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {


            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (adView.getParent() == null) {
                    reLayMain.addView(adView, adViewParams);
                }
            }
        });
        adView.setBackgroundColor(Color.BLACK);
        adView.setBackgroundColor(0);






        ImageButton discover = (ImageButton) findViewById(R.id.learn);



        ImageButton tests = (ImageButton) findViewById(R.id.testa);
        ImageButton flashcards = (ImageButton) findViewById(R.id.flash);
        ImageButton settings = (ImageButton) findViewById(R.id.settings);


        final Intent startlearning = new Intent(MainActivity.this, ActivityLearning.class);

        discover.setOnClickListener(new AudioClickListener() {
            @Override
            public void onAudioClick(View v) {

                startActivity(startlearning);

            }
        });

        final Intent openTests = new Intent(MainActivity.this, ActivityTests.class);


        tests.setOnClickListener(new AudioClickListener() {
            @Override
            public void onAudioClick(View v) {

                startActivity(openTests);

            }
        });

        final Intent openFlashCards = new Intent(MainActivity.this, ActivityFlashCards.class);


        flashcards.setOnClickListener(new AudioClickListener() {
            @Override
            public void onAudioClick(View v) {

                startActivity(openFlashCards);

            }
        });


        final Intent openSettings = new Intent(MainActivity.this, ActivitySettings.class);


        settings.setOnClickListener(new AudioClickListener() {
            @Override
            public void onAudioClick(View v) {

                startActivity(openSettings);

            }
        });

    }
}
