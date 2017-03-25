package app.andy.aminoacids1;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Andrey Borzenko on 16.03.2017.
 */

public class GuessCodeFragment extends Fragment {
    // Store instance variables
    private String code;

    //public int positive;
    //   public int negative;
    public int formula;

    public static int counter = 0;
    //public static int data;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static GuessCodeFragment newInstance(int page, String code) { // заменить на класс Question
        GuessCodeFragment guessCodeFragment = new GuessCodeFragment();
        Bundle args = new Bundle();
        // args.putSerializable();
        args.putInt("page", page);
        args.putString("code", code);
        guessCodeFragment.setArguments(args);
        return guessCodeFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("page", 0);
        code = getArguments().getString("code");
        // positive= getArguments().getInt("positive", -1);


    }


    List<ImageButton> answers = new ArrayList<>(4);


    void text(TextView textViewv, String c){

        // ResourcesCompat rc = new ResourcesCompat();
        Class<?> clazz = R.string.class;
        Field resField = null;
        try {
            resField = clazz.getDeclaredField(c);
            resField.setAccessible(true);
            int stringId = resField.getInt(null);
            //   Drawable drawable = getResources().getDrawable(stringId);
            //  Bitmap b = ((BitmapDrawable)drawable).getBitmap();
            textViewv.setText(stringId);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }




    void prepareButton(ImageButton b, String c){
        b.setTag(R.id.aminocode,c);
        // ResourcesCompat rc = new ResourcesCompat();
        Class<?> clazz = R.drawable.class;
        Field resField = null;
        try {
            resField = clazz.getDeclaredField(c);
            resField.setAccessible(true);
            int stringId = resField.getInt(null);
            b.setImageResource(stringId);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    String[] codes;

    // Inflate the view for the fragment based on layout XML
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guessnamefragment, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.textView0);

        //     ImageView question = (ImageView) view.findViewById(R.id.imageView);

        TextView textView2 = (TextView) view.findViewById(R.id.textView2);

        codes =getResources().getStringArray(R.array.aminocodes);

        Collections.shuffle(Arrays.asList(codes));

        answers.add((ImageButton) view.findViewById(R.id.imagebutton1));
        answers.add((ImageButton) view.findViewById(R.id.imagebutton2));
        answers.add((ImageButton) view.findViewById(R.id.imagebutton3));
        answers.add((ImageButton) view.findViewById(R.id.imagebutton4));
        Collections.shuffle(answers);
        //Map<String,String> list =((ActivityGuessFormula)getActivity()).formList;
        prepareButton(answers.get(0),code);
        List<String> used = new ArrayList<>();
        used.add(code);
        Random r = new Random();
        for(int i=1;i<answers.size();i++){
            String str;
            do {
                str = codes[r.nextInt(codes.length)];
            }while (used.contains(str));
            prepareButton(answers.get(i),str);
            used.add(str);
        }

        text(textView2, code);



        //     prepareImage(question,code);




        final ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.vpPager1);



        final View.OnClickListener MyListener = new View.OnClickListener() {
            public void onClick(View v) {
                String buttonCode = (String) v.getTag(R.id.aminocode);
                final int nextFragment = viewPager.getCurrentItem() + 1;
                ImageButton aButton = (ImageButton)v;
                if(buttonCode.equals(code)) {
                    counter++;
                    aButton.setImageResource(R.drawable.checked);

                    viewPager.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            viewPager.setCurrentItem(nextFragment, true);
                        }
                    }, 300);


                } else {

                    aButton.setImageResource(R.drawable.unchecked);
                }

            }
        };

        int testquestion = page + 1;

        tvLabel.setText(getString(R.string.question) + " " + testquestion);
        tvLabel.setTextSize(20);
        //    question.setImageResource(formula);

        for(ImageButton b:answers){
            b.setOnClickListener(MyListener);
        }

        return view;
    }




}