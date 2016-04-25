package com.anitworld.fictionreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.anitworld.fictionreader.R;
import com.anitworld.fictionreader.bean.Fiction;
import com.anitworld.fictionreader.cache.BitmapCache;

import java.util.List;

/**
 * Created by vision on 4/24/16.
 */
public class FictionAdapter extends BaseAdapter {
    private Context context;
    private List<Fiction> fictionList;
    private ImageLoader imageLoader;

    public FictionAdapter(Context context, List<Fiction> fictionList) {
        this.context = context;
        this.fictionList = fictionList;
        imageLoader = new ImageLoader(Volley.newRequestQueue(context), new BitmapCache());
    }

    @Override
    public int getCount() {
        return fictionList.size();
    }

    @Override
    public Object getItem(int position) {
        return fictionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fiction_list, null);
            holder = new ViewHolder();
            holder.imgFiction = (NetworkImageView) convertView.findViewById(R.id.imgFictionImage);
            holder.txtFictionName = (TextView) convertView.findViewById(R.id.txtFictionName);
            holder.txtFictionAuthor = (TextView) convertView.findViewById(R.id.txtFictionAuthor);
            holder.txtFictionType = (TextView) convertView.findViewById(R.id.txtFictionType);
            holder.txtFictionDescription = (TextView) convertView.findViewById(R.id.txtFictionDescription);
            holder.txtFictionUrl = (TextView) convertView.findViewById(R.id.txtFictionUrl);
            holder.txtFictionImageUrl = (TextView) convertView.findViewById(R.id.txtFictionImageUrl);
            holder.txtFictionTxtUrl = (TextView) convertView.findViewById(R.id.txtFictionTxtUrl);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Fiction fiction = fictionList.get(position);
        holder.imgFiction.setDefaultImageResId(R.mipmap.ic_launcher);
        holder.imgFiction.setErrorImageResId(R.mipmap.ic_launcher);
        holder.imgFiction.setImageUrl(fiction.getFictionImageUrl(), imageLoader);
        holder.txtFictionName.setText(fiction.getFictionName());
        holder.txtFictionAuthor.setText(fiction.getFictionAuthor());
        holder.txtFictionType.setText(fiction.getFictionType());
        holder.txtFictionDescription.setText(fiction.getFictionDescription());
        holder.txtFictionUrl.setText(fiction.getFictionUrl());
        holder.txtFictionImageUrl.setText(fiction.getFictionImageUrl());
        holder.txtFictionTxtUrl.setText(fiction.getFictionTxtUrl());
        return convertView;
    }

    static class ViewHolder {
        NetworkImageView imgFiction;
        TextView txtFictionName;
        TextView txtFictionAuthor;
        TextView txtFictionType;
        TextView txtFictionDescription;
        TextView txtFictionUrl;
        TextView txtFictionImageUrl;
        TextView txtFictionTxtUrl;
    }
}
