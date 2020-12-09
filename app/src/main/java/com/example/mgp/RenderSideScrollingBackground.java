package com.example.mgp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;

public class RenderSideScrollingBackground implements EntityBase{
    private static Bitmap bmp = null;
    private boolean isDone;
    private int renderLayer = 0;
    private int maxX,maxY;
    private int width,height;
    private int srcLeft = 0;
    private int srcRight;
    private boolean IsInit = false;
    private int bitmapID;

    private int moveValue;

    public static RenderSideScrollingBackground Create ()
    {
        RenderSideScrollingBackground result = new RenderSideScrollingBackground();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    public static RenderSideScrollingBackground Create (int bitmapID)
    {
        RenderSideScrollingBackground result = Create();
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

        isDone = false;

        // Using ResourceManager
        bmp = ResourceManager.Instance.GetBitmap(bitmapID);
        maxX = _view.getWidth();
        maxY = _view.getHeight();


        width = bmp.getWidth();
        height = bmp.getHeight();

        srcRight = width / 2;

        moveValue = 10;

        IsInit = true;
    }

    @Override
    public void Update(float _dt) {
        srcLeft += width / 300;
        srcRight += width / 300;

        if (srcRight >= width) {
            srcLeft = 0;
            srcRight = width / 2;
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        // RECT
        //Rect(int left, int top, int right, int bottom)
        //Create a new rectangle with the specified coordinates.
        Rect src = new Rect(srcLeft,0, srcRight ,height);
        Rect dst = new Rect(0, 0, maxX, maxY);
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
        renderLayer = _newLayer;
    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_DEFAULT;
    }
}
