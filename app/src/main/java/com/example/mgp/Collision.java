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
    //code for collision of 2 quads
    //variables objectXpos , objectYpos,objectwidth,objectheight,
    public static boolean Quad(float objectX,float objectY,float objectWidth,float objectHeight,
    //other object xPos, other object yPos,other object width,other object height
                               float otherX,float otherY,float otherWidth,float otherHeight)
    {
        //presetting the variables for each side of the object
        float leftOfObject = objectX - (objectWidth / 2);
        float rightOfObject = objectX + (objectWidth / 2);
        float topOfObject = objectY - (objectHeight / 2);
        float botOfObject = objectY + (objectHeight / 2);
        //presetting the variables for each side of the other object
        float leftOfOther = otherX - (otherWidth / 2);
        float rightOfOther = otherX + (otherWidth / 2);
        float topOfOther = otherY - (otherHeight / 2);
        float botOfOther = otherY + (otherHeight / 2);

        //X axis check for if the overlap each other
        if((rightOfObject >= leftOfOther) && (leftOfObject <= rightOfOther) &&
        //Y axis check for if they overlap each other
           (botOfObject >= topOfOther) && (topOfObject <= botOfOther))
        {
            return true;
        }
        return false;
    }
}
