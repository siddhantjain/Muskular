package com.siddhantjain.muskular;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.siddhantjain.muskular.api.APICallback;
import com.siddhantjain.muskular.api.APIClient;
import com.siddhantjain.muskular.api.MuskAPI;
import com.siddhantjain.muskular.models.UserAuth;
import com.siddhantjain.muskular.models.UserAuthResponse;
import com.siddhantjain.muskular.models.UserCreateRequest;
import com.siddhantjain.muskular.utils.DataStore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WelcomeScreen extends Activity{
    AnimationDrawable welcomeAnimation;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText rePasswordEditText;
    private TextView signInTextView;
    private TextView signUpTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataStore.init(getApplicationContext());

//        SharedPreferences sharedPreferences = DataStore.getUserProfileStore(getApplicationContext());
//        String userId = sharedPreferences.getString("user_id", null);

        if(DataStore.getUserId() != null){
            Intent intent = new Intent(this,Dashboard.class);
            startActivity(intent);
            WelcomeScreen.this.finish();
        }
        setContentView(R.layout.activity_welcome_screen);

        Typeface helvetica = Typeface.createFromAsset(getAssets(), "fonts/GeosansLight.ttf");
        signUpTextView = (TextView) findViewById(R.id.tvSignUp);
        signInTextView = (TextView) findViewById(R.id.tvSignIn);
        signUpTextView.setTypeface(helvetica);
        signInTextView.setTypeface(helvetica);

        ImageView welcomeBgImage = (ImageView) findViewById(R.id.ivBackground);
        welcomeBgImage.setBackgroundResource(R.drawable.splash_animation);
        welcomeAnimation = (AnimationDrawable) welcomeBgImage.getBackground();
        welcomeAnimation.setEnterFadeDuration(1000);
        welcomeAnimation.setExitFadeDuration(1000);
        welcomeAnimation.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void user_sign_up(View view){
        LinearLayout signUpLayout = (LinearLayout) findViewById(R.id.llSignUp);
        signUpLayout.setVisibility(View.VISIBLE);
        LinearLayout origButtonLayout = (LinearLayout) findViewById(R.id.llOrigButtonStack);
        origButtonLayout.setVisibility(View.INVISIBLE);
    }
    public void user_login(View view){
        LinearLayout signInLayout = (LinearLayout) findViewById(R.id.llSignIn);
        signInLayout.setVisibility(View.VISIBLE);
        LinearLayout origButtonLayout = (LinearLayout) findViewById(R.id.llOrigButtonStack);
        origButtonLayout.setVisibility(View.INVISIBLE);
    }

    public void dashboard_view(View view){
        //Validation for email syntax, password syntax and authentication
        System.out.println("Entered dashboard view");
        String email;
        String pass;
        emailEditText = (EditText) findViewById(R.id.etUserName_login);
        passwordEditText = (EditText) findViewById(R.id.etPass_login);
        email = emailEditText.getText().toString();
        pass = passwordEditText.getText().toString();
        boolean isValidEmailFlag = isValidEmail(email);
        if (!isValidEmail(email)) {
            emailEditText.setError("Invalid Email");
        }
        if (!isValidPassword(pass)) {
            passwordEditText.setError("Password should be at least 6 characters and non empty");
        }
        if(isValidEmail(email) && isValidPassword(pass)) {
            UserCreateRequest userCreateRequest = new UserCreateRequest();
            userCreateRequest.setEmailId(email);
            userCreateRequest.setPassword(pass);
            MuskAPI APIGuy = APIClient.getAPIClient();
            ///*
            //temporary code. Remove after api calls are from server
            Intent intent = new Intent(WelcomeScreen.this, Dashboard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //end here
            //*/
            APIGuy.authenticateUser(userCreateRequest, new APICallback<UserAuthResponse, UserAuth>(this) {
                @Override
                public void onSuccess(UserAuth data) {
                    Log.v("POST RESPONSE - ", data.toString());
                    SharedPreferences sharedPreferences = DataStore.getUserProfileStore(getApplicationContext());
                    SharedPreferences.Editor SPEditor = sharedPreferences.edit();
                    if (sharedPreferences.getString("user_id", null) == null) {
                        SPEditor.putString("user_id", data.getUserId());
                        SPEditor.commit();
                        Log.v("Shared Preferences", sharedPreferences.getString("user_id", "no user id"));
                    }
                    Intent intent = new Intent(WelcomeScreen.this, Dashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.v("POST RESPONSE - ", errorMessage);
                    Toast.makeText(getApplicationContext(), errorMessage,
                            Toast.LENGTH_LONG).show();
                   TextView bad_credentials = (TextView) findViewById(R.id.tvBadCredentialsMessage);
                   bad_credentials.setVisibility(View.VISIBLE);
                }
            });
        }
    }


    public void userProfilerConnector(View view){
        String email,pass,repass;
        emailEditText = (EditText) findViewById(R.id.etUserName);
        passwordEditText = (EditText) findViewById(R.id.etPass);
        rePasswordEditText = (EditText) findViewById(R.id.etRePass);

        email = emailEditText.getText().toString();
        pass = passwordEditText.getText().toString();
        repass = rePasswordEditText.getText().toString();

        boolean isValidEmailFlag = isValidEmail(email);
        if (!isValidEmail(email)) {
            emailEditText.setError("Invalid Email");
        }
        if (!isValidPassword(pass)) {
            passwordEditText.setError("Password should be at least 6 characters and non empty");
        }
        if (!isValidPassword(repass)) {
            rePasswordEditText.setError("Password should be at least 6 characters and non empty");
        }
        if(!pass.equals(repass)){
            rePasswordEditText.setError("The two passwords do not match");
        }
        if(isValidEmail(email) && isValidPassword(pass) && pass.equals(repass)) {
            UserCreateRequest userCreateRequest = new UserCreateRequest();
            userCreateRequest.setEmailId(email);
            userCreateRequest.setPassword(pass);
            MuskAPI APIGuy = APIClient.getAPIClient();

            ///*
            // Temporary code. Remove once API calls are to server
//            Intent intent = new Intent(WelcomeScreen.this, UserProfilerConnector.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
            //code ends here
            //*/
            APIGuy.createUser(userCreateRequest, new APICallback<UserAuthResponse, UserAuth>(this) {
                @Override
                public void onSuccess(UserAuth data) {
                    Log.v("CREATE USER RESPONSE - ", data.toString());
//                    SharedPreferences sharedPreferences = DataStore.getUserProfileStore(getApplicationContext());
//                    SharedPreferences.Editor SPEditor = sharedPreferences.edit();
//                    if (sharedPreferences.getString("user_id", null) == null) {
//                        SPEditor.putString("user_id", data.getUserId());
//                        SPEditor.putString("last_section_completed", data.getLastSectionCompleted());
//                        SPEditor.commit();
//                        Log.v("Shared Preferences", sharedPreferences.getString("user_id", "no user id"));
//                    }
                    DataStore.setUserId(data.getUserId());
                    Intent intent = new Intent(WelcomeScreen.this, UserProfilerConnector.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.v("CREATE USER RESPONSE -", errorMessage);
                    Toast.makeText(getApplicationContext(), errorMessage,
                            Toast.LENGTH_LONG).show();
                   TextView sign_up_failure_message = (TextView) findViewById(R.id.tvSignUpFailureMessage);
                    sign_up_failure_message.setVisibility(View.VISIBLE);
                }

                @Override
                public void onNetworkFailure() {
                    System.out.println("Before");
                    super.onNetworkFailure();
                    System.out.println("After");
                }
            });
        }
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 6) {
            return true;
        }
        return false;
    }
}
