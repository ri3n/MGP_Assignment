package com.example.mgp.Entities;

import android.graphics.Canvas;
import android.view.SurfaceView;
import com.example.mgp.*;

// Created by TanSiewLan2020

public interface EntityBase
{
 	 //used for entities such as background
    enum ENTITY_TYPE {
         ENT_PLAYER,
         ENT_SMURF,
         ENT_PAUSE,
         ENT_ENTER,
         ENT_TEXT,
         ENT_NEXT,
         ENT_BACKGROUND,
         ENT_PORTAL,
         ENT_LEFTARROW,
         ENT_RIGHTARROW,
         ENT_HACKERMAN,
         ENT_HOUSE,
         ENT_OBSTACLE,
         ENT_COIN,
         ENT_POWERUP,
         ENT_DEFAULT
     }

    boolean IsDone();
    void SetIsDone(boolean _isDone);

    void Init(SurfaceView _view);
    void Update(float _dt);
    void Render(Canvas _canvas);

    boolean IsInit();

    int GetRenderLayer();
    void SetRenderLayer(int _newLayer);

	 ENTITY_TYPE GetEntityType();
}
