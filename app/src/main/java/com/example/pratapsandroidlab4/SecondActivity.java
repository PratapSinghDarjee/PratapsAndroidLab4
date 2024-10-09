package com.example.pratapsandroidlab4;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.Manifest;
public class SecondActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ActivityResultLauncher<Intent> cameraResult;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent  fromPrevious = getIntent();

       TextView txtWelcome=(TextView) findViewById(R.id.textView3);

       String emailAddress= fromPrevious.getStringExtra("EmailAddress");
        txtWelcome.setText("Welcome Back: "+emailAddress );
        /////Handle Runtime Permissions
        //: If your app targets Android 6.0 (API level 23) or higher, you need to handle runtime permissions.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // You can directly request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }
        ///
        Button buttonPhone=(Button)findViewById(R.id.buttonPhone);
        Button buttonSms=(Button)findViewById(R.id.buttonSms);
        Button buttonImage=(Button)findViewById(R.id.buttonImage);
        Button buttonGMap=(Button)findViewById(R.id.buttonGMap);
        EditText editText=(EditText) findViewById(R.id.enterPhoneNo);
        ImageView image1=(ImageView) findViewById(R.id.image1);

        //// Read image file to get the image that I have saved
        String filename="office.png";
        File file = new File( getFilesDir(), filename);
        if(file.exists())
        {
            Log.w("Pratap", "file exists");
            Bitmap theImage = BitmapFactory.decodeFile(file.getPath());
            image1.setImageBitmap( theImage );
        }
        else {
            Log.w("Pratap", "file not exists");
        }





        // Register activity result launcher
        cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Log.w("Pratap", "Result2....>");
                        }
                    }
                });




        buttonPhone.setOnClickListener(view -> {
            // Replace phoneNumber with the recipient's phone number
            String phoneNumber = editText.getText().toString();
            // Create the intent
            Intent call = new Intent(Intent.ACTION_DIAL);
            // Set the data URI with the recipient's phone number
            call.setData(Uri.parse("tel:" + phoneNumber));
            // Start the activity
            startActivity( call);

        });
        buttonSms.setOnClickListener(view -> {
            // Replace phoneNumber with the recipient's phone number
            String phoneNumber = editText.getText().toString();
            // Create the intent
            Intent sms = new Intent(Intent.ACTION_SENDTO);
            // Set the data URI with the recipient's phone number
            sms.setData(Uri.parse("smsto:" + phoneNumber));
            // Start the activity
            startActivity(sms);
        });

        buttonGMap.setOnClickListener(view -> {
            // Replace latitude and longitude with the coordinates of the location you want to open
            double latitude = 37.7749;
            double longitude = -122.4194;

            // Create the intent
            Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

            // Optionally, specify a zoom level
            mapIntent.putExtra("zoom", 15);

            // Start the activity
            startActivity(mapIntent);


        });

        buttonImage.setOnClickListener(view -> {



            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraResult.launch(cameraIntent);

        });
        /////



    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /// show the captured image in the imageview
        ImageView img=(ImageView) findViewById(R.id.image1);
        Bitmap thumbnail=data.getParcelableExtra("data");
        img.setImageBitmap(thumbnail);
        //// save the captured image in my device
        FileOutputStream fOut = null;
        try {
            fOut = openFileOutput("image.png", Context.MODE_PRIVATE);

            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            Log.w("Pratap", "file saved");
        }
        catch (FileNotFoundException e)
        { e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}