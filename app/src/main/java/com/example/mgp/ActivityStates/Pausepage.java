package com.example.mgp.ActivityStates;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.mgp.R;

public class Pausepage extends Activity implements View.OnClickListener
{
    Button btn_resume;
    Button btn_options;
    Button btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pausepage);


        //Init buttons
        //R.id.___ can be found inside the .xml files
        btn_resume = (Button)findViewById(R.id.btn_resume);
        btn_resume.setOnClickListener(this);

        btn_options = (Button)findViewById(R.id.pausebtn_options);
        btn_options.setOnClickListener(this);

        btn_exit = (Button)findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
        //btn_start = (Button)findViewById(R.id.btn_start);
        //btn_start.setOnClickListener(this);

        //btn_quit = (Button)findViewById(R.id.btn_quit);
        //btn_quit.setOnClickListener(this);
    }

    @Override
    //Invoke a callback event in the view (something has to happen)
    //Activity -> Intent -> Action
    public void onClick(View v)
    {
        //This function consists of: Intent of the click and the action afterwards

        Intent intent = new Intent();

        if (v == btn_resume) //if usr clicks on start
        {
            intent.setClass(this, Gamepage.class); //Set the intent right
        }

        else if (v == btn_exit)
        {
            intent.setClass(this,Mainmenu.class); //Set the intent right
        }

        else if (v == btn_options)
        {
            intent.setClass(this,Optionspage.class); //Set the intent right
        }
        //Transit the screen
        startActivity(intent);
    }

}
