package com.example.mgp.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;

import com.example.mgp.LayerConstants;
import com.example.mgp.ResourceManager;
import com.example.mgp.*;

public class RenderSideScrollingBackground implements EntityBase{
    private static Bitmap bmp = null;
    private boolean isDone;
    private int renderLayer = LayerConstants.BACKGROUND_LAYER;
    private int maxX,maxY;
    private int width,height;
    private int dstLeft;
    private int dstRight;
    private int dstLeft2;
    private int dstRight2;
    private boolean IsInit = false;
    private int bitmapID;

    public int Direction;

    public float moveSpeed;
    public float moveValue;

    public boolean isMoving = true;


    public static RenderSideScrollingBackground Create ()
    {
        RenderSideScrollingBackground result = new RenderSideScrollingBackground();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_BACKGROUND);
        return result;
    }

    public static RenderSideScrollingBackground Create (int bitmapID)
    {
        RenderSideScrollingBackground result = Create();
        result.bitmapID = bitmapID;
        result.SetRenderLayer(LayerConstants.BACKGROUND_LAYER);
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
//        bmp = ResourceManager.Instance.GetBitmap(bitmapID);
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.gamepage);
        maxX = ScreenConstants.GetScreenWidth(_view);
        maxY = ScreenConstants.GetScreenHeight(_view);


        width = bmp.getWidth();
        height = bmp.getHeight();

        dstLeft = 0;
        dstRight = maxX;

        dstLeft2 = -maxX;
        dstRight2 = 0;
        IsInit = true;

        Direction = 1;

        moveValue = 0;
    }

    @Override
    public void Update(float _dt) {
        if (isMoving) {
            moveValue = _dt * moveSpeed * Direction;

            dstLeft += (int) moveValue;
            dstRight += (int) moveValue;

            dstLeft2 += (int) moveValue;
            dstRight2 += (int) moveValue;

            //Check if image 1 goes out of the screen on the right side
            if (dstLeft >= maxX) {
                dstLeft = dstLeft2 - maxX + (int) moveValue;
                dstRight = dstLeft2 + (int) moveValue;
            }

            //Check if image 1 goes out of the screen on the left side
            else if (dstRight <= 0) {
                dstLeft = dstRight2 + (int)moveValue;
                dstRight = dstLeft + maxX + (int)moveValue;
            }

            //Check if image 2 goes out of the screen on the right side
            if (dstLeft2 >= maxX) {
                dstLeft2 = dstLeft - maxX + (int) moveValue;
                dstRight2 = dstLeft + (int) moveValue;
            }

            //Check if image 2 goes out of the screen on the left side
            else if (dstRight2 <= 0) {
                dstLeft2 = dstRight + (int)moveValue;
                dstRight2 = dstLeft2 + maxX + (int)moveValue;
            }
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        // RECT
        //Rect(int left, int top, int right, int bottom)
        //Create a new rectangle with the specified coordinates.

        //draw bitmap 1
        Rect src = new Rect(0,0, width ,height);
        Rect dst = new Rect(dstLeft, 0,dstRight, maxY);
        _canvas.drawBitmap(bmp, src, dst, null);

        //draw bitmap 2
        Rect dst2 = new Rect(dstLeft2,0,dstRight2, maxY);
        _canvas.drawBitmap(bmp, src,dst2,null);
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
        return ENTITY_TYPE.ENT_BACKGROUND;
    }
}
