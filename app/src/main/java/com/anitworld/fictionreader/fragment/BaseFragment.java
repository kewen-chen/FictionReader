package com.anitworld.fictionreader.fragment;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.anitworld.fictionreader.activity.ChapterActivity;
import com.anitworld.fictionreader.activity.MainActivity;
import com.anitworld.fictionreader.adapter.FictionAdapter;
import com.anitworld.fictionreader.bean.Fiction;
import com.anitworld.fictionreader.data.Website;
import com.anitworld.fictionreader.util.UiUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vision on 4/25/16.
 */
public class BaseFragment extends Fragment {
    private MainActivity context;
    protected GridView gridFiction;
    private List<Fiction> fictionList = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        gridFiction = (GridView) view.findViewById(R.id.gridFicrion);
        gridFiction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, ChapterActivity.class);
                intent.putExtra("fictionUrl", ((TextView) view.findViewById(R.id.txtFictionUrl)).getText());
                startActivity(intent);
            }
        });
        gridFiction.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                downloadTxt(((TextView) view.findViewById(R.id.txtFictionName)).getText().toString(), ((TextView) view.findViewById(R.id.txtFictionTxtUrl)).getText().toString());
                return true;
            }
        });
        progressDialog = ProgressDialog.show(context, null, "获取小说...");
        return view;
    }

    protected void getFiction(String fictionTypeUrl) {
        Volley.newRequestQueue(context).add(new StringRequest(fictionTypeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                handleFictions(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDialog.dismiss();
                UiUtil.showToast(context, "获取小说失败：" + volleyError);
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

    protected void handleFictions(String html) {
        try {
            Document document = Jsoup.parse(html);
            Elements infos = document.getElementsByClass("info");
            Elements intro = document.getElementsByClass("intro");
            String fictionName = null;
            String fictionImageUrl;
            String fictionAuthor = null;
            String fictionType = null;
            String fictionUrl;
            String fictionTxtUrl;
            String fictionDescription;
            for (int i = 0; i < infos.size(); i++) {
                Element info = infos.get(i);
                Elements title = info.getElementsByClass("title");
                fictionImageUrl = title.select("img").attr("src");
                String[] temp = title.text().split(" ");
                for (int j = 0; j < temp.length; j++) {
                    String txt = temp[j];
                    switch (j) {
                        case 0:
                            fictionName = txt;
                            break;
                        case 1:
                            fictionAuthor = txt;
                            break;
                        case 2:
                            fictionType = txt;
                            break;
                        default:
                            break;
                    }
                }
                fictionDescription = intro.get(i).text();
                Elements hrefs = title.select("a[target=_blank]");
                fictionUrl = hrefs.get(1).attr("href");
                fictionTxtUrl = Website.SERVER + hrefs.get(2).attr("href");
                fictionList.add(new Fiction(fictionName, fictionImageUrl, fictionAuthor, fictionType, fictionDescription, fictionUrl, fictionTxtUrl));
            }
            gridFiction.setAdapter(new FictionAdapter(context, fictionList));
            progressDialog.dismiss();
        } catch (Exception e) {
            progressDialog.dismiss();
            UiUtil.showToast(context, "获取小说失败：" + e);
        }
    }

    protected void downloadTxt(final String fictionName, String fictionTxtUrl) {
        UiUtil.showToast(context, "开始下载" + fictionName);
        Volley.newRequestQueue(context).add(new StringRequest(fictionTxtUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Website.SERVER + Jsoup.parse(s).select("a[rel=\"nofollow\"]").attr("href")));
                request.setTitle(fictionName + "正在下载...");
                request.setDestinationInExternalPublicDir("小说", fictionName + ".txt");
                final long downloadId = downloadManager.enqueue(request);
                context.registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (downloadId == intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)) {
                            UiUtil.showToast(context, fictionName + "下载成功！");
                        }
                    }
                }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                UiUtil.showToast(context, fictionName + "下载失败！");
            }
        }));
    }
}
