package com.anitworld.fictionreader.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anitworld.fictionreader.R;
import com.anitworld.fictionreader.adapter.ChapterAdapter;
import com.anitworld.fictionreader.bean.Chapter;
import com.anitworld.fictionreader.util.UiUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vision on 4/24/16.
 */
public class ChapterActivity extends AppCompatActivity {
    private String fictionUrl;
    private GridView gridChapter;
    private List<Chapter> chapterList = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        initView();
        fictionUrl = getIntent().getStringExtra("fictionUrl");
        getFictionChapter(fictionUrl);
        fictionUrl = fictionUrl.substring(0, fictionUrl.lastIndexOf('/') + 1);
    }

    private void initView() {
        gridChapter = (GridView) findViewById(R.id.gridChapter);
        gridChapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChapterActivity.this, ChapterDetailActivity.class);
                intent.putExtra("chapterUrl", ((TextView) view.findViewById(R.id.txtChapterUrl)).getText());
                startActivity(intent);
            }
        });
        progressDialog = ProgressDialog.show(this, null, "章节加载中...");
    }

    private void getFictionChapter(String fictionUrl) {
        Volley.newRequestQueue(this).add(new StringRequest(fictionUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                handleFictionChapter(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                UiUtil.showToast(ChapterActivity.this, "获取章节失败：" + volleyError);
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

    private void handleFictionChapter(String html) {
        try {
            Document document = Jsoup.parse(html);
            Elements chapters = document.getElementsByClass("chapter_list_chapter");
            int temp = 1;
            for (int i = 1; i < chapters.size() + 1; i++) {
                try {
                    Elements chapter = chapters.get(i - 1).select("a");
                    chapterList.add(new Chapter(chapter.get(0).text(), fictionUrl + chapter.get(0).attr("href")));
                    chapterList.add(new Chapter(chapter.get(1).text(), fictionUrl + chapter.get(1).attr("href")));
                } catch (Exception e) {
                }
                //每组之间的分割
                if (temp++ % 4 == 0) {
                    chapterList.add(new Chapter(null, null));
                    chapterList.add(new Chapter(null, null));
                }
            }
            gridChapter.setAdapter(new ChapterAdapter(this, chapterList));
            progressDialog.dismiss();
        } catch (Exception e) {
            progressDialog.dismiss();
            UiUtil.showToast(ChapterActivity.this, "处理章节失败：" + e);
        }
    }
}
