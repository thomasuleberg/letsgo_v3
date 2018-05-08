package com.example.thomas.letsgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;

    private ViewPager mViewPager;

    private SectionPagerAdapter sectionPagerAdapter;

  private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();

         toolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Letsgo");

        // creating tabs
        mViewPager=(ViewPager)findViewById(R.id.tabpager);
sectionPagerAdapter=new SectionPagerAdapter(getSupportFragmentManager());
   mViewPager.setAdapter(sectionPagerAdapter);
   tabLayout=(TabLayout)findViewById(R.id.main_tabs);

   // set up tabs with viewpage

   tabLayout.setupWithViewPager(mViewPager);
    }
    @Override
public void onStart(){
    super.onStart();
    //checking if user is signed in and apdate UI accordingly
FirebaseUser currentUser=firebaseAuth.getCurrentUser();
if(currentUser==null){
   // Intent startIntent=new Intent(MainActivity.this,Frontpage.class);
    //startActivity(startIntent);
   // finish();
    sendToStart();
}


    }

    private void sendToStart() {
        Intent startIntent=new Intent(MainActivity.this,Frontpage.class);
        startActivity(startIntent);
         finish();

    }


    @Override
public boolean onCreateOptionsMenu(Menu menu){
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.main_menu,menu);
    return true;

}
@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    super.onOptionsItemSelected(item);
    if (item.getItemId() == R.id.mainlogout) {
        FirebaseAuth.getInstance().signOut();
        sendToStart();

    }
// user går til accoutsetting
    if (item.getItemId() == R.id.acount) {
    Intent intent = new Intent(MainActivity.this, AccountSetting.class);
    startActivity(intent);
}
if(item.getItemId()== R.id.createevent){
    Intent intent = new Intent(MainActivity.this, Events.class);
    startActivity(intent);
}



return  true;
}

}