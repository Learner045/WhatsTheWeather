package util;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;


public class Utils {

    public  static final String API_KEY="&APPID=960ef27b4c5290ea9223ebbf21ba39a8";

    public static final String BASE_URL="http://api.openweathermap.org/data/2.5/weather?q=";
    public static final String ICON_URL="http://openweathermap.org/img/w/";

    public static JSONObject getObject(String tagName,JSONObject jsonObject)throws JSONException{

        JSONObject jObj=jsonObject.getJSONObject(tagName);
        return jObj;

    }
    public static String getString(String tagName,JSONObject jsonObject)throws JSONException{

        return jsonObject.getString(tagName);
    }
    public static Float getFloat(String tagName,JSONObject jsonObject)throws JSONException{

        return (float)jsonObject.getDouble(tagName);
    }
    public static Double getDouble(String tagName,JSONObject jsonObject)throws JSONException{
        return (double)jsonObject.getDouble(tagName);
    }
    public static int getInt(String tagName,JSONObject jsonObject)throws JSONException{
        return (int)jsonObject.getInt(tagName);
    }
    public static String makeprecise(long a){
        DateFormat df=DateFormat.getTimeInstance();
        String x=df.format(new Date(a));
        return x;


    }
}
