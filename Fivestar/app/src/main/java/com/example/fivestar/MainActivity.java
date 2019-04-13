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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {



    EditText edusername,edpass;
    Button btnlogin,btnregister;
    RequestQueue requestQueue;
    String url = "http://192.168.43.237:8888/GaRanFivestar/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhxa();
        init();

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

    }
    public void init(){

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edusername.getText().toString();
                String password = edpass.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();


                } else {
                    login(url);
                }

            }
        });
    }

    public void login(String url){
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                if (response.trim().equals("ok")) {
//                    Toast.makeText(MainActivity.this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("username", edusername.getText().toString().trim());
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "Tên đăng nhập hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Đăng nhập thất bại" +error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> data=  new HashMap<>();

                data.put("username",edusername.getText().toString().trim());
                data.put("password", edpass.getText().toString().trim());

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }


    public void anhxa(){
        edusername = findViewById(R.id.edusername);
        edpass = findViewById(R.id.edpass);
        btnlogin = findViewById(R.id.btnlogin);
        btnregister =  findViewById(R.id.btnregister);
        requestQueue = Volley.newRequestQueue(this);
    }
}
