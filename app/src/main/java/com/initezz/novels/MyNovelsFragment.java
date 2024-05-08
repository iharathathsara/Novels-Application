package com.initezz.novels;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.initezz.novels.adapter.FileAdapter;
import com.initezz.novels.adapter.NovelListAdapter;
import com.initezz.novels.listner.DownloadPdfListner;
import com.initezz.novels.model.Items;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyNovelsFragment extends Fragment implements DownloadPdfListner {

    View view;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_novels, container, false);

        recyclerView = view.findViewById(R.id.myNovelsRecycleView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Snackbar.make(view.findViewById(R.id.container), "Permission needed!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Setting", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                startActivity(intent);
                            }
                        }).show();
            } else {
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/Novels";

                File root = new File(path);
                File[] filesAndFolders = root.listFiles();
//                if(filesAndFolders!=null){
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    recyclerView.setAdapter(new FileAdapter(getActivity().getApplicationContext(), filesAndFolders, this));

//                }


            }
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/Novels";

        File root = new File(path);
        File[] filesAndFolders = root.listFiles();
//        if(filesAndFolders!=null){
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            recyclerView.setAdapter(new FileAdapter(getActivity().getApplicationContext(), filesAndFolders, this));

//        }

    }

    @Override
    public void downlodedNovelView(String path) {
        Intent intent = new Intent(getActivity().getApplicationContext(), DownloadedPdfActivity.class);
        intent.putExtra("path", path);
        startActivity(intent);
    }
}
