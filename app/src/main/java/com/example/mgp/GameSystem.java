package com.example.mgp;

import android.content.SharedPreferences;
import android.view.SurfaceView;

import com.example.mgp.GamePages.MainGameSceneState;
import com.example.mgp.GamePages.ObstacleGame;
import com.example.mgp.GamePages.TapGame;
import com.example.mgp.ActivityStates.Gamepage;
import com.example.mgp.ActivityStates.Mainmenu;

import java.util.Set;

// Created by TanSiewLan2020

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();
    public static final String SHARED_PREF_ID = "GameSaveFile";

    // Game stuff
    private boolean isPaused = false;
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;

    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
    }

    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {
        ResourceManager.Instance.Init(_view);
        AudioManager.Instance.Init(_view);
        // We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new Mainmenu());
        StateManager.Instance.AddState(new InstructionSceneState());
        StateManager.Instance.AddState(new MainGameSceneState());
        StateManager.Instance.AddState(new TapGame());
        StateManager.Instance.AddState(new ObstacleGame());
        //shared preference
        sharedPref = Gamepage.Instance.getSharedPreferences(SHARED_PREF_ID, 0);
    }

    public void SetIsPaused(boolean _newIsPaused)
    {
        isPaused = _newIsPaused;
    }

    public boolean GetIsPaused()
    {
        return isPaused;
    }

    public void SaveEditBegin(){
        if(editor != null)
            return;
        editor=sharedPref.edit();
    }

    public void SaveEditEnd(){
        if(editor == null)
            return;
        editor.commit();
        editor = null;
    }

    public void SetIntInSave(String _key,int _value){
        if(editor == null)
            return;
        editor.putInt(_key,_value);
    }

    public int GetIntFromSave(String _key){
        return sharedPref.getInt(_key, -1);
    }
}
