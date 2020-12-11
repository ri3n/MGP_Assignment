package com.example.mgp;

import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class ScreenConstants {

    public static int GetScreenWidth(SurfaceView _view)
    {
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }
    public static int GetScreenHeight(SurfaceView _view)
    {
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }
    public static int GetQuadWidth(SurfaceView _view)
    {
        return GetScreenWidth(_view) / 5;
    }
    public static int GetQuadHeight(SurfaceView _view)
    {
        return GetScreenWidth(_view) / 5;
    }
    public static int GetUIQuadWidth(SurfaceView _view)
    {
        return GetScreenWidth(_view) / 12;
    }
    public static int GetUIQuadHeight(SurfaceView _view)
    {
        return GetScreenHeight(_view) / 10;
    }
}
