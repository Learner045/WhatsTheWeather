package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import util.Utils;



public class WeatherHttpClient {

    public String getWeatherData(String place){

        HttpURLConnection connection=null;
        InputStream inputStream=null;

        //setting up a connection with web
        try {
            connection=(HttpURLConnection)(new URL(Utils.BASE_URL+place+Utils.API_KEY)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();


            //read response
            StringBuffer stringBuffer=new StringBuffer();
            inputStream=connection.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
            String line=null;
            while((line=br.readLine())!=null){

                stringBuffer.append(line+"\r\n");
            }
            inputStream.close();
            connection.disconnect();
            return stringBuffer.toString();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
