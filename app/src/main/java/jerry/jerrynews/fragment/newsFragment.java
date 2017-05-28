package jerry.jerrynews.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jerry.jerrynews.bean.NewsBean;
import jerry.jerrynews.R;
import jerry.jerrynews.adapter.NewsAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/17 0017.
 */

public class NewsFragment extends Fragment {

    public OkHttpClient mOkHttpClient;
    private NewsAdapter adapter;
    private RecyclerView recycler_view;
    private LinearLayoutManager layoutManager;
    private int arg;
    private List<NewsBean.ResultBean.DataBean> mNewsList = new ArrayList<>();
    private android.os.Handler mHandler;

    private static final int MSG_GET_NEWS = 1001;

    private final String URL1 = "http://v.juhe.cn/toutiao/index?type=top&key=db6ccd0992a63eef37c4fcdc3d6dcb93";
    private final String URL2 = "http://v.juhe.cn/toutiao/index?type=shehui&key=db6ccd0992a63eef37c4fcdc3d6dcb93";
    private final String URL3 = "http://v.juhe.cn/toutiao/index?type=guonei&key=db6ccd0992a63eef37c4fcdc3d6dcb93";
    private final String URL4 = "http://v.juhe.cn/toutiao/index?type=guoji&key=db6ccd0992a63eef37c4fcdc3d6dcb93";
    private final String URL5 = "http://v.juhe.cn/toutiao/index?type=yule&key=db6ccd0992a63eef37c4fcdc3d6dcb93";
    private final String URL6 = "http://v.juhe.cn/toutiao/index?type=tiyu&key=db6ccd0992a63eef37c4fcdc3d6dcb93";
    private final String URL7 = "http://v.juhe.cn/toutiao/index?type=junshi&key=db6ccd0992a63eef37c4fcdc3d6dcb93";
    private final String URL8 = "http://v.juhe.cn/toutiao/index?type=keji&key=db6ccd0992a63eef37c4fcdc3d6dcb93";
    private final String URL9 = "http://v.juhe.cn/toutiao/index?type=caijing&key=db6ccd0992a63eef37c4fcdc3d6dcb93";
    private final String URL10 = "http://v.juhe.cn/toutiao/index?type=shishang&key=db6ccd0992a63eef37c4fcdc3d6dcb93";

    public NewsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.news_fragment, container, false);

        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());

        recycler_view.setLayoutManager(layoutManager);

        adapter = new NewsAdapter(mNewsList);

        recycler_view.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arg = getArguments().getInt("arg");

        initHandler();
        initData();
    }

    private void initHandler() {
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == MSG_GET_NEWS) {
                    adapter.changData(mNewsList);
                    return true;
                }
                return false;
            }
        });

    }


  
    //通过okhttp获得数据

    private void initData() {
        mOkHttpClient = new OkHttpClient();
        Request request = null;
        //0523  11:13
        switch (arg) {
            case 1:
                request = new Request.Builder().url(URL1).build();
                break;
            case 2:
                request = new Request.Builder().url(URL2).build();
                break;
            case 3:
                request = new Request.Builder().url(URL3).build();
                break;
            case 4:
                request = new Request.Builder().url(URL4).build();
                break;
            case 5:
                request = new Request.Builder().url(URL5).build();
                break;
            case 6:
                request = new Request.Builder().url(URL6).build();
                break;
            case 7:
                request = new Request.Builder().url(URL7).build();
                break;
            case 8:
                request = new Request.Builder().url(URL8).build();
                break;
            case 9:
                request = new Request.Builder().url(URL9).build();
                break;
            case 10:
                request = new Request.Builder().url(URL10).build();
                break;
            default:
                break;

        }
        //0523 11:13

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("AAA", "GET DATA FAILED");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                NewsBean newsPageBean = gson.fromJson(response.body().string(), NewsBean.class);
                mNewsList = newsPageBean.getResult().getData();
                mHandler.sendEmptyMessage(MSG_GET_NEWS);
            }
        });

    }
}
