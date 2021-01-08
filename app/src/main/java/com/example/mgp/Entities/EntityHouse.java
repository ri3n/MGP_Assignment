package com.example.mgp.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;

import com.example.mgp.*;

public class EntityHouse implements EntityBase, Collidable {
    private boolean IsDone;
    private boolean IsInit = false;
    private int renderLayer = LayerConstants.BACKGROUNDGAMEOBJECTS_LAYER;
    private float moveValue;
    private int bitmapID;
    private Bitmap bmp;
    //On-Screen coordinates - this coordinates will move with the background
    //When within the surface view value, entityportal will be rendered
    private int screenX,screenY;

    private int numSpriteGrids;

    //x,y
    private int xPos,yPos;

    //Surfaceview max X and max Y
    private int maxX, maxY;

    //Scaling
    public int scaleX, scaleY;

    public static EntityHouse Create ()
    {
        EntityHouse result = new EntityHouse();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PORTAL);

        return result;
    }

    @Override
    public boolean IsDone() {
        return IsDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        IsDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {

        IsDone = false;
        IsInit = true;
        moveValue = 0;

        maxX = ScreenConstants.GetScreenWidth(_view);
        maxY = ScreenConstants.GetScreenHeight(_view);
        bitmapID = R.drawable.house;
        bmp = ResourceManager.Instance.GetBitmap(bitmapID);
        bmp = Bitmap.createScaledBitmap(bmp,ScreenConstants.GetQuadWidth(_view), ScreenConstants.GetQuadHeight(_view),true);

        screenX = 10;
        screenY = ScreenConstants.GetScreenHeight(_view) / 2;

        scaleX = scaleY = 1;
        //(int)(ScreenWidth)/5,(int)(ScreenWidth)/5
    }

    @Override
    public void Update(float _dt) {

        if (EntityManager.Instance.GetBG().isMoving)
        {
            screenX += moveValue;
        }

    }

    @Override
    public void Render(Canvas _canvas)
    {
        float _x = screenX - 0.5f * (bmp.getWidth() * scaleX);
        float _y = screenY - 0.5f * (bmp.getHeight() * scaleY);
        // RECT
        //Rect(int left, int top, int right, int bottom)
        //Create a new rectangle with the specified coordinates.
        Rect src = new Rect(0, 0,  bmp.getWidth(), bmp.getHeight());
        Rect dst = new Rect((int)_x,(int) _y, (int)_x+ (bmp.getWidth() * scaleX), (int)_y + (bmp.getHeight() * scaleY));
        _canvas.drawBitmap(bmp, src, dst, null);

    }

    @Override
    public boolean IsInit() {
        return IsInit;
    }

    @Override
    public int GetRenderLayer() {
        return renderLayer;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        this.renderLayer = _newLayer;
    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_HOUSE;
    }

    @Override
    public String GetType() {
        return "ENT_HOUSE";
    }

    @Override
    public float GetPosX() {
        return screenX;
    }

    @Override
    public float GetPosY() {
        return screenY;
    }

    @Override
    public float GetRadius() {
        return bmp.getWidth() * (scaleX / 2);
    }

    @Override
    public void OnHit(Collidable _other) {
    }

    public void SetMoveValue(float moveValue)
    {
        this.moveValue = moveValue;
    }
}
