package com.example.mgp.GamePages;

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

    boolean KeyDownPreviously;

    @Override
    public String GetName() {
        return "MINIGAME_OBSTACLEGAME";
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        player = EntityCharacter.Create(LayerConstants.GAMEOBJECTS_LAYER,R.drawable.stickman_run_sprite,1,7,7,7,3,3);
        background = RenderSideScrollingBackground.Create(R.drawable.gamepage);
        background.moveSpeed = -500;
        obstacle = EntityObstacle.Create(R.drawable.obstacle_virus,ScreenConstants.GetScreenWidth(_view)/2, ScreenConstants.GetScreenHeight(_view)/2,15,15);
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

        //obstacle.SetMoveValue(background.moveValue);

        if(TouchManager.Instance.IsPress())
        {
            System.out.println("is down");
        }
    }
}
