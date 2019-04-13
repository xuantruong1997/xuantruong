package com.example.fivestar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    ViewPager viewPager;
//    TabLayout tabLayout;

    public static String username;

    ListView lvsanpham;
    ArrayList<SanPham> arraySanPham;
    ProductAdapter adapter;
    //    String urlAddCart ="http://192.168.43.237/GaRanFivestar/giohang.php";
    String urlGetData = "http://192.168.43.237:8888/GaRanFivestar/getdata.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent i = getIntent();
        username = i.getStringExtra("username");
        lvsanpham = (ListView) findViewById(R.id.lvproduct);
        arraySanPham = new ArrayList<>();

        adapter = new ProductAdapter(this, R.layout.product_item, arraySanPham, getIntent().getExtras().getString("username"));
        lvsanpham.setAdapter(adapter);

        GetData(urlGetData);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(i);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        TextView textView;
        textView = headerView.findViewById(R.id.tvuser);
        final String username = i.getStringExtra("username");
        textView.setText(username);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_giohang) {
            startActivity(new Intent(HomeActivity.this, CartActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_donhang) {
            Intent intent = new Intent(HomeActivity.this, DonhangActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
        if (id == R.id.nav_info) {
            dialogInfo();

        } else if (id == R.id.nav_lienhe) {
            dialogLienhe();

        } else if (id == R.id.nav_changepass) {
            final Dialog dialog = new Dialog(HomeActivity.this);
            dialog.setTitle("Đổi mật khẩu");
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_doimatkhau);
            final EditText edmkcu = dialog.findViewById(R.id.mkcu);
            final EditText edmkmoi = dialog.findViewById(R.id.mkmoi);
            final EditText edcfmkmoi = dialog.findViewById(R.id.cfmkmoi);
            Button btnchange = dialog.findViewById(R.id.change);
            Button btncancle = dialog.findViewById(R.id.cancle);
            btncancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

            btnchange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mkcu = edmkcu.getText().toString().trim();
                    String mkmoi = edmkmoi.getText().toString().trim();
                    String cfmkmoi = edcfmkmoi.getText().toString().trim();

                    if (mkcu.isEmpty() || mkmoi.isEmpty() || cfmkmoi.isEmpty()) {
                        Toast.makeText(HomeActivity.this, "Vui lòng nhâp đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {
                        if (mkmoi.length() < 6) {
                            Toast.makeText(HomeActivity.this, "Nhập mật khẩu mới tối thiểu 6 ký tự", Toast.LENGTH_SHORT).show();
                        } else {
                            if (mkmoi.equals(cfmkmoi) == false) {
                                Toast.makeText(HomeActivity.this, "Mật khẩu mới chưa trùng khớp", Toast.LENGTH_SHORT).show();
                            } else {
                                kiemtramkcu("http://192.168.43.237:8888/GaRanFivestar/kiemtramatkhaucu.php", mkcu, mkmoi);
                            }
                        }
                    }
                }
            });
            dialog.show();
        }

        else if (id == R.id.nav_logout) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
            alert.setTitle("Đăng Xuất");
            alert.setMessage("Bạn có thực sự muốn đăng xuất?");
            alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void dialogLienhe() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Liên hệ");
        builder.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.dialog_lienhe, null);

        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void dialogInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Thông tin ứng dụng");
        builder.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.dialog_info, null);


        TextView tvinfo = view.findViewById(R.id.tvinfo);
        TextView tvphienban = view.findViewById(R.id.tvphienban);
        TextView tvbanquyen = view.findViewById(R.id.tvbanquyen);

        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void GetData(String url) {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        arraySanPham.add(new SanPham(
                                object.getInt("idsp"),
                                object.getString("tensp"),
                                object.getInt("giasp"),
                                object.getString("hinhanh")
                        ));
                    } catch (JSONException e) {
                        Toast.makeText(HomeActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    public void kiemtramkcu(String urlcheck, final String mkcu, final String mkmoi) {
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlcheck, new Response.Listener<String>() {
            public void onResponse(String response) {
                if (response.trim().equals("dung")) {
                    doimk("http://192.168.43.237:8888/GaRanFivestar/thaydoimatkhau.php", mkmoi);
                    //Toast.makeText(UserActivity.this, "Mật khẩu cũ đúng !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, "Mật khẩu cũ chưa đúng ! ", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "Kiểm tra kết nối ! " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("username", username);
                data.put("password", mkcu);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void doimk(String urlchange, final String mkmoi) {
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlchange, new Response.Listener<String>() {
            public void onResponse(String response) {
                if (response.trim().equals("thanhcong")) {
                    Toast.makeText(HomeActivity.this, "Đổi mật khẩu thành công ! ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, "Đổi không thành công " + response.trim(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "Kiểm tra kết nối ! " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("username", username);
                data.put("mkmoi", mkmoi);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}