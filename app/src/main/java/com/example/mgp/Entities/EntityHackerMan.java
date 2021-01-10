package com.example.mgp.Entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.mgp.AudioManager;
import com.example.mgp.Collidable;
import com.example.mgp.Collision;
import com.example.mgp.LayerConstants;
import com.example.mgp.R;
import com.example.mgp.ResourceManager;
import com.example.mgp.ScreenConstants;
import com.example.mgp.Sprite;
import com.example.mgp.TouchManager;

import java.util.Random;

public class EntityHackerMan implements EntityBase, Collidable {


    private Bitmap bmp = null; // Define image object name (bmp)
    private Sprite spritesheet = null;
    // Sprite class will take in a sprite sheet image
    // Name of this Sprite object is called spritesheet
    //vector 2 class from ACG, PPHYs, go ahead!!

    private float xPos, yPos, xDir, yDir, lifeTime;
    private float screenX, screenY;
    private boolean hasTouched = false; // Check for ontouch events
    private boolean isDone, isInit;
    private int renderLayer = LayerConstants.GAMEOBJECTS_LAYER;
    private int ScreenWidth, ScreenHeight;
    Random ranGen;
    int score;
    boolean scored;

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
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.hackerman);
//      scaledbmpP = Bitmap.createScaledBitmap(bmpP, (int)(ScreenWidth)/12, (int)(ScreenHeight)/10, true);

        //Define which image / png u want to use for this entity
        // Using ResourceManager

        // For me: my smurf will be render at random position on the screen
        // then when the user touch the smurf on the screen, new smurfs will be render at
        // another position.
        ranGen = new Random();
        lifeTime = ranGen.nextFloat();
        //_view.getWidth(); -- will give the length of the view = surfaceview = screen
        // because we using a state, we created our own surfaceview = screen
        // ranGen will produce random x values based on the view size
        xPos = ranGen.nextFloat() * _view.getWidth();
        //yPos = ranGen.nextFloat() * _view.getHeight();
        yPos = _view.getHeight() / 2;
        // Not used but u can use them if u want
        xDir = ranGen.nextFloat() * 100.0f - 50.0f;
        yDir = ranGen.nextFloat() * 100.0f - 50.0f;

        ScreenWidth = ScreenConstants.GetScreenWidth(_view);
        ScreenHeight = ScreenConstants.GetScreenHeight(_view);

        bmp = Bitmap.createScaledBitmap(bmp, ScreenConstants.GetQuadWidth(_view), ScreenConstants.GetQuadHeight(_view), true);

        score = 0;
        scored = false;
        isInit = true;
    }

    @Override
    public void Update(float _dt) {

        if (TouchManager.Instance.IsPress()) {
            // 0.0f, xPos, yPos, imgRadius ---> Checking collision of finger w the image

            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos,bmp.getWidth())) {
                // Collided!

//                hasTouched = true;

                xPos=ranGen.nextFloat() * ScreenWidth;
                yPos=ranGen.nextFloat() * ScreenHeight;

                score++;
                isDone = true;
                scored = true;
                //lifeTime = 5.0f;

                AudioManager.Instance.PlayAudio(R.raw.damage);

            }

        }

        // Update will pass the delta time in for the animation to happen
        //spritesheet.Update(_dt);

        // Lifetime .. meant to check if time is up, destroy the image created.
        lifeTime -= _dt;
        if (lifeTime < 0.0f)
        {
            xPos=ranGen.nextFloat() * ScreenWidth;
            yPos=ranGen.nextFloat() * ScreenHeight;
            isDone = true;
            //lifeTime = 5.0f;
        }

    }

    // Render
    @Override
    public void Render(Canvas _canvas) {
        // Our basic rendering with image centered
        _canvas.drawBitmap(bmp, xPos - bmp.getWidth() * 0.5f, yPos - bmp.getHeight() * 0.5f, null);

        // This is for our sprite animation!
        //spritesheet.Render(_canvas, (int) xPos, (int) yPos, (int) scaleX, (int) scaleY);
    }

    @Override
    public boolean IsInit() {
        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return renderLayer;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        renderLayer = _newLayer;
    }

    public static EntityHackerMan Create() {
        EntityHackerMan result = new EntityHackerMan();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_HACKERMAN);
        return result;
    }

    public static EntityHackerMan Create(int _layer) {
        EntityHackerMan result = Create();
        result.SetRenderLayer(_layer);
        return result;
    }

    @Override
    public ENTITY_TYPE GetEntityType () {
        return ENTITY_TYPE.ENT_HACKERMAN;
    }

    @Override
    public String GetType () {
        return "ENT_HACKERMAN";
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
        return bmp.getWidth();
    }

    @Override
    public void OnHit (Collidable _other){
        if (_other.GetType() == "NextEntity") //Another Entity
        {
            SetIsDone(true);
        }
    }

    public int GetScore(){
        return score;
    }

    public boolean GetScored() { return scored; }
    public void SetScored(boolean _scored) { scored = _scored; }

}