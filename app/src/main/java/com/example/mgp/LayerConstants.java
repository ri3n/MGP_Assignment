package com.example.mgp;

// Created by TanSiewLan2020
// Able to help you do layering of different assets rendered on the screen

public class LayerConstants {

    public final static int BACKGROUND_LAYER = 0;
    public final static int BACKGROUNDGAMEOBJECTS_LAYER = BACKGROUND_LAYER + 1;
    public final static int GAMEOBJECTS_LAYER = BACKGROUNDGAMEOBJECTS_LAYER + 1;  // Example
    public final static int UI_LAYER = 1000;
    public final static int RENDERTEXT_LAYER = UI_LAYER;
}