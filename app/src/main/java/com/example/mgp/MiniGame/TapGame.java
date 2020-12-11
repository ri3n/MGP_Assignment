package com.example.mgp.MiniGame;

import android.graphics.Canvas;
import android.view.SurfaceView;
import android.view.View;

import com.example.mgp.*;
import com.example.mgp.Entities.EntityHackerMan;
import com.example.mgp.Entities.EntityManager;
import com.example.mgp.Entities.RenderBackground;
import com.example.mgp.Entities.RenderTextEntity;

public class TapGame implements StateBase {
    RenderBackground Background;
    EntityHackerMan hackerman;
    RenderTextEntity textRender;

    @Override
    public String GetName() {
        return "MINIGAME_TAPGAME";
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        Background = RenderBackground.Create(R.drawable.gamepage);
        hackerman = EntityHackerMan.Create();
        textRender = RenderTextEntity.Create();
        textRender.RenderFPS(true);
        textRender.RenderScore(true);
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
        textRender.SetScore(hackerman.GetScore());
    }
}
