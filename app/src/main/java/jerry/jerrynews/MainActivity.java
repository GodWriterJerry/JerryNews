package jerry.jerrynews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import me.weyye.library.colortrackview.ColorTrackTabLayout;

public class MainActivity extends AppCompatActivity {

    protected ColorTrackTabLayout mTab;
    protected ViewPager mViewPager;
    //protected ArrayList<String> list = new ArrayList<String>();
    protected List<Fragment1> fragments = new ArrayList<Fragment1>();
    protected String[] titles = new String[]{"头条","社会","国内","国际","娱乐","体育","军事","科技","财经","时尚"};
    //0522  20:16


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
    }

    protected void initView() {
        mTab = (ColorTrackTabLayout) findViewById(R.id.tab);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    protected void init() {

        //0522  20:16

        // 通过bundle传递次序i。

        for(int i = 1;i<=10;i++){
            Fragment1 fragment = new Fragment1();
            Bundle bundle = new Bundle();
            bundle.putInt("arg",i);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }


        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        mTab.setTabPaddingLeftAndRight(20, 20);
        mTab.setSelectedTabIndicatorHeight(0);
        //默认选中第5个
        mTab.setLastSelectedTabPosition(0);
        //移动到第5个
        mTab.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(4);
        mTab.setupWithViewPager(mViewPager);
    }
}
