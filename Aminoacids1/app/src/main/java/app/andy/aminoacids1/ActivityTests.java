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

/**
 * Created by Andrey Borzenko on 09.03.2017.
 */

public class ActivityTests extends ActivityWithLocales {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tests);

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






        ImageButton guessformula = (ImageButton) findViewById(R.id.guessformulaButton);

        ImageButton guessname = (ImageButton) findViewById(R.id.guessnameButton);

        ImageButton guesscode = (ImageButton) findViewById(R.id.guesscodeButton);


        final Intent guessformulaTest = new Intent(ActivityTests.this, ActivityGuessFormula.class);

        guessformula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(guessformulaTest);

            }
        });

        final Intent guessnameTest = new Intent(ActivityTests.this, ActivityGuessCode.class);

        guessname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(guessnameTest);

            }
        });

        final Intent guesscodeTest = new Intent(ActivityTests.this, ActivityGuessName.class);

        guesscode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(guesscodeTest);

            }
        });


    }







}
