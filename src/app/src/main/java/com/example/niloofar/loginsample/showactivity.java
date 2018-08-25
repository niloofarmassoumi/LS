 package com.example.niloofar.loginsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

 public class showactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showactivity);

        TextView nameobj=findViewById(R.id.txv_name);
        TextView familyObj=findViewById(R.id.txv_family);
        ProgressBar pbObj=findViewById(R.id.pb_main);

        Intent j=getIntent();
        nameobj.setText(j.getStringExtra("name"));
        familyObj.setText(j.getStringExtra("family"));

        pbObj.setMax(j.getIntExtra("max",100));
        pbObj.setProgress(j.getIntExtra("progress",100));
    }
}
