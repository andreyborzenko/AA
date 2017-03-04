package andy.almed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity
         {




             ArrayAdapter<String> adapter;
             EditText editsearch;
             JSONArray result;
             String[] disorders;
             public static String locale;
             public static int size = 100;
             private AdView adView;
            public static int pos;
             ArrayList<SupportedLocale> locales = new ArrayList<>();
             public  String assetJson =  String.format("%s.json", LocaleHelper.getLanguage(MainActivity.this)).toLowerCase();
            public SharedPreferences p;
            public static LinearLayout shtick;

             public static int  backcol1;
             public  ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
             String disordersArrayFull[];
             String disordersArrayFull1[];
             String array[];
             Parcelable state;
             int textlength = 0;
             private ArrayList<String> array_sort = new ArrayList<String>();
             @Override
    protected void onCreate(Bundle savedInstanceState) {
                 LocaleHelper.applyLocale(this);
                 super.onCreate(savedInstanceState);

                 setContentView(R.layout.activity_main);


                ImageButton ment = (ImageButton) findViewById(R.id.mental);

                ImageButton emotion = (ImageButton) findViewById(R.id.emotion);

                 ImageButton syndromes = (ImageButton) findViewById(R.id.synd);

                 ImageButton symptoms = (ImageButton) findViewById(R.id.symp);

                 ment.setOnClickListener(new View.OnClickListener(){

                     @Override
                     public void onClick(View v) {

                         Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=andy.mentaldisorders"));
                         startActivity(intent);
                     }
                 });

                 emotion.setOnClickListener(new View.OnClickListener(){

                     @Override
                     public void onClick(View v) {

                         Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=andy.emotions"));
                         startActivity(intent);
                     }
                 });


                 syndromes.setOnClickListener(new View.OnClickListener(){

                     @Override
                     public void onClick(View v) {

                         Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=andy.syndromes"));
                         startActivity(intent);
                     }
                 });

                 symptoms.setOnClickListener(new View.OnClickListener(){

                     @Override
                     public void onClick(View v) {

                         Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=andy.symptomes"));
                         startActivity(intent);
                     }
                 });

                 editsearch = (EditText) findViewById(R.id.editText);




                 final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                 ImageButton sett = (ImageButton) findViewById(R.id.sett);

                 sett.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         drawer.openDrawer(Gravity.LEFT);
                     }
                 });


                 final RelativeLayout reLay = (RelativeLayout) findViewById(R.id.reLay);

                 AdRequest adRequest = new AdRequest.Builder()
                         .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                         .build();
                 //adRequest.

                 adView = new AdView(this);

                 final RelativeLayout.LayoutParams adViewParams = new RelativeLayout.LayoutParams(
                         AdView.LayoutParams.WRAP_CONTENT,
                         AdView.LayoutParams.WRAP_CONTENT);

                 adViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

                 adView.setAdUnitId("ca-app-pub-2783112053927174/6585384842");
                 adView.setAdSize(AdSize.BANNER);
                 adView.loadAd(adRequest);
                 adView.setAdListener(new AdListener() {


                     @Override
                     public void onAdLoaded() {
                         super.onAdLoaded();
                         if(adView.getParent()==null) {
                             reLay.addView(adView, adViewParams);
                         }
                     }
                 });
                 adView.setBackgroundColor(BLACK);
                 adView.setBackgroundColor(0);


                final Spinner spinner = (Spinner) findViewById(R.id.spinner);

                 spinner.setAdapter(new ArrayAdapter<SupportedLocale>(this,android.R.layout.test_list_item, SupportedLocale.values()));

        //         simple_list_item_1

                 for(SupportedLocale loc:SupportedLocale.values()) {
                     locales.add (loc);
                 }
                 spinner.setSelection( locales.indexOf(LocaleHelper.getCurrentLocale()));

                 spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                     @Override
                     public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                int pos, long arg3) {
                         // TODO Auto-generated method stub


                         Intent intent1 = getIntent();
                         SupportedLocale loc = SupportedLocale.values()[pos];
                         if(loc!=LocaleHelper.getCurrentLocale()) {
                             LocaleHelper.setLocale(MainActivity.this, loc);
                             assetJson = String.format("%s.json",loc.getLangCode()).toLowerCase();
                             finish();

                             startActivity(intent1);
                         }


                     }

                     @Override
                     public void onNothingSelected(AdapterView<?> arg0) {
                         // TODO Auto-generated method stub

                     }
                 });









                 try {


                     JSONArray json = new JSONArray(loadJSONFromAsset());

                     HashMap<String, String> map;

                     int index = 0;

                     for (int i = 0; i < json.length(); i++) {
                         JSONObject jo_inside = json.getJSONObject(i);
                         Log.d("Details-->", jo_inside.getString("h1"));
                         String name = jo_inside.getString("h1");
                         String file = jo_inside.getString("file");

                         //Add your values in your `ArrayList` as below:
                         map = new HashMap<String, String>();
                         map.put("h1", name);
                         map.put("file", file);

                         formList.add(map);

                     }

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

                 disordersArrayFull= new String[formList.size()];
                 for (int j = 0; j < formList.size(); j++) {
                     disordersArrayFull[j] = formList.get(j).get("h1");
                 }


                 disordersArrayFull = new HashSet<String>(asList(disordersArrayFull)).toArray(new String[0]);

               final SharedPreferences prefsMain = getSharedPreferences("prefsmain", 0);

               backcol1 = prefsMain.getInt("backColor", WHITE);

                final LinearLayout shtick = (LinearLayout) findViewById(R.id.shtick);



                 shtick.setBackgroundColor(backcol1);
                 final  ImageButton sort = (ImageButton) findViewById(R.id.sort);

                 if (backcol1 == WHITE) {

                     Collections.sort(asList(disordersArrayFull), String.CASE_INSENSITIVE_ORDER);
                     sort.setImageResource(R.drawable.sortaz);

                 } else {

                     Collections.sort(asList(disordersArrayFull), new Comparator<String>() {
                         @Override
                         public int compare(String s, String t1) {
                             return -s.compareToIgnoreCase(t1);
                         }
                     });

                     sort.setImageResource(R.drawable.sortza);
                 }


                 final ListView listView = (ListView) findViewById(R.id.mainListView1);

                 adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, disordersArrayFull){};

                 final ArrayAdapter  adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array_sort){};

                 listView.setAdapter(adapter);


                 editsearch.addTextChangedListener(new TextWatcher() {



                     public void beforeTextChanged(CharSequence arg0, int arg1,
                                                   int arg2, int arg3) {
                         // TODO Auto-generated method stub

                     }

                     public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                               int arg3) {

              //           MainActivity.this.adapter.getFilter().filter(cs);





                //         }

                    //   String[] stockArr = new String[array_sort.size()];
                     //  stockArr = array_sort.toArray(stockArr);
//

                   //    adapter.notifyDataSetChanged();
//

           //             disordersArrayFull= new String[getArray(adapter).size()];
           //             for (int j = 0; j < getArray(adapter).size(); j++) {
           //                 disordersArrayFull[j] = (String) getArray(adapter).get(j);
           //              }


                     }

                     public void afterTextChanged(Editable arg0) {
                         // TODO Auto-generated method stub


                         textlength = editsearch.getText().length();

                         //     if (textlength == 0) {
                         //         listView.setAdapter(adapter);
//
                         //     } else {
                         array_sort.clear();


                         //       Arrays.asList(disordersArrayFull).clear();


                         for (int i = 0; i < asList(disordersArrayFull).size(); i++) {
                             if (textlength <= disordersArrayFull[i].length()) {

                                 String s2 = editsearch.getText().toString();
                                 if (disordersArrayFull[i].toLowerCase().contains(s2.toLowerCase())) {
                                     array_sort.add(disordersArrayFull[i]);
                                 }


                             }

                         }


                         listView.setAdapter(adapter1);

                         sort.setOnClickListener(new View.OnClickListener() {



                             @Override
                             public void onClick(View view) {
//
                                 ColorDrawable viewCol = (ColorDrawable) shtick.getBackground();
                                 int colorIde = viewCol.getColor();


                                 if (colorIde == WHITE) {
//
                                     Collections.sort(array_sort, new Comparator<String>() {
                                         @Override
                                         public int compare(String s, String t1) {
                                             return -s.compareToIgnoreCase(t1);
                                         }
                                     });
                                     sort.setImageResource(R.drawable.sortza);
                                     adapter1.notifyDataSetChanged();
                                     shtick.setBackgroundColor(BLACK);
                                     SharedPreferences prefsMain = getSharedPreferences("prefsmain", 0);
                                     prefsMain.edit().putInt("backColor", BLACK).commit();
//
//
                                 } else {
//
                                     Collections.sort(array_sort, String.CASE_INSENSITIVE_ORDER);
                                     sort.setImageResource(R.drawable.sortaz);
                                     adapter1.notifyDataSetChanged();
                                     shtick.setBackgroundColor(Color.WHITE);
                                     SharedPreferences prefsMain = getSharedPreferences("prefsmain", 0);
                                     prefsMain.edit().putInt("backColor", WHITE).commit();
                                 }
//
//
//
                             }
                         });

                     }




                 });









                 final SharedPreferences prefsMain1 = getSharedPreferences("prefsmain1", 0);
                 int posi = prefsMain1.getInt("scroll_position",0);









                 listView.setSelection(posi);






                 sort.setOnClickListener(new View.OnClickListener() {



                     @Override
                     public void onClick(View view) {
//
                         ColorDrawable viewCol = (ColorDrawable) shtick.getBackground();
                         int colorIde = viewCol.getColor();


                         if (colorIde == WHITE) {
//
                             Collections.sort(asList(disordersArrayFull), new Comparator<String>() {
                                 @Override
                                 public int compare(String s, String t1) {
                                     return -s.compareToIgnoreCase(t1);
                                 }
                             });
                             sort.setImageResource(R.drawable.sortza);
                             adapter.notifyDataSetChanged();
                             shtick.setBackgroundColor(BLACK);
                             SharedPreferences prefsMain = getSharedPreferences("prefsmain", 0);
                             prefsMain.edit().putInt("backColor", BLACK).commit();
//
//
                         } else {
//
                             Collections.sort(asList(disordersArrayFull), String.CASE_INSENSITIVE_ORDER);
                             sort.setImageResource(R.drawable.sortaz);
                             adapter.notifyDataSetChanged();
                             shtick.setBackgroundColor(Color.WHITE);
                             SharedPreferences prefsMain = getSharedPreferences("prefsmain", 0);
                             prefsMain.edit().putInt("backColor", WHITE).commit();
                         }
//
//
//
                     }
                 });








            //     int index = listView.getFirstVisiblePosition();
