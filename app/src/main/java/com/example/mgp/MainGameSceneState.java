package com.example.mgp;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

import org.w3c.dom.Entity;

// Created by TanSiewLan2020

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;
    EntitySmurf smurf;
    LeftButton left_button;
    RightButton right_button;


    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        // Example to include another Renderview for Pause Button
        RenderSideScrollingBackground.Create(R.drawable.gamepage2);
        smurf = EntitySmurf.Create();
        PauseButton.Create();
        RenderTextEntity.Create();
        left_button = LeftButton.Create();
        right_button = RightButton.Create();
        float xPos=0;
    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
        Gamepage.Instance.finish();
    }

    @Override
    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);
    }

    @Override
    public void Update(float _dt) {

        EntityManager.Instance.Update(_dt);
        if (TouchManager.Instance.IsDown()) {
			
            //Example of touch on screen in the main game to trigger back to Main menu
            //StateManager.Instance.ChangeState("Mainmenu");
        }
        if(left_button.isPressed()){
            smurf.moveLeft();
        }
        if(right_button.isPressed()){
            smurf.moveRight();
        }
    }
}



