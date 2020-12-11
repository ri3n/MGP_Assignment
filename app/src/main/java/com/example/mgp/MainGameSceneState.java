package com.example.mgp;

import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.mgp.Entities.EnterButton;
import com.example.mgp.Entities.EntityManager;
import com.example.mgp.Entities.EntityPortal;
import com.example.mgp.Entities.EntitySmurf;
import com.example.mgp.Entities.LeftButton;
import com.example.mgp.Entities.PauseButton;
import com.example.mgp.Entities.RenderSideScrollingBackground;
import com.example.mgp.Entities.RenderTextEntity;
import com.example.mgp.Entities.RightButton;
// Created by TanSiewLan2020

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;

    //Entity holders
    EntitySmurf smurf;
    EntityPortal portal;

    //Button holders
    LeftButton left_button;
    RightButton right_button;
    EnterButton enter_button;

    //Background holders
    RenderSideScrollingBackground Background;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        // Example to include another Renderview for Pause Button
        Background = RenderSideScrollingBackground.Create(R.drawable.gamepage);
        smurf = EntitySmurf.Create();
        PauseButton.Create();
        RenderTextEntity.Create();
        portal = EntityPortal.Create(3,3, _view.getWidth(), _view.getHeight()/2);
        left_button = LeftButton.Create();
        right_button = RightButton.Create();
        enter_button = EnterButton.Create();
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

        if(left_button.isPressed()){
            smurf.moveLeft(0);
            Background.Direction = 1;
        }
        else if(right_button.isPressed()){
            smurf.moveRight(0);
            Background.Direction = -1;
        }
        else Background.Direction = 0;

        //if (Collision.Quad(smurf.GetPosX(), smurf.GetPosY(), smurf.GetRadius() * 2, smurf.GetRadius() * 2, portal.GetPosX(), portal.GetPosY()))
        if (Collision.SphereToSphere(smurf.GetPosX(),smurf.GetPosY(),smurf.GetRadius() ,portal.GetPosX(),portal.GetPosY(),portal.GetRadius()))
            enter_button.MakeVisible = true;
        else enter_button.MakeVisible = false;

//    public static boolean Quad(float x1,float y1,float width,float height,float posX,float posY)


    }
}



