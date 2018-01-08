package com.mfcwl.server_quest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submit = (Button) findViewById(R.id.submit);
        response = (TextView) findViewById(R.id.response);


        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new AsynchronseTask().execute("http://apicpt.ibbtrade.com/dealer/api/login/getstates");

            }
        });
    }

    public int count =0;


    public class AsynchronseTask extends AsyncTask<String , String, String> {


        private StringBuffer stringBuffer;
        ProgressDialog progress = new ProgressDialog(MainActivity.this);


        @Override
        protected void onPreExecute() {

            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setIndeterminate(true);
            progress.setMax(100);

            progress.show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... values) {

            super.onProgressUpdate(values);

        }

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;


            try {

                URL url = new URL("http://apicpt.ibbtrade.com/dealer/api/login/getstates");

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                InputStream inputStream = httpURLConnection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                stringBuffer = new StringBuffer();
                String line = "";



                while ((line = bufferedReader.readLine()) != null) {

                    stringBuffer.append(line);
                }

                return stringBuffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

          // progress.dismiss();
            response.setText(s.toString());
        }
    }
}
