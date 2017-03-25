package app.andy.aminoacids1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

/**
 * Created by Andrey Borzenko on 18.03.2017.
 */

public class ActivitySettings extends ActivityWithLocales {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings);

        Switch soundSwitch = (Switch) findViewById(R.id.switch1);

        Button language = (Button) findViewById(R.id.languages);

        Button reset = (Button) findViewById(R.id.progress);


        final Intent openlanguages = new Intent(ActivitySettings.this, ActivityLanguage.class);

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(openlanguages);
            }
        });


    }
}
