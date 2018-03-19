package com.kipropkorir.zalego;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kipropkorir.zalego.app.AppConfig;
import com.kipropkorir.zalego.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kiprop Korir on 3/19/18.
 * All code copyrighted by www.kipropkorir.com .
 */

public class SignUp extends AppCompatActivity {

   private Button btnRegister;
    private Button tvLogin;
    MaterialDialog materialDialog;

    EditText ed_email , ed_password, ed_confirm_password, ed_id , ed_fname , ed_lname;
    Spinner spCourse;
    String email , password , confirm_password, id, fname, lname, course;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        initViews();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                course=spCourse.getSelectedItem().toString();
                email=ed_email.getText().toString();
                confirm_password=ed_confirm_password.getText().toString();
                fname=ed_fname.getText().toString();
                lname=ed_lname.getText().toString();
                id=ed_id.getText().toString();
                password=ed_password.getText().toString();

                register(fname, lname, id,course,email,password,confirm_password);
            }
        });
    }

    private void initViews(){
        btnRegister=(Button)findViewById(R.id.btn_sign_up);
        ed_confirm_password=(EditText)findViewById(R.id.ed_confirm_password);
        ed_password=(EditText)findViewById(R.id.ed_password);
        ed_email=(EditText)findViewById(R.id.ed_email);
                ed_fname=(EditText)findViewById(R.id.ed_f_name);
        ed_lname=(EditText)findViewById(R.id.ed_l_name);
        ed_id=(EditText)findViewById(R.id.ed_id_no);
        spCourse=(Spinner)findViewById(R.id.spinCourse);


    }




private  void  register(String fname , String lname, String id_no, String course,String email, String password, String confirm_password)
{


    //check if passwords match first

    if(!password.equals(confirm_password)){
        Toast.makeText(getApplicationContext(), "Password do not match brathe!",Toast.LENGTH_LONG).show();

    }

    else {

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        materialDialog = new MaterialDialog.Builder(SignUp.this)

                .content("Registering , please wait ...")
                .progress(true, 2000)
                .show();


        String tag_json_obj = "json_obj_req";


        JSONObject jsonObjectRequest = new JSONObject();

        try {

            jsonObjectRequest.put("f_name", fname);
            jsonObjectRequest.put("l_name", lname);
            jsonObjectRequest.put("id_no", id_no);
            jsonObjectRequest.put("email", email);
            jsonObjectRequest.put("course", course);
            jsonObjectRequest.put("password", password);

            System.out.println("kenye na post ni: " + jsonObjectRequest.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = AppConfig.BASE_URL;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, jsonObjectRequest,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject result) {
                        // Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                        Log.d("TAG", result.toString());


                        materialDialog.dismiss();


                        try {

                            System.out.println(result.toString());


                            boolean error = result.getBoolean("error");
                            if (!error) {
                                // User successfully stored in MySQL
                                // Now store the user in sqlite
                                String uid = result.getString("uid");

                                JSONObject user = result.getJSONObject("user");
                                String name = user.getString("name");
                                String email = user.getString("email");
                                String created_at = user
                                        .getString("created_at");


                                Toast.makeText(getApplicationContext(), "Welcome " + name + ", your registration was successful!", Toast.LENGTH_LONG).show();

                                // Launch login activity
                                Intent intent = new Intent(
                                        SignUp.this,
                                        LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {

                                // Error occurred in registration. Get the error
                                // message
                                String errorMsg = result.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Toast.makeText(SignUp.this, error.getMessage(), Toast.LENGTH_LONG).show();
                System.out.print("HGHH " + error.getLocalizedMessage());
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    Log.e("Volley", "Error. HTTP Status Code:" + networkResponse.statusCode);
                }

                if (error instanceof TimeoutError) {
                    Log.e("Volley", "TimeoutError");
                } else if (error instanceof NoConnectionError) {
                    Log.e("Volley", "NoConnectionError");
                } else if (error instanceof AuthFailureError) {
                    Log.e("Volley", "AuthFailureError");
                } else if (error instanceof ServerError) {
                    Log.e("Volley", "ServerError");
                } else if (error instanceof NetworkError) {
                    Log.e("Volley", "NetworkError");
                } else if (error instanceof ParseError) {
                    Log.e("Volley", "ParseError");
                }

                materialDialog.dismiss();
            }
        })


        {


        };


        // Toast.makeText(getApplicationContext(), jsonObjReq.getUrl() + " " + jsonObject.toString(), Toast.LENGTH_LONG).show();
        //  Log.d("Before ", jsonObjReq.getUrl() + " " + jsonObject.toString());
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


    }
}
}
