package jerry.jerrynews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/27.
 */

public class TopAdapter extends FragmentPagerAdapter {
    private List<Fragment> topFragmentList;

    public TopAdapter(FragmentManager fm, List<Fragment> topFragmentList) {
        super(fm);
        this.topFragmentList = topFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return topFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return topFragmentList.size();
    }
}
