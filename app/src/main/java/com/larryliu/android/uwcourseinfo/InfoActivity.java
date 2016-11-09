package com.larryliu.android.uwcourseinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import java.net.*;
import java.io.*;
import org.json.*;
import org.w3c.dom.Text;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by clqa on 2016-10-26.
 */
public class InfoActivity extends AppCompatActivity {
    public final static String COURSE_CODE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.COURSE_CODE).toUpperCase();

        TextView textView = (TextView) findViewById(R.id.course_name);
        textView.setText(message);

        AsyncConnect task = new AsyncConnect();
        task.execute(message);
    }

    public void classInfo(View view) {
        Intent intent = new Intent(this, ClassActivity.class);
        TextView textView = (TextView) findViewById(R.id.course_name);
        String message = textView.getText().toString().split(":")[0];
        intent.putExtra(COURSE_CODE, message);
        startActivity(intent);
    }


    private class AsyncConnect extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings){
            String key = "2d91198533b55707eaf3e29850108000";
            String[] courseCode = strings[0].split(" ");

            String courseurl = "https://api.uwaterloo.ca/v2/courses/" + courseCode[0] + '/' + courseCode[1] + ".json?key=" + key;

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

            String [] names = {"title", "units", "description"};
            String [] ret = new String[3];
            try {
                JSONObject JSONresult = toJSON(result);
                printValues(JSONresult, names);

            } catch(JSONException e) {
                e.printStackTrace();
            }

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

        private void printValues(JSONObject json, String [] names) throws JSONException {
            int size = names.length;
            String value = "";

            for (int i=0; i<size; i++) {

                if (names[i] == "title") {
                    TextView textView = (TextView) findViewById(R.id.course_title);
                    textView.setText(json.get(names[i]).toString());
                } else if (names[i] == "units") {
                    TextView textView = (TextView) findViewById(R.id.credit_content);
                    textView.setText(json.get(names[i]).toString() + " units");
                } else if (names[i] == "description") {
                    TextView textView = (TextView) findViewById(R.id.description_content);
                    textView.setText(json.get(names[i]).toString());
                }
            }
        }
    }

}