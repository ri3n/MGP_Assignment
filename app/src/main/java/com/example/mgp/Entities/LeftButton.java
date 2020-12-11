package com.example.mgp.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import com.example.mgp.Collision;
import com.example.mgp.LayerConstants;
import com.example.mgp.R;
import com.example.mgp.ResourceManager;
import com.example.mgp.ScreenConstants;
import com.example.mgp.TouchManager;
import com.example.mgp.*;

public class LeftButton implements EntityBase{

    private boolean isDone = false;
    private boolean isInit = false;

    private Bitmap bmpP = null;
    private Bitmap scaledbmpP = null;
    private int xPos,yPos;
    private boolean pressed = false;

    private int ScreenWidth,ScreenHeight;

    private int renderLayer = LayerConstants.UI_LAYER;

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone=_isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        bmpP = ResourceManager.Instance.GetBitmap(R.drawable.leftarrow);
        //bmpUP = ResourceManager.Instance.GetBitmap(R.drawable.pause1);

        // Find screen width and screen height
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        // Scale image according/ based on the screen size
        // (int)(ScreenWidth)/12 --> 12 is based off my pause image button
        // (int)(ScreenHeight)/7 --> 7 is based off my pause image button
        scaledbmpP = Bitmap.createScaledBitmap(bmpP, ScreenConstants.GetUIQuadWidth(_view), ScreenConstants.GetUIQuadHeight(_view), true);

        // My own position adjustment for the button placement
        // Change accordingly
        //xPos = ScreenWidth - 150;
        //yPos = 150;
        xPos = 100;
        yPos = ScreenHeight-150;

        isInit = true;
    }

    @Override
    public void Update(float _dt) {
        if (TouchManager.Instance.HasTouch())
        {
            //Check touch collision
            if (TouchManager.Instance.IsDown())
            {
                float imgRadius = scaledbmpP.getHeight() * 0.5f;

//                if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius))
//                {
//                    //Paused = true; // Meant user had pressed the Pause button!!!
//
//                    // When button is pressed, U can play an audio clip
//                    // AudioManager.Instance.PlayAudio(R.raw.clicksound);
//
//                    // If just want a pause without the (popup dialog --> No done yet.)
//                    // Method already written in your GameSystem class from Week 5
//                    //GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
//                    entityPosX=100;
//                }

                if(Collision.Quad((float)TouchManager.Instance.GetPosX(),(float)TouchManager.Instance.GetPosY(),
                        1.f,1.f,(float)xPos,(float)yPos,(float)scaledbmpP.getWidth(),(float)scaledbmpP.getHeight()))
                {
                    pressed = true;
                }
            }
        }
        else
        {
            pressed = false;
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(scaledbmpP, xPos - scaledbmpP.getWidth() * 0.5f, yPos - scaledbmpP.getHeight() * 0.5f, null);
    }

    @Override
    public boolean IsInit() { return isInit; }

    @Override
    public int GetRenderLayer() { return renderLayer; }

    @Override
    public void SetRenderLayer(int _newLayer) { renderLayer=_newLayer; }

    @Override
    public ENTITY_TYPE GetEntityType() { return ENTITY_TYPE.ENT_LEFTARROW; }

    public boolean isPressed(){return pressed;}

    public static LeftButton Create(){
        LeftButton result = new LeftButton();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_LEFTARROW);
        return result;
    }

    public static LeftButton Create(int _layer){
        LeftButton result=Create();
        result.SetRenderLayer(_layer);
        return result;
    }
}
