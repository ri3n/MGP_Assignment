package com.example.mgp.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;

import com.example.mgp.Collidable;
import com.example.mgp.LayerConstants;
import com.example.mgp.ResourceManager;
import com.example.mgp.ScreenConstants;

public class EntityObstacle implements EntityBase, Collidable {
    private boolean isInit = false;
    private Bitmap bmp;
    private int bitmapID;

    private int scaleX, scaleY;
    private int screenX, screenY;
    private boolean isVisible;

    private boolean isDone = false;

    private float moveValue;

    private int screenWidth;
    private int screenHeight;

    private int renderLayer;

    static EntityObstacle Create() {
        EntityObstacle result = new EntityObstacle();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_OBSTACLE);
        return result;
    }

    public static EntityObstacle Create(int bitmapID, int screenX, int screenY, int scaleX, int scaleY)
    {
        EntityObstacle result = Create();
        result.bitmapID = bitmapID;
        result.scaleX = scaleX;
        result.scaleY = scaleY;
        result.screenX = screenX;
        result.screenY = screenY;

        return result;
    }

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        bmp = ResourceManager.Instance.GetBitmap(bitmapID);
        bmp = Bitmap.createScaledBitmap(bmp, ScreenConstants.GetQuadWidth(_view), ScreenConstants.GetQuadHeight(_view),true);

        moveValue = 0;
        isVisible = false;

        screenWidth = ScreenConstants.GetScreenWidth(_view);
        screenHeight = ScreenConstants.GetScreenHeight(_view);

        renderLayer = LayerConstants.BACKGROUNDGAMEOBJECTS_LAYER;

        isInit = true;

    }

    @Override
    public void Update(float _dt) {
        screenX += moveValue;

        if (screenX < -bmp.getWidth() * scaleX) screenX = screenWidth + (bmp.getWidth() * scaleX);
    }


    @Override
    public void Render(Canvas _canvas) {
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
        return isInit;
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
        return ENTITY_TYPE.ENT_OBSTACLE;
    }

    @Override
    public String GetType() {
        return "ENT_OBSTACLE";
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
        return bmp.getWidth() * scaleX;
    }

    @Override
    public void OnHit(Collidable _other) {

    }

    public void SetMoveValue(float _moveValue) {
        moveValue = _moveValue;
    }


}
