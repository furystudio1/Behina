package com.fury.behina;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fury.behina.views.FlowingGradientClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import github.com.st235.lib_expandablebottombar.ExpandableBottomBar;
import github.com.st235.lib_expandablebottombar.Menu;
import github.com.st235.lib_expandablebottombar.MenuItem;
import github.com.st235.lib_expandablebottombar.MenuItemDescriptor;
import ir.androidexception.andexalertdialog.AndExAlertDialog;
import ir.androidexception.andexalertdialog.AndExAlertDialogListener;
import ir.androidexception.andexalertdialog.Font;
import ir.androidexception.andexalertdialog.InputType;
import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

public class Admin extends Activity {

    int pos = 0;
    ImageView implus,implus2;
    String nameUser,UserUser,PassUser;
    String JSON_STRING,JSON_STRING1,JSON_STRING2,msg;

    List<Bean> listdata = new ArrayList<>();
    List<Bean2> listdata2 = new ArrayList<>();
    List<Bean3> listdata3 = new ArrayList<>();

    ///POST market
    class BackgroundTaskMarket extends AsyncTask<String,Void,String> {

        String url;

        @Override
        protected void onPreExecute() {
            url = "http://46.100.60.180/behind/add_info_market.php" ; //add_info_market
        }


        @Override
        protected String doInBackground(String... args) {
            String str1;
            str1 = args[0];

            try {
                URL url2 = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("namemarket","UTF-8")+"="+URLEncoder.encode(str1,"UTF-8");
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

                new AndExAlertDialog.Builder(Admin.this)
                        .setTitle("تایید دخیره")
                        .setMessage("با موفقیت ثبت شد")
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

                Toast.makeText(Admin.this, "خطا در ارتباطات",
                        Toast.LENGTH_LONG).show();
            }

        }

    }

