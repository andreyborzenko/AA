package app.andy.aminoacids1;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Andrey Borzenko on 12.03.2017.
 */

public class ResultFragment extends Fragment {


    public static ResultFragment getInstance(int right, int total){
        ResultFragment frag = new ResultFragment();
        Bundle args = new Bundle();
        // args.putSerializable();
        args.putInt("right", right);
        args.putInt("total", total);
        frag.setArguments(args);
        return frag;
    }


    int right;
    int total;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        right= getArguments().getInt("right");
        total = getArguments().getInt("total");

        TextView tv = new TextView(getActivity());

        ViewGroup.LayoutParams params =new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        tv.setLayoutParams(params);
        tv.setText(String.format("%d right answers out of %d !",right,total));


        return tv;


    }
}
