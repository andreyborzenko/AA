package app.andy.aminoacids1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Andrey Borzenko on 07.03.2017.
 */

public class LocaleHelper {

    private static Map<String,SupportedLocale> localeMap = makeLocaleMap();

    private static Map<String,SupportedLocale> makeLocaleMap(){
        Map<String,SupportedLocale> localeMap = new HashMap<String, SupportedLocale>();
        localeMap.put("RU",SupportedLocale.RUSSIAN);//Russia
        localeMap.put("ET",SupportedLocale.RUSSIAN);//Estonia
        localeMap.put("LT",SupportedLocale.RUSSIAN);//latviya
        localeMap.put("LV",SupportedLocale.RUSSIAN);//lituania
        localeMap.put("BE",SupportedLocale.RUSSIAN);//belorussiyas
        //localeMap.put("md",SupportedLocale.RUSSIAN);//Moldova
        localeMap.put("AB",SupportedLocale.RUSSIAN);//abhazia
        localeMap.put("KA",SupportedLocale.RUSSIAN);//georgiya
        localeMap.put("HY",SupportedLocale.RUSSIAN);//armenia
        localeMap.put("AZ",SupportedLocale.RUSSIAN);//azerbaijan
        localeMap.put("TK",SupportedLocale.RUSSIAN);//turkmenistan
        localeMap.put("UZ",SupportedLocale.RUSSIAN);//uzbekistan
        localeMap.put("TG",SupportedLocale.RUSSIAN);//tadjikistan
        localeMap.put("KY",SupportedLocale.RUSSIAN);//kyrgyzstan
        localeMap.put("KK",SupportedLocale.RUSSIAN);//kazakhstan
        localeMap.put("EN",SupportedLocale.ENGLISH);//kazakhstan
        localeMap.put("DE",SupportedLocale.DEUTSCH);//kazakhstan
        localeMap.put("FR",SupportedLocale.FRANCAIS);//kazakhstan
        localeMap.put("ZH",SupportedLocale.CHINESE);
        localeMap.put("ES",SupportedLocale.SPANISH);
        localeMap.put("PT",SupportedLocale.PORTUGESE);
        localeMap.put("JA",SupportedLocale.JAPANESE);
        localeMap.put("HI",SupportedLocale.HINDI);





        return localeMap;
    }

    private static SupportedLocale selectedLocale=null;


    private static volatile SharedPreferences prefs;

    static SharedPreferences getPreferences(Context context){
        if(prefs==null){
            synchronized (LocaleHelper.class){
                if(prefs==null){
                    prefs = context.getApplicationContext().getSharedPreferences("locale",0);
                }
            }
        }
        return prefs;
    }


    public static void onCreate(Context context) {
        String selected = getPreferences(context).getString("locale",null);

        if(TextUtils.isEmpty(selected)){

            String current = Locale.getDefault().getLanguage().toUpperCase();
            if (localeMap.containsKey(current)) {
                selectedLocale = localeMap.get(current);
            } else {
                selectedLocale = SupportedLocale.ENGLISH;
            }

        }else{
            selectedLocale=SupportedLocale.getLocaleByLanguage(selected);
        }
        setLocale(context,selectedLocale);

    }


    public static String getLanguage(Context context) {
        return  getPreferences(context).getString("locale", Locale.getDefault().getLanguage());
    }

    public static void setLocale(Context context, SupportedLocale locale) {
        getPreferences(context).edit().putString("locale",locale.getAndroidLocale().getLanguage()).commit();
        selectedLocale=locale;
        updateResources(context, locale);
        if(context!=context.getApplicationContext()) {
            updateResources(context.getApplicationContext(), locale);
        }
    }
    public static void applyLocale(Context context) {
        updateResources(context, selectedLocale);

    }


    public static SupportedLocale getCurrentLocale(){
        return selectedLocale;
    }

    private static void updateResources(Context context, SupportedLocale locale) {
        Locale.setDefault(locale.getAndroidLocale());
        Resources resources = context.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.locale = locale.getAndroidLocale();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

    }
}
