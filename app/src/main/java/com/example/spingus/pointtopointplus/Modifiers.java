package com.example.spingus.pointtopointplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Modifiers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifiers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Switch switchBike = (Switch) findViewById(R.id.switchBike);
        if(Nav.bike) {
            switchBike.toggle();
        }
        switchBike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Nav.bike = !Nav.bike;
            }
        });

       Switch switchWalk = (Switch) findViewById(R.id.switchWalk);
        if(Nav.walk) {
            switchWalk.toggle();
        }
        switchWalk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Nav.walk = !Nav.walk;
            }
        });

        Switch switchToilets = (Switch) findViewById(R.id.switchToilets);
        if(Nav.toilets) {
            switchToilets.toggle();
        }
        switchToilets.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Nav.toilets = !Nav.toilets;
            }
        });

        Switch switchBench = (Switch) findViewById(R.id.switchBenches);
        if(Nav.bench) {
            switchBench.toggle();
        }
        switchBench.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Nav.bench = !Nav.bench;
            }
        });

        Switch switchPlayground = (Switch) findViewById(R.id.switchPlayground);
        if(Nav.park) {
            switchPlayground.toggle();
        }
        switchPlayground.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Nav.park = !Nav.park;
            }
        });

        Switch switchBBQ = (Switch) findViewById(R.id.switchBarbeque);
        if(Nav.bbq) {
            switchBBQ.toggle();
        }
        switchBBQ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Nav.bbq = !Nav.bbq;
            }
        });

        Switch switchArt = (Switch) findViewById(R.id.switchArt);
        if(Nav.art) {
            switchArt.toggle();
        }
        switchArt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Nav.art = !Nav.art;
            }
        });
    }
}
