package com.example.mgp.Entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;

import com.example.mgp.Collidable;
import com.example.mgp.LayerConstants;
import com.example.mgp.R;
import com.example.mgp.ResourceManager;
import com.example.mgp.ScreenConstants;

public class EntityPowerUp implements EntityBase, Collidable {

    public enum POWERUP_TYPE
    {
        SLOWDOWN,
        INVINCIBILITY ,
        TOTAL
    }

    private boolean IsInit = false;
    POWERUP_TYPE type;
    private Bitmap bmp_SLOWDOWN;
    private Bitmap bmp_INVINCIBILITY;
    private float posX, posY;
    private int scaleX, scaleY;
    private int moveValue;
    public boolean IsActive;
    private boolean IsDone;

    private int screenWidth, screenHeight;

    private float cooldownTimer;

    private float sineValue;
    public static EntityPowerUp Create()
    {
        EntityPowerUp result = new EntityPowerUp();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_POWERUP);
        return result;
    }

    public boolean IsActive()
    {
        return IsActive;
    }

    public void SetActive(boolean IsActive)
    {
        this.IsActive = IsActive;

        if (!IsActive) cooldownTimer = 5;
    }

    public void RandomiseType()
    {
        int randomiser = (int)Math.random() % 2;
        switch(randomiser)
        {
            case 0:
                type = POWERUP_TYPE.SLOWDOWN;
                break;
            case 1:
                type = POWERUP_TYPE.INVINCIBILITY;
                break;
        }
    }

    public void SetMoveValue(int moveValue)
    {
        this.moveValue = moveValue;
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
        return bmp_INVINCIBILITY.getWidth() * scaleX;
    }

    @Override
    public void OnHit(Collidable _other) {

    }

    @Override
    public boolean IsDone() {
        return IsDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        this.IsDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        screenWidth = ScreenConstants.GetScreenWidth(_view);
        screenHeight = ScreenConstants.GetScreenHeight(_view);
        IsDone = false;
        IsInit = true;
        IsActive = true;
        //Init bitmaps
        bmp_INVINCIBILITY = Bitmap.createScaledBitmap(ResourceManager.Instance.GetBitmap(R.drawable.shield), ScreenConstants.GetQuadWidth(_view) / 2, ScreenConstants.GetQuadWidth(_view) / 2, false);
        bmp_SLOWDOWN = Bitmap.createScaledBitmap(ResourceManager.Instance.GetBitmap(R.drawable.slowdown), ScreenConstants.GetQuadWidth(_view) / 2, ScreenConstants.GetQuadWidth(_view) / 2, false);

        posY = screenHeight / 3;
        posX = screenWidth / 2;

        type = POWERUP_TYPE.INVINCIBILITY;
        scaleX = scaleY  = 1;
        sineValue = 0;
        cooldownTimer = 5;
    }

    @Override
    public void Update(float _dt) {
        cooldownTimer -= _dt;

        if (cooldownTimer <= 0)
        {
            IsActive = true;
            posX += moveValue;


            if (sineValue >=  Integer.MAX_VALUE - 1000) sineValue = 0;
            sineValue = sineValue + _dt;
            posY += 10.f * Math.sin(sineValue);
            //System.out.println("dt: " + _dt);
            //System.out.println("sineValue: " + sineValue);
            //System.out.println("Math.sin(sineValue): " + Math.sin(sineValue));
            if (posX < -bmp_INVINCIBILITY.getWidth() * scaleX)
            {
                RandomiseType();
                posX = screenWidth + (bmp_INVINCIBILITY.getWidth() * scaleX);
            }
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        if (!IsActive) return;
            float _x = posX - 0.5f * (bmp_INVINCIBILITY.getWidth() * scaleX);
            float _y = posY - 0.5f * (bmp_INVINCIBILITY.getHeight() * scaleY);
            // RECT
            //Rect(int left, int top, int right, int bottom)
            //Create a new rectangle with the specified coordinates.
            Rect src = new Rect(0, 0, bmp_INVINCIBILITY.getWidth(), bmp_INVINCIBILITY.getHeight());
            Rect dst = new Rect((int) _x, (int) _y, (int) _x + (bmp_INVINCIBILITY.getWidth() * scaleX), (int) _y + (bmp_INVINCIBILITY.getHeight() * scaleY));
        if (type == POWERUP_TYPE.INVINCIBILITY)
            _canvas.drawBitmap(bmp_INVINCIBILITY, src, dst, null);
        else if (type == POWERUP_TYPE.SLOWDOWN)
            _canvas.drawBitmap(bmp_SLOWDOWN, src, dst, null);
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
        return ENTITY_TYPE.ENT_POWERUP;
    }
}
