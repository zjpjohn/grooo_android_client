package com.wenym.grooo.ui.adapters;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.wenym.grooo.R;
import com.wenym.grooo.databinding.ItemShopListDevBinding;
import com.wenym.grooo.model.app.Shop;
import com.wenym.grooo.ui.shop.ShopActivity;
import com.wenym.grooo.util.SmallTools;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ShopViewHolder> {

    private List<Shop> contents;

    public void setContents(List<Shop> contents) {
        this.contents = contents;
    }

    public ShopListAdapter(List<Shop> contents) {
        this.contents = contents;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shop_list_dev, parent, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShopViewHolder holder, final int position) {
        final Shop shop = contents.get(position);
        holder.devBinding.setShop(shop);
        RxView.clicks(holder.itemView)
                .debounce(200, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    Intent intent = new Intent(holder.itemView.getContext(), ShopActivity.class);
                    intent.putExtra("shop", shop);
                    holder.itemView.getContext().startActivity(intent);
                });
    }

    public static class ShopViewHolder extends RecyclerView.ViewHolder {

        ItemShopListDevBinding devBinding;

        public ShopViewHolder(View itemView) {
            super(itemView);
            devBinding = DataBindingUtil.bind(itemView);
        }
    }
}