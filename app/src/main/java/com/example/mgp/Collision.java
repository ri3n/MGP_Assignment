package com.example.mgp;

// Created by TanSiewLan2020

public class Collision {

    public static boolean SphereToSphere(float x1, float y1, float radius1, float x2, float y2, float radius2)
    {
        float xVec = x2 - x1;
        float yVec = y2 - y1;

        float distSquared = xVec * xVec + yVec * yVec;

        float rSquared = radius1 + radius2;
        rSquared *= rSquared;

        if (distSquared > rSquared)
            return false;

        return true;
    }

    public static boolean Quad(float x1,float y1,float width,float height,float posX,float posY)
    {
        if( x1 > (posX - width / 2) && x1 < (posX + width / 2) && (y1 > (posY - height / 2) && y1 < (posY + height / 2))){
            return true;
        }
        return false;
    }
}
