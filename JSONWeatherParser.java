package data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.Place;
import model.Weather;
import util.Utils;



public class JSONWeatherParser {

    //parse data

    public static Weather getWeather(String data){

        Weather weather=new Weather();

        //create JSON object from data
        try {
            //jsonobj holds ALL DATA
            JSONObject jsonObject=new JSONObject(data);

            Place place=new Place();
            JSONObject coordObj= Utils.getObject("coord",jsonObject);
            place.setLat(Utils.getFloat("lat",coordObj));
            place.setLon(Utils.getFloat("lon",coordObj));

            JSONObject sysObj=Utils.getObject("sys",jsonObject);
            place.setCountry(Utils.getString("country",sysObj));
            place.setLastupdate(Utils.getInt("dt",jsonObject));
            place.setSunrise(Utils.getInt("sunrise",sysObj));
            place.setSunset(Utils.getInt("sunset",sysObj));
            place.setCity(Utils.getString("name",jsonObject));
            weather.place=place;

            //weather info
            JSONArray jsonArray=jsonObject.getJSONArray("weather");
            JSONObject jsonWeather= jsonArray.getJSONObject(0);
            weather.currentCondition.setWeatherId(Utils.getInt("id",jsonWeather));
            weather.currentCondition.setDescription(Utils.getString("description",jsonWeather));
            weather.currentCondition.setCondition(Utils.getString("main",jsonWeather));
            weather.currentCondition.setIcon(Utils.getString("icon",jsonWeather));

            JSONObject mainobj=Utils.getObject("main",jsonObject);
            weather.currentCondition.setHumidity(Utils.getInt("humidity",mainobj));
            weather.currentCondition.setPressure(Utils.getInt("pressure",mainobj));
            weather.currentCondition.setMintemp(Utils.getFloat("temp_min",mainobj));
            weather.currentCondition.setMaxTemp(Utils.getFloat("temp_max",mainobj));
            weather.currentCondition.setTemperature(Utils.getDouble("temp",mainobj));

            JSONObject windObj=Utils.getObject("wind",jsonObject);
            weather.wind.setSpeed(Utils.getFloat("speed",windObj));
            weather.wind.setDeg(Utils.getFloat("deg",windObj));

            JSONObject cloudObj=Utils.getObject("clouds",jsonObject);
            weather.clouds.setPrecipitation(Utils.getInt("all",cloudObj));

            return weather;











        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

}
