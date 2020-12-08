package com.example.mgp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;

public class RenderBackground implements EntityBase{
    private Bitmap bmp = null;
    private boolean isDone = false;
    private int renderLayer = 1;

    public RenderBackground Create ()
    {
        RenderBackground result = new RenderBackground();
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.gamepage);
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    public RenderBackground Create ( int _layer)
    {
        RenderBackground result = Create();
        result.SetRenderLayer(_layer);
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

    }

    @Override
    public void Update(float _dt) {

    }

    @Override
    public void Render(Canvas _canvas) {
        // RECT
        //Rect(int left, int top, int right, int bottom)
        //Create a new rectangle with the specified coordinates.
        Rect src = new Rect(0, 10, 10 , 0);
        Rect dst = new Rect(0, 10, 10 , 0);
        _canvas.drawBitmap(bmp, src, src, null);
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
