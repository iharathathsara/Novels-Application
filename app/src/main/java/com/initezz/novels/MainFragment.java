package com.initezz.novels;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.initezz.novels.adapter.NovelListAdapter;
import com.initezz.novels.api.MyApiCall;
import com.initezz.novels.api.RetrofitClient;
import com.initezz.novels.listner.NovelSelectListner;
import com.initezz.novels.model.Items;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends Fragment implements NovelSelectListner {
    View view;
    private MyApiCall myApiCall;
    private RecyclerView recyclerView;
    private ArrayList<Items> items;
    private String BaseUrl = "http://192.168.8.121//novels/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        items = new ArrayList<>();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BaseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
        Retrofit retrofit = RetrofitClient.getClient();

        myApiCall = retrofit.create(MyApiCall.class);
        Call<ArrayList<Items>> call = myApiCall.getData();

        call.enqueue(new Callback<ArrayList<Items>>() {

            @Override
            public void onResponse(Call<ArrayList<Items>> call, Response<ArrayList<Items>> response) {
                items = response.body();
                NovelListAdapter bookAdapter = new NovelListAdapter(items, getActivity().getApplicationContext(),MainFragment.this);
                GridLayoutManager manager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(bookAdapter);

            }

            @Override
            public void onFailure(Call<ArrayList<Items>> call, Throwable t) {
                t.printStackTrace();
            }

        });

        return view;
    }

    @Override
    public void singleNovelView(Items item) {
        Intent intent=(new Intent(getActivity().getApplicationContext(), NovelPdfActivity.class));
        intent.putExtra("pdfPath",item.getPdfpath());
        intent.putExtra("pdfName",item.getTitle());
        startActivity(intent);
    }
}
