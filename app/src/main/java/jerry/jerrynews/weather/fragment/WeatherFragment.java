package jerry.jerrynews.weather.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jerry.jerrynews.R;
import jerry.jerrynews.weather.Adapter.HourAdapter;
import jerry.jerrynews.weather.Data.Forecast;
import jerry.jerrynews.weather.Data.Hour;
import jerry.jerrynews.weather.Data.Hourly;
import jerry.jerrynews.weather.Data.Weather;
import jerry.jerrynews.weather.Ui.WeatherLineChart;
import jerry.jerrynews.weather.Util.TaskKiller;
import jerry.jerrynews.weather.Util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/27.
 */

public class WeatherFragment extends Fragment {
    private static int SIGN_NO_INTERNET = 0;
    private static int SIGN_ALARMS = 1;
    //百度定位
    private LocationClient mlocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();
    public static String currentPosition = "";
    List<String> permissionList = new ArrayList<>();
    //view部分
    private TextView weather_tv;
    //初始化数据
    public OkHttpClient mOkHttpClient;
    //view
    private ScrollView weatherLayout;
    private LinearLayout mainLayout;

    // 以下是 weather_now 的内容

    private TextView titleCity;

    private TextView degreeText;

    private TextView weatherInfoText;

    private RelativeLayout weaherNowLayout;

    private TextView updateTimeText;
    // 以下是 weather_hour 的内容

    private TextView hourTime;

    private TextView hourText;

    private TextView hourDegree;

    private List<Hour> hourList = new ArrayList<>();

    private RecyclerView recyclerView;

    private HourAdapter hourAdapter;

    // 以下是 weather_aqi 内容

    private TextView aqiText;

    private TextView pm25Text;

    private TextView coText;

    private TextView o3Text;

    private TextView pm10Text;

    private TextView so2Text;

    // 以下是 weather_forecast 内容
    private LinearLayout forecastLayout;

    // 以下是 weather_suggestion 内容
    private TextView carWashText;

    private TextView sportText;

    private TextView comfortText;

    private TextView uvText;

    private TextView clothesText;

    private TextView coldText;

    private Button carWashBtn;

    private Button sportBtn;

    private Button comfortBtn;

    private Button uvBtn;

    private Button clothesBtn;

    private Button coldBtn;

    private String carWashInfo;
    private String carWashSign;

    private String sportInfo;
    private String sportSign;

    private String comfortInfo;
    private String comfortSign;

    private String uvInfo;
    private String uvSign;

    private String clothesInfo;
    private String clothesSign;

    private String coldInfo;
    private String coldSign;

    public SwipeRefreshLayout swipeRefresh;

    //自定义view
    private WeatherLineChart weatherLineChart;
    private List<Forecast> mForecastList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        // 初始化各种控件
        weatherLayout = (ScrollView) view.findViewById(R.id.weather_layout);
        forecastLayout = (LinearLayout) view.findViewById(R.id.forecast_layout);
        mainLayout = (LinearLayout) view.findViewById(R.id.main_layout);
        // weather_now
        titleCity = (TextView) view.findViewById(R.id.title_city);
        degreeText = (TextView) view.findViewById(R.id.degree_text);
        weatherInfoText = (TextView) view.findViewById(R.id.weather_info_text);
        weaherNowLayout = (RelativeLayout) view.findViewById(R.id.weather_now_layout);
        updateTimeText = (TextView) view.findViewById(R.id.update_time_text);
        // weather_hour
        hourDegree = (TextView) view.findViewById(R.id.hour_degree);
        hourText = (TextView) view.findViewById(R.id.hour_text);
        hourTime = (TextView) view.findViewById(R.id.hout_time);

