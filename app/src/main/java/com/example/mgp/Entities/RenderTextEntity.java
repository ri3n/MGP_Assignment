package com.example.mgp.Entities;

// import android.graphics.Typeface;
// Font file typeface on BB

// Q: Where to put the file in the project folder
// Go to the project explorer, highlight to app, rightclick to find in explorer
// src > main
// Create a new folder called assets and inside the folder, another folder called fonts
// src > main > assests > fonts
// Place the font typeface file into the fonts folder

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceView;

import com.example.mgp.LayerConstants;
import com.example.mgp.*;

// Create a new entity class
public class RenderTextEntity implements EntityBase{

// This is a way to render text on screen using a font type so
// we need to use paint (in android context it is like ink or color)

    // Paint object has a name "paint"
    Paint paint = new Paint();
    // define red, green and blue
    private int red = 0, green = 0, blue = 0;

    private boolean isDone = false;

    int frameCount;
    long lastTime = 0;
    long lastFPSTime = 0;
    float fps;

    // Define the use of Typeface ... name is myfont
    Typeface myfont;

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

        // Use my own fonts
        // Standard font loading using android API
        myfont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/Gemcut.otf");
    }

    @Override
    public void Update(float _dt) {

        // get actual fps and print fps on screen
        frameCount++;
        long currentTime = System.currentTimeMillis();

        lastTime = currentTime;

        if(currentTime - lastFPSTime > 1000)
        {
            fps = (frameCount * 1000.f) / (currentTime - lastFPSTime);
            lastFPSTime = currentTime;
            frameCount = 0;
        }

    }

    @Override
    public void Render(Canvas _canvas)
    {
        Paint paint = new Paint(); // Use paint to render text on screen
        paint.setARGB(255, 0,0,0); // Alpha, R, G, B Can make it a variable
        paint.setStrokeWidth(200); // Stroke width is just the thickness of the appearance of the text
        paint.setTypeface(myfont); // using the type of font we defined
        paint.setTextSize(70);     // Text size
        _canvas.drawText("FPS: " + fps, 30, 80, paint); // To render text is drawText FPS: 60
        // drawText(String text, float x, float y, Paint paint)
        // Draw the text, with origin at (x,y), using the specified paint.
    }

    @Override
    public boolean IsInit() {
        return true;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.RENDERTEXT_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_TEXT;}

    public static RenderTextEntity Create()
    {
        RenderTextEntity result = new RenderTextEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_TEXT);
        return result;
    }

}

// Allows you to draw a box or rectangle, can be used to create a health bar
// place it in the Render()
// value is an int to increment the width so that it can fill the bar
// Draw a box, then fill the box
     		/*Paint paint = new Paint();
        paint.setARGB(255, 255,0 ,0);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE); // FILL_AND_STROKE
        _canvas.drawRect(ScreenWidth/20 + 5, ScreenHeight/25 - 5, 4 * ScreenWidth/20, 2 * ScreenHeight/25, paint);

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        _canvas.drawRect(ScreenWidth/20 + 8, ScreenHeight/25, ScreenWidth/20 + value, 2 * ScreenHeight/25 - 5, paint);*/
