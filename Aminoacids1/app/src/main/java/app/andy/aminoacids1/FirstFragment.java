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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Andrey Borzenko on 11.03.2017.
 */

public class FirstFragment extends Fragment {
    // Store instance variables
    private String code;

    //public int positive;
 //   public int negative;
    public int formula;

    public static int counter = 0;
    public static int badpoints = 0;
    //public static int data;
    private int page;
    public Boolean b = false;
    // newInstance constructor for creating fragment with arguments
    public static FirstFragment newInstance(int page, String code) { // заменить на класс Question
        FirstFragment fragmentFirst = new FirstFragment();
        Bundle args = new Bundle();
        // args.putSerializable();
        args.putInt("page", page);
        args.putString("code", code);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("page", 0);
        code = getArguments().getString("code");
       // positive= getArguments().getInt("positive", -1);


    }


    List<Button> answers = new ArrayList<>(4);


    void prepareImage(ImageView iv, String c){

        // ResourcesCompat rc = new ResourcesCompat();
        Class<?> clazz = R.drawable.class;
        Field resField = null;
        try {
            resField = clazz.getDeclaredField(c);
            resField.setAccessible(true);
            int stringId = resField.getInt(null);
         //   Drawable drawable = getResources().getDrawable(stringId);
          //  Bitmap b = ((BitmapDrawable)drawable).getBitmap();
            iv.setImageResource(stringId);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }




    void prepareButton(Button b, String c){
        b.setTag(R.id.aminocode,c);
       // ResourcesCompat rc = new ResourcesCompat();
        Class<?> clazz = R.string.class;
        Field resField = null;
        try {
            resField = clazz.getDeclaredField(c);
            resField.setAccessible(true);
            int stringId = resField.getInt(null);
            b.setText(getString(stringId)  + " (" + c + ")");
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
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.textView);

        ImageView question = (ImageView) view.findViewById(R.id.imageView);
        codes =getResources().getStringArray(R.array.aminocodes);

        Collections.shuffle(Arrays.asList(codes));

        answers.add((Button) view.findViewById(R.id.button1));
        answers.add((Button) view.findViewById(R.id.button2));
        answers.add((Button) view.findViewById(R.id.button3));
        answers.add((Button) view.findViewById(R.id.button4));
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
        prepareImage(question,code);




        final ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.vpPager);



        final View.OnClickListener MyListener = new View.OnClickListener() {
            public void onClick(View v) {
                String buttonCode = (String) v.getTag(R.id.aminocode);

                final int nextFragment = viewPager.getCurrentItem() + 1;
                if(buttonCode.equals(code)) {
                    counter++;
                    v.setBackgroundColor(Color.GREEN);



                    viewPager.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            viewPager.setCurrentItem(nextFragment, true);
                        }
                    }, 300);


                } else {

                    badpoints++;
                    int trialsLeft = 5 - badpoints;



                  v.setBackgroundColor(Color.RED);
                }

            }
        };

        int testquestion = page + 1;

        tvLabel.setText(getString(R.string.question) + " " + testquestion);
        tvLabel.setTextSize(20);
    //    question.setImageResource(formula);

        for(Button b:answers){
            b.setOnClickListener(MyListener);
        }

        return view;
    }




}
