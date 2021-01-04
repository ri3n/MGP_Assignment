package com.example.mgp.MiniGame;

import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.mgp.*;
import com.example.mgp.Entities.EntityHackerMan;
import com.example.mgp.Entities.EntityManager;
import com.example.mgp.Entities.RenderBackground;
import com.example.mgp.Entities.RenderTextEntity;

import java.text.DecimalFormat;
import java.util.Random;

public class TapGame implements StateBase {
    RenderBackground Background;
    EntityHackerMan hackerman;
    RenderTextEntity FPSText;
    RenderTextEntity ScoreText;
    RenderTextEntity TimerText;

    Random random;

    private int ScreenWidth,ScreenHeight;

    float GameTime;
    int score;

    @Override
    public String GetName() {
        return "MINIGAME_TAPGAME";
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        ScreenWidth = ScreenConstants.GetScreenWidth(_view);
        ScreenHeight = ScreenConstants.GetScreenHeight(_view);

        Background = RenderBackground.Create(R.drawable.gamepage);
        hackerman = EntityHackerMan.Create();
        FPSText = RenderTextEntity.Create("FPS: ", 70, 35 , 80);
        ScoreText = RenderTextEntity.Create("Score: ", 70 , 1000 , 80);
        TimerText = RenderTextEntity.Create("" , 70 , ScreenWidth/2 - 2 , ScreenHeight);
        //textRender.RenderFPS(true);
        //textRender.RenderScore(true);
        GameTime = 60.f;
        GameSystem.Instance.SaveEditBegin();
    }

    @Override
    public void OnExit() {
        GameSystem.Instance.SaveEditEnd();
        EntityManager.Instance.Clean();
    }

    @Override
    public void Render(Canvas _canvas) { EntityManager.Instance.Render(_canvas); }

    @Override
    public void Update(float _dt)
    {
        if(hackerman == null || hackerman.IsDone()){
//            random.nextInt(3);
//            if(random.nextInt(3)==1)
//            {
//
//            }
            hackerman = EntityHackerMan.Create();
        }
        
        EntityManager.Instance.Update(_dt);

        if(hackerman.GetScored()==true){
            score++;
            hackerman.SetScored(false);
        }

        GameTime -= _dt;

        //FPSText Updates
        FPSCounter.Instance.Update(_dt);
        FPSText.text = "FPS: " + FPSCounter.Instance.fps;

        //ScoreText Updates
        //ScoreText.text = "Score: " + hackerman.GetScore();
        ScoreText.text = "Score: " + String.valueOf(score);

        GameSystem.Instance.SetIntInSave("Score", hackerman.GetScore());

        //update game time
        DecimalFormat numberFormat = new DecimalFormat("#.#");
        String time = numberFormat.format(GameTime);
        TimerText.text = time;

        if(GameTime<=0.f)
        {
            StateManager.Instance.ChangeState("MainGame");
        }
    }
}
