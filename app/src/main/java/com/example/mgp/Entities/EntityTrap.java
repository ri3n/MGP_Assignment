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
/*
Entity: trap
Created by: Kai Yang
 */
public class EntityTrap implements EntityBase, Collidable {


    private Bitmap bmp = null; // Define image object name (bmp)
    private float xPos, yPos, xDir, yDir, lifeTime;
    private float screenX, screenY;
    private boolean hasTouched = false; // Check for ontouch events
    private boolean isDone, isInit;
    private int renderLayer = LayerConstants.GAMEOBJECTS_LAYER;
    private int ScreenWidth, ScreenHeight;
    Random ranGen;
    private boolean tapped,active;

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
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.hackermanbad);
        ranGen = new Random();
        lifeTime = 5.f;
        xPos = ranGen.nextFloat() * _view.getWidth();
        yPos = _view.getHeight() / 2;
        // Not used but u can use them if u want
        xDir = ranGen.nextFloat() * 100.0f - 50.0f;
        yDir = ranGen.nextFloat() * 100.0f - 50.0f;
        ScreenWidth = ScreenConstants.GetScreenWidth(_view);
        ScreenHeight = ScreenConstants.GetScreenHeight(_view);
        bmp = Bitmap.createScaledBitmap(bmp, ScreenConstants.GetQuadWidth(_view), ScreenConstants.GetQuadHeight(_view), true);
        active=true;
        isInit = true;
    }

    @Override
    public void Update(float _dt) {

        if (TouchManager.Instance.IsPress()) {
            // 0.0f, xPos, yPos, imgRadius ---> Checking collision of finger w the image
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, bmp.getWidth())) {
                xPos = ranGen.nextFloat() * ScreenWidth;
                yPos = ranGen.nextFloat() * ScreenHeight;
                lifeTime = 5.f;
                tapped = true;
                active = false;
                AudioManager.Instance.PlayAudio(R.raw.damage);
            }
        }
        // Lifetime .. meant to check if time is up, destroy the image created.
        if(active == true){
            lifeTime -= _dt;
        }
        if (lifeTime < 0.0f) {
            active = false;
            lifeTime = 5.f;
        }

    }

    // Render
    @Override
    public void Render(Canvas _canvas) {
        if(active == true){
            _canvas.drawBitmap(bmp, xPos - bmp.getWidth() * 0.5f, yPos - bmp.getHeight() * 0.5f, null);
        }
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

    public static EntityTrap Create() {
        EntityTrap result = new EntityTrap();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_TRAP);
        return result;
    }

    public static EntityTrap Create(int _layer) {
        EntityTrap result = Create();
        result.SetRenderLayer(_layer);
        return result;
    }

    @Override
    public ENTITY_TYPE GetEntityType () {
        return ENTITY_TYPE.ENT_TRAP;
    }

    @Override
    public String GetType () {
        return "ENT_TRAP";
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

    public void SetActive(boolean _active){ active = _active;}
    public boolean GetActive(){ return active;}

    public void SetTapped(boolean _tapped){ tapped = _tapped;}
    public boolean GetTapped(){ return tapped;}
}