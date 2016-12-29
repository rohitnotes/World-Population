package com.example.theodhor.worldpopulation;

import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView number;
    String sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*number = (TextView)findViewById(R.id.text);

        try {

            System.out.print(sample);
        }catch (IOException e){
            e.printStackTrace();
        }
        number.setText(sample);*/

        /*final String HTML = "<table cellspacing=\"0\" style=\"height: 24px;\">\r\n<tr class=\"tr-hover\">\r\n<th rowspan=\"15\" scope=\"row\">Network</th>\r\n<td class=\"ttl\"><a href=\"network-bands.php3\">Technology</a></td>\r\n<td class=\"nfo\"><a href=\"#\" class=\"link-network-detail collapse\">GSM</a></td>\r\n</tr>\r\n<tr class=\"tr-toggle\">\r\n<td class=\"ttl\"><a href=\"network-bands.php3\">2G bands</a></td>\r\n<td class=\"nfo\">GSM 900 / 1800 - SIM 1 & SIM 2</td>\r\n</tr>   \r\n<tr class=\"tr-toggle\">\r\n<td class=\"ttl\"><a href=\"glossary.php3?term=gprs\">GPRS</a></td>\r\n<td class=\"nfo\">Class 12</td>\r\n</tr>   \r\n<tr class=\"tr-toggle\">\r\n<td class=\"ttl\"><a href=\"glossary.php3?term=edge\">EDGE</a></td>\r\n<td class=\"nfo\">Yes</td>\r\n</tr>\r\n</table>";
        Document document = Jsoup.parse(HTML);



        Element table = document.select("table").first();
        String arrayName = table.select("th").first().text();
        JSONObject jsonObj = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        Elements ttls = table.getElementsByClass("ttl");
        Elements nfos = table.getElementsByClass("nfo");
        JSONObject jo = new JSONObject();
        try {
            for (int i = 0, l = ttls.size(); i < l; i++) {
                String key = ttls.get(i).text();
                String value = nfos.get(i).text();
                jo.put(key, value);
            }
            jsonArr.put(jo);
            jsonObj.put(arrayName, jsonArr);
            System.out.println(jsonObj.toString());
        }catch (JSONException e){
            e.printStackTrace();
        }*/

        Log.e("TAG",""+new Task().execute());
    }


    class Task extends AsyncTask<Void,Void,JSONObject> {

        @Override
        protected void onPostExecute(JSONObject result) {
            //if you had a ui element, you could display the title
            try{
                ((TextView)findViewById(R.id.population)).setText(result.getString("population"));
                ((TextView)findViewById(R.id.birthsToday)).setText(result.getString("births_day"));
                ((TextView)findViewById(R.id.birthsThisYear)).setText(result.getString("births_year"));
                ((TextView)findViewById(R.id.deathsToday)).setText(result.getString("deaths_day"));
                ((TextView)findViewById(R.id.deathsThisYear)).setText(result.getString("deaths_year"));
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected JSONObject doInBackground(Void ...params) {
            String population = "", birthsToday, birthsThisYear, deathsThisYear, deathsToday;
            Document doc;
            JSONObject object = new JSONObject();
            try {
                doc = Jsoup.connect("http://www.livepopulation.com/").get();
                population = doc.getElementsByClass("current-population").first().text();
                birthsToday = doc.getElementsByClass("births-day").first().text();
                birthsThisYear = doc.getElementsByClass("births-year").first().text();
                deathsToday = doc.getElementsByClass("deaths-day").first().text();
                deathsThisYear = doc.getElementsByClass("deaths-year").first().text();
                object.put("population",population);
                object.put("births_day",birthsToday);
                object.put("births_year",birthsThisYear);
                object.put("deaths_day",deathsToday);
                object.put("deaths_year",deathsThisYear);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return object;
        }
    }
}

