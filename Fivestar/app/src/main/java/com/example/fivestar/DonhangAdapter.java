package com.example.fivestar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DonhangAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    List<DonHang> donHangList;

    public DonhangAdapter(Context context, int layout, List<DonHang> donHangList) {
        this.context = context;
        this.layout = layout;
        this.donHangList = donHangList;
    }

    @Override
    public int getCount() {
        return donHangList.size();
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
        TextView tvmadonhang, tvgiadonhang,tvmondat,tvdiachi;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null){
            viewHolder= new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            viewHolder.tvmadonhang = convertView.findViewById(R.id.tvmadonhang);
            viewHolder.tvgiadonhang = convertView.findViewById(R.id.tvgiadonhang);
            viewHolder.tvmondat = convertView.findViewById(R.id.tvmondat);
            viewHolder.tvdiachi = convertView.findViewById(R.id.tvdiachi);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        final DonHang donHang =donHangList.get(position);
        viewHolder.tvmadonhang.setText(String.valueOf(donHang.getIddonhang()));
        viewHolder.tvgiadonhang.setText(String.valueOf(donHang.getTongtien()+" VNĐ"));
        viewHolder.tvmondat.setText(String.valueOf(donHang.getTensp()));
        viewHolder.tvdiachi.setText(String.valueOf(donHang.getDiachi()));


        return convertView;
    }
}
