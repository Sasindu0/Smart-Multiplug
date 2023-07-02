package com.example.switchtest1;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button OnOff, btn_start, btn_stop;
    TextView view, timeView;
    Switch aSwitch;
    Spinner spinner, spinner2;
    int hour, min = 0;
    DatabaseReference databaseReference, databaseReference1, databaseReference2, databaseReference3, databaseReference4, databaseReference5;
    int batteryPercentage;

    public void checkState(){
        databaseReference1 = FirebaseDatabase.getInstance().getReference("OnOff");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("timer/running");
        databaseReference3 = FirebaseDatabase.getInstance().getReference("timer/hour");
        databaseReference4 = FirebaseDatabase.getInstance().getReference("timer/min");
        databaseReference5 = FirebaseDatabase.getInstance().getReference("timer/switch");

        OnOff = findViewById(R.id.btn_onOff);
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        view = findViewById(R.id.textView);
        aSwitch = findViewById(R.id.switch1);
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);

        final int[] valRunning = {0};
        final int[] valOnOff = {0};
        final int[] valSwitch = {0};
        final boolean[] isSwitchTrigerr = {false};

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valOnOff[0] = dataSnapshot.getValue(Integer.class);

                if(valOnOff[0] == 1 && valRunning[0] == 0 && valSwitch[0] == 0){
                    OnOff.setBackgroundColor(Color.parseColor("#14a102"));
                    OnOff.setTextColor(Color.parseColor("#ffffff"));
                    OnOff.setText("ON");
                }
                else if (valOnOff[0] == 0 && valRunning[0] == 0 && valSwitch[0] == 0){
                    OnOff.setBackgroundColor(Color.parseColor("#ff0000"));
                    OnOff.setTextColor(Color.parseColor("#ffffff"));
                    OnOff.setText("OFF");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valRunning[0] = dataSnapshot.getValue(Integer.class);

                if(valRunning[0] == 1){
                    OnOff.setEnabled(false);
                    btn_start.setEnabled(false);
                    btn_stop.setEnabled(true);
                    spinner.setEnabled(false);
                    spinner2.setEnabled(false);
                    aSwitch.setEnabled(false);
                    timeView.setVisibility(View.VISIBLE);

                    OnOff.setBackgroundColor(Color.parseColor("#616161"));
                    OnOff.setTextColor(Color.parseColor("#303030"));
                    btn_start.setBackgroundColor(Color.parseColor("#616161"));
                    btn_start.setTextColor(Color.parseColor("#303030"));
                    btn_stop.setBackgroundColor(Color.parseColor("#ff0000"));
                    btn_stop.setTextColor(Color.parseColor("#ffffff"));
                }
                else {
                    OnOff.setEnabled(true);
                    btn_start.setEnabled(true);
                    btn_stop.setEnabled(false);
                    spinner.setEnabled(true);
                    spinner2.setEnabled(true);
                    aSwitch.setEnabled(true);
                    spinner.setSelection(0);
                    spinner2.setSelection(0);
                    timeView.setVisibility(View.INVISIBLE);

                    if(valOnOff[0] == 1 && valRunning[0] == 0 && valSwitch[0] == 0){
                        OnOff.setBackgroundColor(Color.parseColor("#14a102"));
                        OnOff.setTextColor(Color.parseColor("#ffffff"));
                        OnOff.setText("ON");
                    }
                    else if (valOnOff[0] == 0 && valRunning[0] == 0 && valSwitch[0] == 0){
                        OnOff.setBackgroundColor(Color.parseColor("#ff0000"));
                        OnOff.setTextColor(Color.parseColor("#ffffff"));
                        OnOff.setText("OFF");
                    }

                    btn_start.setBackgroundColor(Color.parseColor("#14a102"));
                    btn_start.setTextColor(Color.parseColor("#ffffff"));
                    btn_stop.setBackgroundColor(Color.parseColor("#616161"));
                    btn_stop.setTextColor(Color.parseColor("#303030"));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int valHour = dataSnapshot.getValue(Integer.class);

                if(valRunning[0] == 1){

                }
                else {

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int valMin = dataSnapshot.getValue(Integer.class);

                if(valRunning[0] == 1){

                }
                else {

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        databaseReference5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valSwitch[0] = dataSnapshot.getValue(Integer.class);

                if(valSwitch[0] == 1){
                    OnOff.setEnabled(false);
                    aSwitch.setChecked(true);
                    btn_start.setEnabled(false);
                    btn_stop.setEnabled(false);
                    spinner.setEnabled(false);
                    spinner2.setEnabled(false);
                    timeView.setVisibility(View.INVISIBLE);

                    isSwitchTrigerr[0] = true;

                    OnOff.setBackgroundColor(Color.parseColor("#616161"));
                    OnOff.setTextColor(Color.parseColor("#303030"));
                    btn_start.setBackgroundColor(Color.parseColor("#616161"));
                    btn_start.setTextColor(Color.parseColor("#303030"));
                    btn_stop.setBackgroundColor(Color.parseColor("#616161"));
                    btn_stop.setTextColor(Color.parseColor("#303030"));
                }
                else if (isSwitchTrigerr[0] && valSwitch[0] == 0){
                    OnOff.setEnabled(true);
                    aSwitch.setChecked(false);
                    btn_start.setEnabled(true);
                    btn_stop.setEnabled(true);
                    spinner.setEnabled(true);
                    spinner2.setEnabled(true);
                    timeView.setVisibility(View.INVISIBLE);

                    isSwitchTrigerr[0] = false;

                    databaseReference = FirebaseDatabase.getInstance().getReference("OnOff");
                    databaseReference.setValue(0);

                    if(valOnOff[0] == 1 && valRunning[0] == 0 && valSwitch[0] == 0){
                        OnOff.setBackgroundColor(Color.parseColor("#14a102"));
                        OnOff.setTextColor(Color.parseColor("#ffffff"));
                        OnOff.setText("ON");
                    }
                    else if (valOnOff[0] == 0 && valRunning[0] == 0 && valSwitch[0] == 0){
                        OnOff.setBackgroundColor(Color.parseColor("#ff0000"));
                        OnOff.setTextColor(Color.parseColor("#ffffff"));
                        OnOff.setText("OFF");
                    }

                    btn_start.setBackgroundColor(Color.parseColor("#14a102"));
                    btn_start.setTextColor(Color.parseColor("#ffffff"));
                    btn_stop.setBackgroundColor(Color.parseColor("#616161"));
                    btn_stop.setTextColor(Color.parseColor("#ffffff"));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkState();

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.green));


//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.title_action_bar);

        OnOff = findViewById(R.id.btn_onOff);
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);
        view = findViewById(R.id.textView);
        aSwitch = findViewById(R.id.switch1);
        timeView = findViewById(R.id.textView5);
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);

        OnOff.setBackgroundColor(Color.parseColor("#616161"));
        OnOff.setTextColor(Color.parseColor("#303030"));
        btn_start.setBackgroundColor(Color.parseColor("#616161"));
        btn_start.setTextColor(Color.parseColor("#303030"));
        btn_stop.setBackgroundColor(Color.parseColor("#616161"));
        btn_stop.setTextColor(Color.parseColor("#303030"));

        final boolean[] isOn = {true};

        OnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("OnOff");
                if(isOn[0]){
                    databaseReference.setValue(0);
                    isOn[0] = false;
                }
                else {
                    databaseReference.setValue(1);
                    isOn[0] = true;
                }
            }
        });

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                batteryPercentage = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                view.setText("Battery: "+Integer.toString(batteryPercentage)+"%");
                if(batteryPercentage == 100){
                    databaseReference = FirebaseDatabase.getInstance().getReference("timer/switch");
                    databaseReference.setValue(0);
                }

            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()){
                    databaseReference = FirebaseDatabase.getInstance().getReference("timer/switch");
                    databaseReference.setValue(1);
                    databaseReference = FirebaseDatabase.getInstance().getReference("OnOff");
                    databaseReference.setValue(1);
                    spinner.setSelection(0);
                    spinner2.setSelection(0);
                }
                else {
                    databaseReference = FirebaseDatabase.getInstance().getReference("OnOff");
                    databaseReference.setValue(0);
                    databaseReference = FirebaseDatabase.getInstance().getReference("timer/switch");
                    databaseReference.setValue(0);
                }
            }
        });

        ArrayAdapter<CharSequence> hr = ArrayAdapter.createFromResource(MainActivity.this,R.array.hour, R.layout.spinner_text);
        spinner.setAdapter(hr);
        ArrayAdapter<CharSequence> mn = ArrayAdapter.createFromResource(MainActivity.this,R.array.min, R.layout.spinner_text);
        spinner2.setAdapter(mn);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                hour = Integer.parseInt(value);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                min = Integer.parseInt(value);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("timer/on");
                databaseReference.setValue(1);
                databaseReference = FirebaseDatabase.getInstance().getReference("timer/hour");
                databaseReference.setValue(hour);
                databaseReference = FirebaseDatabase.getInstance().getReference("timer/min");
                databaseReference.setValue(min);
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("timer/running");
                databaseReference.setValue(0);
                databaseReference = FirebaseDatabase.getInstance().getReference("OnOff");
                databaseReference.setValue(0);

            }
        });



    }
}