package com.example.mgp;

import android.view.MotionEvent;

// Created by TanSiewLan2020
// Manages the touch events

public class TouchManager {
    public final static TouchManager Instance = new TouchManager();

    private TouchManager(){

    }

    public enum TouchState{
        NONE,
        DOWN,
        MOVE
    }

    private int posX, posY;
    private TouchState status = TouchState.NONE; //Set to default as NONE
    private TouchState prevStatus = TouchState.NONE; //Set to NONE first, used for IsPress

    public boolean HasTouch(){  // Check for a touch status on screen
        return status == TouchState.DOWN || status == TouchState.MOVE;
    }

    public boolean IsDown() { return status == TouchState.DOWN; }

    public boolean IsPress() { return (status == TouchState.DOWN && prevStatus == TouchState.NONE); }

    public int GetPosX(){
        return posX;
    }

    public int GetPosY(){
        return posY;
    }

    public void Update(int _posX, int _posY, int _motionEventStatus){
        posX = _posX;
        posY = _posY;

        switch (_motionEventStatus){
            case MotionEvent.ACTION_DOWN:
                prevStatus = status;
                status = TouchState.DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                prevStatus = status;
                status = TouchState.MOVE;
                break;

            case MotionEvent.ACTION_UP:
                prevStatus = status;
                status = TouchState.NONE;
                break;

        }
    }
}

