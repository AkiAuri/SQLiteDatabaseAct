package com.jccanizares.sqliteapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends Activity {
    public EditText Fname, Mname, Lname, Address, Email;
    public String FNAME=null, MNAME=null, LNAME=null, ADDRESS=null, EMAIL=null;
    public Intent DispForm;
    public SQLiteDatabase Conn;
    public Cursor rs;
    public static Integer ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Fname=(EditText)findViewById(R.id.Fname);
        Mname=(EditText)findViewById(R.id.Mname);
        Lname=(EditText)findViewById(R.id.Lname);
        Address=(EditText)findViewById(R.id.Address);
        Email=(EditText)findViewById(R.id.Email);
        Conn=new SQLiteDatabase(this);
        try{
            rs=Conn.getData(ID);
            rs.moveToFirst();
            Fname.setText(rs.getString(rs.getColumnIndex(Conn.PROFILE_FNAME)));
            Mname.setText(rs.getString(rs.getColumnIndex(Conn.PROFILE_MNAME)));
            Lname.setText(rs.getString(rs.getColumnIndex(Conn.PROFILE_LNAME)));
            Address.setText(rs.getString(rs.getColumnIndex(Conn.PROFILE_ADDRESS)));
            Email.setText(rs.getString(rs.getColumnIndex(Conn.PROFILE_EMAIL)));
        }
        catch(Exception e){e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage().toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void Back(View view){
        DispForm=new Intent("com.sqllite.app.RECORDSACTIVITY"); startActivity(DispForm);
    }

    public void UpdateRecord(View view){
        FNAME=Fname.getText().toString();
        MNAME=Mname.getText().toString();
        LNAME=Lname.getText().toString();
        if (FNAME.equals("")){
            Fname.setError("Please Enter Your First Name"); Fname.requestFocus();
        }
        else if (MNAME.equals("")){
            Mname.setError("Please Enter Your Middle Name"); Mname.requestFocus();
        }
        else if (LNAME.equals("")){
            Lname.setError("Please Enter Your Last Name"); Lname.requestFocus();
        }else{
            try{
                Conn.UpdateRecords(FNAME, MNAME, LNAME, ID);
                Fname.setText("");Mname.setText("");Lname.setText("");
                Toast.makeText(getApplicationContext(), "RECORD UPDATED!", Toast.LENGTH_SHORT).show();
                DispForm=new Intent("com.sqllite.app.RECORDSACTIVITY"); startActivity(DispForm);
            }catch(Exception e){e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Error:"+e.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void DeleteRecord(View view){
        try{
            Conn.DeleteRecord(ID);
            Toast.makeText(getApplicationContext(), "RECORD DELETED!", Toast.LENGTH_SHORT).show();
            DispForm=new Intent("com.sqllite.app.RECORDSACTIVITY"); startActivity(DispForm);
        }catch(Exception e){e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error:"+e.getMessage().toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onPause() {
    // TODO Auto-generated method stub
        super.onPause(); finish();
    }
}
