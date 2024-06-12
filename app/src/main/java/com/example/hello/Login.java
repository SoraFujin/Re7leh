package com.example.hello;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private static final String PREFS_NAME = "LoginPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER_ME = "rememberMe";

    private RequestQueue queue;
    private EditText userName;
    private EditText passWord;
    private CheckBox rememberMeCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.username);
        passWord = findViewById(R.id.password);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean rememberMe = preferences.getBoolean(KEY_REMEMBER_ME, false);
        if (rememberMe) {
            String savedUsername = preferences.getString(KEY_USERNAME, "");
            String savedPassword = preferences.getString(KEY_PASSWORD, "");
            userName.setText(savedUsername);
            passWord.setText(savedPassword);
            rememberMeCheckBox.setChecked(true);
        }

        TextView textViewSignUp = findViewById(R.id.signuptext);
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button loginButton = findViewById(R.id.loginbtn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userName.getText().toString();
                String password = passWord.getText().toString();
                boolean rememberMe = rememberMeCheckBox.isChecked();

                // Save login credentials if "Remember Me" is checked
                SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                if (rememberMe) {
                    editor.putString(KEY_USERNAME, username);
                    editor.putString(KEY_PASSWORD, password);
                    editor.putBoolean(KEY_REMEMBER_ME, true);
                } else {
                    editor.remove(KEY_USERNAME);
                    editor.remove(KEY_PASSWORD);
                    editor.remove(KEY_REMEMBER_ME);
                }
                editor.apply();

                canLogin(username, password);
            }
        });
    }

    private void canLogin(String username, String pass) {
        String url = "http://10.0.2.2/android/canLoggin.php?username=" + username + "&pass=" + pass;
        Log.d("url", url);
        queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        String check = "";
                        try {
                            check = response.getString("answer");
                            if(check.equals("yes")){
                                Intent intent = new Intent(Login.this, Menu.class);
                                if(response.has("permission")) {
                                    try {
                                        int idUser = response.getInt("id");
                                        intent.putExtra("permission", response.getString("permission"));
                                        intent.putExtra("id", idUser);
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Username or password is incorrect", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException exception) {
                            Log.d("Error", exception.toString());
                            Toast.makeText(Login.this, "Error parsing response", Toast.LENGTH_LONG).show();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyError", error.toString());
            }
        });
        queue.add(request);
    }

}
