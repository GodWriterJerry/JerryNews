package jerry.jerrynews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import jerry.jerrynews.R;

import static jerry.jerrynews.R.id.tab;

/**
 * Created by Administrator on 2017/5/27.
 */

public class MainFragment extends Fragment {

    protected TabLayout mTab;
    protected ViewPager mViewPager;
    protected List<NewsFragment> fragments = new ArrayList<NewsFragment>();
    protected String[] titles = new String[]{"头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        mTab = (TabLayout) view.findViewById(tab);
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        mViewPager.setOffscreenPageLimit(4);
        mTab.setupWithViewPager(mViewPager);
        return view;
    }

    protected void init() {

        for (int i = 1; i <= 10; i++) {
            NewsFragment fragment = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("arg", i);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }

    }

}
