package com.example.mgp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite{
    // Row, Col, Width and Height
    // Sprite sheet
    private int row = 0;
    private int col = 0;
    private int width = 0;
    private int height = 0;

    private Bitmap bmp = null; // Bitmap for your image loaded

    // Current frame, start frame, end frame
    private int currentFrame = 0;
    private int startFrame = 0;
    private int endFrame = 0;

    // Time per frame, time acc += dt
    private float timePerFrame = 0.0f;
    private float timeAcc = 0.0f;

    // Constructor
    public Sprite(Bitmap _bmp, int _row, int _col, int _fps)
    {
        bmp = _bmp;
        row = _row;
        col = _col;

        width = bmp.getWidth() / _col;
        height = bmp.getHeight() / _row;

        timePerFrame = 1.0f / (float)_fps;

        endFrame = _col * _row;
    }

    // Update running vs delta time (Same as your ACG)
    public void Update(float _dt)
    {
        timeAcc += _dt;
        if (timeAcc > timePerFrame)
        {

            ++currentFrame;
            if (currentFrame >= endFrame)
                currentFrame = startFrame;
            timeAcc = 0.0f;
        }
    }

    // Render --> Canvas and x & y (position to be drawn out via the imaginary rectangle
    public void Render(Canvas _canvas, int _x, int _y, int scaleX, int scaleY)
    {
        int frameX = currentFrame % col;
        int frameY = currentFrame / col;
        int srcX = frameX * width;
        int srcY = frameY * height;

        _x -= 0.5f * (width * scaleX);
        _y -= 0.5f * (height * scaleY);
        // RECT
        //Rect(int left, int top, int right, int bottom)
        //Create a new rectangle with the specified coordinates.
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(_x, _y, _x + (width * scaleX), _y + (height * scaleY));
        _canvas.drawBitmap(bmp, src, dst, null);
        //dst: the pos
        //src: the sprite/image you want to show
    }

    public void SetAnimationFrames(int _start, int _end)
    {
        timeAcc = 0.0f;
        currentFrame = _start;
        startFrame = _start;
        endFrame = _end;
    }

    public int GetHeight()
    {
        return height;
    }

    public int GetWidth()
    {
        return width;
    }

}
