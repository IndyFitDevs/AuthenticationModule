package com.indyfit.authenticationmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    String api_key = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AndroidNetworking.initialize(getApplicationContext());

        // code to POST data to API
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", "indyfit.tester@yopmail.com");
            jsonObject.put("password", "testtest1234!");
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
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast toast = Toast. makeText(getApplicationContext(), "Unable to log in with provided credentials.",
                                Toast. LENGTH_SHORT);
                        toast.show();
                    }
                });


    }
}