package com.indyfit.authenticationmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "";
    EditText user_email;
    EditText user_username;
    EditText user_pass1;
    EditText user_pass2;
    Button signup;
    TextView error;
    TextView email_error;
    TextView username_error;
    TextView password_error;

    String api_key = "";
    String email_string = "";
    String username_string = "";
    String password1_string = "";
    String password2_string = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user_email = (EditText) findViewById(R.id.editText_email);
        user_username = (EditText) findViewById(R.id.editText_username);
        user_pass1 = (EditText) findViewById(R.id.editText_password);
        user_pass2 = (EditText) findViewById(R.id.editText_password2);
        signup = (Button) findViewById(R.id.button_signup);
        error = (TextView) findViewById(R.id.textView_exception);
        email_error = (TextView) findViewById(R.id.textView_emailerror);
        username_error = (TextView) findViewById(R.id.textView_usernameerror);
        password_error = (TextView) findViewById(R.id.textView_passworderror);
    }

    public void loginPage(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void attempt_signup(View view) {
        email_string = user_email.getText().toString().trim();
        username_string = user_username.getText().toString().trim();
        password1_string = user_pass1.getText().toString().trim();
        password2_string = user_pass2.getText().toString().trim();

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username_string);
            jsonObject.put("password1", password1_string);
            jsonObject.put("password2", password2_string);
            jsonObject.put("email", email_string);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://api.indy.fit/rest-auth/registration/")
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String message = "";
                        if (response.has("detail")) {
                            message = response.optString("detail");
                        }
                        error.setText(message);
                    }

                    @Override
                    public void onError(ANError anError) {
                       error.setText("There was an error. Please try again.");
                    }

                });
    }
}