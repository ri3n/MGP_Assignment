package com.example.mgp;

import android.view.MotionEvent;

// Created by TanSiewLan2020
// Manages the touch events

public class TouchManager {
    private boolean isDownPreviously = false;
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

    public boolean HasTouch(){  // Check for a touch status on screen
        return status == TouchState.DOWN || status == TouchState.MOVE;
    }

    public boolean IsDown() { return status == TouchState.DOWN; }

    public boolean IsPress()
    {
        if (status == TouchState.DOWN && !isDownPreviously)
        {
            isDownPreviously = true;
            return true;
        }
        else return false;
    }

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
                status = TouchState.DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                status = TouchState.MOVE;
                break;

            case MotionEvent.ACTION_UP:
                status = TouchState.NONE;
                isDownPreviously = false;
                break;
            case MotionEvent.ACTION_SCROLL:
                System.out.println("scrolling");
                break;
        }
    }
}

