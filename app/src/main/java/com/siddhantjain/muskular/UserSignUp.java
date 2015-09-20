package com.siddhantjain.muskular;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.siddhantjain.muskular.api.APICallback;
import com.siddhantjain.muskular.api.APIClient;
import com.siddhantjain.muskular.api.MuskAPI;
import com.siddhantjain.muskular.models.UserAuth;
import com.siddhantjain.muskular.models.UserAuthResponse;
import com.siddhantjain.muskular.models.UserCreateRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSignUp extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText rePasswordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        setContentView(R.layout.activity_user_sign_up);
    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }

    private boolean isUserRegistered(String email, String pass) {
        //can change this function to return the error code and handle the error code accordingly
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_sign_up, menu);
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
    public void appIntroductionActivityConnector(View view){
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
//            if (!isUserRegistered(email, pass)) {
//                TextView bad_credentials = (TextView) findViewById(R.id.tvBadCredentialsMessage);
//                bad_credentials.setVisibility(View.VISIBLE);
//            }
            UserCreateRequest userCreateRequest = new UserCreateRequest();
            userCreateRequest.setEmailId(email);
            userCreateRequest.setPassword(pass);

            UserAuthResponse userAuthResponse;
//            APIClient apiClient = new APIClient();
            MuskAPI APIGuy = APIClient.getAPIClient();
            APIGuy.createUser(userCreateRequest, new APICallback<UserAuthResponse, UserAuth>(this) {
                @Override
                public void onSuccess(UserAuth data) {
                    Log.v("CREATE USER RESPONSE - ", data.toString());
                    Intent intent = new Intent(UserSignUp.this, AppIntroduction.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.v("CREATE USER RESPONSE -", errorMessage);
                }
            });



        }
    }
}
