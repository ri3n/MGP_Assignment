package com.example.mgp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

//extends Activity - for every new screen
//implements OnClickListener: for the screen to be touchscreen
// Layout Error possiblities:
// - Type
// - Image not found
// - Placed in wrong directory
// - Syntax Error
// !! Never resolve an XML error with import R
// R: Resource registry
public class Mainmenu extends Activity implements OnClickListener,StateBase
{
    //Start button
    private Button btn_start;
    private Button btn_options;

    private boolean isRender = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mainmenu);



        //Init buttons
        //R.id.___ can be found inside the .xml files
        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

        btn_options = (Button)findViewById(R.id.btn_options);
        btn_options.setOnClickListener(this);
    }

    @Override
    //Invoke a callback event in the view (something has to happen)
    //Activity -> Intent -> Action
    public void onClick(View v)
    {
        //This function consists of: Intent of the click and the action afterwards

        Intent intent = new Intent();

        if (v == btn_start) //if usr clicks on start
        {
            intent.setClass(this,Splashpage.class); //Set the intent right
            StateManager.Instance.ChangeState("Default"); // Default is like a loading page
        }

        if (v == btn_options)
        {
            intent.setClass(this,Optionspage.class);
            intent.putExtra("prevPage", 1);
        }



        //Transit the screen
        startActivity(intent);
    }

    @Override
    public void Render(Canvas _canvas) {
    }

    @Override
    public void OnEnter(SurfaceView _view) {
    }

    @Override
    public void OnExit() {
    }

    @Override
    public void Update(float _dt) {
    }

    @Override
    public String GetName() {
        return "Mainmenu";
    }


    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

}
