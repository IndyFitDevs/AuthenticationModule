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
import org.w3c.dom.Text;

public class ForgotActivity extends AppCompatActivity {

    EditText forgot_email;
    Button forgot;
    TextView status;

    String forgotemail;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        forgot_email = (EditText) findViewById(R.id.editText_email);
        forgot = (Button) findViewById(R.id.button);
        forgotemail="";
        status = (TextView) findViewById(R.id.textView_status);
    }

    public void forgot_pass(View view){
        forgotemail = forgot_email.getText().toString().trim();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", forgotemail);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        AndroidNetworking.post("hhttps://api.indy.fit/rest-auth/password/reset/")
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        message = response.optString("detail");
                        status.setText(message);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast toast = Toast. makeText(getApplicationContext(), "There was an " +
                                        "error. Please try again.",
                                Toast. LENGTH_SHORT);
                        toast.show();
                    }
                });
    }
}