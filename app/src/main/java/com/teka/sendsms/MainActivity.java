package com.teka.sendsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity{

    //declaring the variables
    EditText etxtPhone, etxtMessage;
    Button btnSend;
    private final int REQUEST_READ_PHONE_STATE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the variables
        etxtPhone = findViewById(R.id.etxtPhone);
        etxtMessage = findViewById(R.id.etxtMessage);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if permission is allowed
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED){
                    //if permission is granted send sms method called
                    sendSMS();
                }else {
                    //if permission isn't granted, request permission
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS},
                            100);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //if permission is granted call send sms method
            sendSMS();
        }else{
            //if permission is denied send Toast message
            Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
        }
    }


    private void sendSMS() {
        //getting the READ_PHONE_STATE permission
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            //get the values from edit texts
            String phone = etxtPhone.getText().toString();
            String message = etxtMessage.getText().toString();

            //check if strings are empty
            if (!phone.isEmpty() && !message.isEmpty()) {
                //initialize SMS Manager
                SmsManager smsManager = SmsManager.getDefault();
                //send message
                smsManager.sendTextMessage(phone, null, message, null, null);
                //display toast
                Toast.makeText(this, "SMS sent successfully", Toast.LENGTH_SHORT).show();
            } else {
                //if string is empty display following toast
                Toast.makeText(this, "please provide phone and message", Toast.LENGTH_SHORT).show();
            }

        }
    }
}