//
            //     View v = listView.getChildAt(0);
            //     int top = (v == null) ? 0 : (v.getTop() - listView.getPaddingTop());
            //
            //     listView.setSelectionFromTop(index, top);

                 ImageButton like = (ImageButton) findViewById(R.id.button_like);
                 ImageButton like2 = (ImageButton) findViewById(R.id.button_like2);
                 ImageButton like3 = (ImageButton) findViewById(R.id.button_like3);
                 ImageButton like4 = (ImageButton) findViewById(R.id.button_like4);
                 ImageButton like5 = (ImageButton) findViewById(R.id.button_like5);

                 like.setOnClickListener(new View.OnClickListener(){

                     @Override
                     public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=andy.almed"));
                         startActivity(intent);
                     }
                 });

                 like2.setOnClickListener(new View.OnClickListener(){

                     @Override
                     public void onClick(View v) {

                         Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=andy.almed"));
                         startActivity(intent);
                     }
                 });

                 like3.setOnClickListener(new View.OnClickListener(){

                     @Override
                     public void onClick(View v) {

                         Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=andy.almed"));
                         startActivity(intent);
                     }
                 });


                 like4.setOnClickListener(new View.OnClickListener(){

                     @Override
                     public void onClick(View v) {

                         Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=andy.almed"));
                         startActivity(intent);
                     }
                 });

                 like5.setOnClickListener(new View.OnClickListener(){

                     @Override
                     public void onClick(View v) {

                         Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=andy.almed"));
                         startActivity(intent);
                     }
                 });


           //      getSupportActionBar().setDisplayUseLogoEnabled(false);
              //   getSupportActionBar().setDisplayShowTitleEnabled(false);


                 // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                 // setSupportActionBar(toolbar);




          //      ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
          //              this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

           //      drawer.setDrawerListener(toggle);
           //      toggle.syncState();




          //       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //     toolbar.setNavigationIcon(R.drawable.settings48);

              //   toolbar.setLogo(R.drawable.disorder);


                 //       String html = "</p><font color=\"red\"> Some text </font> </p> other text";


                 NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);


                 final Intent a = new Intent(MainActivity.this, andy.almed.Page1.class);











                 listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                     public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                         final SharedPreferences prefsMain1 = getSharedPreferences("prefsmain1", 0);

                                prefsMain1.edit().putInt("scroll_position",position).commit();


                         String item = (String) listView.getItemAtPosition(position);


                         int index = getIndexOFValue(item, formList);


                         String path = formList.get(index).get("file");



                                int fileId = getResources().getIdentifier(path, "raw", getPackageName());
                             a.putExtra("text", fileId);

            //             p.edit().putInt("size", size).commit();

                             a.putExtra("size", size);
                             view.setSelected(true);
                             startActivity(a);
                         }


                     });








                 }


       @Override
       protected void onResume() {
           super.onResume();

        //  pos = p.getInt("position", 0);
        //  assetJson = p.getString("assetJson", null);
           adapter.notifyDataSetChanged();

       }

             public String loadJSONFromAsset(){
                 String json = null;



                 try {
                     InputStream is = getAssets().open(assetJson);
                     int size = is.available();
                     byte[] buffer = new byte[size];
                     is.read(buffer);
                     is.close();
                     json = new String(buffer, "UTF-8");
                 } catch (IOException ex) {
                     ex.printStackTrace();
                     return null;
                 }
                 return json;
             }


             public static int getIndexOFValue(String value, ArrayList<HashMap<String, String>> formList) {

                 int k = 0;
                 for (HashMap<String, String> map : formList) {
                     if (map.containsValue(value)) {
                         return k;
                     }
                     k++;
                 }
                 return -1;
             }




             public void setLocale(String localeCode){
                 Locale locale = new Locale(localeCode);
                 Locale.setDefault(locale);
                 Configuration config = new Configuration();
                 config.locale = locale;
                 getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
          //       Intent intent=new Intent();
         //        intent.setClass(act, act.getClass());
       //          act.startActivity(intent);
        //         act.finish();

             }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


             public static String[] getStringArray(ListAdapter adapter){
                 String[] a = new String[adapter.getCount()];

                 for(int i=0; i<a.length; i++)
                     a[i] = adapter.getItem(i).toString();

                 return a;
             }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }


             public ArrayList getArray (ArrayAdapter adapter) {

                 ArrayList array = null;
                 for (int i = 0; i < adapter.getCount(); i++) {
                     Object obj = adapter.getItem(i);
                     array.add(obj);
                 }
                 return array;

             }




        //     @Override
        //     public boolean onOptionsItemSelected(MenuItem item) {
        //         switch (item.getItemId()) {
        //             case R.id.action_settings:
//
//
//
        //                 Collections.sort(Arrays.asList(disordersArrayFull), new Comparator<String>() {
        //                     @Override
        //                     public int compare(String s, String t1) {
        //                         return -s.compareToIgnoreCase(t1);
        //                     }
        //                 });
//
//
        //                 return true;
        //             case R.id.action_settings2:
//
//
        //                 Collections.sort(Arrays.asList(disordersArrayFull), String.CASE_INSENSITIVE_ORDER);
//
        //                 return true;
//
        //             default:
        //        //         Collections.sort(Arrays.asList(disordersArrayFull), String.CASE_INSENSITIVE_ORDER);
//
        //                 return super.onOptionsItemSelected(item);
        //         }
        //     }










         }
