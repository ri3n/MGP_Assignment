package com.example.mgp.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.mgp.Collision;
import com.example.mgp.GameSystem;
import com.example.mgp.R;
import com.example.mgp.ResourceManager;
import com.example.mgp.TouchManager;
import com.example.mgp.AudioManager;
import com.example.mgp.*;

public class PauseButton implements EntityBase{
    private boolean isDone = false;
    private boolean isInit = false;

    private boolean Paused = false;

    private Bitmap bmpP = null;
    private  Bitmap bmpUP = null;
    private Bitmap scaledbmpP = null;
    private Bitmap scaledbmpUP = null;
    private int xPos,yPos;

    private int ScreenWidth,ScreenHeight;

    private int renderLayer = 1;

    @Override public boolean IsDone()
    {
        return isDone;
    }

    @Override public void SetIsDone(boolean _isDone)
    {
        isDone = _isDone;
    }

    @Override public void Init(SurfaceView _view)
    {
        bmpP = ResourceManager.Instance.GetBitmap(R.drawable.pause);
        bmpUP = ResourceManager.Instance.GetBitmap(R.drawable.ship2_1);

        // Find screen width and screen height
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        // Scale image according/ based on the screen size
        // (int)(ScreenWidth)/12 --> 12 is based off my pause image button
        // (int)(ScreenHeight)/7 --> 7 is based off my pause image button
        scaledbmpP = Bitmap.createScaledBitmap(bmpP, (int)(ScreenWidth)/12, (int)(ScreenHeight)/7, true);
        scaledbmpUP = Bitmap.createScaledBitmap(bmpUP, (int)(ScreenWidth)/12, (int)(ScreenHeight)/7, true);

        // My own position adjustment for the Pause button placement
        // Change accordingly
        xPos = ScreenWidth - 150;
        yPos = 150;

        isInit = true;
    }

    @Override public void Update(float _dt)
    {
        if (TouchManager.Instance.HasTouch())
        {
            //Check touch collision
            if (TouchManager.Instance.IsDown() && !Paused)
            {
                float imgRadius = scaledbmpP.getHeight() * 0.5f;

                if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius))
                {
                    Paused = true; // Meant user had pressed the Pause button!!!

                    // When button is pressed, U can play an audio clip
                    // AudioManager.Instance.PlayAudio(R.raw.clicksound);

                    // If just want a pause without the (popup dialog --> No done yet.)
                    // Method already written in your GameSystem class from Week 5

                    if (PauseConfirmDialogFragment.IsShown) return;

                    PauseConfirmDialogFragment newPauseConfirm = new PauseConfirmDialogFragment();
                    newPauseConfirm.show(Gamepage.Instance.getFragmentManager(), "PauseConfirm");

                }
            }
        }
        else Paused = false;
    }

    @Override public void Render(Canvas _canvas)
    {
        // scaledbmpUP is for button feedback when press and release or
        // indicate the button was pressed before

        if (!Paused)
            _canvas.drawBitmap(scaledbmpP, xPos - scaledbmpP.getWidth() * 0.5f, yPos - scaledbmpP.getHeight() * 0.5f, null);
        else
            _canvas.drawBitmap(scaledbmpUP, xPos - scaledbmpUP.getWidth() * 0.5f, yPos - scaledbmpUP.getHeight() * 0.5f, null);
    }

    @Override
    public boolean IsInit(){
        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return renderLayer;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        renderLayer = _newLayer;
    }

    public static PauseButton Create(){
        PauseButton result = new PauseButton();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PAUSE);
        return result;
    }

    public static PauseButton Create(int _layer)
    {
        PauseButton result = Create();
        result.SetRenderLayer(_layer);
        return result;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){
        return ENTITY_TYPE.ENT_PAUSE;
    }
}
