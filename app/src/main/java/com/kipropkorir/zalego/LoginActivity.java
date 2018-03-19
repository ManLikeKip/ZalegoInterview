
package com.kipropkorir.zalego;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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


public class LoginActivity extends Activity {
    private Button btnLogin;
    private Button btnRegister;
    EditText ed_email, ed_password;


MaterialDialog materialDialog;
String email , password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);



        initViews();


        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                email = ed_email.getText().toString().trim();
                 password = ed_password.getText().toString().trim();


                 logIn(email, password);

            }

        });


        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        SignUp.class);
                startActivity(i);
                finish();
            }
        });

    }

   private void logIn(String email, String password){


       // Tag used to cancel the request
       String tag_string_req = "req_register";

       materialDialog = new MaterialDialog.Builder(LoginActivity.this)

               .content("Logging in, please wait ...")
               .progress(true, 2000)
               .show();


       String tag_json_obj = "json_obj_req";


       JSONObject jsonObjectRequest = new JSONObject();

       try {

           jsonObjectRequest.put("email", email);
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

                           // Check for error node in json
                           if (!error) {

                               Intent intent = new Intent(LoginActivity.this,
                                       MainActivity.class);
                               startActivity(intent);
                               finish();
                           } else {
                               // Error in login. Get the error message
                               String errorMsg = result.getString("error_msg");
                               Toast.makeText(getApplicationContext(),
                                       errorMsg, Toast.LENGTH_LONG).show();
                           }
                       } catch (JSONException e) {
                           // JSON error
                           e.printStackTrace();
                           Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();

                       }
                   }
               }, new Response.ErrorListener() {

           @Override
           public void onErrorResponse(VolleyError error) {
               VolleyLog.d("TAG", "Error: " + error.getMessage());
               Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
    private void initViews(){
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_password= (EditText) findViewById(R.id.ed_password);
        btnLogin = (Button) findViewById(R.id.btn_log_in);
        btnRegister = (Button) findViewById(R.id.btn_sign_up);

    }
}
