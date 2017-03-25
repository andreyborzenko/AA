package app.andy.aminoacids1;
import android.app.Application;
/**
 * Created by Andrey Borzenko on 07.03.2017.
 */

public class LocaleApplication extends Application{



    @Override
    public void onCreate() {
        super.onCreate();
        LocaleHelper.onCreate(this);
        LocaleHelper.applyLocale(this);
        AudioHelper.createInstance(this);
    }
}
