package com.example.thomas.letsgo;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {


    private Toolbar toolbar;
    private TextInputEditText Username;
    private TextInputEditText Email;
    private TextInputEditText Password;
    private Button btnregister;
    private ProgressDialog mRegProgress;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // toolbarset
        toolbar = (Toolbar) findViewById(R.id.register_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Regstration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegProgress = new ProgressDialog(this);

        // initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

       Username=(TextInputEditText) findViewById(R.id.reg_username);
        Email = (TextInputEditText) findViewById(R.id.reg_email);
        Password = (TextInputEditText) findViewById(R.id.reg_pass);
        btnregister = (Button) findViewById(R.id.registerbtn);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = Username.getText().toString().trim();
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();


                if (!TextUtils.isEmpty(username) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {

                    mRegProgress.setTitle("User Registration");
                    mRegProgress.setMessage("Please wait!");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    register_user(username, email, password);

                }
            }

        });
    }

    private void register_user(String username,String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener <AuthResult>() {
            @Override
            public void onComplete(@NonNull Task <AuthResult> task) {
     // checking if User registration
                mRegProgress.dismiss();
                if (task.isSuccessful()) {

                  // creating database for users

                    FirebaseUser current_user= FirebaseAuth.getInstance().getCurrentUser();
                    String uid= current_user.getUid();
                   databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(uid);
                   // creating hash map for compex data
                    HashMap<String,String> userMap= new HashMap <>();
                    userMap.put("username",username);
                    userMap.put("email",email);
                    userMap.put("password",password);


                    // adding value  database
                      databaseReference.setValue(userMap).addOnCompleteListener(new OnCompleteListener <Void>() {
                        @Override
                        public void onComplete(@NonNull Task <Void> task) {
                            // checking if it working
                            if (task.isSuccessful()){

                                Intent intent = new Intent(Register.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });



                } else {

                    mRegProgress.hide();
                    Toast.makeText(Register.this, "registration error! please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}










































