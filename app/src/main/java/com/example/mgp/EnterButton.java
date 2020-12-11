package com.example.mgp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class EnterButton implements EntityBase{
    private boolean isDone = false;
    private boolean isInit = false;

    private boolean Paused = false;

    private Bitmap bmp = null;

    private int xPos,yPos;

    private int ScreenWidth,ScreenHeight;

    private int renderLayer = LayerConstants.UI_LAYER;

    public boolean MakeVisible;

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
        ScreenWidth = _view.getWidth();
        ScreenHeight = _view.getHeight();

        bmp = Bitmap.createScaledBitmap(ResourceManager.Instance.GetBitmap(R.drawable.enter_button),ScreenConstants.GetUIQuadWidth(_view), ScreenConstants.GetUIQuadHeight(_view),true);

        // Scale image according/ based on the screen size
        // (int)(ScreenWidth)/12 --> 12 is based off my pause image button
        // (int)(ScreenHeight)/7 --> 7 is based off my pause image button

        // My own position adjustment for the Pause button placement
        // Change accordingly
        xPos = ScreenWidth - 150;
        yPos = ScreenHeight - 150;

        MakeVisible = false;
        isInit = true;
    }

    @Override public void Update(float _dt)
    {
        if (!MakeVisible) return;
        if (TouchManager.Instance.HasTouch())
        {
            //Check touch collision
            if (TouchManager.Instance.IsDown())
            {
                float imgRadius = bmp.getHeight() * 0.5f;

                if(Collision.Quad(TouchManager.Instance.GetPosX(),TouchManager.Instance.GetPosY(),
                        bmp.getWidth(),bmp.getHeight(),xPos,yPos))
                {
                    System.out.println("enter button is pressed");
                    StateManager.Instance.ChangeState("MINIGAME_TAPGAME");
                }

            }
        }
    }

    @Override public void Render(Canvas _canvas) {
        if (MakeVisible)
            _canvas.drawBitmap(bmp, xPos - bmp.getWidth() * 0.5f, yPos - bmp.getHeight() * 0.5f, null);

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

    public static EnterButton Create(){
        EnterButton result = new EnterButton();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_ENTER);
        return result;
    }

    @Override
    public EntityBase.ENTITY_TYPE GetEntityType(){
        return EntityBase.ENTITY_TYPE.ENT_PAUSE;
    }
}
