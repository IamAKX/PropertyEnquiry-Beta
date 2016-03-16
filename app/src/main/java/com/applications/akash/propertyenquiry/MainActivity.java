package com.applications.akash.propertyenquiry;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    TextView tv;
    String internetStatus="No Internet Connection...";
    static final int TIMER_RUNTIME = 3000;
    static String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initContents();
    }

    private void initContents() {

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
            tv = (TextView)findViewById(R.id.progress_prompt);
            new UserInfoManager().getDetail(getBaseContext());
            runThread();
            ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
            if(cd.isConnectingToInternet())
            internetStatus="Internet Available...";

            SharedPreferences sp = getBaseContext().getSharedPreferences("UserPref", Activity.MODE_PRIVATE);
            SharedPreferences.Editor sEdit = sp.edit();
            if(sp.getString("loggedin","false").equals("true"))
            uname="Loading "+sp.getString("uname","User Not Logged In...")+" credentials...";
            else
            uname="User Not Logged In...";



    }

    int i=0;
    private void runThread() {

        new Thread() {
            public void run() {
                while (i++ < TIMER_RUNTIME) {
                    try {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                if(i==700)
                                    tv.setText("Loading your preferences...");
                                else
                                if(i==1000)
                                    tv.setText(uname);
                                else
                                if(i==1500)
                                {
                                    tv.setText("Checking your Network Connection...");
                                }
                                else
                                if(i==2000)
                                    tv.setText(internetStatus);
                                else
                                if(i==2600)
                                    tv.setText("Let's EXPLORE !!");
                                updateProgress(i);
                            }
                        });
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void timeToMoveOn() {
        Intent i = new Intent(getBaseContext(),HomeActivity.class);
        startActivity(i);
        finish();
    }


    public void updateProgress(final int timePassed) {
        if(null != mProgressBar) {
            // Ignore rounding error here

            final int progress = mProgressBar.getMax() * timePassed / TIMER_RUNTIME;
            mProgressBar.setProgress(progress);
            if(progress==mProgressBar.getMax())
                timeToMoveOn();
        }
    }



}
