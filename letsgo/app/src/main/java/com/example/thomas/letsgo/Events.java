package com.example.thomas.letsgo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Calendar;

public class Events extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{

    EditText Title, Location;

    Button Send;
    Button Pick;
    TextView Result;

    int day,month,year,hour,minuter;
    int dayFinal,monthFinal,yearFinal,hourFinal,minuterFinal;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // initialisation
        Title = findViewById(R.id.title);
        Location = findViewById(R.id.location);
        Result = findViewById(R.id.result);
        Pick = findViewById(R.id.time);
        Send = findViewById(R.id.send);

        int phoneNumber = 45541050;


        Pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                hour = c.get(Calendar.HOUR);
                minuter = c.get(Calendar.MINUTE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Events.this, Events.this, year, month, day);
                datePickerDialog.show();

            }

        });

        Send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                            + phoneNumber)));

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MessageFormat.format("sms:{0}", phoneNumber)));
                    intent.putExtra("sms_body", Result.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

        @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal    = i;
        monthFinal   = i1+1;
        dayFinal     = i2;

       Calendar c=Calendar.getInstance();
        hour= c.get(Calendar.HOUR_OF_DAY);
        minuter=c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog= new TimePickerDialog(Events.this,Events.this,hour,minuter, android.text.format.DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal    = i;
        minuterFinal = i1;

        Result.setText("Vil du være med på: \n"+Title.getText().toString()+ " " +Location.getText().toString() + "" + "\n" + "Dato: " + dayFinal + "th/"  + monthFinal +"/" +yearFinal +" klokken" +" " + hourFinal +":" + minuterFinal);
    }
}