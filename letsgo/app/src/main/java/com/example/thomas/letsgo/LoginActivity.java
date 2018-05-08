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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText Email;
    private TextInputEditText Password;
    private Button b3;

    // progressDialog
    private ProgressDialog mRegProgress;

   // private TextView b4;

    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        toolbar = (Toolbar) findViewById(R.id.login_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRegProgress = new ProgressDialog(this);

        // initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        Email = (TextInputEditText) findViewById(R.id.email);
        Password = (TextInputEditText) findViewById(R.id.pass);
        b3 = (Button) findViewById(R.id.login);

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();
                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {

                    mRegProgress.setTitle("User Login");
                    mRegProgress.setMessage("Please wait!");
                    mRegProgress.setCanceledOnTouchOutside(true);
                    mRegProgress.show();

                    login_user(email, password);

                }

            }
        });
    }

    private void login_user(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener <AuthResult>() {
            @Override
            public void onComplete(@NonNull Task <AuthResult> task) {
// checking if User login
                if (task.isSuccessful()) {

                    mRegProgress.dismiss();

                    Intent intent= new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    finish();
                } else {

                    mRegProgress.hide();
                    Toast.makeText(LoginActivity.this, "Login error! pleas try again", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}








