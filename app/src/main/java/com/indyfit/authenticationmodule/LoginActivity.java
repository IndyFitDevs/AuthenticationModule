package com.indyfit.authenticationmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    // UI elements declaration
    EditText email_username;
    EditText user_password;
    Button login;
    TextView signup;
    TextView forgot;

    // Variable initialization
    String api_key = "";
    String email_username_string = "";
    String user_password_string = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AndroidNetworking.initialize(getApplicationContext());

        // UI elements initialisation
        email_username = (EditText) findViewById(R.id.editText_email);
        user_password = (EditText) findViewById(R.id.editText_password);
        login = (Button) findViewById(R.id.button_signup);
        signup = (TextView) findViewById(R.id.textview_forgotpage);
        forgot = (TextView) findViewById(R.id.textView_forgotpass);

    }

    public void attempt_login(View view){

        //TODO: exception handling in username

        email_username_string = email_username.getText().toString();
        user_password_string = user_password.getText().toString();

        // code to POST data to API
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", email_username_string);
            jsonObject.put("password", user_password_string);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://api.indy.fit/rest-auth/login/")
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        api_key = response.optString("key");
                        Toast toast = Toast. makeText(getApplicationContext(), "Successfully " +
                                        "logged in.",
                                Toast. LENGTH_SHORT);
                        toast.show();

                        Intent intent = new Intent(LoginActivity.this, SuccessActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast toast = Toast. makeText(getApplicationContext(), "Unable to log in with provided credentials.",
                                Toast. LENGTH_SHORT);
                        toast.show();
                    }
                });
    }

    public void signupPage(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void forgotPage(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotActivity.class);
        startActivity(intent);
    }
}