package com.siddhantjain.muskular;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserLogin extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        setContentView(R.layout.activity_user_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_login, menu);
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
    public void dashboard_view(View view){
        //Validation for email syntax, password syntax and authentication
        String email;
        String pass;
        emailEditText = (EditText) findViewById(R.id.etUserName);
        passwordEditText = (EditText) findViewById(R.id.etPass);
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
            APIGuy.authenticateUser(userCreateRequest, new APICallback<UserAuthResponse, UserAuth>(this) {
                @Override
                public void onSuccess(UserAuth data) {
                    Log.v("POST RESPONSE - ", data.toString());
                    SharedPreferences sharedPreferences = DataStore.getSharedPref(getApplicationContext());
                    SharedPreferences.Editor SPEditor = sharedPreferences.edit();
                    if (sharedPreferences.getString("user_id", null) == null) {
                        SPEditor.putString("user_id", data.getUserId());
                        SPEditor.commit();
                        Log.v("Shared Preferences", sharedPreferences.getString("user_id", "no user id"));
                    }
                    Intent intent = new Intent(UserLogin.this, Dashboard.class);
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

    private boolean isUserAuthenticated(String email, String pass) {
        //can change this function to return the error code and handle the error code accordingly
        return true;
    }
}
