package com.wenym.grooo.ui.shop;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout;
import com.wenym.grooo.R;
import com.wenym.grooo.databinding.FragmentShoplistBinding;
import com.wenym.grooo.http.NetworkWrapper;
import com.wenym.grooo.model.app.Shop;
import com.wenym.grooo.ui.adapters.ShopViewPagerAdapter;
import com.wenym.grooo.ui.base.BaseFragment;
import com.wenym.grooo.util.AppPreferences;
import com.wenym.grooo.util.RxJava.RxNetWorking;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Observable;
import rx.Subscription;

public class ShopFragment extends BaseFragment<FragmentShoplistBinding> {


    private Observable<List<Shop>> shopObservable;

    private ShopViewPagerAdapter pagerAdapter;

    public static ShopFragment newInstance() {
        ShopFragment shopFragment = new ShopFragment();
        return shopFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shoplist;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shopObservable = NetworkWrapper.get()
                .getAllShop(AppPreferences.get().getProfile().getSchool().getId())
                .compose(RxNetWorking.bindRefreshing(bind.swipeRefreshLayout))
                .compose(bindToLifecycle());
        RxSwipeRefreshLayout.refreshes(bind.swipeRefreshLayout)
                .compose(bindToLifecycle())
                .subscribe(aVoid -> {
                    loadShop();
                });
        pagerAdapter = new ShopViewPagerAdapter(getLayoutInflater(savedInstanceState));
        setUpViewPager();
        loadShop();
    }

    private void setUpViewPager() {
        bind.pager.setAdapter(pagerAdapter);
        bind.pager.setOffscreenPageLimit(2);
        Random random = new Random();
        int color = 0xff000000 | random.nextInt(0x00ffffff);
        bind.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                bind.swipeRefreshLayout.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
            }
        });
        bind.tabs.setupWithViewPager(bind.pager);
    }


    private void loadShop() {
        shopObservable
                .subscribe(shops -> {
                            pagerAdapter.setShops(shops);
                        }
                        , errorHandle("获取所有商家"));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(bind.toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.label_shop_list);
        }
    }

}
