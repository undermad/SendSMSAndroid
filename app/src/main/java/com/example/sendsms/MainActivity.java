package com.example.sendsms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    Button sendButton;
    EditText messageBody, number;

    String destinationNumber;
    String userMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = findViewById(R.id.button);
        messageBody = findViewById(R.id.editTextTextMessage);
        number = findViewById(R.id.editTextTextNumber);

        sendButton.setOnClickListener(view -> {
            userMessage = messageBody.getText().toString();
            destinationNumber = number.getText().toString();
            sendSms(userMessage, destinationNumber);
        });

    }

    private void sendSms(String message, String destinationNumber) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        } else {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(destinationNumber, null, message, null, null);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(destinationNumber, null, userMessage, null, null);
        }

    }
}