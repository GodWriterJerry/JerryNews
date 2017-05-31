package jerry.jerrynews.weather.Util;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import jerry.jerrynews.weather.Data.Weather;

/**
 * Created by Administrator on 2017/5/29.
 */

public class Utility {

    public static Weather handleWeatherResponse(String weatherResMag) {
        try{
            JSONObject jsonObject = new JSONObject(weatherResMag);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
