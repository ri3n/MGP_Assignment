package com.example.mgp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Gamepage extends Activity implements View.OnClickListener {
    private Button btn_pause;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.gamepage);

        //Init buttons
        btn_pause = (Button)findViewById(R.id.btn_pause);
        btn_pause.setOnClickListener(this);
    }
    @Override
    //Invoke a callback event in the view (something has to happen)
    //Activity -> Intent -> Action
    public void onClick(View v)
    {
        Intent intent = new Intent();
        if(v == btn_pause)
        {
            intent.setClass(this,Pausepage.class);
        }
        startActivity(intent);

    }
}
