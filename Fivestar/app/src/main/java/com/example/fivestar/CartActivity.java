package com.example.fivestar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
//    RequestQueue requestQueue;
    String url = "http://192.168.43.237:8888/GaRanFivestar/login.php";
    String iduser="";

    HomeActivity username;
    GioHang gioHang;

    ListView lvgiohang;
    Button btnmuathem, btnthanhtoan;
    TextView tvtongtien;
    String urlLayGioHang = "http://192.168.43.237:8888/GaRanFivestar/getcart.php";
    ArrayList<GioHang> arrayGioHang;
    GioHangAdapter adapter;
    public static Integer tongtienhoadon=0;
    public static String sanphamvasoluong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        //getiduser("http://192.168.43.237/GaRanFivestar/getiduser.php");

        lvgiohang = findViewById(R.id.lvcart);
        //tvtongtien = findViewById(R.id.tvtongtien);
        btnmuathem = findViewById(R.id.btnmuathem);
        btnthanhtoan = findViewById(R.id.btnthanhtoan);

        arrayGioHang = new ArrayList<>();
        adapter = new GioHangAdapter(CartActivity.this, R.layout.giohang_item, arrayGioHang);

        lvgiohang.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        layGioHang(urlLayGioHang);
        adapter.notifyDataSetChanged();

        btnmuathem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
                KiemtraGioHangCoSPChua("http://192.168.43.237:8888/GaRanFivestar/ktragiohangcospchua.php");

            }
        });

    }

    public  void KiemtraGioHangCoSPChua(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                if (response.trim().equals("ok")) {
                    final Dialog dialog = new Dialog(CartActivity.this);
                    dialog.setTitle("Địa chỉ giao hàng");
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.thongtindathang);
                    final EditText edsonha, edto_duong, edsdt;
                    final Spinner spnxaphuong;
                    Button btnOK, btnHuy;
                    edto_duong = dialog.findViewById(R.id.to_duong);
                    edsonha = dialog.findViewById(R.id.sonha);
                    edsdt = dialog.findViewById(R.id.sdt);
                    spnxaphuong = dialog.findViewById(R.id.spnXaPhuong);
                    btnHuy = dialog.findViewById(R.id.huydon);
                    btnOK = dialog.findViewById(R.id.datdonhang);
                    ArrayList<String> arrayxaphuong = new ArrayList<String>();
                    arrayxaphuong.add("P. Cam Giá"); arrayxaphuong.add("P. Chùa Hang");
                    arrayxaphuong.add("P. Đồng Bẩm");  arrayxaphuong.add("P. Đồng Quang");   arrayxaphuong.add("P. Gia Sàng");
                    arrayxaphuong.add("P. Hoàng Văn Thụ");  arrayxaphuong.add("P. Hương Sơn");   arrayxaphuong.add("P. Phan Đình Phùng");
                    arrayxaphuong.add("P. Phú Xá");  arrayxaphuong.add("P. Quán Triều");   arrayxaphuong.add("P. Quang Trung");
                    arrayxaphuong.add("P. Quang Vinh");  arrayxaphuong.add("P. Tân Lập");   arrayxaphuong.add("P. Tân Long");
                    arrayxaphuong.add("P. Tân Thành");  arrayxaphuong.add("P. Tân Thịnh");   arrayxaphuong.add("P. Thịnh Đán");
                    arrayxaphuong.add("P. Tích Lương");  arrayxaphuong.add("P. Trung Thành");   arrayxaphuong.add("P. Trưng Vương");
                    arrayxaphuong.add("P. Túc Duyên");  arrayxaphuong.add("Xã Cao Ngạn");   arrayxaphuong.add("Xã Đồng Liên");
                    arrayxaphuong.add("Xã Huống Thượng");  arrayxaphuong.add("Xã Linh Sơn");   arrayxaphuong.add("Xã Phúc Hà");
                    arrayxaphuong.add("Xã Phúc Trìu");  arrayxaphuong.add("Xã Phúc Xuân");   arrayxaphuong.add("Xã Quyết Thắng");
                    arrayxaphuong.add("Xã Sơn Cẩm");  arrayxaphuong.add("Xã Tân Cương");   arrayxaphuong.add("Xã Thịnh Đức");

                    ArrayAdapter arrayAdapter = new ArrayAdapter(CartActivity.this, android.R.layout.simple_spinner_item, arrayxaphuong);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnxaphuong.setAdapter(arrayAdapter);

                    btnHuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String sonha = edsonha.getText().toString().trim();
                            String to_duong = edto_duong.getText().toString().trim();
                            String xaphuong = spnxaphuong.getSelectedItem().toString();
                            String sdt1 = edsdt.getText().toString();

                            if (sonha.isEmpty() || to_duong.isEmpty() || sdt1.isEmpty()){
                                Toast.makeText(CartActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_LONG).show();
                            }
                            else {
                                String sdt = "0"+ edsdt.getText().toString();
                                String dc = "Số nhà: "+sonha+" - (Tổ / Đường): "+ to_duong + " - "+ xaphuong + "- TP. Thái Nguyên";
                                dathang(urlLayGioHang, dc, sdt);
                                adapter.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        }
                    });
                    dialog.show();
                }else {
                    Toast.makeText(CartActivity.this, "Chưa có sản phẩm để đặt mua!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CartActivity.this, "Đăng nhập thất bại" +error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> data=  new HashMap<>();

                data.put("username",username.username);
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void dathang(String urlLayGioHang, final String diachi, final String sdt){
            RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlLayGioHang, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //lay thong tin don hang
                    try {
                        JSONArray array = new JSONArray(response);
                        Integer tongtiengiohang = 0;
                        String tensanphamvasl = "";
                        Integer sol, dongia;
                        for (int j=0 ; j< array.length(); j++){
                            JSONObject object = array.getJSONObject(j);
                            sol = object.getInt("sl");
                            dongia = object.getInt("giasp");
                            tongtiengiohang = tongtiengiohang + (sol*dongia);
                            tensanphamvasl =tensanphamvasl + object.getString("tensp") + " (" + String.valueOf(object.getInt("sl")) +"),  ";
                        }
                        tongtienhoadon = tongtiengiohang;
                        sanphamvasoluong = tensanphamvasl;

                        AlertDialog.Builder dialog = new AlertDialog.Builder(CartActivity.this);
                        dialog.setTitle("Đặt hàng");
                        dialog.setCancelable(false); //khong click ra ngoai dialog
                        dialog.setMessage("Đơn hàng của bạn có giá : "+tongtiengiohang+" VNĐ. Nhấn Đặt Hàng để xác nhận");
                        dialog.setPositiveButton("Đặt Hàng", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //them don hang vao csdl
                                themdonhang("http://192.168.43.237:8888/GaRanFivestar/dathang.php", sanphamvasoluong, tongtienhoadon, diachi, sdt);
                                dialog.cancel();
                            }
                        });

                        dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        dialog.show();

                    } catch (JSONException e){
                        Toast.makeText(CartActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    //adapter.notifyDataSetChanged();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CartActivity.this, "Error  "+error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> giohang = new HashMap<>();
                    giohang.put("iduser", username.username);

                    return giohang;

                }
            };
            requestQueue.add(stringRequest);
    }

    public  void themdonhang(String url2, final String spvasl, final int tong, final String diachi, final String sdt){
        RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url2, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response.trim().equals("thanhcong")) {
                    Toast.makeText(CartActivity.this, "Cảm ơn bạn đã đặt hàng ", Toast.LENGTH_SHORT).show();
                    huygiohang("http://192.168.43.237:8888/GaRanFivestar/huygiohang.php");

                } else {
                    Toast.makeText(CartActivity.this, "Đặt hàng chưa thành công"+response.trim(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CartActivity.this, "Kiểm tra kết nối" +error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {

                String ngaydat;
                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf = null;
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                ngaydat = sdf.format(now.getTime()).toString().trim();

                Map<String, String> data=  new HashMap<>();
                    data.put("username",username.username);
                    data.put("spvasl", spvasl.toString());
                    data.put("tongtien", String.valueOf(tong));
                    data.put("ngaydat", ngaydat);
                    data.put("diachi", diachi);
                    data.put("sodienthoai", sdt);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void huygiohang(String url4){
        RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url4, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response.trim().equals("daxoa")) {
                    recreate();
                } else {
                    Toast.makeText(CartActivity.this, response.trim(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CartActivity.this, "Kiểm tra kết nối" +error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> data=  new HashMap<>();
                data.put("username",username.username);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    private  void layGioHang(String urlLayGioHang){
        RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlLayGioHang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String tensp;
                int giasp;
                int soluongsp;
                String hinhanh;
                int idsp;
                int idgiohang;

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i=0; i< array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        tensp = object.getString("tensp");
                        giasp = Integer.parseInt(object.getString("giasp"));
                        idsp = Integer.parseInt(object.getString("idsp"));
                        idgiohang = Integer.parseInt(object.getString("idgiohang"));
                        hinhanh = object.getString("hinhanh");
                        soluongsp= Integer.parseInt(object.getString("sl"));

                        arrayGioHang.add(new GioHang(idgiohang, idsp, soluongsp, tensp, hinhanh, giasp));
                    }
                    Integer tongtiengiohang = 0;
                    for (int j=0 ; j< array.length(); j++){
                        JSONObject object = array.getJSONObject(j);
                        int sol = object.getInt("sl");
                        int dongia = object.getInt("giasp");
                        tongtiengiohang = tongtiengiohang + (sol*dongia);
                    }
                    //tvtongtien.setText(((Integer) tongtiengiohang).toString() +" VND");

                } catch (JSONException e){
                    Toast.makeText(CartActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CartActivity.this, "Error  "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> giohang = new HashMap<>();
                giohang.put("iduser", username.username);

                return giohang;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void xoacart(final int idsp){
        RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, "http://192.168.43.237:8888/GaRanFivestar/deletecart.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                recreate();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CartActivity.this, error.toString().trim(), Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=  new HashMap<>();
                data.put("idsp", String.valueOf(idsp));
                data.put("username",username.username) ;

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }


//    public void getiduser(String url){
//        RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
//        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//            iduser=response.toString();
//            giohang("http://192.168.43.237/GaRanFivestar/giohang.php");
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }){
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> data=  new HashMap<>();
//                data.put("username",getIntent().getExtras().getString("username"));
//
//                return data;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }


}
