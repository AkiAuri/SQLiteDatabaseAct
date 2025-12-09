package com.jccanizares.sqliteapplication;

import android.app. Activity;
import android.content. Intent;
import android.database. Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android. widget.Toast;

public class UpdateActivity extends Activity {
    private static final String TAG = "UpdateActivity";

    public EditText Fname, Mname, Lname, Address, Email;
    public String FNAME=null, MNAME=null, LNAME=null, ADDRESS=null, EMAIL=null;
    public Intent DispForm;
    public SQLiteDatabase Conn;
    public Cursor rs;
    public static Integer ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Starting UpdateActivity");
        Log.d(TAG, "onCreate: Record ID to update: " + ID);

        try {
            setContentView(R.layout.activity_update);
            Log.d(TAG, "onCreate: Layout set successfully");

            Fname=(EditText)findViewById(R.id.Fname);
            Mname=(EditText)findViewById(R.id.Mname);
            Lname=(EditText)findViewById(R.id.Lname);
            Address=(EditText)findViewById(R.id.Address);
            Email=(EditText)findViewById(R.id.Email);
            Log.d(TAG, "onCreate: All EditText fields initialized");

            Conn=new SQLiteDatabase(this);
            Log.d(TAG, "onCreate: Database connection established");

            if (ID == null) {
                Log.e(TAG, "onCreate: Record ID is null");
                Toast. makeText(getApplicationContext(), "Error: No record selected",
                        Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            Log.d(TAG, "onCreate: Fetching data for ID: " + ID);
            rs=Conn.getData(ID);

            if (rs == null) {
                Log.e(TAG, "onCreate:  Cursor is null");
                Toast. makeText(getApplicationContext(), "Error: Could not retrieve data",
                        Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            if (rs.getCount() == 0) {
                Log.w(TAG, "onCreate: No record found for ID: " + ID);
                Toast.makeText(getApplicationContext(), "Error: Record not found",
                        Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            rs. moveToFirst();
            Log.d(TAG, "onCreate: Cursor moved to first position");

            String fname = rs.getString(rs.getColumnIndex(Conn.PROFILE_FNAME));
            String mname = rs.getString(rs. getColumnIndex(Conn. PROFILE_MNAME));
            String lname = rs.getString(rs.getColumnIndex(Conn.PROFILE_LNAME));
            String address = rs.getString(rs.getColumnIndex(Conn.PROFILE_ADDRESS));
            String email = rs.getString(rs.getColumnIndex(Conn.PROFILE_EMAIL));

            Log.d(TAG, "onCreate: Retrieved data - FNAME: " + fname +
                    ", MNAME:  " + mname + ", LNAME: " + lname +
                    ", ADDRESS: " + address + ", EMAIL: " + email);

            Fname. setText(fname);
            Mname.setText(mname);
            Lname.setText(lname);
            Address.setText(address);
            Email.setText(email);
            Log.i(TAG, "onCreate: Form fields populated successfully");

        } catch(Exception e) {
            Log.e(TAG, "onCreate: Exception occurred", e);
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    Log.d(TAG, "onCreate: Cursor closed");
                } catch (Exception e) {
                    Log.e(TAG, "onCreate: Error closing cursor", e);
                }
            }
        }
    }

    public void Back(View view){
        Log.d(TAG, "Back: Method called");

        try {
            DispForm = new Intent(UpdateActivity.this, RecordsActivity.class);
            Log.d(TAG, "Back: Intent created");

            startActivity(DispForm);
            Log.d(TAG, "Back:  Navigating back to RecordsActivity");
        } catch (Exception e) {
            Log.e(TAG, "Back: Exception occurred", e);
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void UpdateRecord(View view){
        Log.d(TAG, "UpdateRecord: Method called for ID: " + ID);

        try {
            FNAME=Fname.getText().toString();
            MNAME=Mname.getText().toString();
            LNAME=Lname.getText().toString();
            ADDRESS=Address.getText().toString();
            EMAIL=Email.getText().toString();

            Log. d(TAG, "UpdateRecord: Retrieved values - FNAME: " + FNAME +
                    ", MNAME: " + MNAME + ", LNAME:  " + LNAME +
                    ", ADDRESS: " + ADDRESS + ", EMAIL: " + EMAIL);

            if (FNAME.equals("")){
                Log.w(TAG, "UpdateRecord:  Validation failed - First name is empty");
                Fname.setError("Please Enter Your First Name");
                Fname.requestFocus();
            }
            else if (MNAME.equals("")){
                Log.w(TAG, "UpdateRecord:  Validation failed - Middle name is empty");
                Mname.setError("Please Enter Your Middle Name");
                Mname.requestFocus();
            }
            else if (LNAME.equals("")){
                Log.w(TAG, "UpdateRecord: Validation failed - Last name is empty");
                Lname.setError("Please Enter Your Last Name");
                Lname.requestFocus();
            }
            else if (ADDRESS.equals("")){
                Log.w(TAG, "UpdateRecord: Validation failed - Address is empty");
                Address.setError("Please Enter Your Address");
                Address.requestFocus();
            }
            else if (EMAIL.equals("")){
                Log.w(TAG, "UpdateRecord:  Validation failed - Email is empty");
                Email.setError("Please Enter Your Email");
                Email.requestFocus();
            }
            else{
                Log.d(TAG, "UpdateRecord: Validation passed, updating record ID: " + ID);

                if (Conn. UpdateRecords(FNAME, MNAME, LNAME, ADDRESS, EMAIL, ID)) {
                    Log.i(TAG, "UpdateRecord: Record updated successfully");

                    Fname.setText("");
                    Mname.setText("");
                    Lname.setText("");
                    Address. setText("");
                    Email.setText("");
                    Log.d(TAG, "UpdateRecord: Form fields cleared");

                    Toast. makeText(getApplicationContext(), "RECORD UPDATED!",
                            Toast.LENGTH_SHORT).show();

                    DispForm = new Intent(UpdateActivity.this, RecordsActivity. class);
                    startActivity(DispForm);
                    Log.d(TAG, "UpdateRecord: Navigating back to RecordsActivity");
                } else {
                    Log.e(TAG, "UpdateRecord: Failed to update record");
                    Toast. makeText(getApplicationContext(), "Error:  Failed to update record",
                            Toast.LENGTH_SHORT).show();
                }
            }
        } catch(Exception e) {
            Log.e(TAG, "UpdateRecord:  Exception occurred", e);
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void DeleteRecord(View view){
        Log.d(TAG, "DeleteRecord: Method called for ID: " + ID);

        try{
            if (ID == null) {
                Log.e(TAG, "DeleteRecord: Record ID is null");
                Toast. makeText(getApplicationContext(), "Error: No record to delete",
                        Toast. LENGTH_SHORT).show();
                return;
            }

            Log.d(TAG, "DeleteRecord: Attempting to delete record ID: " + ID);

            if (Conn.DeleteRecord(ID)) {
                Log.i(TAG, "DeleteRecord: Record deleted successfully");
                Toast.makeText(getApplicationContext(), "RECORD DELETED!",
                        Toast.LENGTH_SHORT).show();

                DispForm = new Intent(UpdateActivity. this, RecordsActivity.class);
                startActivity(DispForm);
                Log.d(TAG, "DeleteRecord: Navigating back to RecordsActivity");
            } else {
                Log.e(TAG, "DeleteRecord: Failed to delete record");
                Toast.makeText(getApplicationContext(), "Error: Failed to delete record",
                        Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e) {
            Log.e(TAG, "DeleteRecord: Exception occurred", e);
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
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
        // TODO Auto-generated method stub
        super.onPause();
        Log.d(TAG, "onPause: Activity paused");
        finish();
        Log.d(TAG, "onPause: Activity finished");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Activity stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy:  Activity destroyed");

        if (rs != null && ! rs.isClosed()) {
            try {
                rs.close();
                Log.d(TAG, "onDestroy: Cursor closed");
            } catch (Exception e) {
                Log.e(TAG, "onDestroy:  Error closing cursor", e);
            }
        }

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