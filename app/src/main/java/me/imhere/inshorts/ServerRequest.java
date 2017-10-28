package me.imhere.inshorts;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;

class ServerRequest
{
    void fetchNewsInBackground(FetchNewsCallBack fetchNewsCallBack)
    {
        new getNewsAsyncTack(fetchNewsCallBack).execute();
    }
    private class getNewsAsyncTack extends AsyncTask<Void,Void,JSONArray>
    {
        FetchNewsCallBack fetchNewsCallBack;
        JSONArray jsonArray;
        getNewsAsyncTack(FetchNewsCallBack fetchNewsCallBack)
        {
            this.fetchNewsCallBack = fetchNewsCallBack;
        }

        @Override
        protected JSONArray doInBackground(Void... params)
        {
            try
            {
                URL url                             = new URL("http://starlord.hackerearth.com/newsjson");
                HttpURLConnection urlConnection     = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(15000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.connect();
                InputStream in                      = new BufferedInputStream(urlConnection.getInputStream());
                String result                       = convertInputStreamToString(in);
                jsonArray                           = new JSONArray(new JSONTokener(result));
                urlConnection.disconnect();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return jsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray)
        {
            if(jsonArray==null)
            {
                fetchNewsCallBack.error("Please check you network connection.");
            }
            else
            {
                try
                {
                    ArrayList <News> newses = new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jo       = jsonArray.getJSONObject(i);
                        News news           = new News();
                        news.setId(jo.getInt("ID"));
                        news.setTitle(jo.getString("TITLE"));
                        news.setUrl(jo.getString("URL"));
                        news.setPublisher(jo.getString("PUBLISHER").replaceAll("\\\\",""));
                        news.setCatgory(jo.getString("CATEGORY"));
                        news.setHostname(jo.getString("HOSTNAME"));
                        news.setTimestamp(new Timestamp(jo.getLong("TIMESTAMP")));
                        newses.add(news);
                    }
                    fetchNewsCallBack.done(newses);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(jsonArray);
        }
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String result,line;
        result                        ="";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }
}
