package com.example.mgp;

// Created by TanSiewLan2020

public interface Collidable {
    String GetType();

    float GetPosX();
    float GetPosY();
    float GetRadius();

    void OnHit(Collidable _other);
}

