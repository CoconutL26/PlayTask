package com.jnu.student;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class TaskFragment extends Fragment {
    public TaskFragment() {
        // Required empty public constructor
    }
    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
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
