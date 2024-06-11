package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ChangeUserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeuserinfo);

        final EditText oldChange = findViewById(R.id.oldChangeText);
        final EditText newChange = findViewById(R.id.newChangeText);

        Intent exintent = getIntent();
        int op = exintent.getIntExtra("op", 0);
        int id = exintent.getIntExtra("id", 0);
        String fullname = exintent.getStringExtra("fullname");
        String username = exintent.getStringExtra("username");
        String password = exintent.getStringExtra("password");
        String email = exintent.getStringExtra("email");

        Button change = findViewById(R.id.changebtn);
        Button cancel = findViewById(R.id.cancelbtn);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeUserInfo.this, ProfileActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (op) {
                    case 1:
                        if(fullname.equals(oldChange.getText().toString())){
                           String fullname2 = newChange.getText().toString();
                           editUserInfo(fullname2,username,password,email,id);
                           Intent intent = new Intent(ChangeUserInfo.this, ProfileActivity.class);
                           intent.putExtra("id", id);
                           startActivity(intent);
                           finish();
                        }else{
                            Toast.makeText(ChangeUserInfo.this, "fullName is wrong try again", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 2:
                        if(username.equals(oldChange.getText().toString())){
                            String username2 = newChange.getText().toString();
                            editUserInfo(fullname,username2,password,email,id);
                            Intent intent = new Intent(ChangeUserInfo.this, ProfileActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(ChangeUserInfo.this, "username is wrong try again", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 3:
                        if(password.equals(oldChange.getText().toString())){
                            String password2 = newChange.getText().toString();
                            editUserInfo(fullname,username,password2,email,id);
                            Intent intent = new Intent(ChangeUserInfo.this, ProfileActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(ChangeUserInfo.this, "password is wrong try again", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 4:
                        if(email.equals(oldChange.getText().toString()) && android.util.Patterns.EMAIL_ADDRESS.matcher(newChange.getText().toString()).matches()){
                            String email2 = newChange.getText().toString();
                            editUserInfo(fullname,username,password,email2,id);
                            Intent intent = new Intent(ChangeUserInfo.this, ProfileActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(ChangeUserInfo.this, "email is wrong try again or new email is invalid", Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:

                        break;
                }

            }
        });

    }

    private void editUserInfo(String fullname, String username,String password,String email,int id){
        String url = "http://10.0.2.2/android/editUserInfo.php";

        RequestQueue queue = Volley.newRequestQueue(this);


        // Create a JsonObjectRequest with POST method
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseTran",response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Log.d("errorTran",error.toString());
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
                params.put("id", String.valueOf(id));
                params.put("username", String.valueOf(username));
                params.put("fullname", String.valueOf(fullname));
                params.put("password", String.valueOf(password));
                params.put("email", String.valueOf(email));

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }

}
