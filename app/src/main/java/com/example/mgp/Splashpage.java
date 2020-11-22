package com.example.mgp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Splashpage extends Activity implements View.OnClickListener {
    private boolean _active = true;
    private int waited=0;
    private float _splashTime = 5000;

    private Button btn_pause;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splashpage);



        Thread splashTread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    int waited = 0;
                    while (_active && (waited < _splashTime))
                    {
                        sleep(200);
                        if (_active) waited += 200;
                    }
                }
                catch (InterruptedException e)
                {
                    //Do nothing
                }
                finally
                {
                    finish();
                    //Create new activity based on and intent with CurrentActivity
                    Intent intent = new Intent(Splashpage.this,Gamepage.class);
                    startActivity(intent);
                }
            }
        };
        splashTread.start();

    }

    @Override
    //Invoke a callback event in the view (something has to happen)
    //Activity -> Intent -> Action
    public void onClick(View v)
    {
        //This function consists of: Intent of the click and the action afterwards

        Intent intent = new Intent();
        //Transit the screen
        startActivity(intent);
    }

    @Override
    //Wait for x seconds for splash page to transit to main menu
    //or click/touch screen then transit
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            _active = false;
        }
        return true;
    }
}
