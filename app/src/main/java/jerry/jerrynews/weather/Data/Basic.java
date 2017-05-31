package jerry.jerrynews.weather.Data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/5/29.
 */
public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public  Update update;

    public class Update{
        public String loc;

    }
}
