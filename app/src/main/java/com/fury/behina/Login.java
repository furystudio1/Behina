package com.fury.behina;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fury.behina.views.FlowingGradientClass;
import com.fury.behina.views.PermissionHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import ss.anoop.awesometextinputlayout.AwesomeTextInputLayout;

public class Login extends AppCompatActivity {


    // Get Save
    SharedPreferences one_play_preferences;
    SharedPreferences.Editor one_play_editor;
    String user, pass, market;
    EditText User, Pass;
    AutoCompleteTextView edit1;
    TextView btn1, btn2;
    AwesomeTextInputLayout aa;
    String JSON_STRING,JSON_STRING1,JSON_STRING2,userper,useradmin,passper,passadmin,marketuser;
    int tedat;


    ///POST
    class BackgroundTask extends AsyncTask<String,Void,String>{

        String url;

        @Override
        protected void onPreExecute() {
            url = "";
        }


        @Override
        protected String doInBackground(String... args) {
            String str1,str2,str3;
            str1 = args[0];
            str2 = args[1];
            str3 = args[2];

            try {
                URL url2 = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(str1,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(str2,"UTF-8")+"&"+
                        URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(str3,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return "success...";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            //result
        }

    }



    ///GET User
    class BackgroundTaskGetUser extends AsyncTask<Void,Void,String>{

        String url;

        @Override
        protected void onPreExecute() {
            url = "http://46.100.60.180/behind/check_info_user.php?user=" + userper; //check_info_user
        }


        @Override
        protected String doInBackground(Void... args) {

            try {
                URL url2 = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine())!= null ){
                    stringBuilder.append(JSON_STRING+"\n");

                }


                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            //result

//if successful

            if (result == "Username not exist"){
                Toast.makeText(Login.this, "Username not exist",
                        Toast.LENGTH_LONG).show();
            }else{
                JSONArray students = null;
                try {
                    students = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // looping through All Students
                JSONObject c = null;
                try {
                    c = students.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String password = null;
                try {
                    password = c.getString("passwordPer");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(passper == password){
                    String nameUser = null;
                    try {
                        nameUser = c.getString("namePer");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    one_play_editor.putString("user", userper);
                    one_play_editor.putString("nameUser", nameUser);
                    one_play_editor.putString("pass", passper);
                    one_play_editor.putString("market", marketuser);
                    one_play_editor.apply();

                    Intent uou = new Intent(Login.this, Pers.class);
                    startActivity(uou);
                    Login.this.finish();
                }
            }


        }

    }


    public void GetUser() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            String url = "http://46.100.60.180/behind/check_info_user.php?user=" + userper;

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    //VolleyLog.v("Response:%n %s", response.toString(4));

                    Log.i("e", String.valueOf(response));

                    // looping through All Students
                    JSONObject c = null;
                    try {
                        c = response.getJSONObject(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    String password = null;
                    try {
                        password = c.getString("passwordPer");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(passper.equals(password)){
                        String nameUser = null;
                        try {
                            nameUser = c.getString("namePer");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        one_play_editor.putString("user", userper);
                        one_play_editor.putString("nameUser", nameUser);
                        one_play_editor.putString("pass", passper);
                        one_play_editor.putString("market", marketuser);
                        one_play_editor.apply();

                        Intent uou = new Intent(Login.this, Pers.class);
                        startActivity(uou);
                        Login.this.finish();
                    }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("  All Json loge ", String.valueOf(error));
                            Toast.makeText(Login.this, "Username not exist",
                                    Toast.LENGTH_LONG).show();
                            // Log.e("VOLLEY", "ERROR");
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    String creds = String.format("%s:%s", "b", "b");
                    String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                    params.put("Authorization", auth);
                    return params;
                }
            };


            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }
    }



    ///GET Admin
    class BackgroundTaskGetAdmin extends AsyncTask<Void,Void,String>{

        String url;

        @Override
        protected void onPreExecute() {
            url = "http://46.100.60.180/behind/check_info_admin.php?user=" + useradmin; //check_info_admin
        }


        @Override
        protected String doInBackground(Void... args) {

            try {
                URL url2 = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING1 = bufferedReader.readLine())!= null ){
                    stringBuilder.append(JSON_STRING1+"\n");

                }


                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            //result

//if successful

            if (result == "Admin not exist"){
                Toast.makeText(Login.this, "Admin not exist",
                        Toast.LENGTH_LONG).show();
            }else{



                JSONArray students = null;
                try {
                    students = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // looping through All Students
                JSONObject c = null;
                try {
                    c = students.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String password = null;
                try {
                    password = c.getString("password");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(passper == password){
                    //if successful
                    one_play_editor.putString("user", useradmin);
                    one_play_editor.putString("pass", passper);
                    one_play_editor.apply();

                    Intent uou = new Intent(Login.this, Admin.class);
                    startActivity(uou);
                    Login.this.finish();
                }
            }


        }

    }


    public void GetAdmin() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            String url = "http://46.100.60.180/behind/check_info_admin.php?user=" + useradmin;

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    //VolleyLog.v("Response:%n %s", response.toString(4));

                    Log.i("e", String.valueOf(response));

                        // looping through All Students
                        JSONObject c = null;
                        try {
                            c = response.getJSONObject(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String password = null;
                        try {
                            password = c.getString("password");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(passadmin.equals(password)){
                            //if successful
                            one_play_editor.putString("user", useradmin);
                            one_play_editor.putString("pass", passadmin);
                            one_play_editor.apply();

                            Intent uou = new Intent(Login.this, Admin.class);
                            startActivity(uou);
                            Login.this.finish();
                        }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("  All Json loge ", String.valueOf(error));
                            Toast.makeText(Login.this, "Admin not exist",
                                    Toast.LENGTH_LONG).show();
                            // Log.e("VOLLEY", "ERROR");
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    String creds = String.format("%s:%s", "b", "b");
                    String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                    params.put("Authorization", auth);
                    return params;
                }
            };


            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }
    }



    ///GET Market
    class BackgroundTaskGetMarket extends AsyncTask<Void,Void,String>{

        String url;

        @Override
        protected void onPreExecute() {
            url = "http://46.100.60.180/behind/get_info_market.php" ; //get_info_market
        }


        @Override
        protected String doInBackground(Void... args) {

            try {
                URL url2 = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING2 = bufferedReader.readLine())!= null ){
                    stringBuilder.append(JSON_STRING2+"\n");

                }


                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            //result

            Log.i("e","test");
            if (result == null){
                Log.i("e","khali");
            }else {
                Log.i("e",result);
            }

            JSONArray students = null;
            try {
                students = new JSONArray(result);
            } catch (JSONException e) {
                Log.e("e",result);
                e.printStackTrace();
            }

            for (int i = 0; i < students.length(); i++){

                JSONObject c = null;
                try {
                    c = students.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String market = null;
                try {
                    market = c.getString("nameMarket");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                COUNTRIES[i] = market;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Login.this,
                    android.R.layout.simple_dropdown_item_1line, COUNTRIES);
            edit1.setAdapter(adapter);

        }

    }


    public void GetMarket() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            String url = "http://46.100.60.180/behind/get_info_market.php";

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    //VolleyLog.v("Response:%n %s", response.toString(4));

                    Log.i("e", String.valueOf(response));
                    tedat = response.length();
                    COUNTRIES = new String[response.length()];
                    for (int i = 0; i < response.length(); i++){

                        JSONObject c = null;
                        try {
                            c = response.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        String market = null;
                        try {
                            market = c.getString("nameMarket");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.i(String.valueOf(i), market);
                        if (market != null){
                            COUNTRIES[i] = market;
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Login.this,
                            android.R.layout.simple_dropdown_item_1line, COUNTRIES);
                    edit1.setAdapter(adapter);


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("  All Json loge ", String.valueOf(error));
                            // Log.e("VOLLEY", "ERROR");
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    String creds = String.format("%s:%s", "b", "b");
                    String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                    params.put("Authorization", auth);
                    return params;
                }
            };


            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        try {
            checkPermissions();
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }



        // Get Save
        one_play_preferences = getApplicationContext().getSharedPreferences("Behina", android.content.Context.MODE_PRIVATE);
        one_play_editor = one_play_preferences.edit();
        user = one_play_preferences.getString("user", "");
        pass = one_play_preferences.getString("pass", "");
        market = one_play_preferences.getString("market", "");

        RelativeLayout rl1 = findViewById(R.id.rl2);
        User = (EditText) findViewById(R.id.et11);
        Pass = (EditText) findViewById(R.id.et22);
        edit1 = (AutoCompleteTextView) findViewById(R.id.et33);
        btn1 = (TextView) findViewById(R.id.tx2);
        btn2 = (TextView) findViewById(R.id.tx3);
        aa = (AwesomeTextInputLayout) findViewById(R.id.et1);

        User.setText(user);
        Pass.setText(pass);
        edit1.setText(market);

        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate2)
                .onRelativeLayout(rl1)
                .setTransitionDuration(3000)
                .start();

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnected()){
            // connected
            GetMarket();
        }else {
            Toast.makeText(Login.this, "خطا در ارتباطات",
                    Toast.LENGTH_LONG).show();
        }

        /**ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        edit1.setAdapter(adapter);*/

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("EditText", User.getText().toString());
                String temp = User.getText().toString();
                userper = temp;
                if (temp.length() < 3) {

                    Log.e("EditText2", Pass.getText().toString());


                    Toast.makeText(Login.this, "لطفا نام کاربری را وارد کنید",
                            Toast.LENGTH_LONG).show();

                } else {

                    Log.e("EditText2", Pass.getText().toString());
                    String temp2 = Pass.getText().toString();
                    passper = temp2;
                    if (temp2.length() < 3) {

                        Log.e("EditText3", edit1.getText().toString());

                        Toast.makeText(Login.this, "لطفا رمزعبور را وارد کنید",
                                Toast.LENGTH_LONG).show();

                    } else {

                        Log.e("EditText3", edit1.getText().toString());
                        String temp3 = edit1.getText().toString();
                        marketuser = temp3;
                        if (temp3.length() < 3) {

                            Toast.makeText(Login.this, "لطفا بازار را انتخاب کنید", Toast.LENGTH_LONG).show();

                        } else {

                            boolean market = false;
                            for(int i = 0; i < tedat; i++){
                                if (marketuser.equals(COUNTRIES[i])){
                                    market = true;
                                }
                            }
                            if (market){
                                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                                if (networkInfo!=null && networkInfo.isConnected()){
                                    // connected
                                    GetUser();
                                }else {
                                    Toast.makeText(Login.this, "خطا در ارتباطات",
                                            Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(Login.this, "بازار وارد شده اشتباه است",
                                        Toast.LENGTH_LONG).show();
                            }


                        }

                    }

                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = User.getText().toString();
                useradmin = temp;
                if (temp.length() < 3) {
                    Toast.makeText(Login.this, "لطفا نام کاربری را وارد کنید",
                            Toast.LENGTH_LONG).show();
                } else {

                    String temp2 = Pass.getText().toString();
                    passadmin = temp2;
                    if (temp2.length() < 3) {


                        Toast.makeText(Login.this, "لطفا رمزعبور را وارد کنید",
                                Toast.LENGTH_LONG).show();

                    } else {

                        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                        if (networkInfo!=null && networkInfo.isConnected()){
                            // connected
                            GetAdmin();
                        }else {
                            Toast.makeText(Login.this, "خطا در ارتباطات",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                }
            }
        });



    }

    String[] COUNTRIES;

    private void checkPermissions() {

        String[] per = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.RECEIVE_BOOT_COMPLETED, android.Manifest.permission.ACCESS_FINE_LOCATION};

        new PermissionHandler().checkPermission(this, per, new PermissionHandler.OnPermissionResponse() {
            @Override
            public void onPermissionGranted() {
                // permission granted
                // your code
            }

            @Override
            public void onPermissionDenied() {
                // User canceled permission
                Toast.makeText(Login.this, "در صورت نپذیرفتن درخواست ها برنامه با مشکل مواجه می شود!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent("PERMISSION_RECEIVER");
        intent.putExtra("requestCode", requestCode);
        intent.putExtra("permissions", permissions);
        intent.putExtra("grantResults", grantResults);
        sendBroadcast(intent);
    }




}
