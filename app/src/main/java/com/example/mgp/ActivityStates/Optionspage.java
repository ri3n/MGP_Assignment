package com.example.mgp.ActivityStates;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.mgp.AudioManager;
import com.example.mgp.R;

public class Optionspage extends Activity implements View.OnClickListener
{
    private Switch btn_sfx;
    private Button btn_back;
    private Switch btn_vibrations;
    private Switch btn_bgm;

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
        btn_sfx = (Switch) findViewById(R.id.btn_togglesfx);
        if (AudioManager.Instance.GetIsMuted() == true)
        {
            btn_sfx.setText("   OFF");
            btn_sfx.setChecked(false);
        }
        else
        {
            btn_sfx.setText("   ON");
            btn_sfx.setChecked(true);
        }

        btn_sfx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                AudioManager.Instance.SetIsMuted(!isChecked);
                System.out.println("AudioManager: " + !isChecked);
                if (isChecked)
                    btn_sfx.setText("   ON");
                else
                    btn_sfx.setText("   OFF");
            }
        });


        btn_vibrations = (Switch) findViewById(R.id.btn_togglevibrations);
        if(btn_vibrations.isChecked()) btn_vibrations.setText("   ON");
        else btn_vibrations.setText("   OFF");

        btn_vibrations.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    btn_vibrations.setText("   ON");
                else
                    btn_vibrations.setText("   OFF");
            }
        });

        btn_bgm = (Switch) findViewById(R.id.btn_togglebgm);
        if(btn_bgm.isChecked()) btn_bgm.setText("   ON");
        else btn_bgm.setText("   OFF");
        btn_bgm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    btn_bgm.setText("   ON");
                else
                    btn_bgm.setText("   OFF");
            }
        });

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();

        if (v == btn_back)
        {
            if(getIntent().getIntExtra("prevPage",0)==1){
                intent.setClass(this, Mainmenu.class); //Set the intent right
            }
            else{
                intent.setClass(this, Pausepage.class); //Set the intent right
            }

        }

        // Transit screen
        startActivity(intent);
    }
}
