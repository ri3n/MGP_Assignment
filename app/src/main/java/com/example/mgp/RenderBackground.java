package com.example.mgp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceView;

public class RenderBackground implements EntityBase{
    private Bitmap bmp = null;
    private boolean isDone = false;


    public static RenderBackground Create ()
    {
        RenderBackground result = new RenderBackground();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    public static RenderBackground Create ( int _layer)
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

    }

    @Override
    public boolean IsInit() {
        return false;
    }

    @Override
    public int GetRenderLayer() {
        return 0;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return null;
    }
}
