package app.andy.aminoacids1;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;

/**
 * Created by Andrey Borzenko on 19.03.2017.
 */

public abstract class AudioClickListener implements View.OnClickListener {
    @Override
    public final void onClick(View v) {


        AudioHelper.getInstance().playClick();
        onAudioClick(v);
    }

    public abstract void onAudioClick(View v);

}
