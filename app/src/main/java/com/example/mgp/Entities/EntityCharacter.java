package com.example.mgp.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.mgp.Collidable;
import com.example.mgp.Collision;
import com.example.mgp.LayerConstants;
import com.example.mgp.R;
import com.example.mgp.ResourceManager;
import com.example.mgp.ScreenConstants;
import com.example.mgp.Sprite;
import com.example.mgp.TouchManager;

import java.util.Random;

public class EntityCharacter implements Collidable,EntityBase{
    private Bitmap bmp = null; // Define image object name (bmp)
    private Sprite spritesheet = null;
    // Sprite class will take in a sprite sheet image
    // Name of this Sprite object is called spritesheet


    //vector 2 class from ACG, PPHYs, go ahead!!

    private float xPos, yPos, xDir, yDir, lifeTime;
    private float screenX, screenY;
    private boolean hasTouched = false; // Check for ontouch events
    private boolean isDone, isInit;
    private int renderLayer = LayerConstants.GAMEOBJECTS_LAYER;;
    private float imgRadius = 0;
    private float imgOffset = 85;
    private int numSpriteGrids;

    private float scaleX,scaleY;

    private int ScreenWidth,ScreenHeight;
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
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.stickman_sprite);
//      scaledbmpP = Bitmap.createScaledBitmap(bmpP, (int)(ScreenWidth)/12, (int)(ScreenHeight)/10, true);

        //Define which image / png u want to use for this entity
        // Using ResourceManager

        // For me: my smurf will be render at random position on the screen
        // then when the user touch the smurf on the screen, new smurfs will be render at
        // another position.
        lifeTime = 10.0f;
        Random ranGen = new Random();
        //_view.getWidth(); -- will give the length of the view = surfaceview = screen
        // because we using a state, we created our own surfaceview = screen
        // ranGen will produce random x values based on the view size
        xPos = bmp.getWidth();
        //yPos = ranGen.nextFloat() * _view.getHeight();
        yPos = _view.getHeight() / 2 + 50;
        // Not used but u can use them if u want
        xDir = ranGen.nextFloat() * 100.0f - 50.0f;
        yDir = ranGen.nextFloat() * 100.0f - 50.0f;

        ScreenWidth = ScreenConstants.GetScreenWidth(_view);
        ScreenHeight = ScreenConstants.GetScreenHeight(_view);

        spritesheet = new Sprite(bmp, 1, 4, 4);
        numSpriteGrids = 4;

        scaleX = scaleY = 3;

        isInit = true;
    }

    @Override
    public void Update(float _dt) {

//        if (TouchManager.Instance.HasTouch()) {
//            // 0.0f, xPos, yPos, imgRadius ---> Checking collision of finger w the image
//
//            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, (scaleX) * bmp.getWidth()  / numSpriteGrids) || hasTouched) {
//                // Collided!
//
//                hasTouched = true;
//
//                xPos = TouchManager.Instance.GetPosX();
//                yPos = TouchManager.Instance.GetPosY();
//            }
//
//        }

        // Update will pass the delta time in for the animation to happen
        spritesheet.Update(_dt);

        // Lifetime .. meant to check if time is up, destroy the image created.
        lifeTime -= _dt;
        //    if (lifeTime < 0.0f)
        //        SetIsDone(true);

    }
    // Render
    @Override
    public void Render(Canvas _canvas) {
        // Our basic rendering with image centered
        //_canvas.drawBitmap(bmp, xPos - bmp.getWidth() * 0.5f, yPos - bmp.getHeight() * 0.5f, null);

        // This is for our sprite animation!
        spritesheet.Render(_canvas, (int)xPos, (int)yPos,(int)scaleX,(int)scaleY);
    }

    @Override
    public boolean IsInit () {
        return isInit;
    }

    @Override
    public int GetRenderLayer () {
        return renderLayer;
    }

    @Override
    public void SetRenderLayer ( int _newLayer){
        renderLayer = _newLayer;
    }

    public static EntityCharacter Create ()
    {
        EntityCharacter result = new  EntityCharacter();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PLAYER);
        return result;
    }

    public static EntityCharacter Create ( int _layer)
    {
        EntityCharacter result = Create();
        result.SetRenderLayer(_layer);
        return result;
    }

    @Override
    public EntityBase.ENTITY_TYPE GetEntityType () {
        return EntityBase.ENTITY_TYPE.ENT_SMURF;
    }

    @Override
    public String GetType () {
        return "ENT_PLAYER";
    }
    @Override
    public float GetPosX () {
        return xPos;
    }

    @Override
    public float GetPosY () {
        return yPos;
    }

    @Override
    public float GetRadius () {
        return  bmp.getWidth()  / numSpriteGrids * (scaleX/2) ;
    }

    @Override
    public void OnHit (Collidable _other){

    }
    public void moveLeft(float value) {
        xPos += value;
        if(xPos<0+imgOffset){
            xPos=0+imgOffset;
        }
    }
    public void moveRight(float value){
        xPos += value;
        if(xPos>ScreenWidth-imgOffset){
            xPos=ScreenWidth-imgOffset;
        }
    }
}
