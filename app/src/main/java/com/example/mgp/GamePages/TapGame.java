package com.example.mgp.GamePages;

import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.mgp.*;
import com.example.mgp.Entities.EntityHackerMan;
import com.example.mgp.Entities.EntityManager;
import com.example.mgp.Entities.EntitySmurf;
import com.example.mgp.Entities.EntityTrap;
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
    EntityTrap trap;
    RenderTextEntity FPSText;
    RenderTextEntity ScoreText;
    RenderTextEntity TimerText;
    RenderTextEntity LifeText;
    RenderTextEntity ComboText;

    Random random;

    private int ScreenWidth,ScreenHeight;

    float GameTime;
    float CDTimer, TrapTimer;
    int score,lives,combo;

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
        trap = EntityTrap.Create();
        FPSText = RenderTextEntity.Create("FPS: ", 70, 35 , 80, true);
        ScoreText = RenderTextEntity.Create("Score: ", 70 , 1000 , 80, true);
        TimerText = RenderTextEntity.Create("" , 70 , ScreenWidth/2 - 2 , ScreenHeight, true);
        LifeText = RenderTextEntity.Create("life: ", 70, ScreenWidth / 50 , ScreenHeight , true);
        ComboText = RenderTextEntity.Create("Combo: ",70, ScreenWidth - 350 , ScreenHeight , true);
        PauseButton.Create();

        random = new Random();
        GameTime = 60.f;
        CDTimer = random.nextFloat();
        TrapTimer = 5.f;
        GameSystem.Instance.SaveEditBegin();
        score = 0;
        lives = 3;
        combo = 0;
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
        if(hackerman.GetRespawn() == true) {
            CDTimer -= _dt;
        }

        if(CDTimer <= 0){
            hackerman.SetRender(true);
            hackerman.SetRespawn(false);
            hackerman.SetPos();
            CDTimer = 2.f;
        }

        if(trap.GetRespawn() == true) {
            TrapTimer -= _dt;
        }

        if(TrapTimer <= 0){
            trap.SetRender(true);
            trap.SetRespawn(false);
            trap.SetPos();
            TrapTimer = 5.f;
        }

//        if(trap.GetActive() == false){
//            TrapTimer -= _dt;
//        }
//        if(TrapTimer <= 0){
//            trap.SetActive(true);
//        }

        EntityManager.Instance.Update(_dt);

        if(hackerman.GetScored() == true){
            combo++;
            if(combo > 2 && combo < 5){
                score += 2;
            }
            else if(combo > 5 && combo < 8){
                score += 3;
            }
            else if(combo > 10){
                score += 3;
                GameTime+=1;
            }
            else{
                score++;
            }
            hackerman.SetScored(false);
        }

        if(hackerman.GetDied()){
            lives--;
            combo = 0;
            hackerman.SetDied();
        }

        if(trap.GetScored() == true){
            GameTime -= 5.f;
            combo = 0;
            trap.SetScored(false);
        }

        if(trap.GetDied()){
            trap.SetDied();
        }

        if(GameSystem.Instance.GetIsPaused()==false)
        {
            GameTime -= _dt;
        }

        //FPSText Updates
        FPSCounter.Instance.Update(_dt);
        FPSText.text = "FPS: " + FPSCounter.Instance.fps;

        //ScoreText Updates
        ScoreText.text = "Score: " + String.valueOf(score);

        //LifeText Updates
        LifeText.text = "Life: "+ String.valueOf(lives);

        ComboText.text = "Combo: " + String.valueOf(combo);

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

            //saving the values
            GameSystem.Instance.SaveEditBegin();
            GameSystem.Instance.SetIntInSave("tapgameCount", GameSystem.Instance.GetIntFromSave("tapgameCount") + 1);
            GameSystem.Instance.SetIntInSave("tapgame"+GameSystem.Instance.GetIntFromSave("tapgameCount"), score);
            GameSystem.Instance.SaveEditEnd();

            //get value
            for(int i = 0 ;i < GameSystem.Instance.GetIntFromSave("tapgameCount"); ++i){
                //GameSystem.Instance.Remove("tapgame"+i);
                String test=String.valueOf(GameSystem.Instance.GetIntFromSave("tapgame"+i));
                System.out.println(test);
            }
        }
    }
}
