package waldrapps.plannit.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import waldrapps.plannit.fragments.CalendarFragment;

public class CalendarPagerAdapter extends FragmentPagerAdapter {

    private int pageCount;

    public CalendarPagerAdapter(FragmentManager fm, int pageCount) {
        super(fm);
        this.pageCount = pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        //Instantiate the fragment for the given page
        return CalendarFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return pageCount;
    }
}
