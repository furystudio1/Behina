package com.fury.behina;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fury.behina.views.FlowingGradientClass;
import com.google.gson.JsonObject;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ir.androidexception.andexalertdialog.AndExAlertDialog;
import ir.androidexception.andexalertdialog.AndExAlertDialogListener;
import ir.androidexception.andexalertdialog.Font;

public class Pers extends Activity {

    EditText et1,et2;
    TextView btn3,tx1,female,male;

    String user,nameUser,market,number,msg,nameb,pass,time,gen;
    String JSON_STRING,JSON_STRING2;
    SharedPreferences one_play_preferences;
    SharedPreferences.Editor one_play_editor;

    ///POST
    class BackgroundTaskBuyer extends AsyncTask<String,Void,String> {

        String url;

        @Override
        protected void onPreExecute() {
            url = "http://46.100.60.180/behind/add_info_buyer.php" ; //add_info_buyer
        }


        @Override
        protected String doInBackground(String... args) {
            String str1,str2,str3,str4,str5,str6;
            str1 = args[0];
            str2 = args[1];
            str3 = args[2];
            str4 = args[3];
            str5 = args[4];
            str6 = args[5];

            try {
                URL url2 = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("nameBuyer","UTF-8")+"="+URLEncoder.encode(str1,"UTF-8")+"&"+
                        URLEncoder.encode("numberBuyer","UTF-8")+"="+URLEncoder.encode(str2,"UTF-8")+"&"+
                        URLEncoder.encode("perBuyer","UTF-8")+"="+URLEncoder.encode(str3,"UTF-8")+"&"+
                        URLEncoder.encode("userPer","UTF-8")+"="+URLEncoder.encode(str4,"UTF-8")+"&"+
                        URLEncoder.encode("market","UTF-8")+"="+URLEncoder.encode(str5,"UTF-8")+"&"+
                        URLEncoder.encode("Date","UTF-8")+"="+URLEncoder.encode(str6,"UTF-8");
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

            if (result == "success..."){

                msg = " ";
                //sms send from here
                BackgroundTaskSMS backgroundTask = new BackgroundTaskSMS();
                backgroundTask.execute(number,msg,nameb);

            }else {

                Toast.makeText(Pers.this, "خطا در ارتباطات",
                        Toast.LENGTH_LONG).show();
            }

        }

    }


    public void Buyer(String str1,String str2,String str3,String str4,String str5,String str6) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Pers.this);
            String url = "http://46.100.60.180/behind/add_info_buyer.php" ;

            String data = URLEncoder.encode("nameBuyer","UTF-8")+"="+URLEncoder.encode(str1,"UTF-8")+"&"+
                    URLEncoder.encode("numberBuyer","UTF-8")+"="+URLEncoder.encode(str2,"UTF-8")+"&"+
                    URLEncoder.encode("perBuyer","UTF-8")+"="+URLEncoder.encode(str3,"UTF-8")+"&"+
                    URLEncoder.encode("userPer","UTF-8")+"="+URLEncoder.encode(str4,"UTF-8")+"&"+
                    URLEncoder.encode("market","UTF-8")+"="+URLEncoder.encode(str5,"UTF-8")+"&"+
                    URLEncoder.encode("Date","UTF-8")+"="+URLEncoder.encode(str6,"UTF-8");

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("nameBuyer", str1);
            params.put("numberBuyer", str2);
            params.put("perBuyer", str3);
            params.put("userPer", str4);
            params.put("market", str5);
            params.put("Date", str6);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    //VolleyLog.v("Response:%n %s", response.toString(4));

                    Log.i("e", String.valueOf(response));

