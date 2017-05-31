package jerry.jerrynews.weather.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/5/29.
 */

public class Weather {

    public Alarms alarms;

    public AQI aqi;

    public Basic basic;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;

    @SerializedName("hourly_forecast")
    public List<Hourly> hourlyList;

    public Now now;

    public String  status;

    public Suggestion suggestion;

    /**
     * aqi : {"city":{"aqi":"109","co":"1","no2":"31","o3":"185","pm10":"80","pm25":"47","qlty":"轻度污染","so2":"13"}}
     * basic : {"city":"广州","cnty":"中国","id":"CN101280101","lat":"23.12517738","lon":"113.28063965","update":{"loc":"2017-05-29 13:54","utc":"2017-05-29 05:54"}}
     * daily_forecast : [{"astro":{"mr":"09:06","ms":"22:41","sr":"05:41","ss":"19:07"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2017-05-29","hum":"71","pcpn":"0.2","pop":"4","pres":"1009","tmp":{"max":"33","min":"24"},"uv":"12","vis":"20","wind":{"deg":"135","dir":"无持续风向","sc":"微风","spd":"6"}},{"astro":{"mr":"10:07","ms":"23:32","sr":"05:40","ss":"19:07"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2017-05-30","hum":"74","pcpn":"0.0","pop":"5","pres":"1008","tmp":{"max":"32","min":"24"},"uv":"12","vis":"20","wind":{"deg":"126","dir":"无持续风向","sc":"微风","spd":"5"}},{"astro":{"mr":"11:08","ms":"null","sr":"05:40","ss":"19:08"},"cond":{"code_d":"300","code_n":"302","txt_d":"阵雨","txt_n":"雷阵雨"},"date":"2017-05-31","hum":"81","pcpn":"1.5","pop":"98","pres":"1006","tmp":{"max":"32","min":"25"},"uv":"11","vis":"19","wind":{"deg":"159","dir":"无持续风向","sc":"微风","spd":"4"}}]
     * hourly_forecast : [{"cond":{"code":"103","txt":"晴间多云"},"date":"2017-05-29 16:00","hum":"53","pop":"3","pres":"1008","tmp":"30","wind":{"deg":"132","dir":"东南风","sc":"微风","spd":"8"}},{"cond":{"code":"103","txt":"晴间多云"},"date":"2017-05-29 19:00","hum":"67","pop":"1","pres":"1007","tmp":"28","wind":{"deg":"165","dir":"东南风","sc":"微风","spd":"10"}},{"cond":{"code":"103","txt":"晴间多云"},"date":"2017-05-29 22:00","hum":"77","pop":"0","pres":"1008","tmp":"27","wind":{"deg":"172","dir":"南风","sc":"微风","spd":"10"}}]
     * now : {"cond":{"code":"101","txt":"多云"},"fl":"36","hum":"52","pcpn":"0","pres":"1009","tmp":"31","vis":"5","wind":{"deg":"107","dir":"东风","sc":"3-4","spd":"12"}}
     * status : ok
     * suggestion : {"air":{"brf":"中","txt":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"},"comf":{"brf":"较不舒适","txt":"白天天气多云，并且空气湿度偏大，在这种天气条件下，您会感到有些闷热，不很舒适。"},"cw":{"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},"drsg":{"brf":"炎热","txt":"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。"},"flu":{"brf":"少发","txt":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"},"sport":{"brf":"较适宜","txt":"天气较好，较适宜进行各种运动，但因湿度偏高，请适当降低运动强度。"},"trav":{"brf":"较适宜","txt":"天气较好，温度较高，天气较热，但有微风相伴，还是比较适宜旅游的，不过外出时要注意防暑防晒哦！"},"uv":{"brf":"中等","txt":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"}}
     */
}
