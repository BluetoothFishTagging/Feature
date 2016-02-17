package com.example.jamiecho.serverclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText urlEdit;
    EditText nameEdit;

    HttpURLConnection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        urlEdit = (EditText) findViewById(R.id.urlEdit);
        nameEdit = (EditText) findViewById(R.id.nameEdit);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSend(View v) {
        Intent i = new Intent(MainActivity.this, UploadActivity.class);
        startActivity(i);
    }

    /* Plain Data
    public void onSend(View v) {
        Log.i("SEND","ACTIVATED");
        String url = urlEdit.getText().toString();
        if(url.equals(""))
            url = "http://192.168.16.67:8000/"; //temporary.
        String name = nameEdit.getText().toString();

        SendHttpRequestTask t = new SendHttpRequestTask();
        String[] params = new String[]{url, name};
        t.execute(params);
        Log.i("SEND", "COMPLETED");

        Intent i = new Intent(MainActivity.this, UploadActivity.class);
        startActivity(i);
    }

    public void connect(String url) {
        try {
            con = (HttpURLConnection) (new URL(url)).openConnection();
            con.setRequestMethod("POST"); //post data
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private class SendHttpRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            Log.i("URL",url);
            String name = params[1];
            Log.i("BACKGROUND","DOING");
            String data = sendHttpRequest(url, name);
            Log.i("DATA", data);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("RESPONSE", s);
            super.onPostExecute(s);

        }
    }
    private String sendHttpRequest(String url, String name) {
        StringBuffer buf = new StringBuffer();
        try {
            connect(url);
            con.getOutputStream().write(("name=" + name).getBytes());
            InputStream iStream = con.getInputStream();
            byte[] b = new byte[1024];
            while (iStream.read(b) != -1)
                buf.append(new String(b));
            con.disconnect();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return buf.toString();
    }
*/

}