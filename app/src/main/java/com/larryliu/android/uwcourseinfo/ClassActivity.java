package com.larryliu.android.uwcourseinfo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by clqa on 2016-10-28.
 */
public class ClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        Intent intent = getIntent();
        String message = intent.getStringExtra(InfoActivity.COURSE_CODE);

        AsyncConnect task = new AsyncConnect(this);
        task.execute(message);
    }

    private class AsyncConnect extends AsyncTask<String, Void, String> {

        private Context context;

        public AsyncConnect (Context context){
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings){
            String key = "2d91198533b55707eaf3e29850108000";
            String[] courseCode = strings[0].split(" ");

            String courseurl = "https://api.uwaterloo.ca/v2/courses/" + courseCode[0] + '/' + courseCode[1] + "/schedule" + ".json?key=" + key;

            URL url;
            String result = "";
            try {
                url = new URL(courseurl);
                HttpsURLConnection request = (HttpsURLConnection) url.openConnection();
                result = get_content(request);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            TextView textView = new TextView(context);
            textView.setText(result);
            ViewGroup layout = (ViewGroup) findViewById(R.id.activity_class);
            layout.addView(textView);

            /*String [] names = {"title", "units", "description"};
            String [] ret = new String[3];
            try {
                JSONObject JSONresult = toJSON(result);
                printValues(JSONresult, names, context);

            } catch(JSONException e) {
                e.printStackTrace();
            }*/

        }

        private String get_content(HttpsURLConnection con){
            String retStr = "";

            if(con!=null){

                try {
                    BufferedReader br =
                            new BufferedReader(
                                    new InputStreamReader(con.getInputStream()));

                    String input;

                    while ((input = br.readLine()) != null){
                        retStr += input;
                    }
                    br.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return retStr;
        }

        private JSONObject toJSON(String result) throws JSONException{
            JSONObject JSONFullresult = new JSONObject(result);
            return new JSONObject(JSONFullresult.get("data").toString());
        }

        private void printValues(JSONObject json, String [] names, Context context) throws JSONException {
            int size = names.length;
            for (int i=0; i<size; i++) {

            }
        }
    }
}
