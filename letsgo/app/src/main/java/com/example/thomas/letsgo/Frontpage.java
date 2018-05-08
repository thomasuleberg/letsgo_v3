package com.example.thomas.letsgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Frontpage extends AppCompatActivity {
    private Button b6;
    private Button b7;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontpage);

        TextView text=(TextView) findViewById(R.id.textView);
            Button b6 = (Button) findViewById(R.id.btn1);
            Button b7 = (Button) findViewById(R.id.btn);

            b6.setOnClickListener(new View.OnClickListener() {
                @Override
                //onclick function
                public void onClick(View v) {
                    // create the intent to start another activity
                    Intent intent = new Intent(Frontpage.this, LoginActivity.class);
                    startActivity(intent);


                }


            });
            b7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // create the intent to start another activity

                    Intent intent = new Intent(Frontpage.this, Register.class);
                    startActivity(intent);

                }
            });
        }
    }
