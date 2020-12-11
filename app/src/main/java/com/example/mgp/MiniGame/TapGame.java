package com.example.mgp.MiniGame;

import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.mgp.*;
import com.example.mgp.Entities.EntityHackerMan;
import com.example.mgp.Entities.EntityManager;
import com.example.mgp.Entities.RenderBackground;
import com.example.mgp.Entities.RenderTextEntity;

public class TapGame implements StateBase {
    RenderBackground Background;
    EntityHackerMan hackerman;
    RenderTextEntity FPSText;
    RenderTextEntity ScoreText;

    @Override
    public String GetName() {
        return "MINIGAME_TAPGAME";
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        Background = RenderBackground.Create(R.drawable.gamepage);
        hackerman = EntityHackerMan.Create();
        FPSText = RenderTextEntity.Create("FPS: ", 70, 35,80);
        ScoreText = RenderTextEntity.Create("Score: ", 70,1000 , 80);
        //textRender.RenderFPS(true);
        //textRender.RenderScore(true);
    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
    }

    @Override
    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);
    }

    @Override
    public void Update(float _dt)
    {
        EntityManager.Instance.Update(_dt);

        //FPSText Updates
        FPSCounter.Instance.Update(_dt);
        FPSText.text = "FPS: " + FPSCounter.Instance.fps;

        //ScoreText Updates
        ScoreText.text = "Score: " + hackerman.GetScore();
    }
}
