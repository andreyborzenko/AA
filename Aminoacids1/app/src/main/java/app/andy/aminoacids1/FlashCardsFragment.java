package app.andy.aminoacids1;

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

/**
 * Created by Andrey Borzenko on 16.03.2017.
 */

public class FlashCardsFragment extends Fragment {
    // Store instance variables
    private String code;

    //public int positive;
    //   public int negative;
    public int formula;

    public static int counter = 0;
    //public static int data;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static FlashCardsFragment newInstance(int page, String code) { // заменить на класс Question
        FlashCardsFragment flashCardsFragment = new FlashCardsFragment();
        Bundle args = new Bundle();
        // args.putSerializable();
        args.putInt("page", page);
        args.putString("code", code);
        flashCardsFragment.setArguments(args);
        return flashCardsFragment;
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




    void text (TextView b, String c){
        b.setTag(R.id.aminocode,c);
        // ResourcesCompat rc = new ResourcesCompat();
        Class<?> clazz = R.string.class;
        Field resField = null;
        try {
            resField = clazz.getDeclaredField(c);
            resField.setAccessible(true);
            int stringId = resField.getInt(null);
            b.setText(stringId);
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
        View view = inflater.inflate(R.layout.flashcards_fragment, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.cardNumber);

        TextView name = (TextView) view.findViewById(R.id.name);

        final TextView codeText = (TextView) view.findViewById(R.id.properties);

        final ImageView question = (ImageView) view.findViewById(R.id.cardImage);

        Button showCard = (Button) view.findViewById(R.id.showCard) ;
        Button nextCard = (Button) view.findViewById(R.id.nextCard) ;

        codes =getResources().getStringArray(R.array.aminocodes);

        Collections.shuffle(Arrays.asList(codes));


        //Map<String,String> list =((ActivityGuessFormula)getActivity()).formList;




        final ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.vpPager);





        showCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareImage(question, code);
                codeText.setText(code);
            }
        });







        nextCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonCode = (String) v.getTag(R.id.aminocode);
                final int nextFragment = viewPager.getCurrentItem() + 1;
                viewPager.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        viewPager.setCurrentItem(nextFragment, true);
                    }
                }, 300);


            }
        });





        text(name, code);

        int testquestion = page + 1;

        tvLabel.setText(getString(R.string.card) + " " + testquestion);
        tvLabel.setTextSize(20);
        //    question.setImageResource(formula);



        return view;
    }




}
