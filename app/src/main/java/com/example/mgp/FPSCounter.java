package com.example.mgp;

public class FPSCounter {
    private int frameCount;
    private long lastTime;
    public float fps;
    private long lastFPSTime;

    public final static FPSCounter Instance = new FPSCounter();

    private FPSCounter()
    {
        frameCount = 0;
        lastFPSTime = 0;
        lastTime = 0;
        fps = 0;
    }


    public void Update(float _dt)
    {
        // get actual fps and print fps on screen
        frameCount++;
        long currentTime = System.currentTimeMillis();

        lastTime = currentTime;

        if(currentTime - lastFPSTime > 1000)
        {
            fps = (frameCount * 1000.f) / (currentTime - lastFPSTime);
            lastFPSTime = currentTime;
            frameCount = 0;
        }

    }
}
