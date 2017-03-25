package app.andy.aminoacids1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Andrey Borzenko on 19.03.2017.
 */

public class ActivityWithLocales extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.applyLocale(this);
    }
}
