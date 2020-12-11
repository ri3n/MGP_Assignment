package com.example.mgp.MiniGame;

import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.mgp.*;

public class TapGame implements StateBase {
    RenderBackground Background;
    @Override
    public String GetName() {
        return "MINIGAME_TAPGAME";
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        Background = RenderBackground.Create(R.drawable.gamepage);
    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
    }

    @Override
    public void Render(Canvas _canvas) {
        EntityManager.Instance.Render(_canvas);
    }

    @Override
    public void Update(float _dt) {
        EntityManager.Instance.Update(_dt);
    }
}
