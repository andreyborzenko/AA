package andy.memes;



import java.util.Locale;

/**
 * Created by Raiv on 12.05.2016.
 */
public enum SupportedLocale {
    ENGLISH("English", "EN"),
    RUSSIAN("Русский", "RU"),
    DEUTSCH("Deutsch","DE"),
    FRANCAIS("Français","FR"),
    CHINESE("中文","ZH"),
    SPANISH("Español","ES"),
    PORTUGESE("Português","PT"),
    JAPANESE("日本語","JA"),
  //  HINDI("हिन्दी","HI"),
    ;

    private final String resourceName;
    private final Locale androidLocale;
    private final String langCode;

    SupportedLocale(String langname,String langCode){
        this.resourceName = langname;
        this.langCode=langCode;
        this.androidLocale=new Locale(langCode);
    }

    public static SupportedLocale getLocaleByLanguage(String language){
        for (SupportedLocale locale: SupportedLocale.values()){
            String lang = locale.getAndroidLocale().getLanguage().toLowerCase();

            if( lang.equals(language.toLowerCase())){
                return locale;
            }
        }
        return  ENGLISH;
    }
    public String getLangCode(){
        return langCode;
    }

    public String getResourceName() {
        return resourceName;
    }

    @Override
    public String toString(){
        return resourceName;
    }

    public Locale getAndroidLocale() {
        return androidLocale;
    }
}
