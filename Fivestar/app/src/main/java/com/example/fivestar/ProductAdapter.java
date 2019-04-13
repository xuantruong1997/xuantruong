package com.example.fivestar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends BaseAdapter {
    HomeActivity username1;

    private Context context;
    private int layout;
    private List<SanPham> listSP;
//    int resource;
    String username;
    public ProductAdapter(Context context, int layout, List<SanPham> listSP,String username) {
        this.context = context;
        this.layout = layout;
        this.listSP = listSP;
//        this.resource = resource;
        this.username=username;
    }

    @Override
    public int getCount() {
        return listSP.size();
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
        TextView tvtenmon, tvgiatien;
        ImageView imvmon;
        Button bt;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        final ViewHolder holder;
        if (convertView ==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.tvtenmon     = (TextView)convertView.findViewById(R.id.tvtenmon);
            holder.imvmon       = (ImageView) convertView.findViewById(R.id.imvmon);
            holder.bt=convertView.findViewById(R.id.btnaddcart);
//            Picasso.with(context).load(listSP.getImage

            holder.tvgiatien    = (TextView)convertView.findViewById(R.id.tvgiatien);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }




        final SanPham sanPham = listSP.get(position);
        holder.tvtenmon.setText(sanPham.getTensp());
        holder.tvgiatien.setText(sanPham.getGiasp()+" VNĐ");
        Picasso.with(context).load(sanPham.getHinhanh()).into(holder.imvmon);

        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent t=new Intent(context,CartActivity.class);
//                t.putExtra("sp",sanPham);
//                t.putExtra("username",username);
//                context.startActivity(t);
                giohang("http://192.168.43.237:8888/GaRanFivestar/giohang.php", sanPham.getId());
            }
        });

        return convertView;
    }

    public void giohang(String url, final int id){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Thêm thành công"+response.trim(), Toast.LENGTH_SHORT).show();
                //onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error  "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=  new HashMap<>();
                //SanPham sp=(SanPham) getIntent().getExtras().getSerializable("sp");
                data.put("masp", String.valueOf(id));
                data.put("iduser",username1.username) ;

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}
