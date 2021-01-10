package com.example.mgp.GamePages;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.mgp.*;
import com.example.mgp.Entities.EntityCharacter;
import com.example.mgp.Entities.EntityManager;
import com.example.mgp.Entities.EntityObstacle;
import com.example.mgp.Entities.RenderSideScrollingBackground;

public class ObstacleGame implements StateBase {

    EntityCharacter player;
    RenderSideScrollingBackground background;
    EntityObstacle obstacle;

    //Physics Variables
    private boolean player_IsInAir;
    private float GROUND_LEVEL;
    private float Gravity;
    private float netForce;
    private float mass;
    private float simulation_speed;

    @Override
    public String GetName() {
        return "MINIGAME_OBSTACLEGAME";
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        // ranGen will produce random x values based on the view size

        Bitmap playerbmp = ResourceManager.Instance.GetBitmap(R.drawable.stickman_run_sprite);
        GROUND_LEVEL = _view.getHeight() - playerbmp.getHeight()*2;
        player = EntityCharacter.Create(R.drawable.stickman_run_sprite,playerbmp.getWidth(),(int)GROUND_LEVEL,1,7,7,7,3,3);
        background = RenderSideScrollingBackground.Create(R.drawable.gamepage);
        background.moveSpeed = -500;
        obstacle = EntityObstacle.Create(R.drawable.obstacle_virus,ScreenConstants.GetScreenWidth(_view)/2, (int)GROUND_LEVEL,1,1);
        player_IsInAir = false;
        Gravity = -9.8f;
        mass = 1;
        simulation_speed = 5;
    }

    @Override
    public void OnExit() {
    }

    @Override
    public void Render(Canvas _canvas) {
        EntityManager.Instance.Render(_canvas);
    }

    @Override
    public void Update(float _dt) {
        EntityManager.Instance.Update(_dt);

        if (background.isMoving)
        {
            obstacle.SetMoveValue(background.moveValue);
        }
        else
        {
            obstacle.SetMoveValue(0);
        }

        if(TouchManager.Instance.IsPress())
        {
            //Jump
            Jump();
        }
        if (Collision.Quad(player.GetPosX(),player.GetPosY(),player.GetRadius(),player.GetRadius(),obstacle.GetPosX(),obstacle.GetPosY(),obstacle.GetRadius(),obstacle.GetRadius()))
        {
            background.isMoving = false;
        }
        else background.isMoving = true;

        //If player_IsJumping == true, then use netForce and set player.screenY to physics
        if (player_IsInAir)
        {
            netForce += Gravity * simulation_speed;
            float acceleration = netForce / mass;
            float newY = player.GetPosY() - (acceleration * _dt);
            player.SetPosY(newY);
            if (player.GetPosY() >= GROUND_LEVEL)
            {
                player_IsInAir = false;
                player.SetPosY(GROUND_LEVEL);
            }
        }

    }

    private void Jump()
    {
        if (player_IsInAir) return;

        float JumpForce = 1000;
        netForce = JumpForce;
        player_IsInAir = true;
    }
}
