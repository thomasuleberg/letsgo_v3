package com.example.thomas.letsgo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

    public class AccountSetting extends AppCompatActivity {
    private DatabaseReference mUserDatabase;
    private FirebaseUser mcurrentUser;
    private CircleImageView circleImageView;
    private TextView displayname;
    private TextView status1;
    private Button ChangeImage;
    private Button ChangeStatus;

    // passing interg for the request of image selection
    private static final int GALLERY_PICK = 1;
    // storage Firebase
    private StorageReference storageReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_seting);
        circleImageView = (CircleImageView) findViewById(R.id.image_setings);
        displayname = (TextView) findViewById(R.id.displayname);
        status1 = (TextView) findViewById(R.id.status);
        ChangeImage = (Button) findViewById(R.id.changeimage);
        ChangeStatus = (Button) findViewById(R.id.changestatus);
        storageReference= FirebaseStorage.getInstance().getReference();

        // save the data to firebase realtime

        mcurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mcurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("username").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String thumb_image = dataSnapshot.child("Thumb_image").getValue().toString();

                // changing the values
                displayname.setText(username);
                status1.setText(status);
                Picasso.with(AccountSetting.this).load(image).into(circleImageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
// create intent for changing status
        ChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // to see the old status before change it
                String status_value = status1.getText().toString();

                Intent status_intent = new Intent(AccountSetting.this, StatusActivity.class);
                status_intent.putExtra("status_value", status_value);
                startActivity(status_intent);
            }
        });
// create intent for picking Image galleri
        ChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create intent for picking Image galleri and get image for cropping and then use the image in cropping activity

                Intent galleryIntent = new Intent();
                galleryIntent.setType(" image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);

            }
        });
    }
    // result for croping image

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
            Uri imageUri =data.getData();

            // costomise an image
            CropImage.activity(imageUri)
                    .setAspectRatio(1,1)
                    .start(this);
           // Toast.makeText(AccountSetting.this,imageUri,Toast.LENGTH_LONG);

        // result for croping image
          }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                progressDialog=new ProgressDialog(AccountSetting.this);
                progressDialog.setTitle("Uploading image...");
                progressDialog.setMessage("please wait!");
                // user can't cancel the dialog box
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();


                Uri resultUri = result.getUri();


                String current_user_id=mcurrentUser.getUid();

                // lagre profile bilde i firebase
                StorageReference  filepath= storageReference.child("profile_image").child( current_user_id + "jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener <UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                     if(task.isSuccessful()){
                         String download_url=task.getResult().getDownloadUrl().toString();

                  mUserDatabase.child("image").setValue(download_url).addOnCompleteListener(new OnCompleteListener <Void>() {
                      @Override
                      public void onComplete(@NonNull Task <Void> task) {
                       if(task.isSuccessful()){
                           progressDialog.dismiss();
                       }
                      }
                  });
                   }
                   else {
                         Toast.makeText(AccountSetting.this,"error uploading",Toast.LENGTH_LONG).show();
                     }
                    }
                   });
                   }

            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
           }
           }
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}