        recyclerView = (RecyclerView) view.findViewById(R.id.weather_hourly);
        hourAdapter = new HourAdapter(hourList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(hourAdapter);

        // weather_aqi
        aqiText = (TextView) view.findViewById(R.id.aqi_text);
        pm25Text = (TextView) view.findViewById(R.id.pm25_text);
        coText = (TextView) view.findViewById(R.id.co_text);
        o3Text = (TextView) view.findViewById(R.id.o3_text);
        pm10Text = (TextView) view.findViewById(R.id.pm10_text);
        so2Text = (TextView) view.findViewById(R.id.so2_text);

        // weather_suggestion
        comfortText = (TextView) view.findViewById(R.id.comfort_text);
        carWashText = (TextView) view.findViewById(R.id.car_wash_text);
        sportText = (TextView) view.findViewById(R.id.sport_text);
        uvText = (TextView) view.findViewById(R.id.uv_text);
        clothesText = (TextView) view.findViewById(R.id.clothes_text);
        coldText = (TextView) view.findViewById(R.id.cold_text);
        comfortBtn = (Button) view.findViewById(R.id.comfort_button);
        carWashBtn = (Button) view.findViewById(R.id.car_wash_button);
        sportBtn = (Button) view.findViewById(R.id.sport_button);
        uvBtn = (Button) view.findViewById(R.id.uv_button);
        clothesBtn = (Button) view.findViewById(R.id.clothes_button);
        coldBtn = (Button) view.findViewById(R.id.cold_button);

        //自定义view
        weatherLineChart= (WeatherLineChart) view.findViewById(R.id.weatherLineChart);

        mlocationClient = new LocationClient(getContext());
        mlocationClient.registerLocationListener(myListener);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void initData() {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setScanSpan(0);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        mlocationClient.setLocOption(option);
        mlocationClient.start();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            // 如果存在某个权限没有处理
                            getActivity().finish();
                        }
                    }
                } else {
                    // 发生未知错误
                    shortToast("权限申请出现位置错误");
                }
                break;
            default:
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            currentPosition = location.getCity();
            if (currentPosition != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestWeather(currentPosition);
                    }
                }).start();
            } else {
                shortToast("没有获取到定位权限，请打开定位权限后再打开此应用");
            }
            Log.i("AAAAAAAAAAAAAAAAAAAA", location.getCity());
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    private void shortToast(final String msg) {

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread() {
            public void run() {
                Looper.prepare();
                new Handler().post(runnable);//在子线程中直接去new 一个handler
                Looper.loop();//这种情况下，Runnable对象是运行在子线程中的，可以进行联网操作，但是不能更新UI
            }
        }.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mlocationClient.stop();
    }

    public void requestWeather(final String cityName) {
        String address = "https://api.heweather.com/v5/weather?city=" + cityName + "&key=bc0418b57b2d4918819d3974ac1285d9";
        mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                shortToast("当前位置:" + cityName);
                final String weatherResMag = response.body().string();
                Log.e("asasasasa", weatherResMag);
                final Weather weather = Utility.handleWeatherResponse(weatherResMag);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWeatherInfo(weather);
                    }
                });

            }

            @Override
            public void onFailure(Call call, IOException e) {
                shortToast("获取天气数据失败");
            }
        });
    }

    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String degree = weather.now.temperature;
        String weatherInfo = weather.now.more.info;
        String updateTime = weather.basic.update.loc;

        titleCity.setText(cityName);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        updateTimeText.setText("数据更新时间: " + updateTime.split(" ")[1]);

        //未来几天的天气预报
        // TODO: 2017/6/3 把列表转化为折线图
        /*forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            // 将未来几天的天气添加到视图中
            View view = LayoutInflater.from(getContext()).inflate(R.layout.weather_forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.data_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxMinText = (TextView) view.findViewById(R.id.max_min_text);
            ImageView weatherPic = (ImageView) view.findViewById(R.id.weather_pic);

            // 动态获取 资源id
            String weatherCode = "weather_" + forecast.more.code;
            int resId = getResources().getIdentifier(weatherCode, "drawable", getContext().getPackageName());
            if (resId != 0) {
                weatherPic.setImageResource(resId);
            }


            dateText.setText(Time.parseTime(forecast.date));
            infoText.setText(forecast.more.info);
            maxMinText.setText(forecast.temperature.max + " ～ " + forecast.temperature.min);
            forecastLayout.addView(view);
        }*/
        // TODO: 2017/6/3 开始自定义折线图

        for (Iterator forecast = weather.forecastList.iterator(); forecast.hasNext();) {
            Forecast forecastMsg = (Forecast) forecast.next();
            mForecastList.add(forecastMsg);
        }

        weatherLineChart.setWeather(mForecastList);
        Toast.makeText(getContext(),mForecastList.size()+"",Toast.LENGTH_SHORT).show();

        hourList.clear();
        for (Hourly hourly : weather.hourlyList) {
            Hour hour = new Hour();
            hour.setDegree(hourly.tmp + "°");
            hour.setText(hourly.cond.txt);
            hour.setTime(hourly.date.split(" ")[1]);
            hourList.add(hour);
        }

        hourAdapter.notifyDataSetChanged();


        // weather_aqi 空气质量

        String infoText = "无";
        if (weather.aqi == null) {
            aqiText.setText(infoText);
            pm25Text.setText(infoText);
            coText.setText(infoText);
            o3Text.setText(infoText);
            pm10Text.setText(infoText);
            so2Text.setText(infoText);
        } else {
            if (weather.aqi.city.aqi != null) {
                aqiText.setText(weather.aqi.city.aqi);
            } else {
                aqiText.setText(infoText);
            }

            if (weather.aqi.city.pm25 != null) {
                pm25Text.setText(weather.aqi.city.pm25);
            } else {
                pm25Text.setText(infoText);
            }

            if (weather.aqi.city.co != null) {
                coText.setText(weather.aqi.city.co);
            } else {
                coText.setText(infoText);
            }

            if (weather.aqi.city.o3 != null) {
                o3Text.setText(weather.aqi.city.o3);
            } else {
                o3Text.setText(infoText);
            }

            if (weather.aqi.city.pm10 != null) {
                pm10Text.setText(weather.aqi.city.pm10);
            } else {
                pm10Text.setText(infoText);
            }

            if (weather.aqi.city.so2 != null) {
                so2Text.setText(weather.aqi.city.so2);
            } else {
                so2Text.setText(infoText);
            }
        }
        aqiText.getPaint().setFakeBoldText(true);
        pm25Text.getPaint().setFakeBoldText(true);
        coText.getPaint().setFakeBoldText(true);
        o3Text.getPaint().setFakeBoldText(true);
        pm10Text.getPaint().setFakeBoldText(true);
        so2Text.getPaint().setFakeBoldText(true);

        // 舒适指数

        comfortSign = weather.suggestion.comfort.sign;
        carWashSign = weather.suggestion.carWash.sign;
        sportSign = weather.suggestion.sport.sign;
        uvSign = weather.suggestion.uv.sign;
        clothesSign = weather.suggestion.clothes.sign;
        coldSign = weather.suggestion.cold.sign;


        comfortText.setText(comfortSign);
        comfortText.getPaint().setFakeBoldText(true);
        carWashText.setText(carWashSign);
        carWashText.getPaint().setFakeBoldText(true);
        sportText.setText(sportSign);
        sportText.getPaint().setFakeBoldText(true);
        uvText.setText(uvSign);
        uvText.getPaint().setFakeBoldText(true);
        clothesText.setText(clothesSign);
        clothesText.getPaint().setFakeBoldText(true);
        coldText.setText(coldSign);
        coldText.getPaint().setFakeBoldText(true);

        comfortInfo = weather.suggestion.comfort.info;
        carWashInfo = weather.suggestion.carWash.info;
        sportInfo = weather.suggestion.sport.info;
        uvInfo = weather.suggestion.uv.info;
        clothesInfo = weather.suggestion.clothes.info;
        coldInfo = weather.suggestion.cold.info;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                weatherLayout.setVisibility(View.VISIBLE);
                mainLayout.setVisibility(View.VISIBLE);
            }
        });


        // 天气预警
        if (weather.alarms != null) {
            String level = weather.alarms.level;
            String title = weather.alarms.title;
            String text = weather.alarms.txt;
            showDialog(title, text, SIGN_ALARMS);
        } else {
        }
    }

    public void showDialog(String title, String info, final int SIGN) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setMessage(info);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (SIGN == SIGN_NO_INTERNET) {
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);
                    TaskKiller.dropAllAcitivty();
                }

                if (SIGN == SIGN_ALARMS) {
                    alertDialog.setCancelable(true);
                }

            }
        });
        alertDialog.show();
    }

    public NetworkInfo getNetworkInfo() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }


}