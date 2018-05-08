package com.example.thomas.letsgo;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class StatusActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputLayout Status;
    private Button Savechanges;
    // firebase
    private DatabaseReference statusDatabase;
    private FirebaseUser currentUser;
    // progress dialog
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        // firebase

        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        String current_uid= currentUser.getUid();
        statusDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);


        toolbar=(Toolbar) findViewById(R.id.status_appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // to see the status
        String satus_value=getIntent().getStringExtra("status_value");

        Status=( TextInputLayout)findViewById(R.id.status_input);
        Savechanges=(Button)findViewById(R.id.save);

        // adding text to ur status
        Status.getEditText().setText(satus_value);

        Savechanges. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //initialize the progress
                progress=new ProgressDialog(StatusActivity.this);
                progress.setTitle("Saving changes");
                progress.setMessage(" Please wait while save the changes! ");
                progress.show();
                // getting values fron textinpulayout
                String status= Status.getEditText().toString();
                // set value
                statusDatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener <Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progress.dismiss();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Ops! something went wrong",Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }
        });
    }
}
