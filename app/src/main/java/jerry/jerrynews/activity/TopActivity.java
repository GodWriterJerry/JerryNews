package jerry.jerrynews.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jerry.jerrynews.R;
import jerry.jerrynews.adapter.TopAdapter;
import jerry.jerrynews.fragment.JokeFragment;
import jerry.jerrynews.fragment.MainFragment;
import jerry.jerrynews.fragment.MeFragmemt;
import jerry.jerrynews.utils.CustomViewPager;
import jerry.jerrynews.weather.Fragment.WeatherFragment;

/**
 * Created by Administrator on 2017/5/27.
 */

public class TopActivity extends AppCompatActivity {
    private CustomViewPager top_viewPager;
    private List<Fragment> topFragmentList = new ArrayList<>();
    private ImageButton news_tab, joke_tab, weather_tab, me_tab;
    private TopAdapter mAdatper;
    private Toolbar main_toolbar;
    private DrawerLayout top_drawer_layout;
    private NavigationView top_nav;
    private ActionBarDrawerToggle mDrawerToggle;
    private long triggerAtTimefirst = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        initTopView();
        initBottomViews();
        initFragmentList();
        mAdatper = new TopAdapter(getSupportFragmentManager(), topFragmentList);
        top_viewPager.setAdapter(mAdatper);
        top_viewPager.setOffscreenPageLimit(4);
        top_viewPager.setScanScroll(false);
    }

    private void initTopView() {
        main_toolbar = (Toolbar) findViewById(R.id.main_toolBar);
        setSupportActionBar(main_toolbar);
        top_drawer_layout = (DrawerLayout) findViewById(R.id.top_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, top_drawer_layout, main_toolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        top_drawer_layout.setDrawerListener(mDrawerToggle);
        top_nav = (NavigationView) findViewById(R.id.top_nav);
        top_nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                top_drawer_layout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        long triggerAtTimeSecond = triggerAtTimefirst;
        triggerAtTimefirst = SystemClock.elapsedRealtime();
        if (triggerAtTimefirst - triggerAtTimeSecond <= 2000) {
            super.onBackPressed();
        } else if (top_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            top_drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            Toast.makeText(TopActivity.this, "请再点击 Back 键, 确认退出", Toast.LENGTH_SHORT).show();
        }
    }


    private void initBottomViews() {
        news_tab = (ImageButton) findViewById(R.id.news_tab);
        joke_tab = (ImageButton) findViewById(R.id.joke_tab);
        weather_tab = (ImageButton) findViewById(R.id.weather_tab);
        me_tab = (ImageButton) findViewById(R.id.me_tab);
        news_tab.setBackgroundColor(Color.TRANSPARENT);
        joke_tab.setBackgroundColor(Color.TRANSPARENT);
        weather_tab.setBackgroundColor(Color.TRANSPARENT);
        me_tab.setBackgroundColor(Color.TRANSPARENT);


        news_tab.setImageResource(R.mipmap.selected_tab_news_icon);
        joke_tab.setImageResource(R.mipmap.tab_joke_icon);
        weather_tab.setImageResource(R.mipmap.tab_weather_icon);
        me_tab.setImageResource(R.mipmap.tab_me_icon);


        news_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                top_viewPager.setCurrentItem(0);
                judge();
            }
        });

        joke_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                top_viewPager.setCurrentItem(1);
                judge();
            }
        });

        weather_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                top_viewPager.setCurrentItem(2);
                judge();
            }
        });

        me_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                top_viewPager.setCurrentItem(3);
                judge();
            }
        });

    }

    private void judge() {
        if (top_viewPager.getCurrentItem() == 0) {
            news_tab.setImageResource(R.mipmap.selected_tab_news_icon);
        } else {
            news_tab.setImageResource(R.mipmap.tab_news_icon);
        }
        if (top_viewPager.getCurrentItem() == 1) {
            joke_tab.setImageResource(R.mipmap.selected_tab_joke_icon);
        } else {
            joke_tab.setImageResource(R.mipmap.tab_joke_icon);
        }
        if (top_viewPager.getCurrentItem() == 2) {
            weather_tab.setImageResource(R.mipmap.selected_tab_weather_icon);
        } else {
            weather_tab.setImageResource(R.mipmap.tab_weather_icon);
        }
        if (top_viewPager.getCurrentItem() == 3) {
            me_tab.setImageResource(R.mipmap.selected_tab_me_icon);
        } else {
            me_tab.setImageResource(R.mipmap.tab_me_icon);
        }
    }


    private void initFragmentList() {
        top_viewPager = (CustomViewPager) findViewById(R.id.top_viewPager);
        MainFragment mainF = new MainFragment();
        topFragmentList.add(mainF);
        JokeFragment jokeF = new JokeFragment();
        topFragmentList.add(jokeF);
        WeatherFragment weatherF = new WeatherFragment();
        topFragmentList.add(weatherF);
        MeFragmemt meF = new MeFragmemt();
        topFragmentList.add(meF);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolabr_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(TopActivity.this, "这是设定", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                top_drawer_layout.openDrawer(GravityCompat.START);
            default:
        }
        return true;
    }
}
