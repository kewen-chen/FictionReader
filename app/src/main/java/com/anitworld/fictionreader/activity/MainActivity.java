package com.anitworld.fictionreader.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.anitworld.fictionreader.R;
import com.anitworld.fictionreader.fragment.ChuanyueFragment;
import com.anitworld.fictionreader.fragment.DushiFragment;
import com.anitworld.fictionreader.fragment.KehuanFragment;
import com.anitworld.fictionreader.fragment.LingyiFragment;
import com.anitworld.fictionreader.fragment.TongrenFragment;
import com.anitworld.fictionreader.fragment.WuxiaFragment;
import com.anitworld.fictionreader.fragment.XuanhuanFragment;
import com.anitworld.fictionreader.fragment.YouxiFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FragmentManager fragmentManager;
    private XuanhuanFragment xuanhuanFragment;
    private WuxiaFragment wuxiaFragment;
    private DushiFragment dushiFragment;
    private ChuanyueFragment chuanyueFragment;
    private YouxiFragment youxiFragment;
    private KehuanFragment kehuanFragment;
    private TongrenFragment tongrenFragment;
    private LingyiFragment lingyiFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        xuanhuanFragment = new XuanhuanFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.flContent, xuanhuanFragment).commit();
        findViewById(R.id.txtXuanhuan).setOnClickListener(this);
        findViewById(R.id.txtWuxia).setOnClickListener(this);
        findViewById(R.id.txtDushi).setOnClickListener(this);
        findViewById(R.id.txtChuanyue).setOnClickListener(this);
        findViewById(R.id.txtYouxi).setOnClickListener(this);
        findViewById(R.id.txtKehuan).setOnClickListener(this);
        findViewById(R.id.txtTongren).setOnClickListener(this);
        findViewById(R.id.txtLingyi).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = getVisibleFragment();
        if (fragment != null) {
            transaction.hide(getVisibleFragment());
        }
        switch (v.getId()) {
            case R.id.txtXuanhuan:
                transaction.show(xuanhuanFragment);
                break;
            case R.id.txtWuxia:
                if (wuxiaFragment == null) {
                    wuxiaFragment = new WuxiaFragment();
                    transaction.add(R.id.flContent, wuxiaFragment);
                } else {
                    transaction.show(wuxiaFragment);
                }
                break;
            case R.id.txtDushi:
                if (dushiFragment == null) {
                    dushiFragment = new DushiFragment();
                    transaction.add(R.id.flContent, dushiFragment);
                } else {
                    transaction.show(dushiFragment);
                }
                break;
            case R.id.txtChuanyue:
                if (chuanyueFragment == null) {
                    chuanyueFragment = new ChuanyueFragment();
                    transaction.add(R.id.flContent, chuanyueFragment);
                } else {
                    transaction.show(chuanyueFragment);
                }
                break;
            case R.id.txtYouxi:
                if (youxiFragment == null) {
                    youxiFragment = new YouxiFragment();
                    transaction.add(R.id.flContent, youxiFragment);
                } else {
                    transaction.show(youxiFragment);
                }
                break;
            case R.id.txtKehuan:
                if (kehuanFragment == null) {
                    kehuanFragment = new KehuanFragment();
                    transaction.add(R.id.flContent, kehuanFragment);
                } else {
                    transaction.show(kehuanFragment);
                }
                break;
            case R.id.txtTongren:
                if (tongrenFragment == null) {
                    tongrenFragment = new TongrenFragment();
                    transaction.add(R.id.flContent, tongrenFragment);
                } else {
                    transaction.show(tongrenFragment);
                }
                break;
            case R.id.txtLingyi:
                if (lingyiFragment == null) {
                    lingyiFragment = new LingyiFragment();
                    transaction.add(R.id.flContent, lingyiFragment);
                } else {
                    transaction.show(lingyiFragment);
                }
                break;
            default:
                break;

        }
        transaction.commit();
    }

    private Fragment getVisibleFragment() {
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible()) {
                return fragment;
            }
        }
        return null;
    }
}
