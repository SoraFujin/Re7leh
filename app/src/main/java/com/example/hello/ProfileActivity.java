package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private RequestQueue queue;

    private String fullname;
    private String username;
    private String password;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent exintent = getIntent();

        int id = exintent.getIntExtra("id", 0);
        getUserInfo(id);

        Button changeFullName = findViewById(R.id.changeFullName);
        Button changePassword = findViewById(R.id.changePassword);
        Button changeUsername = findViewById(R.id.changeUsername);
        Button changeEmail = findViewById(R.id.changeEmail);
        Button signout = findViewById(R.id.signOut);
        Button deleteAcc = findViewById(R.id.deleteAccount);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeUser(id);
                Intent intent = new Intent(ProfileActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        changeFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ChangeUserInfo.class);
                intent.putExtra("op", 1);

                intent.putExtra("id", id);
                intent.putExtra("fullname", fullname);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ChangeUserInfo.class);
                intent.putExtra("op", 2);

                intent.putExtra("id", id);
                intent.putExtra("fullname", fullname);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }
        });

        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ChangeUserInfo.class);
                intent.putExtra("op", 3);

                intent.putExtra("id", id);
                intent.putExtra("fullname", fullname);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }
        });

        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ChangeUserInfo.class);
                intent.putExtra("op", 4);

                intent.putExtra("id", id);
                intent.putExtra("fullname", fullname);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }
        });

    }

    private void getUserInfo(int id) {
        String url = "http://10.0.2.2/android/getUserInfo.php?id=" + id;
        Log.d("url", url);
        queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("userInfoResponse", response.toString());
                                JSONObject userObject = response.getJSONObject(0);
                                fullname = userObject.getString("fullname");
                                username = userObject.getString("username");
                                email = userObject.getString("email");
                                password = userObject.getString("pass");

                                TextView fullnameText = findViewById(R.id.fullName);
                                TextView usernameText = findViewById(R.id.username);
                                TextView emailText = findViewById(R.id.email);

                                fullnameText.setText(fullname);
                                usernameText.setText(username);
                                emailText.setText(email);

                        } catch (JSONException e) {
                            Log.d("Error", e.toString());
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

    private void removeUser(int id) {
        String url = "http://10.0.2.2/android/removeUser.php?id=" + id;
        Log.d("url", url);
        queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

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
