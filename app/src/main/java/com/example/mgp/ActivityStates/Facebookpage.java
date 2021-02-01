
package com.example.mgp.ActivityStates;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.mgp.BuildConfig;
import com.example.mgp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.Share;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;

public class Facebookpage extends Activity implements View.OnClickListener {
    private Button btn_back;
    private com.facebook.login.widget.ProfilePictureView profilePic;
    private LoginButton btn_login;
    private LoginManager loginManager;
    private CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private Button btn_share;
    private ShareDialog shareDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.facebookpage);


        btn_back = (Button)findViewById(R.id.btn_backtomainmenu);
        btn_back.setOnClickListener(this);

        btn_share = (Button)findViewById(R.id.sharebutton);
        btn_share.setOnClickListener(this);

        btn_login = (LoginButton) findViewById(R.id.fb_login_button);
        btn_login.setReadPermissions(Arrays.asList(EMAIL));
        LoginManager.getInstance().logInWithReadPermissions(this,Arrays.asList("public_profile", "email"));
        profilePic = (ProfilePictureView)findViewById(R.id.picture);

        shareDialog = new ShareDialog(this);

        callbackManager = CallbackManager.Factory.create();

        loginManager = LoginManager.getInstance();
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            //On Successful attempt to login
            public void onSuccess(LoginResult loginResult) {
                profilePic.setProfileId(Profile.getCurrentProfile().getId());
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                loginResult.getAccessToken().getUserId();
            }

            @Override
            public void onCancel() {
                System.out.println("Failed login attempt");
            }

            @Override
            public void onError(FacebookException error) {
                System.out.println("Error login attempt");
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        if (v == btn_back)
        {
            intent.setClass(this,Mainmenu.class);
            startActivity(intent);

        }

        else if (v == btn_share)
        {
            shareScore();
        }
    }

    public void shareScore(){
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.mainmenu);


        if (ShareDialog.canShow(SharePhotoContent.class)){
            System.out.println("photo shown");

            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(image)
                    .setCaption("Thank you for playing MGP2020. Your final score is ")
                    .build();
            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();

            shareDialog.show(content);
        }
    }
}
