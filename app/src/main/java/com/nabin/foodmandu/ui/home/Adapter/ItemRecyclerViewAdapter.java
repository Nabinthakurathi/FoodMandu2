package com.nabin.foodmandu.ui.home.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nabin.foodmandu.R;
import com.nabin.foodmandu.model.Item;
import com.nabin.foodmandu.url.Url;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemRecyclerViewAdapter extends  RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemVeiewHOlder> {

    private Context mContext;
    private List<Item> lsItems;

    public ItemRecyclerViewAdapter(Context mContext, List<Item> lsItems) {
        this.mContext = mContext;
        this.lsItems = lsItems;
    }

    @NonNull
    @Override
    public ItemVeiewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ItemVeiewHOlder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemVeiewHOlder holder, int position) {
       Item item= lsItems.get(position);
        Picasso.get().load(Url.base_url+"/"+item.getImage()).into(holder.imageView2);
        holder.itemname.setText(item.getItemname());
        holder.cateogry.setText(item.getItem_category());
        holder.details.setText(item.getDetail());
        holder.price.setText(item.getPrice()+"");
    }

    @Override
    public int getItemCount() {
        return lsItems.size();
    }

    public class ItemVeiewHOlder extends RecyclerView.ViewHolder {

        private ImageView imageView2;
        private TextView itemname,cateogry,details,price;
        public ItemVeiewHOlder(@NonNull View itemView) {
            super(itemView);
            imageView2= itemView.findViewById(R.id.imageView2item);
            itemname= itemView.findViewById(R.id.textView2item);
            cateogry= itemView.findViewById(R.id.textView3item);
            details= itemView.findViewById(R.id.textView4item);
            price= itemView.findViewById(R.id.textView5price);
        }
    }
}
