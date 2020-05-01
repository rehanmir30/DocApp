package com.example.docapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText username, password;
    TextView forget;


    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        forget = findViewById(R.id.forget);


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
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ResetActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void signin(final String email, final String password) {

        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "Loading", "Please wait...", true);
        String url = "http://medico.yac-tech.com/api/api-get-endpoints.php?data=doctors";
        String Url = "http://medico.yac-tech.com/api/api-doc-signin.php";

        StringRequest request = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject j = new JSONObject(response);
                    String status = j.getString("status");
                    String message = j.getString("message");
                    String docId = j.getString("doctor_id");


                    if (status.equals("200")) {

                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("id", docId);
                        myEdit.commit();

                        Intent i = new Intent(MainActivity.this, HomeActivity.class);
                        i.putExtra("d_id", docId);
                        dialog.dismiss();
                        startActivity(i);
                        finish();
                    } else if (status.equals("404")) {

                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Wrong email or passsword entered", Toast.LENGTH_LONG).show();
                        return;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Network Problem. Try Again later", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("docEmail", email);
                params.put("docPassword", password);
                return params;
            }
        };
        mQueue.add(request);


    }


}
