package com.initezz.novels.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.initezz.novels.R;
import com.initezz.novels.listner.DownloadPdfListner;

import java.io.File;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.AdapterViewholder> {

    Context context;
    File[] fileAndFolders;
    private DownloadPdfListner downloadPdfListner;
    public FileAdapter(Context context, File[] fileAndFolders,DownloadPdfListner downloadPdfListner) {
        this.context = context;
        this.fileAndFolders = fileAndFolders;
        this.downloadPdfListner = downloadPdfListner;
    }

    @NonNull
    @Override
    public AdapterViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.my_novels_list,parent,false);
        return new AdapterViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewholder holder, int position) {
        File selectedFile=fileAndFolders[position];
        holder.fileName.setText(selectedFile.getName());
        holder.downNovelsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPdfListner.downlodedNovelView(selectedFile.getAbsolutePath());
            }
        });
        holder.downNovelsCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,v);
                popupMenu.getMenu().add("DELETE");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){
                            boolean deleted=selectedFile.delete();
                            if(deleted){
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                v.setVisibility(View.GONE);
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileAndFolders.length;
    }

    static class AdapterViewholder extends RecyclerView.ViewHolder{

        TextView fileName;
        CardView downNovelsCard;
        public AdapterViewholder(@NonNull View itemView){
            super(itemView);

            fileName = itemView.findViewById(R.id.fileName);
            downNovelsCard=itemView.findViewById(R.id.downNovelsCard);
        }
    }


}