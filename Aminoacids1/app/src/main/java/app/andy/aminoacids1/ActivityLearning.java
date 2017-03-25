package app.andy.aminoacids1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

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

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static java.util.Arrays.asList;


public class ActivityLearning extends ActivityWithLocales {
    public EditText editsearch;
    private AdView adView;
    public static int pos;
    ArrayList<SupportedLocale> locales = new ArrayList<>();

//    public  String assetJson = "en.json";

    public  String assetJson =  String.format("%s.json", LocaleHelper.getLanguage(ActivityLearning.this)).toLowerCase();
    public SharedPreferences p;
    public static int  backcol1;
    public  ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
    String disordersArrayFull[];
    int textlength = 0;
    private ArrayList<String> array_sort = new ArrayList<String>();
    public static int size = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.learning);


        ImageButton backMain = (ImageButton) findViewById(R.id.sett);

        backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        editsearch = (EditText) findViewById(R.id.editText);
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

        adView.setAdUnitId("ca-app-pub-2783112053927174/3180286441");
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


        disordersArrayFull = new HashSet<String>(Arrays.asList(disordersArrayFull)).toArray(new String[0]);

        final SharedPreferences prefsMain = getSharedPreferences("prefsmain", 0);

        backcol1 = prefsMain.getInt("backColor", WHITE);

        final LinearLayout shtick = (LinearLayout) findViewById(R.id.shtick);

        final ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, disordersArrayFull){};

        final ArrayAdapter  adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array_sort){};
        final ListView listView = (ListView) findViewById(R.id.mainListView1);


        listView.setAdapter(adapter);

        shtick.setBackgroundColor(backcol1);
        final ImageButton sort = (ImageButton) findViewById(R.id.sort);

        if (backcol1 == WHITE) {

            Collections.sort(Arrays.asList(disordersArrayFull), String.CASE_INSENSITIVE_ORDER);
            sort.setImageResource(R.drawable.sortaz);

        } else {

            Collections.sort(Arrays.asList(disordersArrayFull), new Comparator<String>() {
                @Override
                public int compare(String s, String t1) {
                    return -s.compareToIgnoreCase(t1);
                }
            });

            sort.setImageResource(R.drawable.sortza);
        }

        editsearch.addTextChangedListener(new TextWatcher() {

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

            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {

       //         ActivityLearning.this.adapter.getFilter().filter(cs);




                //             disordersArrayFull= new String[getArray(adapter).size()];
                //             for (int j = 0; j < getArray(adapter).size(); j++) {
                //                 disordersArrayFull[j] = (String) getArray(adapter).get(j);
                //              }


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
                    Collections.sort(Arrays.asList(disordersArrayFull), new Comparator<String>() {
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
                    Collections.sort(Arrays.asList(disordersArrayFull), String.CASE_INSENSITIVE_ORDER);
                    sort.setImageResource(R.drawable.sortaz);
                    adapter.notifyDataSetChanged();
                    shtick.setBackgroundColor(Color.WHITE);
                    SharedPreferences prefsMain = getSharedPreferences("prefsmain", 0);
                    prefsMain.edit().putInt("backColor", WHITE).commit();
                }
//



            }
        });



                final Intent a = new Intent(ActivityLearning.this, ActivityPage1.class);

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
//








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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ActivityLearning.this, MainActivity.class));
        finish();

    }



}
