package com.example.fivestar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fivestar.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText edusername,edpass, repass;
    Button btnlogin,btnregister;
    RequestQueue requestQueue;
    String url = "http://192.168.43.237:8888/GaRanFivestar/register.php";
    String urlcapgh = "http://192.168.43.237:8888/GaRanFivestar/capgiohang.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        anhxa();
        init();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
    public void init(){

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edusername.getText().toString();
                String password = edpass.getText().toString();
                String cfpass = repass.getText().toString();
                if (username.isEmpty() || password.isEmpty() || cfpass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin đăng kí", Toast.LENGTH_SHORT).show();
                }else{
                    if (password.length() <6 || password.length() >12){
                        Toast.makeText(RegisterActivity.this, "Nhập mật khẩu trong khoảng từ 6-12 kí tự", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (password.equals(cfpass)== false){
                            Toast.makeText(RegisterActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            register(url);
                        }
                    }

                }
            }
        });
    }

    public void register(String url){
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response.trim().equals("ok")) {
                    Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    capgiohang(urlcapgh);
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Kiểm tra kết nối mạng" +error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=  new HashMap<>();

                data.put("username",edusername.getText().toString().trim());
                data.put("password", edpass.getText().toString().trim());

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void capgiohang(String urlcapgh){
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, urlcapgh, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response.trim().equals("ok")) {

//                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                    startActivity(intent);

                } else {
                    Toast.makeText(RegisterActivity.this, "Lỗi "+response.trim(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Đăng ký thất bại" +error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=  new HashMap<>();

                data.put("username",edusername.getText().toString().trim());

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }


    public void anhxa() {
        edusername = findViewById(R.id.edusername);
        edpass = findViewById(R.id.edpass);
        repass = findViewById(R.id.edrepass);
        btnlogin = findViewById(R.id.btnlogin);
        btnregister = findViewById(R.id.btnregister);
        requestQueue = Volley.newRequestQueue(this);
    }
}
