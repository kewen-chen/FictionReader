package com.anitworld.fictionreader.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anitworld.fictionreader.R;
import com.anitworld.fictionreader.util.UiUtil;

import org.jsoup.Jsoup;

/**
 * Created by vision on 4/25/16.
 */
public class ChapterDetailActivity extends AppCompatActivity {
    private TextView txtChapterDetail;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_detail);
        initView();
        getChapterDetail(getIntent().getStringExtra("chapterUrl"));
    }

    private void initView() {
        txtChapterDetail = (TextView) findViewById(R.id.txtChapterDetail);
        progressDialog=ProgressDialog.show(this,null,"获取文章内容...");
    }

    private void getChapterDetail(String chapterUrl) {
        Volley.newRequestQueue(this).add(new StringRequest(chapterUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                txtChapterDetail.setText(Jsoup.parse(s).getElementById("text_area").text());
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                UiUtil.showToast(ChapterDetailActivity.this,"获取文章内容失败："+volleyError);
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    return Response.success(new String(response.data, "GBK"), HttpHeaderParser.parseCacheHeaders(response));
                } catch (Exception e) {
                    return Response.error(new ParseError(e));
                }
            }
        });
    }
}
