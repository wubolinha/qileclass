package air.edu.qile.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2018/4/16.
 */

public class ListFragmentPagerAdapter   extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 4;
    private Fragment_1 myFragment1 = null;
    private Fragment_2 myFragment2 = null;
    private Fragment_3 myFragment3 = null;
    private Fragment_4 myFragment4 = null;



    public ListFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        myFragment1 = new Fragment_1();
        myFragment2 = new Fragment_2();
        myFragment3 = new Fragment_3();
        myFragment4 = new Fragment_4();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = myFragment1;
                break;
            case 1:
                fragment = myFragment2;
                break;
            case 2:
                fragment = myFragment3;
                break;
            case 3:
                fragment = myFragment4;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
}
