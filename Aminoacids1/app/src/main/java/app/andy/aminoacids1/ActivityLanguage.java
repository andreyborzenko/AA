package app.andy.aminoacids1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;



/**
 * Created by Andrey Borzenko on 18.03.2017.
 */

public class ActivityLanguage extends ActivityWithLocales {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.language);
       // String[] lang = new String[]{"English", "Русский"};
        ListView languagesList = (ListView) findViewById(R.id.languageList);

        final ArrayAdapter<SupportedLocale> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, SupportedLocale.values() );

        languagesList.setAdapter(adapter);

        languagesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               SupportedLocale locale= adapter.getItem(position);
                LocaleHelper.setLocale(getApplicationContext(),locale);

                Intent i = new Intent(ActivityLanguage.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });


    }
}
