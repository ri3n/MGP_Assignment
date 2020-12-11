package com.example.mgp.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.mgp.Collidable;
import com.example.mgp.LayerConstants;
import com.example.mgp.R;
import com.example.mgp.ResourceManager;
import com.example.mgp.ScreenConstants;
import com.example.mgp.Sprite;
import com.example.mgp.*;

public class EntityPortal implements EntityBase, Collidable {
    private boolean IsDone;
    private boolean IsInit = false;
    private int renderLayer = LayerConstants.BACKGROUNDGAMEOBJECTS_LAYER;
    private float moveValue;
    private Sprite sprite;
    private int bitmapID;
    private Bitmap bmp;
    //On-Screen coordinates - this coordinates will move with the background
    //When within the surface view value, entityportal will be rendered
    private int screenX,screenY;

    private int numSpriteGrids;

    //Surfaceview max X and max Y
    private int maxX, maxY;

    //Scaling
    public int scaleX, scaleY;

    public static EntityPortal Create ()
    {
        EntityPortal result = new EntityPortal();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PORTAL);

        return result;
    }

    public static EntityPortal Create(int scaleX, int scaleY, int screenX, int screenY)
    {
        EntityPortal result = Create();
        result.screenX = screenX;
        result.screenY = screenY;
        result.scaleX = scaleX;
        result.scaleY = scaleY;

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
        bitmapID = R.drawable.portal_sprite;
        bmp = ResourceManager.Instance.GetBitmap(bitmapID);
        bmp = Bitmap.createScaledBitmap(bmp,ScreenConstants.GetQuadWidth(_view), ScreenConstants.GetQuadHeight(_view),true);

        sprite = new Sprite(ResourceManager.Instance.GetBitmap(bitmapID), 1, 11, 11);
        numSpriteGrids = 11;
        //(int)(ScreenWidth)/5,(int)(ScreenWidth)/5
    }

    @Override
    public void Update(float _dt) {
        sprite.Update(_dt);

        if (EntityManager.Instance.GetBG().isMoving)
        {
            moveValue = EntityManager.Instance.GetBG().moveValue;
            screenX += moveValue;
        }

    }

    @Override
    public void Render(Canvas _canvas) {
        sprite.Render(_canvas, screenX,screenY,scaleX,scaleY);
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
        return ENTITY_TYPE.ENT_PORTAL;
    }

    @Override
    public String GetType() {
        return "ENT_PORTAL";
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
        return scaleX * bmp.getWidth() / numSpriteGrids;
    }

    @Override
    public void OnHit(Collidable _other) {
    }
}
