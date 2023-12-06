package com.jnu.student;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class MeFragment extends Fragment {
    public MeFragment() {
        // Required empty public constructor
    }
    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }
}
