package com.jccanizares.sqliteapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app. AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view. ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    public Intent DisplayForm;
    public SQLiteDatabase Conn;
    public EditText Fname, Mname, Lname, Address, Email;
    public String FNAME=null, MNAME=null, LNAME=null, ADDRESS=null, EMAIL=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Starting MainActivity");

        try {
            setContentView(R.layout.activity_main);
            Log.d(TAG, "onCreate: Layout set successfully");

            Fname=(EditText)findViewById(R.id.Fname);
            Mname=(EditText)findViewById(R.id.Mname);
            Lname=(EditText)findViewById(R.id.Lname);
            Address=(EditText)findViewById(R.id.Address);
            Email=(EditText)findViewById(R.id.Email);
            Log.d(TAG, "onCreate: All EditText fields initialized");

            Conn=new SQLiteDatabase(this);
            Log.d(TAG, "onCreate: Database connection established");
        } catch (Exception e) {
            Log.e(TAG, "onCreate: Error during initialization", e);
            e.printStackTrace();
        }
    }

    public void AddRecord(View view){
        Log.d(TAG, "AddRecord: Method called");

        try {
            FNAME=Fname.getText().toString();
            MNAME=Mname. getText().toString();
            LNAME=Lname.getText().toString();
            ADDRESS=Address. getText().toString();
            EMAIL=Email.getText().toString();

            Log.d(TAG, "AddRecord: Retrieved values - FNAME:  " + FNAME +
                    ", MNAME:  " + MNAME + ", LNAME: " + LNAME +
                    ", ADDRESS: " + ADDRESS + ", EMAIL: " + EMAIL);

            if (FNAME.equals("")){
                Log.w(TAG, "AddRecord:  Validation failed - First name is empty");
                Fname.setError("Please Enter Your First Name");
                Fname.requestFocus();
            }
            else if (MNAME. equals("")){
                Log.w(TAG, "AddRecord:  Validation failed - Middle name is empty");
                Mname.setError("Please Enter Your Middle Name");
                Mname.requestFocus();
            }
            else if (LNAME.equals("")){
                Log.w(TAG, "AddRecord: Validation failed - Last name is empty");
                Lname.setError("Please Enter Your Last Name");
                Lname.requestFocus();
            }
            else if (ADDRESS. equals("")){
                Log.w(TAG, "AddRecord:  Validation failed - Address is empty");
                Address.setError("Please Enter Your Address");
                Address.requestFocus();
            }
            else if (EMAIL.equals("")){
                Log.w(TAG, "AddRecord: Validation failed - Email is empty");
                Email. setError("Please Enter Your Email");
                Email.requestFocus();
            }
            else{
                Log.d(TAG, "AddRecord: Validation passed, attempting to save record");

                if(Conn.AddRecords(FNAME, MNAME, LNAME, ADDRESS, EMAIL)){
                    Log.i(TAG, "AddRecord: Record saved successfully");

                    Fname.setText("");
                    Mname.setText("");
                    Lname.setText("");
                    Address.setText("");
                    Email.setText("");
                    Log.d(TAG, "AddRecord: Form fields cleared");

                    Toast. makeText(getApplicationContext(), "RECORD SAVED!", Toast.LENGTH_SHORT).show();
                }else{
                    Log.e(TAG, "AddRecord: Failed to save record");
                    Toast. makeText(getApplicationContext(), "SAVING INFORMATION FAILED!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "AddRecord: Exception occurred", e);
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void ViewRecords(View view){
        Log.d(TAG, "ViewRecords:  Method called");

        try {
            DisplayForm = new Intent(MainActivity.this, RecordsActivity. class);
            Log.d(TAG, "ViewRecords: Intent created");

            startActivity(DisplayForm);
            Log.d(TAG, "ViewRecords: Activity started successfully");
        } catch (Exception e) {
            Log.e(TAG, "ViewRecords: Failed to start activity", e);
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error opening records:  " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void ClearRecords(View view){
        Log.d(TAG, "ClearRecords: Method called");

        try{
            Log.d(TAG, "ClearRecords: Attempting to clear all records");
            Conn.ClearRecord();
            Log.i(TAG, "ClearRecords: Records cleared successfully");

            Toast.makeText(getApplicationContext(),"RECORDS CLEAR", Toast. LENGTH_SHORT).show();
        }
        catch(Exception e){
            Log.e(TAG, "ClearRecords: Exception occurred", e);
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Activity started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Activity resumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Activity paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Activity stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Activity destroyed");

        if (Conn != null) {
            try {
                Conn.close();
                Log. d(TAG, "onDestroy: Database connection closed");
            } catch (Exception e) {
                Log.e(TAG, "onDestroy: Error closing database", e);
            }
        }
    }
}