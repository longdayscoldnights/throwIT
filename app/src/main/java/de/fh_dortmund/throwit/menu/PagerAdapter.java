package de.fh_dortmund.throwit.menu;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter{

        private Context ctx;

        public PagerAdapter(FragmentManager fm, Context ctx) {
            super(fm);
            this.ctx = ctx;
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {
                case 0: return HighscoreFragment.newInstance(3);
                case 1: return ThrowFragment.newInstance(ctx);
                case 2: return AchieveFragment.newInstance("","");
                default: return new Fragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
}