    public void Market(String str1) {
        try {
            Log.i("str1", String.valueOf(str1));
            RequestQueue requestQueue = Volley.newRequestQueue(Admin.this);
            String url = "http://46.100.60.180/behind/add_info_market.php" ;

            String data = URLEncoder.encode("namemarket","UTF-8")+"="+URLEncoder.encode(str1,"UTF-8");

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("namemarket", str1);

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
                        new AndExAlertDialog.Builder(Admin.this)
                                .setTitle("تایید دخیره")
                                .setMessage("با موفقیت ثبت شد")
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
                            Log.e("  All Json loge ", String.valueOf(error));
                            Toast.makeText(Admin.this, "خطا در ارتباطات",
                                    Toast.LENGTH_LONG).show();
                            // Log.e("VOLLEY", "ERROR");
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("userPer", str1);
                    return params;
                }
            };


            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }
    }



    ///POST user
    class BackgroundTaskUser extends AsyncTask<String,Void,String> {

        String url;

        @Override
        protected void onPreExecute() {
            url = "http://46.100.60.180/behind/add_info_user.php" ; //add_info_user
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
                String data = URLEncoder.encode("userPer","UTF-8")+"="+URLEncoder.encode(str1,"UTF-8")+"&"+
                        URLEncoder.encode("passwordPer","UTF-8")+"="+URLEncoder.encode(str2,"UTF-8")+"&"+
                        URLEncoder.encode("namePer","UTF-8")+"="+URLEncoder.encode(str3,"UTF-8");
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

                new AndExAlertDialog.Builder(Admin.this)
                        .setTitle("تایید دخیره")
                        .setMessage("با موفقیت ثبت شد")
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

                Toast.makeText(Admin.this, "خطا در ارتباطات",
                        Toast.LENGTH_LONG).show();
            }

        }

    }

    public void User(String str1,String str2,String str3) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Admin.this);
            String url = "http://46.100.60.180/behind/add_info_user.php" ;

            String data = URLEncoder.encode("userPer","UTF-8")+"="+URLEncoder.encode(str1,"UTF-8")+"&"+
                    URLEncoder.encode("passwordPer","UTF-8")+"="+URLEncoder.encode(str2,"UTF-8")+"&"+
                    URLEncoder.encode("namePer","UTF-8")+"="+URLEncoder.encode(str3,"UTF-8");

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("userPer", str1);
            params.put("passwordPer", str2);
            params.put("namePer", str3);

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
                        new AndExAlertDialog.Builder(Admin.this)
                                .setTitle("تایید دخیره")
                                .setMessage("با موفقیت ثبت شد")
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
                            Log.e("  All Json loge ", String.valueOf(error));
                            Toast.makeText(Admin.this, "خطا در ارتباطات",
                                    Toast.LENGTH_LONG).show();
                            // Log.e("VOLLEY", "ERROR");
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("userPer", str1);
                    return params;
                }
            };


            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }
    }



    ///GET market
    class BackgroundTaskGetMarket extends AsyncTask<Void,Void,String>{

        String url;

        @Override
        protected void onPreExecute() {
            url = "http://46.100.60.180/behind/get_info_market3.php"; //get_info_market3
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
            //json read
//if successful

            String[] market = new String[0];
            String[] allM = new String[0];
            String[] lastTime = new String[0];

            JSONArray students = null;
            try {
                students = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            for (int i = 0; i < students.length(); i++){

                JSONObject c = null;
                try {
                    c = students.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String market2 = null;
                String allM2 = null;
                String lastTime2 = null;
                try {
                    market2 = c.getString("nameMarket");
                    allM2 = c.getString("allB");
                    lastTime2 = c.getString("lastTime");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                market[i] = market2;
                allM[i] = allM2;
                lastTime[i] = lastTime2;
            }


            //after read json
            DataTable dataTable = findViewById(R.id.data_table);

            Typeface tf = Typeface.createFromAsset(getAssets(),"iran_sans.ttf");
            DataTableHeader header = new DataTableHeader.Builder()
                    .item("نام بازار", 2)
                    .item("تعداد ثبت شده", 3)
                    .item("اخرین ثبت", 2)
                    .build();

            ArrayList<DataTableRow> rows = new ArrayList<>();
            for(int i=0; i<= market.length ;i++) {
                DataTableRow row = new DataTableRow.Builder()
                        .value(market[i])
                        .value(allM[i])
                        .value(lastTime[i])
                        .build();
                rows.add(row);

                listdata3.add(new Bean3(market[i],allM[i],lastTime[i]));

            }

            dataTable.setTypeface(tf);
            dataTable.setHeader(header);
            dataTable.setRows(rows);
            dataTable.inflate(Admin.this);


        }

    }

    public void GetMarket() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Admin.this);
            String url = "http://46.100.60.180/behind/get_info_market3.php" ;

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    //VolleyLog.v("Response:%n %s", response.toString(4));

                    Log.i("e", String.valueOf(response));

                    //after read json
                    DataTable dataTable = findViewById(R.id.data_table);

                    Typeface tf = Typeface.createFromAsset(getAssets(),"iran_sans.ttf");
                    DataTableHeader header = new DataTableHeader.Builder()
                            .item("نام بازار", 3)
                            .item("تعداد ثبت شده", 2)
                            .item("اخرین ثبت", 3)
                            .build();

                    ArrayList<DataTableRow> rows = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++){

                        JSONObject c = null;
                        try {
                            c = response.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        String market2 = null;
                        String allM2 = null;
                        String lastTime2 = null;
                        try {
                            market2 = c.getString("nameMarket");
                            allM2 = c.getString("allB");
                            lastTime2 = c.getString("lastTime");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        DataTableRow row = new DataTableRow.Builder()
                                .value(market2)
                                .value(allM2)
                                .value(lastTime2)
                                .build();
                        rows.add(row);

                        listdata3.add(new Bean3(market2,allM2,lastTime2));
                    }

                    dataTable.setTypeface(tf);
                    dataTable.setHeader(header);
                    dataTable.setRows(rows);
                    dataTable.inflate(Admin.this);


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("  All Json loge ", String.valueOf(error));
                            Toast.makeText(Admin.this, "خطا در ارتباطات",
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



    ///GET buyer
    class BackgroundTaskGetBuyer extends AsyncTask<Void,Void,String>{

        String url;

        @Override
        protected void onPreExecute() {
            url = "http://46.100.60.180/behind/get_info_buyer.php"; //get_info_buyer
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
            //json read
//if successful

            String[] nameBuyer = new String[0];
            String[] numberBuyer = new String[0];
            String[] perBuyer = new String[0];
            String[] market = new String[0];
            String[] Date = new String[0];


            JSONArray students = null;
            try {
                students = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            for (int i = 0; i < students.length(); i++){

                JSONObject c = null;
                try {
                    c = students.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String market2 = null;
                String nameBuyer2 = null;
                String numberBuyer2 = null;
                String perBuyer2 = null;
                String Date2 = null;
                try {
                    nameBuyer2 = c.getString("nameBuyer");
                    numberBuyer2 = c.getString("numberBuyer");
                    perBuyer2 = c.getString("perBuyer");
                    market2 = c.getString("market");
                    Date2 = c.getString("Date");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                nameBuyer[i] = nameBuyer2;
                numberBuyer[i] = numberBuyer2;
                perBuyer[i] = perBuyer2;
                market[i] = market2;
                Date[i] = Date2;
            }


            //after read json
            DataTable dataTable = findViewById(R.id.data_table);

            Typeface tf = Typeface.createFromAsset(getAssets(),"iran_sans.ttf");
            DataTableHeader header = new DataTableHeader.Builder()
                    .item("نام مشتری", 3)
                    .item("شماره مشتری", 3)
                    .item("ثبت کننده", 2)
                    .item("بازار", 2)
                    .item("تاریخ", 2)
                    .build();

            ArrayList<DataTableRow> rows = new ArrayList<>();
            for(int i=0;i<=nameBuyer.length;i++) {
                DataTableRow row = new DataTableRow.Builder()
                        .value(nameBuyer[i])
                        .value(numberBuyer[i])
                        .value(perBuyer[i])
                        .value(market[i])
                        .value(Date[i])
                        .build();
                rows.add(row);

                listdata2.add(new Bean2(nameBuyer[i],numberBuyer[i],perBuyer[i],market[i],Date[i]));

            }

            dataTable.setTypeface(tf);
            dataTable.setHeader(header);
            dataTable.setRows(rows);
            dataTable.inflate(Admin.this);


        }

    }

    public void GetBuyer() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Admin.this);
            String url = "http://46.100.60.180/behind/get_info_buyer.php" ;

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    //VolleyLog.v("Response:%n %s", response.toString(4));

                    Log.i("e", String.valueOf(response));

                    //after read json
                    DataTable dataTable = findViewById(R.id.data_table);

                    Typeface tf = Typeface.createFromAsset(getAssets(),"iran_sans.ttf");
                    DataTableHeader header = new DataTableHeader.Builder()
                            .item("نام مشتری", 3)
                            .item("شماره مشتری", 3)
                            .item("ثبت کننده", 2)
                            .item("بازار", 2)
                            .item("تاریخ", 2)
                            .build();

                    ArrayList<DataTableRow> rows = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++){

                        JSONObject c = null;
                        try {
                            c = response.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        String market2 = null;
                        String nameBuyer2 = null;
                        String numberBuyer2 = null;
                        String perBuyer2 = null;
                        String Date2 = null;
                        try {
                            nameBuyer2 = c.getString("nameBuyer");
                            numberBuyer2 = c.getString("numberBuyer");
                            perBuyer2 = c.getString("perBuyer");
                            market2 = c.getString("market");
                            Date2 = c.getString("Date");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        DataTableRow row = new DataTableRow.Builder()
                                .value(nameBuyer2)
                                .value(numberBuyer2)
                                .value(perBuyer2)
                                .value(market2)
                                .value(Date2)
                                .build();
                        rows.add(row);

                        listdata2.add(new Bean2(nameBuyer2,numberBuyer2,perBuyer2,market2,Date2));

                    }

                    dataTable.setTypeface(tf);
                    dataTable.setHeader(header);
                    dataTable.setRows(rows);
                    dataTable.inflate(Admin.this);


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("  All Json loge ", String.valueOf(error));
                            Toast.makeText(Admin.this, "خطا در ارتباطات",
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



    ///GET User
    class BackgroundTaskGetUser extends AsyncTask<Void,Void,String>{

        String url;

        @Override
        protected void onPreExecute() {
            url = "http://46.100.60.180/behind/get_info_user2.php"; //get_info_user2
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
            //json read
//if successful


            String[] nameBuyer = new String[0];
            String[] numberBuyer = new String[0];
            String[] perBuyer = new String[0];
            String[] market = new String[0];
            String[] Date = new String[0];


            JSONArray students = null;
            try {
                students = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            for (int i = 0; i < students.length(); i++){

                JSONObject c = null;
                try {
                    c = students.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String market2 = null;
                String nameBuyer2 = null;
                String numberBuyer2 = null;
                String perBuyer2 = null;
                String Date2 = null;
                try {
                    nameBuyer2 = c.getString("userPer");
                    numberBuyer2 = c.getString("passwordPer");
                    perBuyer2 = c.getString("namePer");
                    market2 = c.getString("allB");
                    Date2 = c.getString("all24");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                nameBuyer[i] = nameBuyer2;
                numberBuyer[i] = numberBuyer2;
                perBuyer[i] = perBuyer2;
                market[i] = market2;
                Date[i] = Date2;
            }


            //after read json
            DataTable dataTable = findViewById(R.id.data_table);

            Typeface tf = Typeface.createFromAsset(getAssets(),"iran_sans.ttf");
            DataTableHeader header = new DataTableHeader.Builder()
                    .item("نام کاربری", 3)
                    .item("رمز", 2)
                    .item("نام", 3)
                    .item("کل", 1)
                    .item("امروز", 1)
                    .build();

            ArrayList<DataTableRow> rows = new ArrayList<>();
            for(int i=0;i<=nameBuyer.length;i++) {
                DataTableRow row = new DataTableRow.Builder()
                        .value(nameBuyer[i])
                        .value(numberBuyer[i])
                        .value(perBuyer[i])
                        .value(market[i])
                        .value(Date[i])
                        .build();
                rows.add(row);

                listdata.add(new Bean(perBuyer[i],nameBuyer[i],numberBuyer[i],market[i],Date[i]));

            }

            dataTable.setTypeface(tf);
            dataTable.setHeader(header);
            dataTable.setRows(rows);
            dataTable.inflate(Admin.this);


        }

    }

    public void GetUser() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Admin.this);
            String url = "http://46.100.60.180/behind/get_info_user2.php" ;

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    //VolleyLog.v("Response:%n %s", response.toString(4));

                    Log.i("e", String.valueOf(response));

                    //after read json
                    DataTable dataTable = findViewById(R.id.data_table);

                    Typeface tf = Typeface.createFromAsset(getAssets(),"iran_sans.ttf");
                    DataTableHeader header = new DataTableHeader.Builder()
                            .item("نام کاربری", 2)
                            .item("رمز", 2)
                            .item("نام", 3)
                            .item("کل", 1)
                            .item("امروز", 1)
                            .build();

                    ArrayList<DataTableRow> rows = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++){

                        JSONObject c = null;
                        try {
                            c = response.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        String market2 = null;
                        String nameBuyer2 = null;
                        String numberBuyer2 = null;
                        String perBuyer2 = null;
                        String Date2 = null;
                        try {
                            nameBuyer2 = c.getString("userPer");
                            numberBuyer2 = c.getString("passwordPer");
                            perBuyer2 = c.getString("namePer");
                            market2 = c.getString("allB");
                            Date2 = c.getString("all24");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        DataTableRow row = new DataTableRow.Builder()
                                .value(nameBuyer2)
                                .value(numberBuyer2)
                                .value(perBuyer2)
                                .value(market2)
                                .value(Date2)
                                .build();
                        rows.add(row);

                        listdata.add(new Bean(perBuyer2,nameBuyer2,numberBuyer2,market2,Date2));
                    }

                    dataTable.setTypeface(tf);
                    dataTable.setHeader(header);
                    dataTable.setRows(rows);
                    dataTable.inflate(Admin.this);


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("  All Json loge ", String.valueOf(error));
                            Toast.makeText(Admin.this, "خطا در ارتباطات",
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



    public void MSG(String str1,String str2) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Admin.this);
            String url = "http://46.100.60.180/behind/update_msg.php" ;

            String data = URLEncoder.encode("int","UTF-8")+"="+URLEncoder.encode(str1,"UTF-8")+"&"+
                    URLEncoder.encode("massage","UTF-8")+"="+URLEncoder.encode(str2,"UTF-8");

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("int", str1);
            params.put("massage", str2);

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
                        new AndExAlertDialog.Builder(Admin.this)
                                .setTitle("تایید دخیره")
                                .setMessage("با موفقیت ثبت شد")
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
                            Log.e("  All Json loge ", String.valueOf(error));
                            Toast.makeText(Admin.this, "خطا در ارتباطات",
                                    Toast.LENGTH_LONG).show();
                            // Log.e("VOLLEY", "ERROR");
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("int", str1);
                    params.put("massage", str2);
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
            RequestQueue requestQueue = Volley.newRequestQueue(Admin.this);
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



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);



        ExpandableBottomBar eb = findViewById(R.id.expandable_bottom_bar);
        RelativeLayout plus1 = findViewById(R.id.plus1);
        RelativeLayout plus2 = findViewById(R.id.plus2);
        RelativeLayout rl7 = findViewById(R.id.rl7);
        implus = findViewById(R.id.implus);
        implus2 = findViewById(R.id.implus2);


        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate4)
                .onRelativeLayout(rl7)
                .setTransitionDuration(5000)
                .start();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        GetMSG();


        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pos == 0){
                    Intent uou = new Intent(Admin.this, Pers.class);
                    startActivity(uou);
                }else if (pos == 1){
                    new AndExAlertDialog.Builder(Admin.this)
                            .setTitle("پرسنل جدید")
                            .setMessage("نام و نام خانوادگی")
                            .setPositiveBtnText("بعدی")
                            .setNegativeBtnText("انصراف")
                            .setCancelableOnTouchOutside(true)
    .setFont(Font.IRAN_SANS)
                            .setEditText(true, false, "Name", InputType.TEXT_MULTI_LINE)
                            .OnPositiveClicked(new AndExAlertDialogListener() {
                                @Override
                                public void OnClick(String input) {
                                    nameUser = input;

                                    new AndExAlertDialog.Builder(Admin.this)
                                            .setTitle("پرسنل جدید")
                                            .setMessage("نام کاربری")
                                            .setPositiveBtnText("بعدی")
                                            .setNegativeBtnText("انصراف")
                                            .setCancelableOnTouchOutside(true)
                                            .setFont(Font.IRAN_SANS)
                                            .setEditText(true, false, "Username", InputType.TEXT_MULTI_LINE)
                                            .OnPositiveClicked(new AndExAlertDialogListener() {
                                                @Override
                                                public void OnClick(String input) {
                                                    UserUser = input;

                                                    new AndExAlertDialog.Builder(Admin.this)
                                                            .setTitle("پرسنل جدید")
                                                            .setMessage("رمز")
                                                            .setPositiveBtnText("ثبت")
                                                            .setNegativeBtnText("انصراف")
                                                            .setCancelableOnTouchOutside(true)
                                                            .setFont(Font.IRAN_SANS)
                                                            .setEditText(true, false, "Password", InputType.PASSWORD)
                                                            .OnPositiveClicked(new AndExAlertDialogListener() {
                                                                @Override
                                                                public void OnClick(String input) {
                                                                    PassUser = input;
                                                                    Log.e("  AndExAlertDialog 3", input);

                                                                    User(UserUser,PassUser,nameUser);

                                                                }
                                                            })
                                                            .OnNegativeClicked(new AndExAlertDialogListener() {
                                                                @Override
                                                                public void OnClick(String input) {

                                                                }
                                                            })
                                                            .build();

                                                    Log.e("  AndExAlertDialog 2", input);
                                                }
                                            })
                                            .OnNegativeClicked(new AndExAlertDialogListener() {
                                                @Override
                                                public void OnClick(String input) {

                                                }
                                            })
                                            .build();

                                    Log.e("  AndExAlertDialog 1", input);
                                }
                            })
                            .OnNegativeClicked(new AndExAlertDialogListener() {
                                @Override
                                public void OnClick(String input) {

                                }
                            })
                            .build();
                }else if (pos == 2){
                    new AndExAlertDialog.Builder(Admin.this)
                            .setTitle("بازار جدید")
                            .setMessage("نام بازار وارد کنید")
                            .setPositiveBtnText("ثبت")
                            .setNegativeBtnText("انصراف")
                            .setCancelableOnTouchOutside(true)
                            .setFont(Font.IRAN_SANS)
                            .setEditText(true, false, "Name", InputType.TEXT_SINGLE_LINE)
                            .OnPositiveClicked(new AndExAlertDialogListener() {
                                @Override
                                public void OnClick(String input) {
                                    Log.e("  AndExAlertDialog 3", input);

                                    Market(input);

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
        });


        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createExcelSheet();
            }
        });


        eb.setOnItemSelectedListener(new Function3<View, MenuItem, Boolean, Unit>() {
            @Override
            public Unit invoke(View view, MenuItem menuItem, Boolean aBoolean) {

                switch (menuItem.getId()) {
                    case R.id.home2:

                        pos = 0;
                        implus.setImageResource(R.drawable.fifth_bg2);
                        implus2.setImageResource(R.drawable.fifth_bg2);

                        GetBuyer();

                        /**DataTable dataTable = findViewById(R.id.data_table);

                        Typeface tf = Typeface.createFromAsset(getAssets(),"iran_sans.ttf");
                        DataTableHeader header = new DataTableHeader.Builder()
                                .item("نام مشتری", 3)
                                .item("شماره مشتری", 3)
                                .item("ثبت کننده", 2)
                                .item("بازار", 2)
                                .item("تاریخ", 2)
                                .build();

                        ArrayList<DataTableRow> rows = new ArrayList<>();
                        // define 200 fake rows for table
                        for(int i=0;i<200;i++) {
                            Random r = new Random();
                            int random = r.nextInt(i+1);
                            int randomDiscount = r.nextInt(20);
                            DataTableRow row = new DataTableRow.Builder()
                                    .value("اریا غلامی" + i)
                                    .value(String.valueOf(random*90854721))
                                    .value(" فرهاد اصلانی" + i)
                                    .value(" دیجی کالا " + i)
                                    .value("1400/1/" + i)
                                    .build();
                            rows.add(row);
                        }

                        dataTable.setTypeface(tf);
                        dataTable.setHeader(header);
                        dataTable.setRows(rows);
                        dataTable.inflate(Admin.this);*/

                        Log.e("  aBoolean ", "1");
                        break;
                    case R.id.settings2:

                        pos = 1;
                        implus.setImageResource(R.drawable.forth_bg4);
                        implus2.setImageResource(R.drawable.forth_bg4);

                        GetUser();

                        /**DataTable dataTable2 = findViewById(R.id.data_table);

                        Typeface tf2 = Typeface.createFromAsset(getAssets(),"iran_sans.ttf");
                        DataTableHeader header2 = new DataTableHeader.Builder()
                                .item("Username", 2)
                                .item("Password", 2)
                                .item("نام", 3)
                                .item("امروز", 1)
                                .item("کل", 1)
                                .build();

                        ArrayList<DataTableRow> rows2 = new ArrayList<>();
                        // define 200 fake rows for table
                        for(int i=0;i<200;i++) {
                            Random r = new Random();
                            int random = r.nextInt(i+1);
                            int randomDiscount = r.nextInt(20);
                            DataTableRow row = new DataTableRow.Builder()
                                    .value("alireza" + i)
                                    .value("1234" + i)
                                    .value("علی انصاری" + i)
                                    .value(String.valueOf(random))
                                    .value(String.valueOf(randomDiscount))
                                    .build();
                            rows2.add(row);
                        }

                        dataTable2.setTypeface(tf2);
                        dataTable2.setHeader(header2);
                        dataTable2.setRows(rows2);
                        dataTable2.inflate(Admin.this);*/


                        Log.e("  aBoolean ", "2");
                        break;
                         case R.id.bookmarks2:

                             pos = 2;
                             implus.setImageResource(R.drawable.original_state3);
                             implus2.setImageResource(R.drawable.original_state3);

                             GetMarket();

                             /**DataTable dataTable3 = findViewById(R.id.data_table);

                             Typeface tf3 = Typeface.createFromAsset(getAssets(),"iran_sans.ttf");
                             DataTableHeader header3 = new DataTableHeader.Builder()
                                     .item("نام بازار", 3)
                                     .item("فروش", 3)
                                     .build();

                             ArrayList<DataTableRow> rows3 = new ArrayList<>();
                             // define 200 fake rows for table
                             for(int i=0;i<200;i++) {
                                 Random r = new Random();
                                 int random = r.nextInt(i+1);
                                 int randomDiscount = r.nextInt(20);
                                 DataTableRow row = new DataTableRow.Builder()
                                         .value("صادقیه" + i)
                                         .value(String.valueOf(random*8))
                                         .build();
                                 rows3.add(row);
                             }

                             dataTable3.setTypeface(tf3);
                             dataTable3.setHeader(header3);
                             dataTable3.setRows(rows3);
                             dataTable3.inflate(Admin.this);*/


                             Log.e("  aBoolean ", "3");
                             break;
                    case R.id.massage2:

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("msg", msg);
                        clipboard.setPrimaryClip(clip);
                        new AndExAlertDialog.Builder(Admin.this)
                                .setTitle("تغییر متن پیامک")
                                .setMessage("متن پیامک : ** " + msg + " ** ")
                                .setPositiveBtnText("ثبت")
                                .setNegativeBtnText("انصراف")
                                .setCancelableOnTouchOutside(true)
                                .setFont(Font.IRAN_SANS)
                                .setEditText(true, false, "متن پیامک جدید", InputType.TEXT_MULTI_LINE)
                                .OnPositiveClicked(new AndExAlertDialogListener() {
                                    @Override
                                    public void OnClick(String input) {
                                        Log.e("  AndExAlertDialog 3", input);

                                        MSG("1",input);

                                    }
                                })
                                .OnNegativeClicked(new AndExAlertDialogListener() {
                                    @Override
                                    public void OnClick(String input) {

                                    }
                                })
                                .build();


                        Log.e("  aBoolean ", "4");
                             break;
                    }return null;
            }
        });

        eb.setOnItemReselectedListener(new Function3<View, MenuItem, Boolean, Unit>() {
            @Override
            public Unit invoke(View view, MenuItem menuItem, Boolean aBoolean) {

                return null;
            }
        });


        GetBuyer();

    }

    WritableWorkbook workbook;

    void createExcelSheet() {
        File folder = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/Behina");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            // Do something on success

            //File futureStudioIconFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
            String currentDateandTime = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
            //Log.e("  currentTime ", String.valueOf(currentDateandTime));
            String csvFile = currentDateandTime + ".xls";
            java.io.File futureStudioIconFile = new java.io.File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    + "/Behina/" + csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            try {
                workbook = Workbook.createWorkbook(futureStudioIconFile, wbSettings);

                if (pos == 1){
                    createFirstSheet1(futureStudioIconFile);
                }else if (pos == 0){
                    createFirstSheet2(futureStudioIconFile);
                }else if (pos == 2){
                    createFirstSheet3(futureStudioIconFile);
                }

//            createSecondSheet();
                //closing cursor
                workbook.write();
                workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // Do something else on failure
        }

    }

    void createFirstSheet1(File name) {
        try {
            //Excel sheet name. 0 (number)represents first sheet
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            // column and row title
            sheet.addCell(new Label(0, 0, "نام پرسنل"));
            sheet.addCell(new Label(1, 0, "نام کاربری"));
            sheet.addCell(new Label(2, 0, "رمز"));
            sheet.addCell(new Label(3, 0, "کل کارکرد"));
            sheet.addCell(new Label(4, 0, "کارکرد امروز"));

            for (int i = 0; i < listdata.size(); i++) {
                sheet.addCell(new Label(0, i + 1, listdata.get(i).getInitial()));
                sheet.addCell(new Label(1, i + 1, listdata.get(i).getFirstName()));
                sheet.addCell(new Label(2, i + 1, listdata.get(i).getMiddleName()));
                sheet.addCell(new Label(3, i + 1, listdata.get(i).getLastName()));
                sheet.addCell(new Label(4, i + 1, listdata.get(i).getToday()));
            }
            share(name, getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void createFirstSheet2(File name) {
        try {
            //Excel sheet name. 0 (number)represents first sheet
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            // column and row title
            sheet.addCell(new Label(0, 0, "نام مشتری"));
            sheet.addCell(new Label(1, 0, "شماره مشتری"));
            sheet.addCell(new Label(2, 0, "ثبت کننده"));
            sheet.addCell(new Label(3, 0, "بازار"));
            sheet.addCell(new Label(4, 0, "تاریخ"));

            for (int i = 0; i < listdata2.size(); i++) {
                sheet.addCell(new Label(0, i + 1, listdata2.get(i).getInitial()));
                sheet.addCell(new Label(1, i + 1, listdata2.get(i).getFirstName()));
                sheet.addCell(new Label(2, i + 1, listdata2.get(i).getMiddleName()));
                sheet.addCell(new Label(3, i + 1, listdata2.get(i).getLastName()));
                sheet.addCell(new Label(4, i + 1, listdata2.get(i).getToday()));
            }
            share(name, getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void createFirstSheet3(File name) {
        try {
            //Excel sheet name. 0 (number)represents first sheet
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            // column and row title
            sheet.addCell(new Label(0, 0, "نام بازار"));
            sheet.addCell(new Label(1, 0, "کل کارکرد بازار"));
            sheet.addCell(new Label(2, 0, "اخرین ثبت بازار"));

            for (int i = 0; i < listdata3.size(); i++) {
                sheet.addCell(new Label(0, i + 1, listdata3.get(i).getInitial()));
                sheet.addCell(new Label(1, i + 1, listdata3.get(i).getFirstName()));
                sheet.addCell(new Label(2, i + 1, listdata3.get(i).getLastTime()));
            }
            share(name, getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void share(File fileName, Context context) {

        if(fileName.exists()) {
            Log.e("  yes ", "3");
            Uri fileUri = Uri.parse(fileName.getAbsolutePath());
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
            shareIntent.setType("application/octet-stream");
            startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.app_name)));
        }else {

            Log.e("  no ", "3");
        }

    }


}
