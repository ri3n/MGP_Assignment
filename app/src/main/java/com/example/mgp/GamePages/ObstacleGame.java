/*
Scene: Obstacle Game
Created by: Ryan Ang
 */

package com.example.mgp.GamePages;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.mgp.*;
import com.example.mgp.Entities.EntityCharacter;
import com.example.mgp.Entities.EntityManager;
import com.example.mgp.Entities.EntityObstacle;
import com.example.mgp.Entities.EntityPowerUp;
import com.example.mgp.Entities.RenderSideScrollingBackground;
import com.example.mgp.Entities.RenderTextEntity;
import com.example.mgp.Entities.EntityCoin;

public class ObstacleGame implements StateBase {

    enum GAME_STATE
    {
        READY,
        START,
        GAME,
        END,
        DEFAULT
    }

    //Entity Variables
    EntityCharacter player;
    RenderSideScrollingBackground background;
    EntityObstacle obstacle;
    RenderTextEntity GameText; //Text for Ready, Start and Score
    EntityCoin coin;
    EntityPowerUp powerUp;

    //Physics Variables
    private boolean player_IsInAir;
    private float GROUND_LEVEL;
    private float Gravity;
    private float netForce;
    private float mass;
    private float simulation_speed;
    private float textDisplayTimer;

    //Game Variables
    private boolean GameOver = false;
    private int score;
    private float GameOverTimer;
    GAME_STATE curr_GameState;
    private float survivalTimer;
    private float totalElapsedTime;
    private boolean HasIncreased;

    private int screenWidth,screenHeight;

    @Override
    public String GetName() {
        return "MINIGAME_OBSTACLEGAME";
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        // ranGen will produce random x values based on the view size

        Bitmap playerbmp = ResourceManager.Instance.GetBitmap(R.drawable.stickman_run_sprite);
        Bitmap virusbmp = ResourceManager.Instance.GetBitmap(R.drawable.obstacle_virus);
        GROUND_LEVEL = _view.getHeight() - playerbmp.getHeight()*3;
        player = EntityCharacter.Create(R.drawable.stickman_run_sprite,playerbmp.getWidth()/3,(int)GROUND_LEVEL,1,7,7,7,5,5);
        background = RenderSideScrollingBackground.Create(R.drawable.gamepage);
        background.moveSpeed = -500;
        obstacle = EntityObstacle.Create(R.drawable.obstacle_virus,ScreenConstants.GetScreenWidth(_view) + virusbmp.getWidth(), (int)GROUND_LEVEL + 75,1,1);
        player_IsInAir = false;
        Gravity = -9.8f;
        mass = 1;
        simulation_speed = 4;
        score = 0;
        GameOver = false;
        GameOverTimer = 3;
        GameText = RenderTextEntity.Create("READY?",250,_view.getWidth()/2 - (125*3),_view.getHeight()/4,true);
        curr_GameState = GAME_STATE.READY;
        textDisplayTimer = 3;
        survivalTimer = 0;
        screenHeight = ScreenConstants.GetScreenHeight(_view);
        screenWidth = ScreenConstants.GetScreenWidth(_view);
        totalElapsedTime = 0;
        HasIncreased = false;
        coin = EntityCoin.Create();
        powerUp = EntityPowerUp.Create();
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

        switch(curr_GameState)
        {
            case READY:
                GameText.text = "READY?";
                textDisplayTimer -= _dt;
                if (textDisplayTimer <= 0.f)
                {
                    curr_GameState = GAME_STATE.START;
                    textDisplayTimer = 1;
                }
                break;
            case START:
                GameText.text = "START!";
                textDisplayTimer -= _dt;
                if (textDisplayTimer <= 0.f)
                    curr_GameState = GAME_STATE.GAME;
                break;
            case GAME:
                //Background Entity updates
                if (background.isMoving)
                {
                    coin.SetMoveValue((int)background.moveValue);
                    powerUp.SetMoveValue((int)background.moveValue);
                    obstacle.SetMoveValue(background.moveValue);
                }
                else
                {
                    powerUp.SetMoveValue(0);
                    coin.SetMoveValue(0);
                    obstacle.SetMoveValue(0);
                }

                totalElapsedTime += _dt;
                if ((int)totalElapsedTime % 10 == 0 && totalElapsedTime >= 1 && !HasIncreased)
                {
                    background.moveSpeed -= 25;
                    HasIncreased = true;
                    System.out.println("Background Speed: " + background.moveSpeed);
                }
                else if ((int)totalElapsedTime % 10 != 0) HasIncreased = false;

                //Jump Updates
                if(TouchManager.Instance.IsPress()) //Jump
                    Jump();

                //If player_IsJumping == true, then use netForce and set player.screenY to physics
                if (player_IsInAir)
                {
                    netForce += Gravity * simulation_speed;
                    float acceleration = netForce / mass;
                    float newY = player.GetPosY() - (acceleration * _dt);
                    //if player hits the top of the screen
                    if (newY <= player.GetRadius())
                    {
                        netForce = 0;
                        newY = player.GetRadius();
                    }
                    player.SetPosY(newY);
                    //If player is lower than ground level(since Y is inverted)
                    if (player.GetPosY() >= GROUND_LEVEL)
                    {
                        player_IsInAir = false;
                        player.SetPosY(GROUND_LEVEL);
                    }
                }
                //Collision Updates (with obstacle)
                if (Collision.Quad(player.GetPosX(),player.GetPosY(),player.GetRadius(),player.GetRadius(),obstacle.GetPosX(),obstacle.GetPosY(),obstacle.GetRadius(),obstacle.GetRadius()))
                {
                    background.isMoving = false;
                    curr_GameState = GAME_STATE.END;
                }
                else background.isMoving = true;

                //Collision Updates (with coin)
                if (coin.IsActive() &&
                        Collision.Quad(player.GetPosX(),player.GetPosY(),player.GetRadius(),player.GetRadius(),coin.GetPosX(),coin.GetPosY(),coin.GetRadius(),coin.GetRadius()))
                {
                    ++score;
                    coin.SetActive(false);
                }

                if (powerUp.IsActive() &&
                        Collision.Quad(player.GetPosX(),player.GetPosY(),player.GetRadius(),player.GetRadius(),powerUp.GetPosX(),powerUp.GetPosY(),powerUp.GetRadius(),powerUp.GetRadius()))
                {
                    ++score;
                    powerUp.SetActive(false);
                }

                //Score management
                survivalTimer += _dt;
                if ( (int)survivalTimer % 5 == 0 && (int)survivalTimer != 0)
                {
                    ++score;
                    survivalTimer = 0;
                }

                GameText.text = String.valueOf(score);
                GameText.SetxPos(screenWidth / 2);
                break;

            case END:
                powerUp.SetMoveValue(0);
                background.isMoving = false;
                coin.SetMoveValue(0);
                obstacle.SetMoveValue(0);
                GameText.text = "GAME OVER!";
                GameText.SetxPos(screenWidth / 2 - (5 * 125));
                GameOverTimer -= _dt;
                if (GameOverTimer <= 0)
                {
                    StateManager.Instance.ChangeState("MainGame");
                }
                break;
            default:
                break;

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
