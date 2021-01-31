package com.example.mgp.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;

import com.example.mgp.Collidable;
import com.example.mgp.LayerConstants;
import com.example.mgp.R;
import com.example.mgp.ResourceManager;
import com.example.mgp.ScreenConstants;

public class EntityCoin implements EntityBase, Collidable {
    private boolean IsDone;
    private boolean IsInit = false;
    private int points;
    private Bitmap bmp;
    private int screenWidth,screenHeight;

    public int moveValue;

    private int scaleX, scaleY;
    private int posX, posY;

    private boolean IsActive;

    public static EntityCoin Create()
    {
        EntityCoin result = new EntityCoin();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_COIN);
        return result;
    }

    public void SetActive(boolean active)
    {
        IsActive = active;
    }

    public boolean IsActive()
    {
        return IsActive;
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
        IsInit = true;
        points = 1;
        scaleX = 1;
        scaleY = 1;
        screenWidth = ScreenConstants.GetScreenWidth(_view);
        screenHeight = ScreenConstants.GetScreenHeight(_view);
        bmp = Bitmap.createScaledBitmap(ResourceManager.Instance.GetBitmap(R.drawable.coin),ScreenConstants.GetQuadWidth(_view) / 2,   ScreenConstants.GetQuadHeight(_view) / 2, false);
        moveValue = 0;
        posX = ScreenConstants.GetScreenWidth(_view) + (scaleX * bmp.getWidth());
        posY = ScreenConstants.GetScreenHeight(_view) / 4;
        IsActive = true;
    }

    @Override
    public void Update(float _dt) {
        posX += moveValue;

        if (posX < -bmp.getWidth() * scaleX)
        {
            IsActive = true;
            posX = screenWidth + (bmp.getWidth() * scaleX);
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        if (!IsActive) return;

        float _x = posX - 0.5f * (bmp.getWidth() * scaleX);
        float _y = posY - 0.5f * (bmp.getHeight() * scaleY);
        // RECT
        //Rect(int left, int top, int right, int bottom)
        //Create a new rectangle with the specified coordinates.
        Rect src = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        Rect dst = new Rect((int) _x, (int) _y, (int) _x + (bmp.getWidth() * scaleX), (int) _y + (bmp.getHeight() * scaleY));
        _canvas.drawBitmap(bmp, src, dst, null);
    }

    public void SetMoveValue(int moveValue)
    {
        this.moveValue = moveValue;
    }


    @Override
    public boolean IsInit() {
        return IsInit;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.BACKGROUNDGAMEOBJECTS_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_COIN;
    }

    @Override
    public String GetType() {
        return null;
    }

    @Override
    public float GetPosX() {
        return posX;
    }

    @Override
    public float GetPosY() {
        return posY;
    }

    @Override
    public float GetRadius() {
        return (scaleX * bmp.getWidth()) / 2;
    }

    @Override
    public void OnHit(Collidable _other) {

    }
}
