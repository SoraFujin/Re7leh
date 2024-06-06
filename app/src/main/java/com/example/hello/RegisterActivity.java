package com.example.hello;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final EditText email = findViewById(R.id.textInputLayoutEmail);

        TextView textViewLogIn = findViewById(R.id.loginText);
        textViewLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, Login.class);
                startActivity(intent);
            }
        });

        Button register = findViewById(R.id.buttonSignUp);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                    String user = username.getText().toString();
                    String pass = password.getText().toString();
                    String EMAIL = email.getText().toString();
                    registerNew(user,pass,EMAIL);
                }else{
                    email.setBackgroundColor(Color.RED);
                    Toast.makeText(RegisterActivity.this, "Email not valid", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void registerNew(String username, String pass,String email){
        String url = "http://10.0.2.2/android/registerNew.php";

        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("username", username);
            jsonParams.put("pass", pass);
            jsonParams.put("email", email);
            jsonParams.put("permission", "customer");
        } catch (JSONException e) {
            e.printStackTrace();
        }



        // Create a JsonObjectRequest with POST method
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", "RESPONSE IS " + response);
                String str = "" ;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    str = jsonObject.getString("answer");
                    Log.d("Response2", "RESPONSE2 IS " + str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(str.equals("error")){
                    Toast.makeText(RegisterActivity.this, "Username or email is already in use", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(RegisterActivity.this, Login.class);
                    startActivity(intent);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(RegisterActivity.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("username", username);
                params.put("pass", pass);
                params.put("email", email);
                params.put("permission", "customer");

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }

}
