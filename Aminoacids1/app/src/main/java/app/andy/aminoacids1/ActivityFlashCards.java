package app.andy.aminoacids1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Andrey Borzenko on 16.03.2017.
 */

public class ActivityFlashCards extends ActivityWithLocales {
    FragmentPagerAdapter adapterViewPager;
 //   public static ActivityFlashCards mInstance = null;
    public static int lang = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);

        String aminocodes [] = getResources().getStringArray(R.array.aminocodes);

        Collections.shuffle(Arrays.asList(aminocodes));

        adapterViewPager = new ActivityFlashCards.MyPagerAdapter(getSupportFragmentManager(),aminocodes);



        vpPager.setAdapter(adapterViewPager);
  //      mInstance = this;
        lang = getIntent().getIntExtra("lan", 0);


    }


    public static class QuestionVariant {
        String text;
        int modifier;
    }

    class Question implements Serializable {
        String code;
        String question;
        ArrayList<ActivityGuessFormula.QuestionVariant> answers;
    }








    public class MyPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 8;
        private String[] codes;

        public MyPagerAdapter(FragmentManager fragmentManager, String[] codes) {
            super(fragmentManager);
            this.codes=codes;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return codes.length+1;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            if(position<codes.length) {
                return FlashCardsFragment.newInstance(position,codes[position]);
            }

            return ResultFragment.getInstance(FirstFragment.counter,codes.length);
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }


}
