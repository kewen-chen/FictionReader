package com.anitworld.fictionreader.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anitworld.fictionreader.R;
import com.anitworld.fictionreader.bean.Chapter;

import java.util.List;

/**
 * Created by vision on 4/24/16.
 */
public class ChapterAdapter extends BaseAdapter {
    private Context context;
    private List<Chapter> chapterList;

    public ChapterAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @Override
    public int getCount() {
        return chapterList.size();
    }

    @Override
    public Object getItem(int position) {
        return chapterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.chapter_list, null);
            holder = new ViewHolder();
            holder.txtChapterName = (TextView) convertView.findViewById(R.id.txtChapterName);
            holder.txtChapterUrl = (TextView) convertView.findViewById(R.id.txtChapterUrl);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Chapter chapter = chapterList.get(position);
        String chapterName = chapter.getChapterName();
        String chapterUrl = chapter.getChapterUrl();
        //每组之间的分割
        if (TextUtils.isEmpty(chapterName) || TextUtils.isEmpty(chapterUrl)) {
            holder.txtChapterName.setVisibility(View.INVISIBLE);
        } else {
            holder.txtChapterName.setVisibility(View.VISIBLE);
            holder.txtChapterName.setText(chapter.getChapterName());
            holder.txtChapterUrl.setText(chapter.getChapterUrl());
        }
        return convertView;
    }

    static class ViewHolder {
        TextView txtChapterName;
        TextView txtChapterUrl;
    }
}
