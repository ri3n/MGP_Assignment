package com.example.mgp;

import android.graphics.Canvas;
import android.view.SurfaceView;

public class EntityPortal implements EntityBase, Collidable {
    private boolean IsDone;
    private boolean IsInit = false;
    private int renderLayer = LayerConstants.BACKGROUNDGAMEOBJECTS_LAYER;
    private float moveValue;
    private Sprite sprite;
    private int bitmapID;

    //On-Screen coordinates - this coordinates will move with the background
    //When within the surface view value, entityportal will be rendered
    private int screenX,screenY;

    //In-Game coordinates
    public int gameX, gameY;

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
        bitmapID = R.drawable.portal_sprite;
        sprite = new Sprite(ResourceManager.Instance.GetBitmap(bitmapID), 1, 11, 11);

        IsDone = false;
        IsInit = true;
        moveValue = 0;

        maxX = _view.getWidth();
        maxY = _view.getHeight();
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
        return null;
    }

    @Override
    public float GetPosX() {
        return 0;
    }

    @Override
    public float GetPosY() {
        return 0;
    }

    @Override
    public float GetRadius() {
        return 0;
    }

    @Override
    public void OnHit(Collidable _other) {

    }
}