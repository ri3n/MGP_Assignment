package com.example.mgp.ActivityStates;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.mgp.BuildConfig;
import com.example.mgp.R;
import com.example.mgp.Splashpage;
import com.example.mgp.StateBase;
import com.example.mgp.StateManager;

import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

//extends Activity - for every new screen
//implements OnClickListener: for the screen to be touchscreen
// Layout Error possiblities:
// - Type
// - Image not found
// - Placed in wrong directory
// - Syntax Error
// !! Never resolve an XML error with import R
// R: Resource registry
public class Mainmenu extends Activity implements OnClickListener, StateBase
{
    //Start button
    private Button btn_start;
    private Button btn_options;

    private Button btn_fb;
    private CallbackManager callbackManager;
    private LoginManager loginManager;

    private static final String EMAIL = "email";
    private LoginButton btn_fbLogin;

    private ShareDialog shareDialog;
    private int PICK_IMAGE_REQUEST = 1;

    ProfilePictureView profilePictureView;

    private boolean isRender = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mainmenu);

        //Init buttons
        //R.id.___ can be found inside the .xml files
        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

        btn_options = (Button)findViewById(R.id.btn_options);
        btn_options.setOnClickListener(this);

        btn_fb = (Button)findViewById(R.id.btn_fb);
        btn_fb.setOnClickListener(this);

        FacebookSdk.setApplicationId("1115827632194810");
        FacebookSdk.isInitialized();

        if (BuildConfig.DEBUG)
        {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }

        loginManager = LoginManager.getInstance();
/*
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                //boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                loginResult.getAccessToken().getUserId();
            }

            @Override
            public void onCancel() {
                System.out.println("Login attempt failed");
            }

            @Override
            public void onError(FacebookException error) {
                System.out.println("Login attempt failed");
            }
        });
*/

    }

    @Override
    //Invoke a callback event in the view (something has to happen)
    //Activity -> Intent -> Action
    public void onClick(View v)
    {
        //This function consists of: Intent of the click and the action afterwards

        Intent intent = new Intent();

        if (v == btn_start) //if usr clicks on start
        {
            intent.setClass(this, Splashpage.class); //Set the intent right
            StateManager.Instance.ChangeState("Default"); // Default is like a loading page

            //Transit the screen
            startActivity(intent);
        }

        else if (v == btn_options)
        {
            intent.setClass(this,Optionspage.class);
            intent.putExtra("prevPage", 1);

            //Transit the screen
        }

        else if (v == btn_fb)
        {
            intent.setClass(this,Facebookpage.class);
        }

        startActivity(intent);

    }

    @Override
    public void Render(Canvas _canvas) {
    }

    @Override
    public void OnEnter(SurfaceView _view) {
    }

    @Override
    public void OnExit() {
    }

    @Override
    public void Update(float _dt) {
    }

    @Override
    public String GetName() {
        return "Mainmenu";
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    public void shareScore(){
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ship2_3);

        if (ShareDialog.canShow(SharePhotoContent.class)){
            System.out.println("photo shown");
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(image)
                    .setCaption("Thank you for playing MGP2020. Your final score is " + 5/*highscore*/)
                    .build();
            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();

            shareDialog.show(content);
        }
    }
}
