package com.example.mgp;

import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.mgp.Entities.EntityManager;
import com.example.mgp.Entities.EntitySmurf;

public class InstructionSceneState implements StateBase{

    private float timer = 0.0f;

    @Override
    public String GetName() {
        return "InstructionP";
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        EntitySmurf.Create();
    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
        Gamepage.Instance.finish();
    }

    @Override
    public void Render(Canvas _canvas) {
        EntityManager.Instance.Render(_canvas);
    }

    @Override
    public void Update(float _dt) {
// Other example: Set random position for image to appear randomly on the screen

        // example: Set time for image smurf to appear on the screen
        timer += _dt;
        if (timer > 1.0f)
        {
            EntitySmurf.Create();  //Example: Smurf Sprite
            timer = 0.0f;
        }

        EntityManager.Instance.Update(_dt);

        // If you want to transit to another scene from here, Do this below
        if (TouchManager.Instance.IsDown()) {
            //Example of touch on screen in the main game to trigger back to Main menu
            StateManager.Instance.ChangeState("MainMenuState");
        }
    }
}
