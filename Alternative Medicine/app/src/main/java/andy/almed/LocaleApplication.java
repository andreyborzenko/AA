package andy.almed;

import android.app.Application;

/**
 * Created by chach on 2017-02-16.
 */

public class LocaleApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        LocaleHelper.onCreate(this);

    }
}
