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
    private TextView mTextViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toast toast = Toast. makeText(getApplicationContext(), "Hello Parth",
                Toast. LENGTH_SHORT);
        toast.show();


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
                        final String myResponse = response.optString("key");
                        Toast toast = Toast. makeText(getApplicationContext(), myResponse,
                                Toast. LENGTH_SHORT);

                        toast.show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast toast = Toast. makeText(getApplicationContext(), "Error",
                                Toast. LENGTH_SHORT);
                        toast.show();
                    }
                });

//        mTextViewResult = findViewById(R.id.text_view_result);
//        OkHttpClient client = new OkHttpClient();
//        String url = "https://indyfit.herokuapp.com/diet/recipes/45/";
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    final String myResponse = response.body().string();
//                    LoginActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mTextViewResult.setText(myResponse);
//                        }
//                    });
//                }
//            }
//        });


    }
}