package com.initezz.novels.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.initezz.novels.R;
import com.initezz.novels.api.RetrofitClient;
import com.initezz.novels.listner.NovelSelectListner;
import com.initezz.novels.model.Items;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Retrofit;

public class NovelListAdapter extends RecyclerView.Adapter<NovelListAdapter.ViewHolder> {
    private ArrayList<Items> items;
    private Context context;
    private NovelSelectListner novelSelectListner;

    public NovelListAdapter(ArrayList<Items> items, Context context, NovelSelectListner novelSelectListner) {
        this.items = items;
        this.context = context;
        this.novelSelectListner = novelSelectListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.novel_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Items items1 = items.get(position);

        holder.itemTitle.setText(items1.getTitle());

        holder.item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novelSelectListner.singleNovelView(items.get(position));
            }
        });

        String imagePath = RetrofitClient.getBaseUrl()+ items1.getImage();

        Picasso.get()
                .load(imagePath)
                .fit()
                .centerCrop()
                .into(holder.itemImg);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;
        ImageView itemImg;
        CardView item_card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemImg = itemView.findViewById(R.id.itemImg);
            item_card = itemView.findViewById(R.id.item_card);
        }
    }
}
