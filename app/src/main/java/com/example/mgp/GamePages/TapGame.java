package com.example.mgp.GamePages;

import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.mgp.*;
import com.example.mgp.Entities.EntityHackerMan;
import com.example.mgp.Entities.EntityManager;
import com.example.mgp.Entities.EntitySmurf;
import com.example.mgp.Entities.PauseButton;
import com.example.mgp.Entities.RenderBackground;
import com.example.mgp.Entities.RenderTextEntity;

import java.text.DecimalFormat;
import java.util.Random;
/*Scene: Tap Game
  Created by: Kai Yang
*/

public class TapGame implements StateBase {
    RenderBackground Background;
    EntityHackerMan hackerman;
    EntitySmurf smurf;
    RenderTextEntity FPSText;
    RenderTextEntity ScoreText;
    RenderTextEntity TimerText;
    RenderTextEntity LifeText;

    Random random;

    private int ScreenWidth,ScreenHeight;

    float GameTime;
    float CDTimer;
    int score;
    int lives;

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
        FPSText = RenderTextEntity.Create("FPS: ", 70, 35 , 80, true);
        ScoreText = RenderTextEntity.Create("Score: ", 70 , 1000 , 80, true);
        TimerText = RenderTextEntity.Create("" , 70 , ScreenWidth/2 - 2 , ScreenHeight, true);
        LifeText = RenderTextEntity.Create("life: ", 70, ScreenWidth / 50 , ScreenHeight , true);
        PauseButton.Create();

        random = new Random();
        GameTime = 60.f;
        CDTimer = random.nextFloat();
        GameSystem.Instance.SaveEditBegin();
        score = 0;
        lives = 3;
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
        if(hackerman == null || hackerman.IsDone() ){
            CDTimer -= _dt;
        }

        if(CDTimer<=0){
            hackerman = EntityHackerMan.Create();
            CDTimer = random.nextFloat();
        }

        EntityManager.Instance.Update(_dt);

        if(hackerman.GetScored()==true){
            score++;
            hackerman.SetScored(false);
        }

        if(hackerman.GetDied()){
            lives--;
            hackerman.SetDied();
        }

        GameTime -= _dt;

        //FPSText Updates
        FPSCounter.Instance.Update(_dt);
        FPSText.text = "FPS: " + FPSCounter.Instance.fps;

        //ScoreText Updates
        ScoreText.text = "Score: " + String.valueOf(score);

        //LifeText Updates
        LifeText.text = "Life: "+ String.valueOf(lives);

        if(GameSystem.Instance.GetIntFromSave("Score") < score)
        {
            GameSystem.Instance.SetIntInSave("Score", score);
        }

        //update game time
        DecimalFormat numberFormat = new DecimalFormat("#.#");
        String time = numberFormat.format(GameTime);
        TimerText.text = time;

        if(GameTime<=0.f||lives<=0)
        {
            StateManager.Instance.ChangeState("MainGame");
        }
    }
}
