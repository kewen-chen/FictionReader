package com.anitworld.fictionreader.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.anitworld.fictionreader.data.Website;

/**
 * Created by vision on 4/25/16.
 */
public class TongrenFragment extends BaseFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFiction(Website.TONG_REN);
    }
}