                    String allB = null;
                    try {
                        allB = response.getString("succses");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (allB.equals("ok")){
                        //sms send from here
                        SMS(number,msg,nameb);
                    }


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
                    params.put("nameBuyer", str1);
                    params.put("numberBuyer", str2);
                    params.put("perBuyer", str3);
                    params.put("userPer", str4);
                    params.put("market", str5);
                    params.put("Date", str6);
                    return params;
                }
            };


            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }
    }




    ///SMS
    class BackgroundTaskSMS extends AsyncTask<String,Void,String> {

        String url;

        @Override
        protected void onPreExecute() {
            url = "http://46.100.60.180/behind/sendSms_OneToMany.php" ; //add_info_buyer
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
                String data = URLEncoder.encode("number","UTF-8")+"="+URLEncoder.encode(str1,"UTF-8")+"&"+
                        URLEncoder.encode("msg","UTF-8")+"="+URLEncoder.encode(str2,"UTF-8")+"&"+
                        URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(str3,"UTF-8");
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

            if (result == "success..."){

                et1.getText().clear();
                et2.getText().clear();

                new AndExAlertDialog.Builder(Pers.this)
                        .setTitle("تایید ارسال")
                        .setMessage("با موفقیت ارسال شد")
                        .setPositiveBtnText("بستن")
                        .setNegativeBtnText("")
                        .setImage(R.drawable.confirm,15)
                        .setFont(Font.IRAN_SANS)
                        .setCancelableOnTouchOutside(true)
                        .OnPositiveClicked(new AndExAlertDialogListener() {
                            @Override
                            public void OnClick(String input) {
                            }
                        })
                        .OnNegativeClicked(new AndExAlertDialogListener() {
                            @Override
                            public void OnClick(String input) {
                            }
                        })
                        .build();

            }else {

                Toast.makeText(Pers.this, "خطا در ارتباطات",
                        Toast.LENGTH_LONG).show();
            }

        }

    }

    public void SMS(String str1,String str2,String str3) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Pers.this);
            String url = "http://46.100.60.180/behind/sendSms_OneToMany.php" ;

            String data = URLEncoder.encode("number","UTF-8")+"="+URLEncoder.encode(str1,"UTF-8")+"&"+
                    URLEncoder.encode("msg","UTF-8")+"="+URLEncoder.encode(str2,"UTF-8")+"&"+
                    URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(str3,"UTF-8");

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("number", str1);
            params.put("msg", str2);
            params.put("name", str3);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    //VolleyLog.v("Response:%n %s", response.toString(4));

                    Log.i("e", String.valueOf(response));

                    String allB = null;
                    try {
                        allB = response.getString("succses");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (allB.equals("ok")){
                        //sms send from here
                        et1.getText().clear();
                        et2.getText().clear();

                        new AndExAlertDialog.Builder(Pers.this)
                                .setTitle("تایید ارسال")
                                .setMessage("با موفقیت ارسال شد")
                                .setPositiveBtnText("بستن")
                                .setNegativeBtnText("")
                                .setImage(R.drawable.confirm,15)
                                .setFont(Font.IRAN_SANS)
                                .setCancelableOnTouchOutside(true)
                                .OnPositiveClicked(new AndExAlertDialogListener() {
                                    @Override
                                    public void OnClick(String input) {
                                    }
                                })
                                .OnNegativeClicked(new AndExAlertDialogListener() {
                                    @Override
                                    public void OnClick(String input) {
                                    }
                                })
                                .build();
                    }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Log.e("  All Json loge ", String.valueOf(error));
                             Log.e("VOLLEY", "ERROR");
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("number", str1);
                    params.put("msg", str2);
                    params.put("name", str3);
                    return params;
                }
            };


            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }
    }



    ///GET market day
    class BackgroundTaskGetMarket extends AsyncTask<Void,Void,String>{

        String url;

        @Override
        protected void onPreExecute() {
            url = "http://46.100.60.180/behind/get_info_market2.php?nameMarket=" + market; //get_info_market2
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


            String number = null;
            try {
                number = c.getString("allB");
            } catch (JSONException e) {
                e.printStackTrace();
            }

//if successful
            int num;
            if (number == ""){
                num = 0;
            }else{
                num = Integer.parseInt(number);
            }
            num = num + 1;

            BackgroundTaskMarket backgroundTask = new BackgroundTaskMarket();
            backgroundTask.execute(market, String.valueOf(num),time);

        }

    }


    public void GetMarket() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Pers.this);
            String url = "http://46.100.60.180/behind/get_info_market2.php?nameMarket=" + market;

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    //VolleyLog.v("Response:%n %s", response.toString(4));

                    Log.i("e", String.valueOf(response));

                    JSONObject c = null;
                    try {
                        c = response.getJSONObject(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    String number = null;
                    try {
                        number = c.getString("allB");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//if successful
                    int num;
                    if (number.equals("")){
                        num = 0;
                    }else{
                        num = Integer.parseInt(number);
                    }
                    num = num + 1;

                    Market(market, String.valueOf(num),time);


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



    public void GetMSG() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Pers.this);
            String url = "http://46.100.60.180/behind/get_msg.php?int=1";

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    //VolleyLog.v("Response:%n %s", response.toString(4));

                    Log.i("e", String.valueOf(response));

                    JSONObject c = null;
                    try {
                        c = response.getJSONObject(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    String number = null;
                    try {
                        number = c.getString("massage");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//if successful
                    msg = number;

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



    ///GET user day
    class BackgroundTaskGetUser extends AsyncTask<Void,Void,String>{

        String url;

        @Override
        protected void onPreExecute() {
            url = "http://46.100.60.180/behind/get_info_user.php?userPer=" + user; //get_info_user
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


            String allB = null;
            try {
                allB = c.getString("allB");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String all24 = null;
            try {
                all24 = c.getString("all24");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String day = null;
            try {
                day = c.getString("day");
            } catch (JSONException e) {
                e.printStackTrace();
            }


//if successful
            int num;
            if (allB == ""){
                num = 0;
            }else {
                num = Integer.parseInt(allB);
            }
            num = num + 1;

            int num24;
            if (all24 == ""){
                num24 = 0;
            }else {
                num24 = Integer.parseInt(all24);
            }
            num24 = num24 + 1;

            String currentDate = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
            if (currentDate == day){
                //save
                User(user, String.valueOf(num), String.valueOf(num24),currentDate,pass,nameUser);
            }else {
                int numday = 1;
                //save
                User(user, String.valueOf(num), String.valueOf(numday),currentDate,pass,nameUser);
            }


        }

    }


    public void GetUser() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Pers.this);
            String url = "http://46.100.60.180/behind/get_info_user.php?userPer=" + user;

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    //VolleyLog.v("Response:%n %s", response.toString(4));

                    Log.i("e", String.valueOf(response));

                    JSONObject c = null;
                    try {
                        c = response.getJSONObject(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    String allB = null;
                    try {
                        allB = c.getString("allB");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String all24 = null;
                    try {
                        all24 = c.getString("all24");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String day = null;
                    try {
                        day = c.getString("day");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


//if successful
                    int num;
                    if (allB.equals("")){
                        num = 0;
                    }else {
                        num = Integer.parseInt(allB);
                    }
                    num = num + 1;

                    int num24;
                    if (all24.equals("")){
                        num24 = 0;
                    }else {
                        num24 = Integer.parseInt(all24);
                    }
                    num24 = num24 + 1;

                    Log.i("num24", String.valueOf(num24));
                    Log.i("num", String.valueOf(num));
                    String currentDate = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
                    Log.i("currentDate", currentDate);
                    if (currentDate.equals(day)){
                        //save

                        User(user, String.valueOf(num), String.valueOf(num24),currentDate,pass,nameUser);
                    }else {
                        int numday = 1;
                        //save

                        User(user, String.valueOf(num), String.valueOf(numday),currentDate,pass,nameUser);
                    }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("  All Json loge ", String.valueOf(error));
                            Toast.makeText(Pers.this, "خطا در ارتباطات",
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





    ///POST update user
    class BackgroundTaskUser extends AsyncTask<String,Void,String> {

        String url;

        @Override
        protected void onPreExecute() {
            url = "http://46.100.60.180/behind/update_info_user.php" ; //update_info_user
        }


        @Override
        protected String doInBackground(String... args) {
            String str1,str2,str3,str4,str5,str6;
            str1 = args[0];
            str2 = args[1];
            str3 = args[2];
            str4 = args[3];
            str5 = args[4];
            str6 = args[5];

            try {
                URL url2 = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("userPer","UTF-8")+"="+URLEncoder.encode(str1,"UTF-8")+"&"+
                        URLEncoder.encode("allB","UTF-8")+"="+URLEncoder.encode(str2,"UTF-8")+"&"+
                        URLEncoder.encode("all24","UTF-8")+"="+URLEncoder.encode(str3,"UTF-8")+"&"+
                        URLEncoder.encode("passwordPer","UTF-8")+"="+URLEncoder.encode(str5,"UTF-8")+"&"+
                        URLEncoder.encode("namePer","UTF-8")+"="+URLEncoder.encode(str6,"UTF-8")+"&"+
                        URLEncoder.encode("day","UTF-8")+"="+URLEncoder.encode(str4,"UTF-8");
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

            if (result == "success..."){

                //sms send from here


            }else {

                Toast.makeText(Pers.this, "خطا در ارتباطات",
                        Toast.LENGTH_LONG).show();
            }

        }

    }


    public void User(String str1,String str2,String str3,String str4,String str5,String str6) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Pers.this);
            String url = "http://46.100.60.180/behind/update_info_user.php" ;

            String data = URLEncoder.encode("userPer","UTF-8")+"="+URLEncoder.encode(str1,"UTF-8")+"&"+
                    URLEncoder.encode("allB","UTF-8")+"="+URLEncoder.encode(str2,"UTF-8")+"&"+
                    URLEncoder.encode("all24","UTF-8")+"="+URLEncoder.encode(str3,"UTF-8")+"&"+
                    URLEncoder.encode("passwordPer","UTF-8")+"="+URLEncoder.encode(str5,"UTF-8")+"&"+
                    URLEncoder.encode("namePer","UTF-8")+"="+URLEncoder.encode(str6,"UTF-8")+"&"+
                    URLEncoder.encode("day","UTF-8")+"="+URLEncoder.encode(str4,"UTF-8");

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("userPer", str1);
            params.put("allB", str2);
            params.put("all24", str3);
            params.put("passwordPer", str5);
            params.put("namePer", str6);
            params.put("day", str4);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    //VolleyLog.v("Response:%n %s", response.toString(4));

                    Log.i("e", String.valueOf(response));

                    String allB = null;
                    try {
                        allB = response.getString("succses");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (allB.equals("ok")){
                        //
                    }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("  All Json loge ", String.valueOf(error));
                            Toast.makeText(Pers.this, "خطا در ارتباطات",
                                    Toast.LENGTH_LONG).show();
                            // Log.e("VOLLEY", "ERROR");
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("userPer", str1);
                    params.put("allB", str2);
                    params.put("all24", str3);
                    params.put("passwordPer", str4);
                    params.put("namePer", str5);
                    params.put("day", str6);
                    return params;
                }
            };


            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }
    }



    ///POST update market
    class BackgroundTaskMarket extends AsyncTask<String,Void,String> {

        String url;

        @Override
        protected void onPreExecute() {
            url = "http://46.100.60.180/behind/update_info_market.php" ; //update_info_market
        }


        @Override
        protected String doInBackground(String... args) {
            String str1,str2,str3;
            str1 = args[0];
            str2 = args[1];
            str3 = args[1];

            try {
                URL url2 = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("nameMarket","UTF-8")+"="+URLEncoder.encode(str1,"UTF-8")+"&"+
                        URLEncoder.encode("allB","UTF-8")+"="+URLEncoder.encode(str2,"UTF-8")+"&"+
                        URLEncoder.encode("lastTime","UTF-8")+"="+URLEncoder.encode(str3,"UTF-8");
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

            if (result == "success..."){

                //sms send from here


            }else {

                Toast.makeText(Pers.this, "خطا در ارتباطات",
                        Toast.LENGTH_LONG).show();
            }

        }

    }


    public void Market(String str1,String str2,String str3) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Pers.this);
            String url = "http://46.100.60.180/behind/update_info_market.php" ;

            String data = URLEncoder.encode("nameMarket","UTF-8")+"="+URLEncoder.encode(str1,"UTF-8")+"&"+
                    URLEncoder.encode("allB","UTF-8")+"="+URLEncoder.encode(str2,"UTF-8")+"&"+
                    URLEncoder.encode("lastTime","UTF-8")+"="+URLEncoder.encode(str3,"UTF-8");

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("nameMarket", str1);
            params.put("allB", str2);
            params.put("lastTime", str3);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    //VolleyLog.v("Response:%n %s", response.toString(4));

                    Log.i("e", String.valueOf(response));


                    String allB = null;
                    try {
                        allB = response.getString("succses");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (allB.equals("ok")){
                        //
                    }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("  All Json loge ", String.valueOf(error));
                            Toast.makeText(Pers.this, "خطا در ارتباطات",
                                    Toast.LENGTH_LONG).show();
                            // Log.e("VOLLEY", "ERROR");
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("nameMarket", str1);
                    params.put("allB", str2);
                    params.put("lastTime", str3);
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
        setContentView(R.layout.per);


        one_play_preferences = getApplicationContext().getSharedPreferences("Behina", android.content.Context.MODE_PRIVATE);
        one_play_editor = one_play_preferences.edit();
        user = one_play_preferences.getString("user", "");
        nameUser = one_play_preferences.getString("nameUser", "");
        pass = one_play_preferences.getString("pass", "");
        market = one_play_preferences.getString("market", "");


        RelativeLayout rl1 = findViewById(R.id.rl6);
        et1 = findViewById(R.id.et44);
        et2 = findViewById(R.id.et55);
        btn3 = findViewById(R.id.btn3);
        tx1 = findViewById(R.id.tnp);
        female = findViewById(R.id.female);
        male = findViewById(R.id.male);

        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate3)
                .onRelativeLayout(rl1)
                .setTransitionDuration(5000)
                .start();

        //get name user
        tx1.setText(nameUser +" "+ "خوش آمدید");

        GetMSG();

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setBackgroundResource(R.drawable.original_state);
                male.setBackgroundResource(R.drawable.original_state4);
                gen = " سركار خانم ";
            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setBackgroundResource(R.drawable.original_state);
                female.setBackgroundResource(R.drawable.original_state4);
                gen = " جناب آقاي ";
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = et1.getText().toString();
                nameb = gen + temp;
                if (temp.length() < 3){

                    Toast.makeText(Pers.this, "لطفا نام مشتری را وارد کنید",
                            Toast.LENGTH_LONG).show();


                }else {
                    String temp2 = et2.getText().toString();
                    number = temp2;
                    if (temp2.length() < 3){

                        Toast.makeText(Pers.this, "لطفا شماره تماس مشتری را وارد کنید",
                                Toast.LENGTH_LONG).show();

                    }else{

                        Utility util = new Utility();
                        String ywd = util.getCurrentShamsidate();
                        String currentDateandTime = new SimpleDateFormat("HH:mm:ss").format(new Date());

                        //Toast.makeText(Pers.this, ywd +"_"+ currentDateandTime,Toast.LENGTH_LONG).show();

                        //save successful
                        time = ywd +"_"+ currentDateandTime;
                        Buyer(temp,temp2,user,nameUser,market,ywd +"_"+ currentDateandTime);

                        GetMarket();

                        GetUser();

                        /**if(){

                         }else {

                         }*/

                    }
                }
            }
        });

    }
}
