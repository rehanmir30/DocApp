package com.example.docapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class DetailUpdateFragment extends Fragment {

    ImageView cross,docimg;

    EditText name, email, phone,pass,c_pass;
    Button update;
    Bitmap bitmap;

    String st_img;

    RequestQueue mQueue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_update, container, false);

        cross = view.findViewById(R.id.cross);
        name = view.findViewById(R.id.user);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        pass=view.findViewById(R.id.pass);
        c_pass=view.findViewById(R.id.c_pass);
        docimg=view.findViewById(R.id.docimage);
        update = view.findViewById(R.id.update);


        String Name = getActivity().getIntent().getExtras().getString("name");
        String Phone = getActivity().getIntent().getExtras().getString("contact");
        final String Email = getActivity().getIntent().getExtras().getString("email");

        name.setText(Name);
        phone.setText(Phone);
        email.setText(Email);

        mQueue = Volley.newRequestQueue(getContext());

        docimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1000);

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String naam=name.getText().toString().trim();
                String lock= pass.getText().toString().trim();
                String c_lock= c_pass.getText().toString().trim();

                if (lock.isEmpty()){
                    pass.setError("Password missing!");
                    return;
                }
                if (c_lock.isEmpty()){
                    c_pass.setError("Confirm your Password");
                    return;
                }
                if (!lock.equals(c_lock)){
                    c_pass.setError("Passwords doesn't Match");
                    return;
                }


                String url = "http://medico.yac-tech.com/api/api-doc-personal-update.php";
                Toast.makeText(getContext(),"Restart your app to see changes",Toast.LENGTH_LONG).show();


                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();
                        String naam = name.getText().toString().trim();
                        String Email=email.getText().toString().trim();
                        String Phone=phone.getText().toString().trim();
                        String Pass=pass.getText().toString().trim();
                        String image="1343212";
                        String id="1";

                        params.put("did",id);
                        params.put("doctorName", naam);
                        params.put("doctorEmail",Email);
                        params.put("password",Pass);
                        params.put("contactNo",Phone);
                        params.put("image",image);
                        return params;
                    }

                };
                mQueue.add(stringRequest);

                FragmentManager fm = getFragmentManager();
                fm.popBackStack();


            }

        });


        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });


        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1000) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                docimg.setImageBitmap(bitmap);
                st_img=st_img(bitmap);

            }
            catch (IOException e) {

            }
        }

    }
    public String st_img(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] imgBytes=byteArrayOutputStream.toByteArray();
        String encoded_image= Base64.encodeToString(imgBytes,Base64.DEFAULT);

        return encoded_image;

    }


}
