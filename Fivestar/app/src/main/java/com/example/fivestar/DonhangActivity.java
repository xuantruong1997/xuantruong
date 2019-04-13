package com.example.fivestar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DonhangActivity extends AppCompatActivity {


    String url = "http://192.168.43.237:8888/GaRanFivestar/laydonhang.php";

    ListView lvDH;
    ArrayList<DonHang>donHangArray;
    DonhangAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donhang);

        lvDH= findViewById(R.id.lvdonhang);
        donHangArray = new ArrayList<>();
        adapter = new DonhangAdapter(DonhangActivity.this,R.layout.donhang_item,donHangArray);
//        donHangArray.add(new DonHang(1,120000,"acb","tn"));

        lvDH.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        laydonhang(url);
        adapter.notifyDataSetChanged();
    }
    private void laydonhang(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(DonhangActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int iddonhang;
                String tensp;
                int tongtien;
                String diachi;



                try {
                    JSONArray array = new JSONArray(response);
                    for (int i=0; i< array.length();i++){
                        JSONObject object = array.getJSONObject(i);

                        iddonhang = Integer.parseInt(object.getString("iddonhang"));
                        tensp = object.getString("sanphamvasoluong");
                        tongtien = Integer.parseInt(object.getString("tongtienhoadon"));
                        diachi = object.getString("diachi");

                        donHangArray.add(new DonHang(iddonhang,tongtien,tensp,diachi));
                    }

                } catch (JSONException e){
                    Toast.makeText(DonhangActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DonhangActivity.this, "Error  "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> donhang = new HashMap<>();
                Intent i = getIntent();
                donhang.put("username", i.getStringExtra("username"));
                return donhang;

            }
        };
        requestQueue.add(stringRequest);
    }
}
