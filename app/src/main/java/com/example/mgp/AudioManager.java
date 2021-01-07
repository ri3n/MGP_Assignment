package com.example.mgp;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.view.SurfaceView;

import java.util.HashMap;

public class AudioManager
{
    private SurfaceView view = null;
    private Resources res = null;

    private HashMap<Integer, MediaPlayer> audioMap = new HashMap<Integer, MediaPlayer>();

    private MediaPlayer newAudio;

    public final static AudioManager Instance = new AudioManager();

    private AudioManager()
    {

    }

    public void Init(SurfaceView _view)
    {
        view = _view;
        res = _view.getResources();
    }

    public void PlayAudio(int _id)
    {
        if (audioMap.containsKey(_id))   //Hashmap using a key -- _id
        {
            audioMap.get(_id).reset();
            audioMap.get(_id).start();
        }

        // Load the audio
        MediaPlayer newAudio = MediaPlayer.create(view.getContext(), _id);
        audioMap.put(_id, newAudio);
        newAudio.start();
    }

    public boolean IsPlaying(int _id)
    {
        if (!audioMap.containsKey(_id))
            return false;

        return audioMap.get(_id).isPlaying();
    }

    public void StopAudio(int _id)
    {
        audioMap.get(_id);
        newAudio.stop();
    }
}

