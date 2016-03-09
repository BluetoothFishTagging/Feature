package com.example.jamiecho.client;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UploadActivity extends AppCompatActivity {

    String param1;
    String param2;
    private Uri fileURI;
    //private URI fileURI;
    final String url = "http://192.168.16.67:8000/";
    //assuming a REAL domain name for actual server, though.

    EditText param1Edit;
    EditText param2Edit;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        param1Edit = (EditText) findViewById(R.id.param1Edit);
        param2Edit = (EditText) findViewById(R.id.param2Edit);
        submitBtn = (Button) findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SUBMIT DATA
                param1 = param1Edit.getText().toString();
                param2 = param2Edit.getText().toString();
                fetchFile();
            }
        });

    }

    private static final int FILE_SELECT_CODE = 0;

    private void fetchFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getApplicationContext(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    try {
                        fileURI = data.getData();
                        //fileURI = new URI(data.getData().toString());
                        Log.i("FILEURI",fileURI.toString());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }
        SendHttpRequestTask t = new SendHttpRequestTask();
        String[] params = new String[]{url, param1, param2};
        t.execute(params);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static byte[] convertFileToByteArray(File f)
    {
        byte[] byteArray = null;
        try
        {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024*8];
            int bytesRead =0;

            while ((bytesRead = inputStream.read(b)) != -1)
            {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return byteArray;
    }
    public static String getRealPathFromURI_API19(Context context, Uri uri){
    //works for API19 and potentially above
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }
    private class SendHttpRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String param1 = params[1];
            String param2 = params[2];

            /*FOR RESOURCE FILES, construct byte array as follows:
            Bitmap b = BitmapFactory.decodeResource(UploadActivity.this.getResources(), R.drawable.walk);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 0, baos); //for this specific file.
            byte[] byteArray = baos.toByteArray(); // = bytearray
            */

            // FOR STORAGE FILES, construct byte array as follows:
            File f = new File(getRealPathFromURI_API19(getApplicationContext(),fileURI));
            byte[] byteArray = convertFileToByteArray(f); // = bytearray

            try {
                HttpClient client = new HttpClient(url);
                client.connectMultipart();
                client.addFormPart("param1", param1);
                client.addFormPart("param2", param2); //form (plain text, JSON, etc) data.

                client.addFilePart("photo","camera",byteArray);
                client.finishMultipart();
                String data = client.getResponse();
            }
            catch(Throwable t) {
                t.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //Log.i("RESPONSE", s);
            super.onPostExecute(s);
        }

    }
}
