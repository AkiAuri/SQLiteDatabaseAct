package com.jccanizares.sqliteapplication;

import android.app. ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android. widget.ListView;
import android. widget.Toast;

import java. util.ArrayList;

public class RecordsActivity extends ListActivity {
    private static final String TAG = "RecordsActivity";

    public SQLiteDatabase Conn;
    public Intent DispForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Starting RecordsActivity");

        try {
            Conn = new SQLiteDatabase(this);
            Log.d(TAG, "onCreate: Database connection established");

            ArrayList<String> Records = Conn.GetAllData();
            Log.d(TAG, "onCreate: Retrieved " + Records.size() + " records from database");

            if(Records.size() > 0){
                Log.i(TAG, "onCreate: Records found, setting up list adapter");

                for (int i = 0; i < Records.size(); i++) {
                    Log.d(TAG, "onCreate: Record " + i + " - ID: " + Conn.RecordsId.get(i) +
                            ", Data: " + Records.get(i));
                }

                setListAdapter(new ArrayAdapter<String>(
                        RecordsActivity.this,
                        android.R.layout.simple_list_item_1,
                        Records));
                Log.d(TAG, "onCreate: List adapter set successfully");
            } else {
                Log.w(TAG, "onCreate: No records found in database");
                Toast.makeText(getApplicationContext(), "No Records Found!",
                        Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e) {
            Log.e(TAG, "onCreate: Exception occurred while loading records", e);
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        Log.d(TAG, "onListItemClick: Item clicked at position:  " + position + ", id: " + id);

        try {
            if (Conn == null) {
                Log.e(TAG, "onListItemClick: Database connection is null");
                Toast.makeText(getApplicationContext(), "Database error",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (Conn.RecordsId == null || Conn.RecordsId.size() <= position) {
                Log.e(TAG, "onListItemClick:  RecordsId is null or position out of bounds.  " +
                        "Position: " + position + ", Size: " +
                        (Conn.RecordsId != null ? Conn.RecordsId.size() : "null"));
                Toast.makeText(getApplicationContext(), "Invalid record selection",
                        Toast. LENGTH_SHORT).show();
                return;
            }

            UpdateActivity.ID = Conn.RecordsId.get(position);
            Log.i(TAG, "onListItemClick: Selected record ID: " + UpdateActivity.ID);

            DispForm = new Intent("com.sqllite.app. UPDATEACTIVITY");
            Log.d(TAG, "onListItemClick: Intent created for UpdateActivity");

            startActivity(DispForm);
            Log.d(TAG, "onListItemClick: UpdateActivity started successfully");

        } catch (Exception e) {
            Log.e(TAG, "onListItemClick: Exception occurred", e);
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error opening record: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart:  Activity started");
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
        Log.d(TAG, "onDestroy: Activity destroyed");

        if (Conn != null) {
            try {
                Conn.close();
                Log.d(TAG, "onDestroy: Database connection closed");
            } catch (Exception e) {
                Log.e(TAG, "onDestroy: Error closing database", e);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart:  Activity restarting");
    }
}