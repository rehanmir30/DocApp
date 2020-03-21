package com.example.docapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText username, password;

    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        mQueue = Volley.newRequestQueue(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = username.getText().toString().trim();
                String Password = password.getText().toString().trim();

                if (Username.isEmpty()) {
                    username.setError("Username Required");
                    return;
                } else if (Password.isEmpty()) {
                    password.setError("Password Required");
                    return;
                } else {
                    signin(Username, Password);
                }
            }

        });
    }

    public void signin(String email, String password) {

        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "Loading", "Please wait...", true);
        String url = "http://medico.yac-tech.com/api/api-get-endpoints.php?data=doctors";


        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("0");
                    JSONObject doctor = jsonArray.getJSONObject(0);

                    String email = doctor.getString("email");
                    String name = doctor.getString("doctorName");
                    String contact=doctor.getString("contactNo");


                    Intent i = new Intent(MainActivity.this, HomeActivity.class);

                    i.putExtra("name",name);
                    i.putExtra("email",email);
                    i.putExtra("contact",contact);

                    dialog.dismiss();
                    startActivity(i);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mQueue.add(request);
    }

}
