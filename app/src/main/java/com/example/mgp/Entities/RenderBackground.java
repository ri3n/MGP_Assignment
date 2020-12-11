package com.example.mgp.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;

import com.example.mgp.ResourceManager;
import com.example.mgp.*;

public class RenderBackground implements EntityBase{
    private static Bitmap bmp = null;
    private boolean isDone = false;
    private int renderLayer = 0;
    private int maxX,maxY;
    private int width,height;
    private boolean IsInit = false;
    private int bitmapID;

    public static RenderBackground Create ()
    {
        RenderBackground result = new RenderBackground();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    public static RenderBackground Create (int bitmapID)
    {
        RenderBackground result = Create();
        result.bitmapID = bitmapID;
        result.SetRenderLayer(0);
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
        //Define which image / png u want to use for this entity
        // Using ResourceManager
        bmp = ResourceManager.Instance.GetBitmap(bitmapID);
        maxX = _view.getWidth();
        maxY = _view.getHeight();

        width = bmp.getWidth();
        height = bmp.getHeight();
        IsInit = true;
    }

    @Override
    public void Update(float _dt) {

    }

    @Override
    public void Render(Canvas _canvas) {
        // RECT
        //Rect(int left, int top, int right, int bottom)
        //Create a new rectangle with the specified coordinates.

        Rect dst = new Rect(0, 0, maxX , maxY);
        Rect src = new Rect(0,0,width,height);
        _canvas.drawBitmap(bmp, src, dst, null);
    }

    @Override
    public boolean IsInit() {
        return false;
    }

    @Override
    public int GetRenderLayer() {
        return renderLayer;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        renderLayer = _newLayer;
    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return null;
    }
}
