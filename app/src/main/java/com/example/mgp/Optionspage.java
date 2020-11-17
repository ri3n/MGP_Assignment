package com.example.mgp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Optionspage extends Activity implements View.OnClickListener
{
    private Button btn_sfx;
    private Button btn_back;
    private Button btn_vibrations;
    private Button btn_bgm;

    private Activity PrevScene;

    public void SetPrevScene(Activity activity)
    {
        PrevScene = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.optionspage);

        //Init buttons
        //btn_resume = (Button)findViewById(R.id.btn_resume);
        //btn_resume.setOnClickListener(this);
        btn_sfx = (Button)findViewById(R.id.btn_togglesfx);

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        btn_vibrations = (Button)findViewById(R.id.btn_togglevibrations);
        btn_bgm = (Button)findViewById(R.id.btn_togglebgm);

    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();

        if (v == btn_back)
        {
            intent.setClass(this, Mainmenu.class); //Set the intent right
        }
        if (v == btn_sfx)
        {

        }
        if (v == btn_vibrations)
        {

        }
        if (v == btn_bgm)
        {

        }

        // Transit screen
        startActivity(intent);
    }
}
