package com.example.niloofar.loginsample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("HandlerLeak")
    Handler hndlr=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle temp=msg.getData();
            String resultFromServer=temp.getString("JsonResultFromServer");
            temp=null;


            String username,password,name,family;
            int max,progress;

            EditText inpUserObj=findViewById(R.id.txt_username);
            EditText inpPassObj=findViewById(R.id.txt_password);

            try {
                JSONObject object=new JSONObject(resultFromServer);

                username=object.getString("username");
                password=object.getString("password");
                name=object.getString("name");
                family=object.getString("family");
                max=object.getInt("max");
                progress=object.getInt("progress");

                object=null;
                if ( inpUserObj.getText().toString().equals(username)&&inpPassObj.getText().toString().equals(password));

                Intent i=new Intent(getApplicationContext(),showactivity.class);
                i.putExtra("name",name);
                i.putExtra("family",family);
                i.putExtra("max",max);
                i.putExtra("progress",progress);
                startActivity(i);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
    Runnable r=new Runnable() {
        @Override
        public void run() {

            try {
                URL url = new URL(/*your link*/);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                InputStream in=httpURLConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);

                String result="";
                int data=reader.read();
                while (data!=-1){
                    result+=(char)data;
                    data=reader.read();
                }

                httpURLConnection.disconnect();
                reader=null;
                url=null;
                httpURLConnection=null;


                Bundle bndl=new Bundle();
                bndl.putString("JsonResultFromServer",result);
                Message hndlmsg=new Message();
                hndlmsg.setData(bndl);
                hndlr.sendMessage(hndlmsg);

            }catch (MalformedInputException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

public void btnLogin(View view){
    Thread th=new Thread(r);
    th.start();
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

