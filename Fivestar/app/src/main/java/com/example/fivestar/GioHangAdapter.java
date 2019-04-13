package com.example.fivestar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.fivestar.HomeActivity.username;


public class GioHangAdapter extends BaseAdapter {

    CartActivity context;
    int layout;
    ArrayList<GioHang> gioHang;
    HomeActivity homeActivity;
    CartActivity cartActivity;

    public GioHangAdapter(CartActivity context, int layout, ArrayList<GioHang> gioHang) {
        this.context = context;
        this.layout = layout;
        this.gioHang = gioHang;
    }

    @Override
    public int getCount() {
        return gioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView hinhanh;
        TextView tensp, giasp, soluong, idgiohang, idsp, thanhtien;
        Button  btnxoa, btntang, btngiam;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GioHangAdapter.ViewHolder viewHolder;
        if (convertView== null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.tensp = convertView.findViewById(R.id.tvtensp);
            viewHolder.giasp = convertView.findViewById(R.id.tvgiasp);
            viewHolder.thanhtien = convertView.findViewById(R.id.thanhtien);
            viewHolder.soluong = convertView.findViewById(R.id.tvsoluong);
            viewHolder.hinhanh = convertView.findViewById(R.id.imvsp);
            viewHolder.btnxoa = convertView.findViewById(R.id.btndelProduct);
            viewHolder.btntang = convertView.findViewById(R.id.btncong);
            viewHolder.btngiam = convertView.findViewById(R.id.btntru);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder =(ViewHolder) convertView.getTag();
        }

        final  GioHang gioHangs = gioHang.get(position);
        viewHolder.tensp.setText(gioHangs.getTensp());
        viewHolder.giasp.setText(Integer.toString(gioHangs.getGiasp()));
        viewHolder.soluong.setText(Integer.toString(gioHangs.getSl()));
        viewHolder.thanhtien.setText(Integer.toString(gioHangs.getSl()*gioHangs.getGiasp()));
        Picasso.with(context).load(gioHangs.getHinhanh()).into(viewHolder.hinhanh);

        viewHolder.btntang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String slsp = viewHolder.soluong.getText().toString();
                    Integer sol = Integer.valueOf(slsp) + 1;
//                Toast.makeText(context, sol.toString(), Toast.LENGTH_LONG).show();
                tangsl("http://192.168.43.237:8888/GaRanFivestar/tangsl.php", gioHangs.getIdsp());
                    viewHolder.soluong.setText(Integer.toString(sol));
                viewHolder.thanhtien.setText(Integer.toString(sol*gioHangs.getGiasp()));

                }
        });

        viewHolder.btngiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String slsp = viewHolder.soluong.getText().toString();
                if (Integer.valueOf(slsp)<=1){
                    viewHolder.soluong.setText("1");
//                    viewHolder.thanhtien.setText("0");
                }
                else {
                    Integer sol = Integer.valueOf(slsp)-1;
    //                Toast.makeText(context, sol.toString(), Toast.LENGTH_LONG).show();

                    giamsl("http://192.168.43.237:8888/GaRanFivestar/giamsl.php", gioHangs.getIdsp());
                    viewHolder.soluong.setText(Integer.toString(sol));
                    viewHolder.thanhtien.setText(Integer.toString(sol*gioHangs.getGiasp()));
                }
            }
        });
        viewHolder.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent xoakhoanthu = new Intent(context, CartActivity.class);
//                xoakhoanthu.putExtra("dulieu", gioHangs);
//                context.startActivity(xoakhoanthu);
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Bạn có chắc muốn xóa sản phẩm này ? ");
                dialog.setCancelable(false); //khong click ra ngoai dialog
                //dialog.setMessage("Nhấn Đặt Hàng để xác nhận");
                dialog.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.xoacart(gioHangs.getIdsp());
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

                //xoacart(gioHangs.getIdsp());
            }
        });

        return convertView;
    }

//    public void xoacart(final int idsp){
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, "http://192.168.43.237/GaRanFivestar/deletecart.php", new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                //Toast.makeText(context, response.trim(), Toast.LENGTH_LONG).show();
//                //cartActivity.recreate();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, error.toString().trim(), Toast.LENGTH_LONG).show();
//            }
//        }){
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> data=  new HashMap<>();
//                data.put("idsp", String.valueOf(idsp));
//                data.put("username",homeActivity.username) ;
//
//                return data;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

    public  void tangsl(String url, final int idsp){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Toast.makeText(context, response.trim(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString().trim(), Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=  new HashMap<>();
                data.put("masp", String.valueOf(idsp));
                data.put("username",homeActivity.username) ;

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    public  void giamsl(String url1, final int idsp){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Toast.makeText(context, response.trim(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString().trim(), Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=  new HashMap<>();
                data.put("masp", String.valueOf(idsp));
                data.put("username",homeActivity.username) ;

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}
