package com.socialapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.socialapp.bean.Person;
import com.socialapp.R;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.login_face_button)
    LoginButton loginFacebookButton;

    public static final String TAG = MainActivity.class.getName();
    public static final String PERSON = "person";

    CallbackManager callbackManager;
    AccessToken accessToken;
    public static final String[] permissions = {"public_profile", "email", "user_birthday", "user_likes",
            "user_location", "user_posts", "user_about_me", "user_relationship_details",
            "user_religion_politics", "user_work_history", "user_education_history"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        callbackManager = CallbackManager.Factory.create();
    }

    @OnClick(R.id.login_face_button) public void loginByFacebook() {
        loginFacebookButton.setReadPermissions(permissions);
        loginFacebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                if (accessToken != null)
                    getDataFromFacebook();
            }

            @Override
            public void onCancel() {
                createToast("login was canceled. Try again");
            }

            @Override
            public void onError(FacebookException error) {
                createToast("Can't login. Try Again");
            }
        });
    }


    private void createToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void initDetailActivity(Person p) {
        Intent i = new Intent(this, TimelineActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(PERSON, p);
        i.putExtras(b);
        startActivity(i);
    }

    private void getDataFromFacebook() {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                finish();
                Person person = Person.getPersonFomJson(object);
                initDetailActivity(person);
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, email, birthday, location, bio, education, relationship_status, religion, work");
        request.setParameters(parameters);
        request.executeAsync();
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
