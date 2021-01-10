package com.example.mgp.GamePages;

import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.mgp.Collision;
import com.example.mgp.Entities.EnterButton;
import com.example.mgp.Entities.EntityCharacter;
import com.example.mgp.Entities.EntityHouse;
import com.example.mgp.Entities.EntityManager;
import com.example.mgp.Entities.EntityPortal;
import com.example.mgp.Entities.EntitySmurf;
import com.example.mgp.Entities.LeftButton;
import com.example.mgp.Entities.PauseButton;
import com.example.mgp.Entities.RenderSideScrollingBackground;
import com.example.mgp.Entities.RenderTextEntity;
import com.example.mgp.Entities.RightButton;
import com.example.mgp.FPSCounter;
import com.example.mgp.GameSystem;
import com.example.mgp.LayerConstants;
import com.example.mgp.R;
import com.example.mgp.StateBase;
// Created by TanSiewLan2020

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;

    //Entity holders
    EntitySmurf smurf;
    EntityPortal portal_tapgame;
    EntityPortal portal_obstaclegame;
    EntityCharacter player;
    EntityHouse house;

    //Button holders
    LeftButton left_button;
    RightButton right_button;
    EnterButton enter_button;

    //Background holders
    RenderSideScrollingBackground Background;

    //Text holders
    RenderTextEntity FPSText;
    RenderTextEntity WelcomeText;
    RenderTextEntity ScoreEntity;

    private float ChangeTextTimer = 10;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        // Example to include another Renderview for Pause Button
        Background = RenderSideScrollingBackground.Create(R.drawable.gamepage);
        Background.moveSpeed = 300;
        player = EntityCharacter.Create(LayerConstants.GAMEOBJECTS_LAYER ,R.drawable.stickman_sprite,1,4,4,4,3,3);
        PauseButton.Create();
        FPSText = RenderTextEntity.Create("FPS: ", 70, 35,80, true);
        WelcomeText = RenderTextEntity.Create("Welcome to KEYBOARD WARRIOR!", 70, 35, 160, true);
        portal_tapgame = EntityPortal.Create(3,3, _view.getWidth() / 2, _view.getHeight()/2, R.drawable.portal_sprite,1,11,11,11);
        portal_obstaclegame = EntityPortal.Create(3,3,_view.getWidth() - 150,_view.getHeight()/2,R.drawable.portal2_sprite,1,5,5,5);
        left_button = LeftButton.Create();
        right_button = RightButton.Create();
        enter_button = EnterButton.Create();
        house = EntityHouse.Create();
        ScoreEntity = RenderTextEntity.Create("Highscore: "+ GameSystem.Instance.GetIntFromSave("Score"),70 ,_view.getWidth()/2 -(6*35), _view.getHeight()/2 - 150, false);
        float xPos=0;
    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
        //Gamepage.Instance.finish();
    }

    @Override
    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);
    }

    private void UpdateWelcomeText(float _dt)
    {
        ChangeTextTimer -= _dt;

        if (WelcomeText != null || !WelcomeText.IsDone())
        {
            if (ChangeTextTimer <= 5.f && ChangeTextTimer > 0.f)
            {
                WelcomeText.text = "This is the lobby";
            }
            else if (ChangeTextTimer <= 0.f)
            {
                WelcomeText.text = "Enter the portal to begin!";
            }
        }

        if (ChangeTextTimer <= -3)
        {
            WelcomeText.SetIsDone(true);
        }
    }

    @Override
    public void Update(float _dt) {

        //Update WelcomeText
        UpdateWelcomeText(_dt);

        //Update FPSCounter
        FPSCounter.Instance.Update(_dt);
        FPSText.text = "FPS: " + FPSCounter.Instance.fps;

        //Update EntityManager
        EntityManager.Instance.Update(_dt);

        //Update Buttons
        if(left_button.isPressed()){
            player.moveLeft(0);
            Background.Direction = 1;
        }
        else if(right_button.isPressed()){
            player.moveRight(0);
            Background.Direction = -1;
        }
        else Background.Direction = 0;

        //Update moving entites by setting the move values
        ScoreEntity.SetMoveValue(Background.moveValue);
        portal_tapgame.SetMoveValue(Background.moveValue);
        portal_obstaclegame.SetMoveValue(Background.moveValue);
        house.SetMoveValue(Background.moveValue);

        //if (Collision.Quad(smurf.GetPosX(), smurf.GetPosY(), smurf.GetRadius() * 2, smurf.GetRadius() * 2, portal.GetPosX(), portal.GetPosY()))
        if (Collision.SphereToSphere(player.GetPosX(),player.GetPosY(),player.GetRadius() , portal_tapgame.GetPosX(), portal_tapgame.GetPosY(), portal_tapgame.GetRadius())) {
            enter_button.MakeVisible = true;
            enter_button.nextScene = "MINIGAME_TAPGAME";
        }
        else if (Collision.SphereToSphere(player.GetPosX(),player.GetPosY(),player.GetRadius() ,house.GetPosX(),house.GetPosY(),house.GetRadius())) {
            enter_button.MakeVisible = true;
            enter_button.nextScene = "Mainmenu";
        }
        else if (Collision.SphereToSphere(player.GetPosX(),player.GetPosY(),player.GetRadius() ,portal_obstaclegame.GetPosX(),portal_obstaclegame.GetPosY(),portal_obstaclegame.GetRadius())) {
            enter_button.MakeVisible = true;
            enter_button.nextScene = "MINIGAME_OBSTACLEGAME";
        }
        else enter_button.MakeVisible = false;


    }
}



