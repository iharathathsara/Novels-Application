package com.initezz.novels.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.initezz.novels.MainFragment;
import com.initezz.novels.MyNovelsFragment;

public class VPAdapter extends FragmentStateAdapter {

    public VPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MainFragment();
            case 1:
                return new MyNovelsFragment();
            default:
                return new MainFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
