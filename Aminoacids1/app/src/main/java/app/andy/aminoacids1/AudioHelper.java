package app.andy.aminoacids1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

/**
 * Created by Andrey Borzenko on 19.03.2017.
 */

public class AudioHelper {
    private static AudioHelper instance=null;
    SoundPool pool;
    Context appContext;
    boolean ready = false;
    int clickSoundId;

    public static  AudioHelper getInstance(){
        return instance;
    }

    static AudioHelper createInstance(Context c){
        if(instance == null) {
            instance = new AudioHelper(c);
        }
        return instance;
    }
    AudioHelper(Context c){
        pool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        appContext = c.getApplicationContext();
        pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        if(status==0){
                            ready = true;
                        }else{
                            Log.e("AudioHelper","Unable to load sound!");
                        }
            }
        });

        clickSoundId=pool.load(appContext,R.raw.click,0);
    }

    public void playClick(){

            if (ready) {
                pool.play(clickSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
            }


    }

}
