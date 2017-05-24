package jerry.jerrynews;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.IOException;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Administrator on 2017/5/24.
 */
public class DetailActivity extends SwipeBackActivity {

    public static final String URL_EXTRA = "URL_EXTRA";
    public static final String KEY_EXTRA = "KEY_EXTRA";
    public static final String TITLE_EXTRA = "TITLE_EXTRA";
    private NewsBean.ResultBean.DataBean mNews;
    private ProgressBar mProgressBar;
    private HtmlTextView mTVNewsContent;
    private ImageView ivImage;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mTVNewsContent = (HtmlTextView) findViewById(R.id.htNewsContent);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeSize(ToolsUtil.getWidthInPx(this));
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        mNews = (NewsBean.ResultBean.DataBean) getIntent().getSerializableExtra("news");
        Log.d("mNews=", mNews.getUrl());
        showProgress();
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(mNews.getTitle());
        Picasso.with(DetailActivity.this).load(mNews.getThumbnail_pic_s())
                .placeholder(R.mipmap.loading)
                .into(ivImage);
        new Thread() {
            @Override
            public void run() {
                super.run();
                String content = new String();
                try {
                    Document doc = Jsoup.connect(mNews.getUrl()).get();
                    Log.e("一、HTML內容", mNews.getUrl());
                    Elements els = doc.select("p.section");
                    Log.e("一、HTML內容", els.toString());
                    for (int i = 0; i < els.size(); i++) {
                        Element el = els.get(i);
                        Log.e("1.标题", el.text());
                        content = "\2"+content + el.text()+"\n"+"\n"+"\2";
                    }
                    final String finalContent = content;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTVNewsContent.setText(finalContent);
                            hideProgress();
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }


    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }
}
