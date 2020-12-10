package com.example.mgp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import java.util.Random;

public class EntitySmurf implements EntityBase,Collidable {

    private Bitmap bmp = null; // Define image object name (bmp)
    private Sprite spritesheet = null;
    // Sprite class will take in a sprite sheet image
    // Name of this Sprite object is called spritesheet


    //vector 2 class from ACG, PPHYs, go ahead!!

    private float xPos, yPos, xDir, yDir, lifeTime;
    private boolean hasTouched = false; // Check for ontouch events
    private boolean isDone, isInit;
    private int renderLayer = LayerConstants.GAMEOBJECTS_LAYER;;
    private float imgRadius = 0;
    private float imgOffset = 85;

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
        spritesheet = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite), 4, 4, 16);

        //Define which image / png u want to use for this entity
        // Using ResourceManager
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite);

        // For me: my smurf will be render at random position on the screen
        // then when the user touch the smurf on the screen, new smurfs will be render at
        // another position.
        lifeTime = 10.0f;
        Random ranGen = new Random();
        //_view.getWidth(); -- will give the length of the view = surfaceview = screen
        // because we using a state, we created our own surfaceview = screen
        // ranGen will produce random x values based on the view size
        xPos = ranGen.nextFloat() * _view.getWidth();
        yPos = ranGen.nextFloat() * _view.getHeight();

        // Not used but u can use them if u want
        xDir = ranGen.nextFloat() * 100.0f - 50.0f;
        yDir = ranGen.nextFloat() * 100.0f - 50.0f;

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        isInit = true;
    }

    @Override
    public void Update(float _dt) {

        if (TouchManager.Instance.HasTouch()) {
// 0.0f, xPos, yPos, imgRadius ---> Checking collision of finger w the image
//
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius) || hasTouched) {
                // Collided!

                hasTouched = true;

                xPos = TouchManager.Instance.GetPosX();
                yPos = TouchManager.Instance.GetPosY();
            }

        }

        //        if(yPos<ScreenHeight){
//            yPos+=9.8;
//        }
//        if(yPos>ScreenHeight){
//            yPos=ScreenHeight;
//        }
//        if(yPos<0) {
//            yPos=0;
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
        spritesheet.Render(_canvas, (int)xPos, (int)yPos,1,1);
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

        public static EntitySmurf Create ()
        {
            EntitySmurf result = new EntitySmurf();
            EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_SMURF);
            return result;
        }

        public static EntitySmurf Create ( int _layer)
        {
            EntitySmurf result = Create();
            result.SetRenderLayer(_layer);
            return result;
        }

        @Override
        public ENTITY_TYPE GetEntityType () {
            return ENTITY_TYPE.ENT_SMURF;
        }

        @Override
        public String GetType () {
            return "SampleEntity";
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
            return 0;
        }

        @Override
        public void OnHit (Collidable _other){
            if (_other.GetType() == "NextEntity") //Another Entity
            {
                SetIsDone(true);
            }
        }
    public void moveLeft() {
        xPos -= 100;
        if(xPos<0+imgOffset){
            xPos=0+imgOffset;
        }
    }
    public void moveRight(){
        xPos += 100;
        if(xPos>ScreenWidth-imgOffset){
            xPos=ScreenWidth-imgOffset;
        }
    }
}

